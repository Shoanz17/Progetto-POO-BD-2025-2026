package dao;

import model.CampoNonValidoException;
import model.EdizioneGioco;
import model.Recensione;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RecensioneDAO {
    ArrayList<Recensione> getListaRecensioniUtente(int idUtente) throws SQLException, CampoNonValidoException;
    ArrayList<Recensione> getListaRecensioniEdizione(int idEdizioneGioco) throws SQLException, CampoNonValidoException;
    ArrayList<Recensione> getRecensioniFiltrateAdmin(String testoRicerca) throws SQLException, CampoNonValidoException;

    void creaRecensione (int idFattura, int voto, String testo) throws SQLException;
    void eliminaRecensione(int idFattura) throws SQLException;
    void aggiornaRecensione(int idFattura, int nuovoVoto, String nuovoTesto) throws SQLException;

    int getMediaVotiEdizioneGioco(int idEdizioneGioco) throws SQLException;
    void aggiornaDifferenzaLike(int idFattura, int differenza) throws SQLException;

    ArrayList<Recensione> getRecensioniPerGioco(int idGioco) throws SQLException, CampoNonValidoException;

}
