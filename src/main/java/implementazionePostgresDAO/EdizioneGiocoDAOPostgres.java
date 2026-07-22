package implementazionePostgresDAO;

import dao.EdizioneGiocoDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EdizioneGiocoDAOPostgres implements EdizioneGiocoDAO {

    private Connection connection;

    public EdizioneGiocoDAOPostgres() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<EdizioneGioco> getListaEdizioniSviluppatore(int idSviluppatore) throws SQLException {

        ArrayList<EdizioneGioco> listaEdizioni = new ArrayList<>();

        String query = "SELECT eg.idEdizione, eg.idGioco, eg.nomePiattaforma, eg.prezzo, eg.dataRilascio, " +
                "g.titolo, g.categoria, g.pegi " +
                "FROM edizione_gioco eg " +
                "JOIN gioco g ON eg.idGioco = g.idGioco " +
                "WHERE g.idSviluppatore = ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idSviluppatore);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    int idEdizione = rs.getInt("idEdizione");
                    int prezzo = rs.getInt("prezzo");
                    java.time.LocalDate dataRilascio = rs.getDate("dataRilascio").toLocalDate();
                    String nomePiattaforma = rs.getString("nomePiattaforma");

                    int idGioco = rs.getInt("idGioco");
                    String titolo = rs.getString("titolo");
                    String categoriaString = rs.getString("categoria");
                    int pegi = rs.getInt("pegi");


                    Sviluppatore fintoSviluppatore = new Sviluppatore("Sconosciuto", idSviluppatore, "nessuna", java.time.LocalDate.now(), 0, "", 0);



                    try {
                        PiattaformaDiGioco piattaformaObj = new PiattaformaDiGioco(nomePiattaforma, "Sconosciuto", false);
                        Gioco fintoGioco = new Gioco(fintoSviluppatore, idGioco, titolo, Categoria.valueOf(categoriaString), pegi);

                        EdizioneGioco edizioneCorrente = new EdizioneGioco(idEdizione, fintoGioco, piattaformaObj, prezzo, dataRilascio);

                        listaEdizioni.add(edizioneCorrente);

                    } catch (CampoNonValidoException e) {
                        // Catturiamo l'eccezione lanciata sia da Gioco che da EdizioneGioco
                        System.out.println("Errore DB Corrotto nel caricamento: " + e.getMessage());
                    }
                }
            }
        }

        return listaEdizioni;
    }

    @Override
    public ArrayList<EdizioneGioco> getCatalogoCompleto() throws SQLException {

        ArrayList<EdizioneGioco> catalogo = new ArrayList<>();

        String query = "SELECT eg.idEdizione, eg.idGioco, eg.nomePiattaforma, eg.prezzo, eg.dataRilascio, " +
                "g.titolo, g.categoria, g.pegi, g.idSviluppatore " +
                "FROM edizione_gioco eg " +
                "JOIN gioco g ON eg.idGioco = g.idGioco";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int idEdizione = rs.getInt("idEdizione");
                int prezzo = rs.getInt("prezzo");
                java.time.LocalDate dataRilascio = rs.getDate("dataRilascio").toLocalDate();
                String nomePiattaforma = rs.getString("nomePiattaforma");

                int idGioco = rs.getInt("idGioco");
                String titolo = rs.getString("titolo");
                String categoriaString = rs.getString("categoria");
                int pegi = rs.getInt("pegi");
                int idSviluppatore = rs.getInt("idSviluppatore");

                Sviluppatore fintoSviluppatore = new Sviluppatore(
                        "Sconosciuto",
                        idSviluppatore,
                        "nessuna",
                        java.time.LocalDate.now(),
                        0,
                        "",
                        0
                );

                try {
                    PiattaformaDiGioco piattaformaObj = new PiattaformaDiGioco(nomePiattaforma, "Sconosciuto", false);

                    Gioco fintoGioco = new Gioco(
                            fintoSviluppatore,
                            idGioco,
                            titolo,
                            Categoria.valueOf(categoriaString),
                            pegi
                    );

                    EdizioneGioco edizioneCorrente = new EdizioneGioco(
                            idEdizione,
                            fintoGioco,
                            piattaformaObj,
                            prezzo,
                            dataRilascio
                    );

                    catalogo.add(edizioneCorrente);

                } catch (CampoNonValidoException e) {
                    System.out.println("Errore DB Corrotto nel caricamento del catalogo: " + e.getMessage());
                }
            }
        }

        return catalogo;
    }

    @Override
    public ArrayList<EdizioneGioco> getListaGiochiCarrello(int idUtente) throws SQLException {

        ArrayList<EdizioneGioco> listaCarrello = new ArrayList<>();

        String query = "SELECT eg.idEdizione, eg.idGioco, eg.nomePiattaforma, eg.prezzo, eg.dataRilascio, " +
                "g.titolo, g.categoria, g.pegi, g.idSviluppatore " +
                "FROM carrello c " +
                "JOIN edizione_gioco eg ON c.idEdizione = eg.idEdizione " +
                "JOIN gioco g ON eg.idGioco = g.idGioco " +
                "WHERE c.idUtente = ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUtente);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    int idEdizione = rs.getInt("idEdizione");
                    int prezzo = rs.getInt("prezzo");
                    java.time.LocalDate dataRilascio = rs.getDate("dataRilascio").toLocalDate();
                    String nomePiattaforma = rs.getString("nomePiattaforma");

                    int idGioco = rs.getInt("idGioco");
                    String titolo = rs.getString("titolo");
                    String categoriaString = rs.getString("categoria");
                    int pegi = rs.getInt("pegi");
                    int idSviluppatore = rs.getInt("idSviluppatore");

                    Sviluppatore fintoSviluppatore = new Sviluppatore(
                            "Sconosciuto",
                            idSviluppatore,
                            "nessuna",
                            java.time.LocalDate.now(),
                            0,
                            "",
                            0
                    );

                    try {
                        PiattaformaDiGioco piattaformaObj = new PiattaformaDiGioco(nomePiattaforma, "Sconosciuto", false);

                        Gioco fintoGioco = new Gioco(
                                fintoSviluppatore,
                                idGioco,
                                titolo,
                                Categoria.valueOf(categoriaString),
                                pegi
                        );

                        EdizioneGioco edizioneCorrente = new EdizioneGioco(
                                idEdizione,
                                fintoGioco,
                                piattaformaObj,
                                prezzo,
                                dataRilascio
                        );

                        listaCarrello.add(edizioneCorrente);

                    } catch (CampoNonValidoException e) {
                        System.out.println("Errore DB Corrotto nel caricamento del carrello: " + e.getMessage());
                    }
                }
            }
        }

        return listaCarrello;
    }

    @Override
    public void inserisciEdizione(EdizioneGioco edizioneGioco) throws SQLException {

        String query = "INSERT INTO edizione_gioco (idGioco, nomePiattaforma, prezzo, dataRilascio) VALUES (?, ?, ?, ?)";

        ConnessioneDatabase.getInstance().eseguiUpdate(
                query,
                edizioneGioco.getGioco().getId(),
                edizioneGioco.getPiattaforma().getNome(),
                edizioneGioco.getPrezzo(),
                java.sql.Date.valueOf(edizioneGioco.getDataRilascio())
        );
    }
}
