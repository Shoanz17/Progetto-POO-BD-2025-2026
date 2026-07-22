package implementazionePostgresDAO;

import dao.RecensioneDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecensioneDAOPostgres implements RecensioneDAO {
    private Connection connection;

    public RecensioneDAOPostgres(){
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Recensione> getListaRecensioniUtente(int idUtente) throws SQLException, CampoNonValidoException {
        ArrayList<Recensione> lista = new ArrayList<>();
        String query = "SELECT " +
                "r.voto, r.descrizione, r.differenza_like, " +
                "f.id AS f_id, f.prezzo_acquisto, f.key, f.data_acquisto, " +
                "u.id_utente AS u_id, a_u.nome AS u_nome, a_u.password AS u_password, a_u.data_creazione AS u_data_creazione, " +
                "u.genere AS u_genere, u.email AS u_email, u.data_nascita AS u_data_nascita, u.saldo AS u_saldo, u.bannato AS u_bannato, " +
                "p.nome AS p_nome, p.produttore AS p_produttore, p.portatile AS p_portatile, " +
                "s.id_sviluppatore AS s_id, a_s.nome AS s_nome, a_s.password AS s_password, a_s.data_creazione AS s_data_creazione, " +
                "s.strike AS s_strike, s.descrizione AS s_descrizione, s.fondi AS s_fondi, " +
                "g.id AS g_id, g.titolo AS g_titolo, g.categoria AS g_categoria, g.pegi AS g_pegi, " +
                "eg.id AS eg_id, eg.prezzo AS eg_prezzo, eg.data_rilascio AS eg_data_rilascio " +
                "FROM Recensione r " +
                "JOIN Fattura f ON r.id_fattura = f.id " +
                "JOIN Utente u ON f.id_utente = u.id_utente " +
                "JOIN Account a_u ON u.id_utente = a_u.id " +
                "JOIN EdizioneGioco eg ON f.id_edizione_gioco = eg.id " +
                "JOIN PiattaformaDiGioco p ON eg.nome_piattaforma = p.nome " +
                "JOIN Gioco g ON eg.id_gioco = g.id " +
                "JOIN Sviluppatore s ON g.id_sviluppatore = s.id_sviluppatore " +
                "JOIN Account a_s ON s.id_sviluppatore = a_s.id " +
                "WHERE f.id_utente = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idUtente);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String strGenere = rs.getString("u_genere");
                    GenereEnum genereEnum = strGenere != null ? GenereEnum.valueOf(strGenere) : null;

                    Utente utente = new Utente(
                            rs.getInt("u_id"),
                            rs.getString("u_nome"),
                            rs.getString("u_password"),
                            rs.getDate("u_data_creazione").toLocalDate(),
                            genereEnum,
                            rs.getString("u_email"),
                            rs.getDate("u_data_nascita").toLocalDate(),
                            rs.getInt("u_saldo"),
                            rs.getBoolean("u_bannato")
                    );

                    PiattaformaDiGioco piattaforma = new PiattaformaDiGioco(
                            rs.getString("p_nome"),
                            rs.getString("p_produttore"),
                            rs.getBoolean("p_portatile")
                    );

                    Sviluppatore sviluppatore = new Sviluppatore(
                            rs.getString("s_nome"),
                            rs.getInt("s_id"),
                            rs.getString("s_password"),
                            rs.getDate("s_data_creazione").toLocalDate(),
                            rs.getInt("s_strike"),
                            rs.getString("s_descrizione"),
                            rs.getInt("s_fondi")
                    );

                    String strCat = rs.getString("g_categoria");
                    Categoria catEnum = strCat != null ? Categoria.valueOf(strCat) : null;

                    Gioco gioco = new Gioco(
                            sviluppatore,
                            rs.getInt("g_id"),
                            rs.getString("g_titolo"),
                            catEnum,
                            rs.getInt("g_pegi")
                    );

                    EdizioneGioco edizione = new EdizioneGioco(
                            rs.getInt("eg_id"),
                            gioco,
                            piattaforma,
                            rs.getInt("eg_prezzo"),
                            rs.getDate("eg_data_rilascio").toLocalDate()
                    );

                    Fattura fattura = new Fattura(
                            rs.getInt("f_id"),
                            utente,
                            edizione,
                            rs.getInt("prezzo_acquisto"),
                            rs.getString("key"),
                            rs.getDate("data_acquisto").toLocalDate()
                    );

                    Recensione recensione = new Recensione(
                            rs.getInt("voto"),
                            rs.getString("descrizione"),
                            rs.getInt("differenza_like"),
                            fattura
                    );

                    lista.add(recensione);
                }
            }
        }
        return lista;
    }

    @Override
    public ArrayList<Recensione> getListaRecensioniEdizione(int idEdizioneGioco) throws SQLException, CampoNonValidoException {
        ArrayList<Recensione> lista = new ArrayList<>();
        String query = "SELECT " +
                "r.voto, r.descrizione, r.differenza_like, " +
                "f.id AS f_id, f.prezzo_acquisto, f.key, f.data_acquisto, " +
                "u.id_utente AS u_id, a_u.nome AS u_nome, a_u.password AS u_password, a_u.data_creazione AS u_data_creazione, " +
                "u.genere AS u_genere, u.email AS u_email, u.data_nascita AS u_data_nascita, u.saldo AS u_saldo, u.bannato AS u_bannato, " +
                "p.nome AS p_nome, p.produttore AS p_produttore, p.portatile AS p_portatile, " +
                "s.id_sviluppatore AS s_id, a_s.nome AS s_nome, a_s.password AS s_password, a_s.data_creazione AS s_data_creazione, " +
                "s.strike AS s_strike, s.descrizione AS s_descrizione, s.fondi AS s_fondi, " +
                "g.id AS g_id, g.titolo AS g_titolo, g.categoria AS g_categoria, g.pegi AS g_pegi, " +
                "eg.id AS eg_id, eg.prezzo AS eg_prezzo, eg.data_rilascio AS eg_data_rilascio " +
                "FROM Recensione r " +
                "JOIN Fattura f ON r.id_fattura = f.id " +
                "JOIN Utente u ON f.id_utente = u.id_utente " +
                "JOIN Account a_u ON u.id_utente = a_u.id " +
                "JOIN EdizioneGioco eg ON f.id_edizione_gioco = eg.id " +
                "JOIN PiattaformaDiGioco p ON eg.nome_piattaforma = p.nome " +
                "JOIN Gioco g ON eg.id_gioco = g.id " +
                "JOIN Sviluppatore s ON g.id_sviluppatore = s.id_sviluppatore " +
                "JOIN Account a_s ON s.id_sviluppatore = a_s.id " +
                "WHERE f.id_edizione_gioco = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idEdizioneGioco);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String strGenere = rs.getString("u_genere");
                    GenereEnum genereEnum = strGenere != null ? GenereEnum.valueOf(strGenere) : null;

                    Utente utente = new Utente(
                            rs.getInt("u_id"),
                            rs.getString("u_nome"),
                            rs.getString("u_password"),
                            rs.getDate("u_data_creazione").toLocalDate(),
                            genereEnum,
                            rs.getString("u_email"),
                            rs.getDate("u_data_nascita").toLocalDate(),
                            rs.getInt("u_saldo"),
                            rs.getBoolean("u_bannato")
                    );

                    PiattaformaDiGioco piattaforma = new PiattaformaDiGioco(
                            rs.getString("p_nome"),
                            rs.getString("p_produttore"),
                            rs.getBoolean("p_portatile")
                    );

                    Sviluppatore sviluppatore = new Sviluppatore(
                            rs.getString("s_nome"),
                            rs.getInt("s_id"),
                            rs.getString("s_password"),
                            rs.getDate("s_data_creazione").toLocalDate(),
                            rs.getInt("s_strike"),
                            rs.getString("s_descrizione"),
                            rs.getInt("s_fondi")
                    );

                    String strCat = rs.getString("g_categoria");
                    Categoria catEnum = strCat != null ? Categoria.valueOf(strCat) : null;

                    Gioco gioco = new Gioco(
                            sviluppatore,
                            rs.getInt("g_id"),
                            rs.getString("g_titolo"),
                            catEnum,
                            rs.getInt("g_pegi")
                    );

                    EdizioneGioco edizione = new EdizioneGioco(
                            rs.getInt("eg_id"),
                            gioco,
                            piattaforma,
                            rs.getInt("eg_prezzo"),
                            rs.getDate("eg_data_rilascio").toLocalDate()
                    );

                    Fattura fattura = new Fattura(
                            rs.getInt("f_id"),
                            utente,
                            edizione,
                            rs.getInt("prezzo_acquisto"),
                            rs.getString("key"),
                            rs.getDate("data_acquisto").toLocalDate()
                    );

                    Recensione recensione = new Recensione(
                            rs.getInt("voto"),
                            rs.getString("descrizione"),
                            rs.getInt("differenza_like"),
                            fattura
                    );

                    lista.add(recensione);
                }
            }
        }
        return lista;
    }

    @Override
    public ArrayList<Recensione> getRecensioniFiltrateAdmin(String testoRicerca) throws SQLException, CampoNonValidoException {
        ArrayList<Recensione> lista = new ArrayList<>();
        String query = "SELECT " +
                "r.voto, r.descrizione, r.differenza_like, " +
                "f.id AS f_id, f.prezzo_acquisto, f.key, f.data_acquisto, " +
                "u.id_utente AS u_id, a_u.nome AS u_nome, a_u.password AS u_password, a_u.data_creazione AS u_data_creazione, " +
                "u.genere AS u_genere, u.email AS u_email, u.data_nascita AS u_data_nascita, u.saldo AS u_saldo, u.bannato AS u_bannato, " +
                "p.nome AS p_nome, p.produttore AS p_produttore, p.portatile AS p_portatile, " +
                "s.id_sviluppatore AS s_id, a_s.nome AS s_nome, a_s.password AS s_password, a_s.data_creazione AS s_data_creazione, " +
                "s.strike AS s_strike, s.descrizione AS s_descrizione, s.fondi AS s_fondi, " +
                "g.id AS g_id, g.titolo AS g_titolo, g.categoria AS g_categoria, g.pegi AS g_pegi, " +
                "eg.id AS eg_id, eg.prezzo AS eg_prezzo, eg.data_rilascio AS eg_data_rilascio " +
                "FROM Recensione r " +
                "JOIN Fattura f ON r.id_fattura = f.id " +
                "JOIN Utente u ON f.id_utente = u.id_utente " +
                "JOIN Account a_u ON u.id_utente = a_u.id " +
                "JOIN EdizioneGioco eg ON f.id_edizione_gioco = eg.id " +
                "JOIN PiattaformaDiGioco p ON eg.nome_piattaforma = p.nome " +
                "JOIN Gioco g ON eg.id_gioco = g.id " +
                "JOIN Sviluppatore s ON g.id_sviluppatore = s.id_sviluppatore " +
                "JOIN Account a_s ON s.id_sviluppatore = a_s.id " +
                "WHERE LOWER(r.descrizione) LIKE LOWER(?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + testoRicerca.trim() + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String strGenere = rs.getString("u_genere");
                    GenereEnum genereEnum = strGenere != null ? GenereEnum.valueOf(strGenere) : null;

                    Utente utente = new Utente(
                            rs.getInt("u_id"),
                            rs.getString("u_nome"),
                            rs.getString("u_password"),
                            rs.getDate("u_data_creazione").toLocalDate(),
                            genereEnum,
                            rs.getString("u_email"),
                            rs.getDate("u_data_nascita").toLocalDate(),
                            rs.getInt("u_saldo"),
                            rs.getBoolean("u_bannato")
                    );

                    PiattaformaDiGioco piattaforma = new PiattaformaDiGioco(
                            rs.getString("p_nome"),
                            rs.getString("p_produttore"),
                            rs.getBoolean("p_portatile")
                    );

                    Sviluppatore sviluppatore = new Sviluppatore(
                            rs.getString("s_nome"),
                            rs.getInt("s_id"),
                            rs.getString("s_password"),
                            rs.getDate("s_data_creazione").toLocalDate(),
                            rs.getInt("s_strike"),
                            rs.getString("s_descrizione"),
                            rs.getInt("s_fondi")
                    );

                    String strCat = rs.getString("g_categoria");
                    Categoria catEnum = strCat != null ? Categoria.valueOf(strCat) : null;

                    Gioco gioco = new Gioco(
                            sviluppatore,
                            rs.getInt("g_id"),
                            rs.getString("g_titolo"),
                            catEnum,
                            rs.getInt("g_pegi")
                    );

                    EdizioneGioco edizione = new EdizioneGioco(
                            rs.getInt("eg_id"),
                            gioco,
                            piattaforma,
                            rs.getInt("eg_prezzo"),
                            rs.getDate("eg_data_rilascio").toLocalDate()
                    );

                    Fattura fattura = new Fattura(
                            rs.getInt("f_id"),
                            utente,
                            edizione,
                            rs.getInt("prezzo_acquisto"),
                            rs.getString("key"),
                            rs.getDate("data_acquisto").toLocalDate()
                    );

                    Recensione recensione = new Recensione(
                            rs.getInt("voto"),
                            rs.getString("descrizione"),
                            rs.getInt("differenza_like"),
                            fattura
                    );

                    lista.add(recensione);
                }
            }
        }
        return lista;
    }

    @Override
    public void creaRecensione(int idFattura, int voto, String testo) throws SQLException {
        String query = "INSERT INTO Recensione (id_fattura, voto, descrizione, differenza_like) VALUES (?, ?, ?, 0)";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, idFattura, voto, testo);
    }

    @Override
    public void eliminaRecensione(int idFattura) throws SQLException {
        String query = "DELETE FROM Recensione WHERE id_fattura = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, idFattura);
    }

    @Override
    public void aggiornaRecensione(int idFattura, int nuovoVoto, String nuovoTesto) throws SQLException {
        String query = "UPDATE Recensione SET voto = ?, descrizione = ? WHERE id_fattura = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, nuovoVoto, nuovoTesto, idFattura);
    }

    @Override
    public int getMediaVotiEdizioneGioco(int idEdizioneGioco) throws SQLException {
        String query = "SELECT AVG(r.voto) AS media FROM Recensione r " +
                "JOIN Fattura f ON r.id_fattura = f.id " +
                "WHERE f.id_edizione_gioco = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idEdizioneGioco);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("media");
                }
            }
        }
        return 0;
    }

    @Override
    public void aggiornaDifferenzaLike(int idFattura, int differenza) throws SQLException {
        String query = "UPDATE Recensione SET differenza_like = ? WHERE id_fattura = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, differenza, idFattura);
    }

    @Override
    public ArrayList<Recensione> getRecensioniPerGioco(int idGioco) throws SQLException, CampoNonValidoException {
        ArrayList<Recensione> lista = new ArrayList<>();
        String query = "SELECT " +
                "r.voto, r.descrizione, r.differenza_like, " +
                "f.id AS f_id, f.prezzo_acquisto, f.key, f.data_acquisto, " +
                "u.id_utente AS u_id, a_u.nome AS u_nome, a_u.password AS u_password, a_u.data_creazione AS u_data_creazione, " +
                "u.genere AS u_genere, u.email AS u_email, u.data_nascita AS u_data_nascita, u.saldo AS u_saldo, u.bannato AS u_bannato, " +
                "p.nome AS p_nome, p.produttore AS p_produttore, p.portatile AS p_portatile, " +
                "s.id_sviluppatore AS s_id, a_s.nome AS s_nome, a_s.password AS s_password, a_s.data_creazione AS s_data_creazione, " +
                "s.strike AS s_strike, s.descrizione AS s_descrizione, s.fondi AS s_fondi, " +
                "g.id AS g_id, g.titolo AS g_titolo, g.categoria AS g_categoria, g.pegi AS g_pegi, " +
                "eg.id AS eg_id, eg.prezzo AS eg_prezzo, eg.data_rilascio AS eg_data_rilascio " +
                "FROM Recensione r " +
                "JOIN Fattura f ON r.id_fattura = f.id " +
                "JOIN Utente u ON f.id_utente = u.id_utente " +
                "JOIN Account a_u ON u.id_utente = a_u.id " +
                "JOIN EdizioneGioco eg ON f.id_edizione_gioco = eg.id " +
                "JOIN PiattaformaDiGioco p ON eg.nome_piattaforma = p.nome " +
                "JOIN Gioco g ON eg.id_gioco = g.id " +
                "JOIN Sviluppatore s ON g.id_sviluppatore = s.id_sviluppatore " +
                "JOIN Account a_s ON s.id_sviluppatore = a_s.id " +
                "WHERE eg.id_gioco = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idGioco);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String strGenere = rs.getString("u_genere");
                    GenereEnum genereEnum = strGenere != null ? GenereEnum.valueOf(strGenere) : null;

                    Utente utente = new Utente(
                            rs.getInt("u_id"),
                            rs.getString("u_nome"),
                            rs.getString("u_password"),
                            rs.getDate("u_data_creazione").toLocalDate(),
                            genereEnum,
                            rs.getString("u_email"),
                            rs.getDate("u_data_nascita").toLocalDate(),
                            rs.getInt("u_saldo"),
                            rs.getBoolean("u_bannato")
                    );

                    PiattaformaDiGioco piattaforma = new PiattaformaDiGioco(
                            rs.getString("p_nome"),
                            rs.getString("p_produttore"),
                            rs.getBoolean("p_portatile")
                    );

                    Sviluppatore sviluppatore = new Sviluppatore(
                            rs.getString("s_nome"),
                            rs.getInt("s_id"),
                            rs.getString("s_password"),
                            rs.getDate("s_data_creazione").toLocalDate(),
                            rs.getInt("s_strike"),
                            rs.getString("s_descrizione"),
                            rs.getInt("s_fondi")
                    );

                    String strCat = rs.getString("g_categoria");
                    Categoria catEnum = strCat != null ? Categoria.valueOf(strCat) : null;

                    Gioco gioco = new Gioco(
                            sviluppatore,
                            rs.getInt("g_id"),
                            rs.getString("g_titolo"),
                            catEnum,
                            rs.getInt("g_pegi")
                    );

                    EdizioneGioco edizione = new EdizioneGioco(
                            rs.getInt("eg_id"),
                            gioco,
                            piattaforma,
                            rs.getInt("eg_prezzo"),
                            rs.getDate("eg_data_rilascio").toLocalDate()
                    );

                    Fattura fattura = new Fattura(
                            rs.getInt("f_id"),
                            utente,
                            edizione,
                            rs.getInt("prezzo_acquisto"),
                            rs.getString("key"),
                            rs.getDate("data_acquisto").toLocalDate()
                    );

                    Recensione recensione = new Recensione(
                            rs.getInt("voto"),
                            rs.getString("descrizione"),
                            rs.getInt("differenza_like"),
                            fattura
                    );

                    lista.add(recensione);
                }
            }
        }
        return lista;
    }
}
