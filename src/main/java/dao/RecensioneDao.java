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

    ArrayList<Recensione> getRecensioniPerGioco(int idGioco) throws SQLException;

}
