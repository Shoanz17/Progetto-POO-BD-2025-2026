package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;
import model.GenereEnum;
import model.Utente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UtenteDAOPostgres implements UtenteDAO {
    private Connection connection;

    public UtenteDAOPostgres(){
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
    public ArrayList<Utente> getListaUtenti() throws SQLException {
        ArrayList<Utente> listaUtenti = new ArrayList<>();
        String query = "SELECT a.nome, a.password,a.datacreazione, u.idUtente, u.genere, u.saldo, u.bannato, u.dataNascita, u.email " +
                        "FROM ACCOUNT a JOIN UTENTE u " +
                        "ON a.idAccount = u.idUtente";


        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("idUtente");
                String nome = rs.getString("nome");
                LocalDate dataCreazione = rs.getDate("dataCreazione").toLocalDate();
                String password = rs.getString("password");
                GenereEnum genere = GenereEnum.valueOf(rs.getString("genere"));
                String email = rs.getString("email");
                LocalDate dataNascita = rs.getDate("dataNascita").toLocalDate();
                int saldo = rs.getInt("saldo");
                boolean bannato = rs.getBoolean("bannato");

                Utente u = new Utente(id,nome,password,dataCreazione,genere,email,dataNascita,saldo,bannato);

                listaUtenti.add(u);
            }
        }
        return listaUtenti;
    }

    @Override
    public void aggiungiSaldo(int idUtente, int importoDaAggiungere) throws SQLException {
        String query = "UPDATE UTENTE SET saldo = saldo + ? WHERE idUtente = ?";

        ConnessioneDatabase.getInstance().eseguiUpdate(query, importoDaAggiungere, idUtente);

    }

}
