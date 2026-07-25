package dao;

import model.CampoNonValidoException;
import model.EdizioneGioco;
import model.Genere;
import model.Gioco;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione delle operazioni di persistenza relative ai {@link Gioco}.
 */
public interface GiocoDAO {

    /**
     * Filtra e restituisce i giochi in base a una stringa di ricerca testuale.
     *
     * @param testoRicerca La stringa digitata per la ricerca dei giochi.
     * @return Un'ArrayList di {@link Gioco} che soddisfano i criteri di ricerca.
     * @throws SQLException Se si verifica un errore di comunicazione con il Database.
     * @throws CampoNonValidoException Se i dati recuperati non sono validi.
     */
    ArrayList<Gioco> getGiochiFiltrati(String testoRicerca) throws SQLException, CampoNonValidoException;

    /**
     * Aggiorna il titolo di uno specifico gioco nel Database.
     *
     * @param idGioco L'identificativo univoco del {@link Gioco}.
     * @param titolo Il nuovo titolo da assegnare.
     * @throws SQLException Se l'aggiornamento nel Database fallisce.
     */
    void updateTitolo(int idGioco, String titolo) throws SQLException;

    /**
     * Aggiorna la categoria di uno specifico gioco nel Database.
     *
     * @param idGioco L'identificativo univoco del {@link Gioco}.
     * @param nomeCategoria Il nome della nuova categoria.
     * @throws SQLException Se si verifica un errore con il Database.
     */
    void updateCategoriaGioco(int idGioco, String nomeCategoria) throws SQLException;

    /**
     * Aggiorna il limite PEGI di uno specifico gioco nel Database.
     *
     * @param idGioco L'identificativo univoco del {@link Gioco}.
     * @param pegi Il nuovo valore PEGI.
     * @throws SQLException Se l'operazione di aggiornamento fallisce.
     */
    void updatePegiGioco(int idGioco, int pegi) throws SQLException;

    /**
     * Aggiorna l'elenco dei generi associati a uno specifico gioco nel Database.
     *
     * @param idGioco L'identificativo univoco del {@link Gioco}.
     * @param nuoviGeneri Un'ArrayList con i nuovi oggetti {@link Genere} da associare.
     * @throws SQLException Se si verifica un errore durante l'aggiornamento.
     */
    void updateGeneriGioco(int idGioco, ArrayList<Genere> nuoviGeneri) throws SQLException;

    /**
     * Recupera la lista completa di tutti i giochi presenti nel sistema.
     *
     * @return Un'ArrayList contenente tutti i {@link Gioco} registrati.
     * @throws SQLException Se si verifica un errore di accesso al Database.
     */
    ArrayList<Gioco> getListaGiochi() throws SQLException;

    /**
     * Aggiorna le informazioni generali di un gioco nel Database.
     *
     * @param gioco L'oggetto {@link Gioco} con i dati aggiornati.
     * @throws SQLException Se il salvataggio dei dati fallisce.
     */
    void aggiornaGioco(Gioco gioco) throws SQLException;

    /**
     * Inserisce un nuovo gioco all'interno del Database.
     *
     * @param gioco L'oggetto {@link Gioco} da inserire.
     * @return L'identificativo generato per il nuovo gioco inserito.
     * @throws SQLException Se l'inserimento nel Database fallisce.
     */
    int inserisciGioco(Gioco gioco) throws SQLException;

    /**
     * Inserisce un'edizione di gioco collegandola a uno specifico gioco nel Database.
     *
     * @param edizioneGioco L'oggetto {@link EdizioneGioco} da aggiungere.
     * @param idGioco L'identificativo univoco del {@link Gioco} di riferimento.
     * @throws SQLException Se l'operazione di inserimento fallisce.
     */
    void inserisciEdizione(EdizioneGioco edizioneGioco, int idGioco) throws SQLException;

    /**
     * Calcola e restituisce il numero totale di unità vendute per un determinato gioco.
     *
     * @param titoloGioco Il titolo del {@link Gioco}.
     * @return Il numero intero delle unità vendute.
     * @throws SQLException Se si verifica un errore con il Database.
     */
    int getUnitaVendutePerGioco(String titoloGioco) throws SQLException;

    /**
     * Calcola e restituisce il guadagno economico totale generato da un determinato gioco.
     *
     * @param titoloGioco Il titolo del {@link Gioco}.
     * @return Il valore del guadagno totale complessivo.
     * @throws SQLException Se si verifica un errore durante la query sul Database.
     */
    int getGuadagnoTotalePerGioco(String titoloGioco) throws SQLException;

    /**
     * Recupera l'elenco dei giochi sviluppati da uno specifico sviluppatore.
     *
     * @param idSviluppatore L'identificativo univoco dello sviluppatore.
     * @return Un'ArrayList di {@link Gioco} associati allo sviluppatore.
     * @throws SQLException Se si verifica un errore di accesso al Database.
     */
    ArrayList<Gioco> getGiochiSviluppatore(int idSviluppatore) throws SQLException;
}