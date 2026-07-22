package implementazionePostgresDAO;

import dao.FatturaDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FatturaDAOPostgres implements FatturaDAO {
    private Connection connection;

    public FatturaDAOPostgres() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Fattura> getLibreriaUtente(int idUtente) throws SQLException, CampoNonValidoException {
        ArrayList<Fattura> libreria = new ArrayList<>();

        String query = "SELECT " +
                "f.idFattura, f.prezzoAcquisto, f.key, f.dataAcquisto, " +
                "u.idUtente, u.genere, u.saldo, u.bannato, u.dataNascita, u.email, " +
                "au.nome AS nomeUtente, au.password AS passwordUtente, au.dataCreazione AS dataCreazioneUtente, " +
                "eg.idEdizione, eg.prezzo AS prezzoEdizione, eg.dataRilascio, " +
                "p.nome AS nomePiattaforma, p.produttore, p.portatile, " +
                "g.idGioco, g.titolo, g.categoria, g.pegi, " +
                "s.idSviluppatore, s.strike, s.descrizione, s.fondi, " +
                "asv.nome AS nomeSviluppatore, asv.password AS passwordSviluppatore, asv.dataCreazione AS dataCreazioneSviluppatore " +
                "FROM FATTURA f " +
                "JOIN UTENTE u ON f.idUtente = u.idUtente " +
                "JOIN ACCOUNT au ON u.idUtente = au.idAccount " +
                "JOIN EDIZIONE_GIOCO eg ON f.idEdizione = eg.idEdizione " +
                "JOIN PIATTAFORMA_DI_GIOCO p ON eg.nomePiattaforma = p.nome " +
                "JOIN GIOCO g ON eg.idGioco = g.idGioco " +
                "JOIN SVILUPPATORE s ON g.idSviluppatore = s.idSviluppatore " +
                "JOIN ACCOUNT asv ON s.idSviluppatore = asv.idAccount " +
                "WHERE f.idUtente = ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idUtente);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Utente utente = new Utente(
                            rs.getInt("idUtente"),
                            rs.getString("nomeUtente"),
                            rs.getString("passwordUtente"),
                            rs.getDate("dataCreazioneUtente").toLocalDate(),
                            GenereEnum.valueOf(rs.getString("genere")),
                            rs.getString("email"),
                            rs.getDate("dataNascita").toLocalDate(),
                            rs.getInt("saldo"),
                            rs.getBoolean("bannato")
                    );

                    Sviluppatore sviluppatore = new Sviluppatore(
                            rs.getString("nomeSviluppatore"),
                            rs.getInt("idSviluppatore"),
                            rs.getString("passwordSviluppatore"),
                            rs.getDate("dataCreazioneSviluppatore").toLocalDate(),
                            rs.getInt("strike"),
                            rs.getString("descrizione"),
                            rs.getInt("fondi")
                    );

                    Gioco gioco = new Gioco(
                            sviluppatore,
                            rs.getInt("idGioco"),
                            rs.getString("titolo"),
                            Categoria.valueOf(rs.getString("categoria")),
                            rs.getInt("pegi")
                    );

                    PiattaformaDiGioco piattaforma = new PiattaformaDiGioco(
                            rs.getString("nomePiattaforma"),
                            rs.getString("produttore"),
                            rs.getBoolean("portatile")
                    );

                    EdizioneGioco edizione = new EdizioneGioco(
                            rs.getInt("idEdizione"),
                            gioco,
                            piattaforma,
                            rs.getInt("prezzoEdizione"),
                            rs.getDate("dataRilascio").toLocalDate()
                    );

                    Fattura f = new Fattura(
                            rs.getInt("idFattura"),
                            utente,
                            edizione,
                            rs.getInt("prezzoAcquisto"),
                            rs.getString("key"),
                            rs.getDate("dataAcquisto").toLocalDate()
                    );

                    libreria.add(f);

                }
            }
        }

        return libreria;
    }

    @Override
    public void inserisciFattura(Fattura fattura) throws SQLException {
        String query = "INSERT INTO FATTURA (idUtente, idEdizione, prezzoAcquisto, key, dataAcquisto) VALUES (?, ?, ?, ?, ?)";

        ConnessioneDatabase.getInstance().eseguiUpdate(query, fattura.getUtente().getId(), fattura.getGioco().getId(), fattura.getPrezzoAcquisto(), fattura.getKey(), java.sql.Date.valueOf(fattura.getDataAcquisto()));
    }

    @Override
    public void effettuaRimborso(int idFattura, int idUtente, int importo) throws SQLException {
        String querySaldo = "UPDATE UTENTE SET saldo = saldo + ? WHERE idUtente = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(querySaldo, importo, idUtente);

        String queryEliminaFattura = "DELETE FROM FATTURA WHERE idFattura = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(queryEliminaFattura, idFattura);
    }
}
