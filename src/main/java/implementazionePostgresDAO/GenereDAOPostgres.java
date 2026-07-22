package implementazionePostgresDAO;

import dao.GenereDAO;
import database.ConnessioneDatabase;
import model.CampoNonValidoException;
import model.Genere;
import model.Gioco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenereDAOPostgres implements GenereDAO {
    private Connection connection;

    public GenereDAOPostgres(){
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void creaGenere(Genere genere) throws SQLException {
        String query = "INSERT INTO Genere (nome) VALUES (?)";

        ConnessioneDatabase.getInstance().eseguiUpdate(query, genere.getNome());
    }

    @Override
    public ArrayList<Genere> getListaGeneri() throws SQLException, CampoNonValidoException {
        ArrayList<Genere> lista = new ArrayList<>();
        String query = "SELECT nome FROM Genere ORDER BY nome ASC";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Genere(rs.getString("nome")));
            }
        }
        return lista;
    }

    @Override
    public ArrayList<Genere> getListaGeneriDaGioco(Gioco gioco) throws SQLException, CampoNonValidoException {
        ArrayList<Genere> lista = new ArrayList<>();
        String query = "SELECT g.nome FROM Genere g " +
                "JOIN GiocoGenere gg ON g.nome = gg.nome_genere " +
                "WHERE gg.id_gioco = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, gioco.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Genere(rs.getString("nome")));
                }
            }
        }
        return lista;
    }

    @Override
    public ArrayList<Genere> getGeneriFiltrati(String testoRicerca) throws SQLException, CampoNonValidoException {
        ArrayList<Genere> lista = new ArrayList<>();
        String query = "SELECT nome FROM Genere WHERE LOWER(nome) LIKE LOWER(?) ORDER BY nome ASC";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + testoRicerca.trim() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Genere(rs.getString("nome")));
                }
            }
        }
        return lista;
    }

    @Override
    public ArrayList<Genere> getGeneriDaListaNomi(ArrayList<String> listaNomi) throws SQLException, CampoNonValidoException {
        ArrayList<Genere> lista = new ArrayList<>();

        if (listaNomi == null || listaNomi.isEmpty()) {
            return lista;
        }

        //Costruzione dinamica della query con IN (?, ?, ...)
        StringBuilder sb = new StringBuilder("SELECT nome FROM Genere WHERE nome IN (");
        for (int i = 0; i < listaNomi.size(); i++) {
            sb.append("?");
            if (i < listaNomi.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");

        try (PreparedStatement pstmt = connection.prepareStatement(sb.toString())) {
            for (int i = 0; i < listaNomi.size(); i++) {
                pstmt.setString(i + 1, listaNomi.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Genere(rs.getString("nome")));
                }
            }
        }
        return lista;
    }

    @Override
    public void collegaGeneriAGioco(int idGioco, ArrayList<Genere> generi) throws SQLException {
        if (generi == null || generi.isEmpty()) {
            return;
        }
        //Rimuove tutti i vecchi generi per evitare duplicati
        String queryDelete = "DELETE FROM GiocoGenere WHERE id_gioco = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(queryDelete, idGioco);

        //Inserisco i nuovi generi
        String queryInsert = "INSERT INTO GiocoGenere (id_gioco, nome_genere) VALUES (?, ?)";
        for (Genere g : generi) {
            ConnessioneDatabase.getInstance().eseguiUpdate(queryInsert, idGioco, g.getNome());
        }
    }
}
