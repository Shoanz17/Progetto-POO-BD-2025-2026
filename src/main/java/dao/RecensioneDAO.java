package dao;

import model.Recensione;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RecensioneDAO {
    ArrayList<Recensione> getListaRecensioniUtente(int idUtente) throws SQLException;

    void creaRecensione (int idFattura, int voto, String testo) throws SQLException;
    void eliminaRecensione(int idFattura) throws SQLException;
    void aggiornaRecensione(int idFattura, int nuovoVoto, String nuovoTesto) throws SQLException;

    int getMediaVotiEdizioneGioco() throws SQLException;
    void aggiornaDifferenzaLike(int idFattura, int differenza) throws SQLException;

    int getVotoUtenteSuRecensione(int idUtente, int idFattura) throws SQLException;
    void inserisciVotoRecensione(int idUtente, int idFattura, int voto) throws SQLException;
    void aggiornaVotoRecensione(int idUtente, int idFattura, int nuovoVoto) throws SQLException;
    void eliminaVotoRecensione(int idUtente, int idFattura) throws SQLException;
}
