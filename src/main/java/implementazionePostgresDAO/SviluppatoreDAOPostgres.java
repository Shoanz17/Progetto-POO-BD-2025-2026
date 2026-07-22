package implementazionePostgresDAO;

import dao.SviluppatoreDAO;
import database.ConnessioneDatabase;
import model.Sviluppatore;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SviluppatoreDAOPostgres implements SviluppatoreDAO {
    private Connection connection;

    public SviluppatoreDAOPostgres(){
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registraSviluppatore(Sviluppatore sviluppatore) throws SQLException {
        String queryAccount = "INSERT INTO ACCOUNT (nome, password) VALUES (?, ?)";

        Connection conn = ConnessioneDatabase.getInstance().connection;
        int idGenerato;

        try (PreparedStatement pstmt = conn.prepareStatement(queryAccount, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, sviluppatore.getNome());
            pstmt.setString(2, sviluppatore.getPassword());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerato = rs.getInt(1);
                    sviluppatore.setId(idGenerato);
                } else {
                    throw new SQLException("Registrazione fallita");
                }
            }
        }

        String querySviluppatore = "INSERT INTO SVILUPPATORE (idSviluppatore, strike, descrizione, fondi) VALUES (?, ?, ?, ?)";

        ConnessioneDatabase.getInstance().eseguiUpdate(
                querySviluppatore,
                idGenerato,
                sviluppatore.getStrike(),
                sviluppatore.getDescrizione(),
                sviluppatore.getFondi()
        );
    }

    @Override
    public ArrayList<Sviluppatore> getListaSviluppatoriFiltrati(String testoRicerca) throws SQLException {
        ArrayList<Sviluppatore> listaSviluppatori = new ArrayList<>();
        String query = "SELECT a.nome, a.password, a.dataCreazione, s.idSviluppatore, s.strike, s.descrizione, s.fondi " +
                "FROM ACCOUNT a JOIN SVILUPPATORE s ON a.idAccount = s.idSviluppatore " +
                "WHERE LOWER(a.nome) LIKE LOWER(?) OR LOWER(s.descrizione) LIKE LOWER(?)";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + testoRicerca.trim() + "%");
            pstmt.setString(2, "%" + testoRicerca.trim() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("idSviluppatore");
                    String nome = rs.getString("nome");
                    String password = rs.getString("password");
                    LocalDate dataCreazione = rs.getDate("dataCreazione").toLocalDate();
                    int strike = rs.getInt("strike");
                    String descrizione = rs.getString("descrizione");
                    int fondi = rs.getInt("fondi");

                    Sviluppatore s = new Sviluppatore(nome, id, password, dataCreazione, strike, descrizione, fondi);
                    listaSviluppatori.add(s);
                }
            }
        }
        return listaSviluppatori;
    }

    @Override
    public ArrayList<Sviluppatore> getListaSviluppatori() throws SQLException {
        ArrayList<Sviluppatore> listaSviluppatori = new ArrayList<>();
        String query = "SELECT a.nome, a.password, a.dataCreazione, s.idSviluppatore, s.strike, s.descrizione, s.fondi " +
                "FROM ACCOUNT a JOIN SVILUPPATORE s ON a.idAccount = s.idSviluppatore";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idSviluppatore");
                String nome = rs.getString("nome");
                String password = rs.getString("password");
                LocalDate dataCreazione = rs.getDate("dataCreazione").toLocalDate();
                int strike = rs.getInt("strike");
                String descrizione = rs.getString("descrizione");
                int fondi = rs.getInt("fondi");

                Sviluppatore s = new Sviluppatore(nome, id, password, dataCreazione, strike, descrizione, fondi);
                listaSviluppatori.add(s);
            }
        }
        return listaSviluppatori;
    }

    @Override
    public void aggiungiStrike(int idSviluppatore) throws SQLException {
        String query = "UPDATE SVILUPPATORE SET strike = strike + 1 WHERE idSviluppatore = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, idSviluppatore);
    }

    @Override
    public void rimuoviStrike(int idSviluppatore) throws SQLException {
        String query = "UPDATE SVILUPPATORE SET strike = strike - 1 WHERE idSviluppatore = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, idSviluppatore);
    }

    @Override
    public void aggiornaProfilo(Sviluppatore sviluppatore) throws SQLException {
        String queryAccount = "UPDATE ACCOUNT SET nome = ?, password = ? WHERE idAccount = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(queryAccount, sviluppatore.getNome(), sviluppatore.getPassword(), sviluppatore.getId());

        String querySviluppatore = "UPDATE SVILUPPATORE SET descrizione = ?, fondi = ?, strike = ? WHERE idSviluppatore = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(querySviluppatore, sviluppatore.getDescrizione(), sviluppatore.getFondi(), sviluppatore.getStrike(), sviluppatore.getId());
    }

    @Override
    public int getGiocoPiuVendutoSviluppatore(int idSviluppatore) throws SQLException {
        String query = "SELECT g.idGioco, COUNT(f.idFattura) AS vendite " +
                "FROM GIOCO g " +
                "JOIN EDIZIONEGIOCO eg ON g.idGioco = eg.idGioco " +
                "JOIN FATTURA f ON eg.idEdizione = f.idEdizione " +
                "WHERE g.idSviluppatore = ? " +
                "GROUP BY g.idGioco " +
                "ORDER BY vendite DESC " +
                "LIMIT 1";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idSviluppatore);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idGioco");
                }
            }
        }
        return 0;
    }
}
