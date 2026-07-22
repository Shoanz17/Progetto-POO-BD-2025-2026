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
