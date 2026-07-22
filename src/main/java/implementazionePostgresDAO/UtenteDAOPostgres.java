package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.GenereEnum;
import model.Utente;

import java.security.PublicKey;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UtenteDAOPostgres implements UtenteDAO {
    private Connection connection;

    public UtenteDAOPostgres() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registraUtente(Utente utente) throws SQLException {
        String queryAccount = "INSERT INTO ACCOUNT (nome, password) VALUES (?, ?)";

        Connection conn = ConnessioneDatabase.getInstance().connection;
        int idGenerato;

        try (PreparedStatement pstmt = conn.prepareStatement(queryAccount, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, utente.getNome());
            pstmt.setString(2, utente.getPassword());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerato = rs.getInt(1);

                    utente.setId(idGenerato);
                } else {
                    throw new SQLException("Registrazione fallita: impossibile recuperare l'idAccount generato.");
                }
            }
        }
        String queryUtente = "INSERT INTO UTENTE (idUtente, genere, saldo, bannato, dataNascita, email) VALUES (?, ?, ?, ?, ?, ?)";

        ConnessioneDatabase.getInstance().eseguiUpdate(queryUtente, idGenerato, utente.getGenere().name(), utente.getSaldo(), utente.isBannato(), Date.valueOf(utente.getDataNascita()), utente.getEmail()
        );
    }

    @Override
    public ArrayList<Utente> getUtentiFiltratiAdmin(String testoRicerca, boolean bannato) throws SQLException {
        ArrayList<Utente> listaUtenti = new ArrayList<>();

        String query = "SELECT a.nome, a.password, a.dataCreazione, u.idUtente, u.genere, u.saldo, u.bannato, u.dataNascita, u.email " +
                "FROM ACCOUNT a JOIN UTENTE u ON a.idAccount = u.idUtente " +
                "WHERE u.bannato = ? AND LOWER(a.nome) LIKE LOWER(?)";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, bannato);
            // Trovato su internet. I % servono per cercare qualsiasi nome che contiene quella parola
            pstmt.setString(2, "%" + testoRicerca.trim() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("idUtente");
                    String nome = rs.getString("nome");
                    String password = rs.getString("password");
                    LocalDate dataCreazione = rs.getDate("dataCreazione").toLocalDate();
                    GenereEnum genere = GenereEnum.valueOf(rs.getString("genere"));
                    String email = rs.getString("email");
                    LocalDate dataNascita = rs.getDate("dataNascita").toLocalDate();
                    int saldo = rs.getInt("saldo");
                    boolean statoBan = rs.getBoolean("bannato");

                    Utente u = new Utente(id, nome, password, dataCreazione, genere, email, dataNascita, saldo, statoBan);
                    listaUtenti.add(u);
                }
            }
        }
        return listaUtenti;
    }

    @Override
    public Utente getUtenteById(int idUtente) throws SQLException {
        String query = "SELECT a.nome, a.password, a.dataCreazione, u.idUtente, u.genere, u.saldo, u.bannato, u.dataNascita, u.email " +
                "FROM ACCOUNT a JOIN UTENTE u ON a.idAccount = u.idUtente " +
                "WHERE u.idUtente = ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;
        Utente u = null;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idUtente);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("idUtente");
                    String nome = rs.getString("nome");
                    String password = rs.getString("password");
                    LocalDate dataCreazione = rs.getDate("dataCreazione").toLocalDate();
                    GenereEnum genere = GenereEnum.valueOf(rs.getString("genere"));
                    String email = rs.getString("email");
                    LocalDate dataNascita = rs.getDate("dataNascita").toLocalDate();
                    int saldo = rs.getInt("saldo");
                    boolean statoBan = rs.getBoolean("bannato");

                    u = new Utente(id, nome, password, dataCreazione, genere, email, dataNascita, saldo, statoBan);
                }
            }
        }
        return u;
    }

    @Override
    public void invertiStatoBan(int idUtente) throws SQLException {
        String query = "UPDATE UTENTE SET bannato = NOT bannato WHERE idUtente = ?";

        ConnessioneDatabase.getInstance().eseguiUpdate(query, idUtente);
    }

    @Override
    public void setBannato(int idUtente) throws SQLException {
        String query = "UPDATE UTENTE SET bannato = ? WHERE idUtente = ?";

        ConnessioneDatabase.getInstance().eseguiUpdate(query,true, idUtente);
    }

    @Override
    public ArrayList<Utente> getListaUtenti() throws SQLException {
        ArrayList<Utente> listaUtenti = new ArrayList<>();
        String query = "SELECT a.nome, a.password, a.dataCreazione, u.idUtente, u.genere, u.saldo, u.bannato, u.dataNascita, u.email " +
                "FROM ACCOUNT a JOIN UTENTE u ON a.idAccount = u.idUtente";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        // Convertito da Statement a PreparedStatement
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idUtente");
                String nome = rs.getString("nome");
                String password = rs.getString("password");
                LocalDate dataCreazione = rs.getDate("dataCreazione").toLocalDate();
                GenereEnum genere = GenereEnum.valueOf(rs.getString("genere"));
                String email = rs.getString("email");
                LocalDate dataNascita = rs.getDate("dataNascita").toLocalDate();
                int saldo = rs.getInt("saldo");
                boolean bannato = rs.getBoolean("bannato");

                Utente u = new Utente(id, nome, password, dataCreazione, genere, email, dataNascita, saldo, bannato);
                listaUtenti.add(u);
            }
        }
        return listaUtenti;
    }

    @Override
    public void aggiornaProfiloUtente(Utente utente) throws SQLException {

        String queryAccount = "UPDATE ACCOUNT SET nome = ?, password = ? WHERE idAccount = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(queryAccount, utente.getNome(), utente.getPassword(), utente.getId());

        String queryUtente = "UPDATE UTENTE SET email = ?, genere = ?, dataNascita = ? WHERE idUtente = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(queryUtente, utente.getEmail(), utente.getGenere().name(), java.sql.Date.valueOf(utente.getDataNascita()), utente.getId());
    }

    @Override
    public void aggiungiSaldo(int idUtente, int importoDaAggiungere) throws SQLException {
        String query = "UPDATE UTENTE SET saldo = saldo + ? WHERE idUtente = ?";

        ConnessioneDatabase.getInstance().eseguiUpdate(query, importoDaAggiungere, idUtente);

    }

    @Override
    public void inserisciAmico(int idUtente, int idAmico) throws SQLException {
        int primoAmico = Math.min(idUtente, idAmico);
        int secondoAmico = Math.max(idUtente, idAmico);

        String query = "INSERT INTO AMICI (idAmico1, idAmico2) VALUES (?, ?)";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, primoAmico, secondoAmico);
    }

    @Override
    public void eliminaAmico(int idUtente, int idAmico) throws SQLException {
        int utente = Math.min(idUtente, idAmico);
        int secondoAmico = Math.max(idUtente, idAmico);

        String query = "DELETE FROM AMICI WHERE idAmico1 = ? AND idAmico2 = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, utente, secondoAmico);
    }

    @Override
    public ArrayList<Utente> getListaAmici(int idUtente) throws SQLException {
        ArrayList<Utente> listaAmici = new ArrayList<>();

        String query = "SELECT a.nome, a.password, a.dataCreazione, u.idUtente, u.genere, u.saldo, u.bannato, u.dataNascita, u.email " +
                "FROM ACCOUNT a JOIN UTENTE u ON a.idAccount = u.idUtente " +
                "JOIN AMICI am ON (am.idAmico1 = ? AND am.idAmico2 = u.idUtente) " +
                "OR (am.idAmico2 = ? AND am.idAmico1 = u.idUtente)";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idUtente);
            pstmt.setInt(2, idUtente);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("idUtente");
                    String nome = rs.getString("nome");
                    String password = rs.getString("password");
                    LocalDate dataCreazione = rs.getDate("dataCreazione").toLocalDate();
                    GenereEnum genere = GenereEnum.valueOf(rs.getString("genere"));
                    String email = rs.getString("email");
                    LocalDate dataNascita = rs.getDate("dataNascita").toLocalDate();
                    int saldo = rs.getInt("saldo");
                    boolean statoBan = rs.getBoolean("bannato");

                    Utente amico = new Utente(id, nome, password, dataCreazione, genere, email, dataNascita, saldo, statoBan);
                    listaAmici.add(amico);
                }
            }
        }

        return listaAmici;
    }


}
