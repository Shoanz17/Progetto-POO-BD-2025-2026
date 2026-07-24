package implementazionePostgresDAO;

import dao.EdizioneGiocoDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    public ArrayList<EdizioneGioco> getCatalogoCompleto() throws SQLException, CampoNonValidoException {
        ArrayList<EdizioneGioco> catalogo = new ArrayList<>();

        String query =
                "SELECT eg.idEdizione, eg.prezzo, eg.dataRilascio, " +
                        "g.idGioco, g.titolo, g.categoria, g.pegi, " +
                        "p.nome AS platNome, p.produttore AS platProd, p.portatile AS platPortatile, " +
                        "s.idSviluppatore, s.descrizione AS devDesc, s.strike AS devStrike, s.fondi AS devFondi, " +
                        "a.nome AS devNome, a.password AS devPass, a.dataCreazione AS devData, " +
                        "STRING_AGG(gen.idGenere || ':' || gen.nome, ',') AS generi_concat " +
                        "FROM edizione_gioco eg " +
                        "JOIN gioco g ON eg.idGioco = g.idGioco " +
                        "JOIN piattaforma_di_gioco p ON eg.nomePiattaforma = p.nome " +
                        "JOIN sviluppatore s ON g.idSviluppatore = s.idSviluppatore " +
                        "JOIN account a ON s.idSviluppatore = a.idAccount " +
                        "LEFT JOIN gioco_genere gg ON g.idGioco = gg.idGioco " +
                        "LEFT JOIN genere gen ON gg.idGenere = gen.idGenere " +
                        "GROUP BY eg.idEdizione, eg.prezzo, eg.dataRilascio, " +
                        "g.idGioco, g.titolo, g.categoria, g.pegi, " +
                        "p.nome, p.produttore, p.portatile, " +
                        "s.idSviluppatore, s.descrizione, s.strike, s.fondi, " +
                        "a.nome, a.password, a.dataCreazione";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idEdizione = rs.getInt("idEdizione");
                int prezzo = rs.getInt("prezzo");
                LocalDate dataRilascio = rs.getDate("dataRilascio").toLocalDate();

                int idGioco = rs.getInt("idGioco");
                String titolo = rs.getString("titolo");
                String categoriaString = rs.getString("categoria");
                int pegi = rs.getInt("pegi");

                PiattaformaDiGioco piattaforma = new PiattaformaDiGioco(
                        rs.getString("platNome"),
                        rs.getString("platProd"),
                        rs.getBoolean("platPortatile")
                );

                Sviluppatore sviluppatore = new Sviluppatore(
                        rs.getString("devNome"),
                        rs.getInt("idSviluppatore"),
                        rs.getString("devDesc"),
                        rs.getDate("devData").toLocalDate(),
                        rs.getInt("devStrike"),
                        rs.getString("devPass"),
                        rs.getInt("devFondi")
                );

                    Gioco gioco = new Gioco(sviluppatore, idGioco, titolo, Categoria.valueOf(categoriaString), pegi);

                    String generiString = rs.getString("generi_concat");
                    ArrayList<Genere> listaGeneri = new ArrayList<>();

                    if (generiString != null && !generiString.isEmpty()) {
                        String[] generiArray = generiString.split(",");
                        for (String gStr : generiArray) {
                            String[] parts = gStr.split(":");
                            Genere gen = new Genere(parts[1]);
                            listaGeneri.add(gen);
                        }
                    }
                    gioco.setListaGeneri(listaGeneri);

                    EdizioneGioco edizioneCorrente = new EdizioneGioco(idEdizione, gioco, piattaforma, prezzo, dataRilascio);

                    catalogo.add(edizioneCorrente);
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
