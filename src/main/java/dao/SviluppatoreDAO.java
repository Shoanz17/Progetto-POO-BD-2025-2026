package dao;

import model.Sviluppatore;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia che definisce le operazioni di accesso ai dati per l'entità {@link Sviluppatore}.
 * Gestisce il salvataggio dei nuovi account di sviluppo, l'aggiornamento della loro vetrina,
 * l'applicazione del sistema disciplinare a strike e il calcolo delle statistiche di vendita (followers, giochi più venduti).
 */
public interface SviluppatoreDAO {

    /**
     * Inserisce un nuovo account sviluppatore all'interno del database.
     *
     * @param sviluppatore L'oggetto {@link Sviluppatore} contenente le credenziali e i dati di base da salvare.
     * @throws SQLException Se l'inserimento fallisce (es. nome utente già esistente o vincoli violati).
     */
    void registraSviluppatore(Sviluppatore sviluppatore) throws SQLException;

    /**
     * Estrae dal database una lista di sviluppatori in base a una chiave di ricerca.
     * Viene utilizzato principalmente dalla barra di ricerca della Dashboard Admin per trovare software house specifiche.
     *
     * @param testoRicerca La stringa da cercare all'interno del nome o della descrizione dello sviluppatore.
     * @return Una lista di oggetti {@link Sviluppatore} che corrispondono al filtro.
     * @throws SQLException Se la query di ricerca fallisce.
     */
    ArrayList<Sviluppatore> getListaSviluppatoriFiltrati(String testoRicerca) throws SQLException;

    /**
     * Recupera l'elenco completo di tutte le case di sviluppo registrate sulla piattaforma.
     *
     * @return La lista contenente tutti gli oggetti {@link Sviluppatore} del sistema.
     * @throws SQLException Se l'interrogazione al database fallisce.
     */
    ArrayList<Sviluppatore> getListaSviluppatori() throws SQLException;

    /**
     * Esegue un rapido conteggio lato database per determinare quanti {@link model.Gioco}
     * ha pubblicato un determinato sviluppatore. Molto più efficiente rispetto al caricare l'intera lista dei giochi in RAM.
     *
     * @param idSviluppatore L'ID univoco dello sviluppatore da analizzare.
     * @return Il numero totale di giochi rilasciati.
     * @throws SQLException Se la funzione COUNT del database fallisce.
     */
    int getNumeroGiochiRilasciati(int idSviluppatore) throws SQLException;

    /**
     * Incrementa direttamente sul database il contatore delle penalità disciplinari dello sviluppatore.
     * Se questo valore raggiunge 3, l'account viene considerato bannato dal sistema.
     *
     * @param idSviluppatore L'ID dello sviluppatore da punire.
     * @throws SQLException Se l'operazione di UPDATE fallisce.
     */
    void aggiungiStrike(int idSviluppatore) throws SQLException;

    /**
     * Decrementa sul database il contatore delle penalità di uno sviluppatore, condonando un'infrazione.
     *
     * @param idSviluppatore L'ID dello sviluppatore da perdonare.
     * @throws SQLException Se l'operazione di UPDATE fallisce.
     */
    void rimuoviStrike(int idSviluppatore) throws SQLException;

    /**
     * Sovrascrive i dati anagrafici e la vetrina pubblica dello sviluppatore nel database con quelli aggiornati.
     *
     * @param sviluppatore L'istanza di {@link Sviluppatore} contenente le nuove informazioni (es. nuova descrizione).
     * @throws SQLException Se l'aggiornamento viene rifiutato dal DB.
     */
    void aggiornaProfilo(Sviluppatore sviluppatore) throws SQLException;

    /**
     * Esegue una interrogazione statistica sul database
     * per trovare il titolo del videogioco che ha generato il maggior numero di vendite per questa software house.
     *
     * @param idSviluppatore L'ID dello sviluppatore in questione.
     * @return Il nome (titolo) del gioco più venduto, oppure una stringa vuota se non ha ancora venduto nulla.
     * @throws SQLException Se il calcolo statistico fallisce.
     */
    String getGiocoPiuVendutoSviluppatore(int idSviluppatore) throws SQLException;

    /**
     * Conta lato database il numero di {@link model.Utente} che seguono attivamente questo sviluppatore.
     *
     * @param idSviluppatore L'ID della software house.
     * @return Il conteggio totale dei seguaci (follower).
     * @throws SQLException Se l'interrogazione aggregata fallisce.
     */
    int getNumeroSeguaci(int idSviluppatore) throws SQLException;
}
