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
                "r.voto, r.descrizione, r.differenzaLike, " +
                "f.idFattura AS f_id, f.prezzoAcquisto AS f_prezzo_acquisto, f.key, f.dataAcquisto AS f_data_acquisto, " +
                "u.idUtente AS u_id, a_u.nome AS u_nome, a_u.password AS u_password, a_u.dataCreazione AS u_data_creazione, " +
                "u.genere AS u_genere, u.email AS u_email, u.dataNascita AS u_data_nascita, u.saldo AS u_saldo, u.bannato AS u_bannato, " +
                "p.nome AS p_nome, p.produttore AS p_produttore, p.portatile AS p_portatile, " +
                "s.idSviluppatore AS s_id, a_s.nome AS s_nome, a_s.password AS s_password, a_s.dataCreazione AS s_data_creazione, " +
                "s.strike AS s_strike, s.descrizione AS s_descrizione, s.fondi AS s_fondi, " +
                "g.idGioco AS g_id, g.titolo AS g_titolo, g.categoria AS g_categoria, g.pegi AS g_pegi, " +
                "eg.idEdizione AS eg_id, eg.prezzo AS eg_prezzo, eg.dataRilascio AS eg_data_rilascio " +
                "FROM recensione r " +
                "JOIN fattura f ON r.idFattura = f.idFattura " +
                "JOIN utente u ON f.idUtente = u.idUtente " +
                "JOIN account a_u ON u.idUtente = a_u.idAccount " +
                "JOIN edizione_gioco eg ON f.idEdizione = eg.idEdizione " +
                "JOIN piattaforma_di_gioco p ON eg.nomePiattaforma = p.nome " +
                "JOIN gioco g ON eg.idGioco = g.idGioco " +
                "JOIN sviluppatore s ON g.idSviluppatore = s.idSviluppatore " +
                "JOIN account a_s ON s.idSviluppatore = a_s.idAccount " +
                "WHERE f.idUtente = ?";

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
                            rs.getInt("f_prezzo_acquisto"),
                            rs.getString("key"),
                            rs.getDate("f_data_acquisto").toLocalDate()
                    );

                    Recensione recensione = new Recensione(
                            rs.getInt("voto"),
                            rs.getString("descrizione"),
                            rs.getInt("differenzaLike"),
                            fattura
                    );

                    fattura.setRecensione(recensione);

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
                "r.voto, r.descrizione, r.differenzaLike, " +
                "f.idFattura AS f_id, f.prezzoAcquisto AS f_prezzo_acquisto, f.key, f.dataAcquisto AS f_data_acquisto, " +
                "u.idUtente AS u_id, a_u.nome AS u_nome, a_u.password AS u_password, a_u.dataCreazione AS u_data_creazione, " +
                "u.genere AS u_genere, u.email AS u_email, u.dataNascita AS u_data_nascita, u.saldo AS u_saldo, u.bannato AS u_bannato, " +
                "p.nome AS p_nome, p.produttore AS p_produttore, p.portatile AS p_portatile, " +
                "s.idSviluppatore AS s_id, a_s.nome AS s_nome, a_s.password AS s_password, a_s.dataCreazione AS s_data_creazione, " +
                "s.strike AS s_strike, s.descrizione AS s_descrizione, s.fondi AS s_fondi, " +
                "g.idGioco AS g_id, g.titolo AS g_titolo, g.categoria AS g_categoria, g.pegi AS g_pegi, " +
                "eg.idEdizione AS eg_id, eg.prezzo AS eg_prezzo, eg.dataRilascio AS eg_data_rilascio " +
                "FROM recensione r " +
                "JOIN fattura f ON r.idFattura = f.idFattura " +
                "JOIN utente u ON f.idUtente = u.idUtente " +
                "JOIN account a_u ON u.idUtente = a_u.idAccount " +
                "JOIN edizione_gioco eg ON f.idEdizione = eg.idEdizione " +
                "JOIN piattaforma_di_gioco p ON eg.nomePiattaforma = p.nome " +
                "JOIN gioco g ON eg.idGioco = g.idGioco " +
                "JOIN sviluppatore s ON g.idSviluppatore = s.idSviluppatore " +
                "JOIN account a_s ON s.idSviluppatore = a_s.idAccount " +
                "WHERE f.idEdizione = ?";

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
                            rs.getInt("f_prezzo_acquisto"),
                            rs.getString("key"),
                            rs.getDate("f_data_acquisto").toLocalDate()
                    );

                    Recensione recensione = new Recensione(
                            rs.getInt("voto"),
                            rs.getString("descrizione"),
                            rs.getInt("differenzaLike"),
                            fattura
                    );

                    fattura.setRecensione(recensione);
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
                "r.voto, r.descrizione, r.differenzaLike, " +
                "f.idFattura AS f_id, f.prezzoAcquisto AS f_prezzo_acquisto, f.key, f.dataAcquisto AS f_data_acquisto, " +
                "u.idUtente AS u_id, a_u.nome AS u_nome, a_u.password AS u_password, a_u.dataCreazione AS u_data_creazione, " +
                "u.genere AS u_genere, u.email AS u_email, u.dataNascita AS u_data_nascita, u.saldo AS u_saldo, u.bannato AS u_bannato, " +
                "p.nome AS p_nome, p.produttore AS p_produttore, p.portatile AS p_portatile, " +
                "s.idSviluppatore AS s_id, a_s.nome AS s_nome, a_s.password AS s_password, a_s.dataCreazione AS s_data_creazione, " +
                "s.strike AS s_strike, s.descrizione AS s_descrizione, s.fondi AS s_fondi, " +
                "g.idGioco AS g_id, g.titolo AS g_titolo, g.categoria AS g_categoria, g.pegi AS g_pegi, " +
                "eg.idEdizione AS eg_id, eg.prezzo AS eg_prezzo, eg.dataRilascio AS eg_data_rilascio " +
                "FROM recensione r " +
                "JOIN fattura f ON r.idFattura = f.idFattura " +
                "JOIN utente u ON f.idUtente = u.idUtente " +
                "JOIN account a_u ON u.idUtente = a_u.idAccount " +
                "JOIN edizione_gioco eg ON f.idEdizione = eg.idEdizione " +
                "JOIN piattaforma_di_gioco p ON eg.nomePiattaforma = p.nome " +
                "JOIN gioco g ON eg.idGioco = g.idGioco " +
                "JOIN sviluppatore s ON g.idSviluppatore = s.idSviluppatore " +
                "JOIN account a_s ON s.idSviluppatore = a_s.idAccount " +
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
                            rs.getInt("f_prezzo_acquisto"),
                            rs.getString("key"),
                            rs.getDate("f_data_acquisto").toLocalDate()
                    );

                    Recensione recensione = new Recensione(
                            rs.getInt("voto"),
                            rs.getString("descrizione"),
                            rs.getInt("differenzaLike"),
                            fattura
                    );

                    fattura.setRecensione(recensione);
                    lista.add(recensione);
                }
            }
        }
        return lista;
    }

    @Override
    public void creaRecensione(int idFattura, int voto, String descrizione) throws SQLException {
        String query = "INSERT INTO recensione (idFattura, voto, descrizione) " +
                "VALUES (?, ?, ?) " +
                "ON CONFLICT (idFattura) " +
                "DO UPDATE SET voto = EXCLUDED.voto, descrizione = EXCLUDED.descrizione";

        ConnessioneDatabase.getInstance().eseguiUpdate(query, idFattura, voto, descrizione);
    }
    @Override
    public void eliminaRecensione(int idFattura) throws SQLException {
        String query = "DELETE FROM recensione WHERE idFattura = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, idFattura);
    }

    @Override
    public void aggiornaRecensione(int idFattura, int nuovoVoto, String nuovoTesto) throws SQLException {
        String query = "UPDATE recensione SET voto = ?, descrizione = ? WHERE idFattura = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, nuovoVoto, nuovoTesto, idFattura);
    }

    @Override
    public int getMediaVotiEdizioneGioco(int idEdizioneGioco) throws SQLException {
        String query = "SELECT AVG(r.voto) AS media FROM recensione r " +
                "JOIN fattura f ON r.idFattura = f.idFattura " +
                "WHERE f.idEdizione = ?";

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
        String query = "UPDATE recensione SET differenzaLike = ? WHERE idFattura = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, differenza, idFattura);
    }

    @Override
    public ArrayList<Recensione> getRecensioniPerGioco(int idGioco) throws SQLException, CampoNonValidoException {
        ArrayList<Recensione> lista = new ArrayList<>();
        String query = "SELECT " +
                "r.voto, r.descrizione, r.differenzaLike, " +
                "f.idFattura AS f_id, f.prezzoAcquisto AS f_prezzo_acquisto, f.key, f.dataAcquisto AS f_data_acquisto, " +
                "u.idUtente AS u_id, a_u.nome AS u_nome, a_u.password AS u_password, a_u.dataCreazione AS u_data_creazione, " +
                "u.genere AS u_genere, u.email AS u_email, u.dataNascita AS u_data_nascita, u.saldo AS u_saldo, u.bannato AS u_bannato, " +
                "p.nome AS p_nome, p.produttore AS p_produttore, p.portatile AS p_portatile, " +
                "s.idSviluppatore AS s_id, a_s.nome AS s_nome, a_s.password AS s_password, a_s.dataCreazione AS s_data_creazione, " +
                "s.strike AS s_strike, s.descrizione AS s_descrizione, s.fondi AS s_fondi, " +
                "g.idGioco AS g_id, g.titolo AS g_titolo, g.categoria AS g_categoria, g.pegi AS g_pegi, " +
                "eg.idEdizione AS eg_id, eg.prezzo AS eg_prezzo, eg.dataRilascio AS eg_data_rilascio " +
                "FROM recensione r " +
                "JOIN fattura f ON r.idFattura = f.idFattura " +
                "JOIN utente u ON f.idUtente = u.idUtente " +
                "JOIN account a_u ON u.idUtente = a_u.idAccount " +
                "JOIN edizione_gioco eg ON f.idEdizione = eg.idEdizione " +
                "JOIN piattaforma_di_gioco p ON eg.nomePiattaforma = p.nome " +
                "JOIN gioco g ON eg.idGioco = g.idGioco " +
                "JOIN sviluppatore s ON g.idSviluppatore = s.idSviluppatore " +
                "JOIN account a_s ON s.idSviluppatore = a_s.idAccount " +
                "WHERE eg.idGioco = ?";

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
                            rs.getInt("f_prezzo_acquisto"),
                            rs.getString("key"),
                            rs.getDate("f_data_acquisto").toLocalDate()
                    );

                    Recensione recensione = new Recensione(
                            rs.getInt("voto"),
                            rs.getString("descrizione"),
                            rs.getInt("differenzaLike"),
                            fattura
                    );

                    lista.add(recensione);
                }
            }
        }
        return lista;
    }
}
