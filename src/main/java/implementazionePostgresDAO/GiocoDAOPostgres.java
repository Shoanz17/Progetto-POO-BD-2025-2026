package implementazionePostgresDAO;

import dao.GiocoDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GiocoDAOPostgres implements GiocoDAO {

    private Connection connection;

    public GiocoDAOPostgres() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Gioco> getGiochiFiltrati(String testoRicerca) throws SQLException {
        ArrayList<Gioco> giochiFiltrati = new ArrayList<>();

        String query = "SELECT idGioco, titolo, categoria, pegi, idSviluppatore " +
                "FROM gioco " +
                "WHERE titolo ILIKE ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + testoRicerca + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    try {

                        int idSviluppatore = rs.getInt("idSviluppatore");
                        Sviluppatore sviluppatore = new Sviluppatore("Sconosciuto", idSviluppatore, "dummyPassword", LocalDate.now(), 0, "N/A", 0);
                        int idGioco = rs.getInt("idGioco");
                        String titolo = rs.getString("titolo");
                        Categoria categoria = Categoria.valueOf(rs.getString("categoria"));
                        int pegi = rs.getInt("pegi");
                        Gioco gioco = new Gioco(sviluppatore, idGioco, titolo, categoria, pegi);
                        giochiFiltrati.add(gioco);

                    } catch (CampoNonValidoException ex) {
                        System.err.println("Errore caricamento gioco filtrato (ID " + rs.getInt("idGioco") + "): " + ex.getMessage());
                    }
                }
            }
        }

        return giochiFiltrati;
    }


    @Override
    public void updateTitolo(int idGioco, String titolo) throws SQLException {
        String query = "UPDATE gioco SET titolo = ? WHERE idGioco = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, titolo, idGioco);
    }

    @Override
    public void updateCategoriaGioco(int idGioco, String nomeCategoria) throws SQLException {
        String query = "UPDATE gioco SET categoria = ? WHERE idGioco = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, nomeCategoria, idGioco);
    }

    @Override
    public void updatePegiGioco(int idGioco, int pegi) throws SQLException {
        String query = "UPDATE gioco SET pegi = ? WHERE idGioco = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, pegi, idGioco);
    }

    @Override
    public void updateGeneriGioco(int idGioco, ArrayList<Genere> nuoviGeneri) throws SQLException {

        String queryDelete = "DELETE FROM gioco_genere WHERE idGioco = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(queryDelete, idGioco);

        if (nuoviGeneri == null || nuoviGeneri.isEmpty()) {
            return;
        }

        String queryInsert = "INSERT INTO gioco_genere (idGioco, idGenere) VALUES (?, (SELECT idGenere FROM genere WHERE nome = ?))";

        for (Genere genere : nuoviGeneri) {
            ConnessioneDatabase.getInstance().eseguiUpdate(queryInsert, idGioco, genere.getNome());
        }
    }

    @Override
    public ArrayList<Gioco> getListaGiochi() throws SQLException {
        ArrayList<Gioco> listaGiochi = new ArrayList<>();

        String query = "SELECT idGioco, titolo, categoria, pegi, idSviluppatore FROM gioco";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                try {
                    int idSviluppatore = rs.getInt("idSviluppatore");
                    Sviluppatore sviluppatore = new Sviluppatore("Sconosciuto", idSviluppatore, "dummyPassword", LocalDate.now(), 0, "N/A", 0);

                    int idGioco = rs.getInt("idGioco");
                    String titolo = rs.getString("titolo");
                    Categoria categoria = Categoria.valueOf(rs.getString("categoria")); // Conversione stringa -> Enum
                    int pegi = rs.getInt("pegi");

                    Gioco gioco = new Gioco(sviluppatore, idGioco, titolo, categoria, pegi);

                    listaGiochi.add(gioco);

                } catch (CampoNonValidoException ex) {
                    System.err.println("Errore caricamento gioco (ID " + rs.getInt("idGioco") + "): " + ex.getMessage());
                }
            }
        }
        return listaGiochi;
    }

    @Override
    public void aggiornaGioco(Gioco gioco) throws SQLException {
        String query = "UPDATE gioco SET titolo = ?, categoria = ?, pegi = ? WHERE idGioco = ?";

        String categoriaNome = gioco.getCategoria().name();

        ConnessioneDatabase.getInstance().eseguiUpdate(
                query,
                gioco.getTitolo(),
                categoriaNome,
                gioco.getPegi(),
                gioco.getId()
        );
    }

    @Override
    public int inserisciGioco(Gioco gioco) throws SQLException {
        String query = "INSERT INTO gioco (titolo, categoria, pegi, idSviluppatore) VALUES (?, ?, ?, ?)";

        int idGenerato = -1;
        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, gioco.getTitolo());
            stmt.setString(2, gioco.getCategoria().name());
            stmt.setInt(3, gioco.getPegi());
            stmt.setInt(4, gioco.getSviluppatore().getId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerato = rs.getInt(1);
                }
            }
        }

        return idGenerato;
    }

    @Override
    public void inserisciEdizione(EdizioneGioco edizioneGioco, int idGioco) throws SQLException {
        String query = "INSERT INTO edizione_gioco (idGioco, nomePiattaforma, prezzo, dataRilascio) VALUES (?, ?, ?, ?)";

        String nomePiattaforma = edizioneGioco.getPiattaforma().getNome();

        ConnessioneDatabase.getInstance().eseguiUpdate(
                query,
                idGioco,
                nomePiattaforma,
                edizioneGioco.getPrezzo(),
                edizioneGioco.getDataRilascio()
        );
    }

    @Override
    public int getUnitaVendutePerGioco(String titoloGioco) throws SQLException {

        String query = "SELECT COUNT(*) " +
                "FROM fattura f " +
                "JOIN edizione_gioco eg ON f.idEdizione = eg.idEdizione " +
                "JOIN gioco g ON eg.idGioco = g.idGioco " +
                "WHERE g.titolo = ?";

        int unitaVendute = 0;

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, titoloGioco);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    unitaVendute = rs.getInt(1); // Leggiamo il conteggio
                }
            }
        }

        return unitaVendute;
    }

    @Override
    public int getGuadagnoTotalePerGioco(String titoloGioco) throws SQLException {

        String query = "SELECT SUM(f.prezzoAcquisto) " +
                "FROM fattura f " +
                "JOIN edizione_gioco eg ON f.idEdizione = eg.idEdizione " +
                "JOIN gioco g ON eg.idGioco = g.idGioco " +
                "WHERE g.titolo = ?";

        int guadagnoTotale = 0;

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, titoloGioco);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    guadagnoTotale = rs.getInt(1);
                }
            }
        }

        return guadagnoTotale;
    }

    @Override
    public ArrayList<Gioco> getGiochiSviluppatore(int idSviluppatore) throws SQLException {

        ArrayList<Gioco> listaGiochi = new ArrayList<>();

        String query = "SELECT idGioco, titolo, categoria, pegi FROM gioco WHERE idSviluppatore = ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idSviluppatore);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    int idGioco = rs.getInt("idGioco");
                    String titolo = rs.getString("titolo");
                    String categoriaString = rs.getString("categoria");
                    int pegi = rs.getInt("pegi");

                    Sviluppatore sviluppatoreObj = new Sviluppatore("Sconosciuto", idSviluppatore, "dummyPassword", LocalDate.now(), 0, "N/A", 0);

                    try {
                        Gioco giocoCorrente = new Gioco(sviluppatoreObj, idGioco, titolo, Categoria.valueOf(categoriaString), pegi);

                        listaGiochi.add(giocoCorrente);

                    } catch (CampoNonValidoException e) {
                        System.out.println("Errore nel caricamento del gioco: " + e.getMessage());
                    }
                }
            }
        }

        return listaGiochi;
    }
}