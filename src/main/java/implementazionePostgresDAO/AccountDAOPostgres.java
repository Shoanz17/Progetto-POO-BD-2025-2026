package implementazionePostgresDAO;

import dao.AccountDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AccountDAOPostgres implements AccountDAO {
    private Connection connection;

    public AccountDAOPostgres(){
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account accedi(String nome, String password) throws SQLException {
        String query = "SELECT a.idAccount, a.nome, a.password, a.dataCreazione, " +
                "u.idUtente, u.genere, u.saldo, u.bannato, u.dataNascita, u.email, " +
                "s.idSviluppatore, s.strike, s.descrizione, s.fondi, " +
                "adm.idAdmin " +
                "FROM ACCOUNT a " +
                "LEFT JOIN UTENTE u ON a.idAccount = u.idUtente " +
                "LEFT JOIN SVILUPPATORE s ON a.idAccount = s.idSviluppatore " +
                "LEFT JOIN ADMIN adm ON a.idAccount = adm.idAdmin " +
                "WHERE a.nome = ? AND a.password = ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int idAccount = rs.getInt("idAccount");
                    String nomeAccount = rs.getString("nome");
                    String passwordAccount = rs.getString("password");
                    LocalDate dataCreazione = rs.getDate("dataCreazione").toLocalDate();

                    //è un UTENTE
                    if (rs.getObject("idUtente") != null) {
                        GenereEnum genere = GenereEnum.valueOf(rs.getString("genere"));
                        String email = rs.getString("email");
                        LocalDate dataNascita = rs.getDate("dataNascita").toLocalDate();
                        int saldo = rs.getInt("saldo");
                        boolean bannato = rs.getBoolean("bannato");

                        return new Utente(idAccount, nomeAccount, passwordAccount, dataCreazione, genere, email, dataNascita, saldo, bannato);
                    }
                    //è uno SVILUPPATORE
                    else if (rs.getObject("idSviluppatore") != null) {
                        int strike = rs.getInt("strike");
                        String descrizione = rs.getString("descrizione");
                        int fondi = rs.getInt("fondi");

                        return new Sviluppatore(nomeAccount, idAccount, passwordAccount, dataCreazione, strike, descrizione, fondi);
                    }
                    //è un ADMIN
                    else if (rs.getObject("idAdmin") != null) {
                        return new Admin(idAccount, nomeAccount, passwordAccount, dataCreazione);
                    }
                }
            }
        }

        return null;
    }
}
