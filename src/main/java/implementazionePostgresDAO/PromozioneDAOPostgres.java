package implementazionePostgresDAO;

import dao.PromozioneDAO;
import database.ConnessioneDatabase;
import model.Promozione;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PromozioneDAOPostgres implements PromozioneDAO {
    private Connection connection;

    public PromozioneDAOPostgres() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void creaPromozione(String nome, LocalDate dataInizio, LocalDate dataFine) throws SQLException {
        String query = "INSERT INTO PROMOZIONE (nome, dataInizio, dataFine) VALUES (?, ?, ?)";

        ConnessioneDatabase.getInstance().eseguiUpdate(query, nome, Date.valueOf(dataInizio), Date.valueOf(dataFine));
    }

    @Override
    public void inserisciGiocoInPromozione(int idGioco, int idPromozione, int percentuale) throws SQLException {
        String query = "INSERT INTO GIOCO_IN_PROMOZIONE (idGioco, idPromozione, percentuale) VALUES (?, ?, ?)";

        ConnessioneDatabase.getInstance().eseguiUpdate(query, idGioco, idPromozione, percentuale);
    }

    @Override
    public ArrayList<Promozione> getTuttePromozioni() throws SQLException{
        ArrayList<Promozione> listaPromozioni = new ArrayList<>();
        String query = "SELECT idPromozione, nome, dataInizio, dataFine FROM PROMOZIONE";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                    int idPromozione = rs.getInt("idPromozione");
                    String nome = rs.getString("nome");
                    LocalDate dataInizio = rs.getDate("dataInizio").toLocalDate();
                    LocalDate dataFine = rs.getDate("dataFine").toLocalDate();

                    Promozione p = new Promozione(idPromozione, nome, dataInizio, dataFine);
                    listaPromozioni.add(p);
            }
        }

        return listaPromozioni;
    }

    @Override
    public ArrayList<Promozione> getPromozioniFiltrate(String testoRicerca, boolean ordinaPerData) throws SQLException {
        ArrayList<Promozione> listaPromozioni = new ArrayList<>();

        String query = "SELECT idPromozione, nome, dataInizio, dataFine " +
                "FROM PROMOZIONE " +
                "WHERE LOWER(nome) LIKE LOWER(?)";

        if (ordinaPerData) {
            query += " ORDER BY dataInizio ASC";
        }

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + testoRicerca.trim() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                        int idPromozione = rs.getInt("idPromozione");
                        String nome = rs.getString("nome");
                        LocalDate dataInizio = rs.getDate("dataInizio").toLocalDate();
                        LocalDate dataFine = rs.getDate("dataFine").toLocalDate();

                        Promozione p = new Promozione(idPromozione, nome, dataInizio, dataFine);
                        listaPromozioni.add(p);
                }
            }
        }
        return listaPromozioni;
    }

}
