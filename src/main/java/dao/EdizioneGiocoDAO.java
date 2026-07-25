package dao;

import model.CampoNonValidoException;
import model.EdizioneGioco;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione delle operazioni di persistenza relative alle {@link EdizioneGioco}.
 */
public interface EdizioneGiocoDAO {

    /**
     * Recupera la lista di tutte le edizioni di giochi pubblicate da uno specifico sviluppatore.
     *
     * @param idSviluppatore L'identificativo univoco dello sviluppatore.
     * @return Un'ArrayList contenente le {@link EdizioneGioco} associate allo sviluppatore.
     * @throws SQLException Se si verifica un errore di accesso al Database.
     */
    ArrayList<EdizioneGioco> getListaEdizioniSviluppatore(int idSviluppatore) throws SQLException;

    /**
     * Recupera l'intero catalogo disponibile di tutte le edizioni di giochi presenti nel sistema.
     *
     * @return Un'ArrayList con tutte le {@link EdizioneGioco} disponibili nel catalogo.
     * @throws SQLException Se si verifica un errore con il Database.
     * @throws CampoNonValidoException Se i dati recuperati non rispettano i vincoli del modello.
     */
    ArrayList<EdizioneGioco> getCatalogoCompleto() throws SQLException, CampoNonValidoException;

    /**
     * Recupera l'elenco dei giochi inseriti nel carrello da un determinato utente.
     *
     * @param idUtente L'identificativo univoco dell'utente.
     * @return Un'ArrayList di {@link EdizioneGioco} presenti nel carrello.
     * @throws SQLException Se si verifica un errore di comunicazione con il Database.
     */
    ArrayList<EdizioneGioco> getListaGiochiCarrello(int idUtente) throws SQLException;

    /**
     * Inserisce una nuova edizione di gioco all'interno del Database.
     *
     * @param edizioneGioco L'oggetto {@link EdizioneGioco} da inserire.
     * @throws SQLException Se l'inserimento nel Database fallisce.
     */
    void inserisciEdizione(EdizioneGioco edizioneGioco) throws SQLException;

    /**
     * Recupera tutte le edizioni esistenti associate a un determinato gioco.
     *
     * @param idGioco L'identificativo univoco del {@link model.Gioco}.
     * @return Un'ArrayList di {@link EdizioneGioco} legate a quel gioco.
     * @throws SQLException Se si verifica un errore con il Database.
     */
    ArrayList<EdizioneGioco> getEdizioniDaGioco(int idGioco) throws SQLException;
}