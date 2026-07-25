package dao;

import model.CampoNonValidoException;
import model.EdizioneGioco;
import model.Recensione;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia che definisce le operazioni di accesso ai dati (CRUD) per l'entità {@link Recensione}.
 * Gestisce il salvataggio, l'aggiornamento e la lettura delle valutazioni lasciate dalla community.
 * Poiché il sistema vieta il review bombing e richiede il possesso del gioco, quasi tutte le
 * operazioni di scrittura e identificazione avvengono sfruttando l'ID della {@link model.Fattura} come chiave univoca.
 */
public interface RecensioneDAO {

    /**
     * Recupera dal database l'elenco completo di tutti i pareri scritti da un determinato utente.
     *
     * @param idUtente L'ID dell'{@link model.Utente} autore.
     * @return Una lista di oggetti {@link Recensione} associati all'utente.
     * @throws SQLException Se l'interrogazione al database fallisce.
     * @throws CampoNonValidoException Se i dati recuperati (voti fuori scala, testi oltre il limite) risultano corrotti.
     */
    ArrayList<Recensione> getListaRecensioniUtente(int idUtente) throws SQLException, CampoNonValidoException;

    /**
     * Recupera tutte le valutazioni lasciate dai giocatori per una specifica edizione fisica/digitale.
     *
     * @param idEdizioneGioco L'ID dell'{@link EdizioneGioco} sotto esame.
     * @return La lista delle {@link Recensione} associate a quella copia.
     * @throws SQLException Se l'interrogazione al database fallisce.
     * @throws CampoNonValidoException Se si tenta di istanziare una recensione con dati anomali presi dal DB.
     */
    ArrayList<Recensione> getListaRecensioniEdizione(int idEdizioneGioco) throws SQLException, CampoNonValidoException;

    /**
     * Estrae dal database una lista di recensioni in base a una chiave di ricerca, permettendo
     * la moderazione globale. Utilizzato nella Dashboard dell'Admin.
     *
     * @param testoRicerca La stringa da cercare all'interno del testo della recensione, nel nome dell'autore o nel gioco.
     * @return Una lista di {@link Recensione} filtrate.
     * @throws SQLException Se la query di ricerca fallisce.
     * @throws CampoNonValidoException Se il DB restituisce dati malformati.
     */
    ArrayList<Recensione> getRecensioniFiltrateAdmin(String testoRicerca) throws SQLException, CampoNonValidoException;

    /**
     * Inserisce fisicamente una nuova recensione nel database, agganciandola alla fattura d'acquisto
     * che ne certifica il possesso legale.
     *
     * @param idFattura L'ID della {@link model.Fattura} che funge da pass d'accesso.
     * @param voto Il punteggio numerico assegnato.
     * @param testo Le motivazioni testuali del giocatore.
     * @throws SQLException Se il database rifiuta l'inserimento (es. l'utente aveva già recensito questo acquisto).
     */
    void creaRecensione (int idFattura, int voto, String testo) throws SQLException;

    /**
     * Rimuove definitivamente una recensione dal database.
     * Azione richiamata quando un utente cancella il suo parere o quando un Admin modera contenuti inopportuni.
     *
     * @param idFattura L'ID della fattura che fa da chiave primaria alla recensione da eliminare.
     * @throws SQLException Se l'operazione di DELETE fallisce.
     */
    void eliminaRecensione(int idFattura) throws SQLException;

    /**
     * Sovrascrive una recensione preesistente aggiornandone i contenuti.
     *
     * @param idFattura L'ID della fattura legata alla recensione da modificare.
     * @param nuovoVoto Il nuovo punteggio assegnato al gioco.
     * @param nuovoTesto Il nuovo testo della recensione.
     * @throws SQLException Se l'operazione di UPDATE fallisce.
     */
    void aggiornaRecensione(int idFattura, int nuovoVoto, String nuovoTesto) throws SQLException;

    /**
     * Calcola direttamente tramite query sul database la media matematica di tutti i voti assegnati
     * a una specifica edizione del gioco.
     *
     * @param idEdizioneGioco L'ID dell'{@link EdizioneGioco}.
     * @return Il valore medio dei voti.
     * @throws SQLException Se il calcolo lato database fallisce.
     */
    int getMediaVotiEdizioneGioco(int idEdizioneGioco) throws SQLException;

    /**
     * Aggiorna nel database il contatore di popolarità di una recensione (il bilancio tra Like e Dislike).
     *
     * @param idFattura L'ID identificativo della recensione tramite la sua fattura.
     * @param differenza Il nuovo valore della somma algebrica dei feedback della community.
     * @throws SQLException Se l'update fallisce.
     */
    void aggiornaDifferenzaLike(int idFattura, int differenza) throws SQLException;

    /**
     * Conta rapidamente lato database il numero totale di recensioni rilasciate da un utente.
     * Molto più efficiente rispetto al caricare in RAM l'intera lista di recensioni solo per contarle.
     *
     * @param idUtente L'ID dell'utente da analizzare.
     * @return Il conteggio totale delle recensioni scritte.
     * @throws SQLException Se la funzione di COUNT fallisce.
     */
    int getNumeroRecensioni(int idUtente) throws SQLException;

    /**
     * Recupera massivamente tutte le recensioni relative a un videogioco base, raggruppando assieme
     * i pareri lasciati per tutte le sue edizioni sulle varie piattaforme.
     *
     * @param idGioco L'ID del {@link model.Gioco} radice.
     * @return La lista completa di tutte le {@link Recensione} del franchise.
     * @throws SQLException Se la complessa interrogazione relazionale fallisce.
     * @throws CampoNonValidoException Se il DB restituisce informazioni corrotte.
     */
    ArrayList<Recensione> getRecensioniPerGioco(int idGioco) throws SQLException, CampoNonValidoException;

}