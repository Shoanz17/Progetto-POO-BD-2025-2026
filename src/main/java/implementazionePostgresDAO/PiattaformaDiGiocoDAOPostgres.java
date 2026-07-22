package implementazionePostgresDAO;

import dao.PiattaformaDiGiocoDAO;
import database.ConnessioneDatabase;
import model.CampoNonValidoException;
import model.Gioco;
import model.PiattaformaDiGioco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PiattaformaDiGiocoDAOPostgres implements PiattaformaDiGiocoDAO {


    private Connection connection;

    public PiattaformaDiGiocoDAOPostgres() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void creaPiattaforma(PiattaformaDiGioco piattaforma) throws SQLException {
        String query = "INSERT INTO PIATTAFORMA_DI_GIOCO (nome, produttore, portatile) VALUES (?, ?, ?)";

        ConnessioneDatabase.getInstance().eseguiUpdate(
                query,
                piattaforma.getNome(),
                piattaforma.getProduttore(),
                piattaforma.isPortatile()
        );
    }

    @Override
    public ArrayList<PiattaformaDiGioco> getListaPiattaforme() throws SQLException {
        ArrayList<PiattaformaDiGioco> listaPiattaforme = new ArrayList<>();
        String query = "SELECT nome, produttore, portatile FROM PIATTAFORMA_DI_GIOCO";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                try {
                    String nome = rs.getString("nome");
                    String produttore = rs.getString("produttore");
                    boolean portatile = rs.getBoolean("portatile");

                    PiattaformaDiGioco p = new PiattaformaDiGioco(nome, produttore, portatile);
                    listaPiattaforme.add(p);
                } catch (CampoNonValidoException e) {
                    throw new SQLException("Errore nel ripristino della piattaforma dal DB: " + e.getMessage(), e);
                }
            }
        }

        return listaPiattaforme;
    }

    @Override
    public ArrayList<PiattaformaDiGioco> getPiattaformeFiltrate(String testoRicerca) throws SQLException {
        ArrayList<PiattaformaDiGioco> listaPiattaforme = new ArrayList<>();

        // Cerca sia sul nome della piattaforma sia sul nome del produttore
        String query = "SELECT nome, produttore, portatile " +
                "FROM PIATTAFORMA_DI_GIOCO " +
                "WHERE LOWER(nome) LIKE LOWER(?) OR LOWER(produttore) LIKE LOWER(?)";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            String filtro = "%" + testoRicerca.trim() + "%";
            pstmt.setString(1, filtro);
            pstmt.setString(2, filtro);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        String nome = rs.getString("nome");
                        String produttore = rs.getString("produttore");
                        boolean portatile = rs.getBoolean("portatile");

                        PiattaformaDiGioco p = new PiattaformaDiGioco(nome, produttore, portatile);
                        listaPiattaforme.add(p);
                    } catch (CampoNonValidoException e) {
                        throw new SQLException("Errore nel ripristino della piattaforma filtrata: " + e.getMessage(), e);
                    }
                }
            }
        }

        return listaPiattaforme;
    }

    @Override
    public ArrayList<PiattaformaDiGioco> getListaPiattaformeDaGioco(Gioco gioco) throws SQLException {
        ArrayList<PiattaformaDiGioco> listaPiattaforme = new ArrayList<>();

        // JOIN tra PIATTAFORMA_DI_GIOCO ed EDIZIONE_GIOCO filtrando per l'id del Gioco
        String query = "SELECT p.nome, p.produttore, p.portatile " +
                "FROM PIATTAFORMA_DI_GIOCO p " +
                "JOIN EDIZIONE_GIOCO eg ON p.nome = eg.nomePiattaforma " +
                "WHERE eg.idGioco = ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, gioco.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        String nome = rs.getString("nome");
                        String produttore = rs.getString("produttore");
                        boolean portatile = rs.getBoolean("portatile");

                        PiattaformaDiGioco p = new PiattaformaDiGioco(nome, produttore, portatile);
                        listaPiattaforme.add(p);
                    } catch (CampoNonValidoException e) {
                        throw new SQLException("Errore nel ripristino delle piattaforme per il gioco: " + e.getMessage(), e);
                    }
                }
            }
        }

        return listaPiattaforme;
    }
}
