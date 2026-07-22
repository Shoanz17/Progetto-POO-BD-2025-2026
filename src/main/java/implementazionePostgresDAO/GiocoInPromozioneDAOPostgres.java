package implementazionePostgresDAO;

import dao.GiocoInPromozioneDAO;
import database.ConnessioneDatabase;
import model.CampoNonValidoException;
import model.Gioco;
import model.GiocoInPromozione;
import model.Promozione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GiocoInPromozioneDAOPostgres implements GiocoInPromozioneDAO {
    private Connection connection;

    public GiocoInPromozioneDAOPostgres() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<GiocoInPromozione> getPromozioniPerGioco(Gioco giocoScelto) throws SQLException, CampoNonValidoException {
        ArrayList<GiocoInPromozione> listaPromozioni = new ArrayList<>();

        String query = "SELECT p.idPromozione, p.nome, p.dataInizio, p.dataFine, gip.percentuale " +
                "FROM GIOCO_IN_PROMOZIONE gip " +
                "JOIN PROMOZIONE p ON gip.idPromozione = p.idPromozione " +
                "WHERE gip.idGioco = ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, giocoScelto.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                        int idPromozione = rs.getInt("idPromozione");
                        String nome = rs.getString("nome");
                        LocalDate dataInizio = rs.getDate("dataInizio").toLocalDate();
                        LocalDate dataFine = rs.getDate("dataFine").toLocalDate();
                        int percentuale = rs.getInt("percentuale");

                        Promozione promozione = new Promozione(idPromozione, nome, dataInizio, dataFine);
                        GiocoInPromozione gip = new GiocoInPromozione(percentuale, giocoScelto, promozione);

                        listaPromozioni.add(gip);

                }
            }
        }
        return listaPromozioni;
    }
}
