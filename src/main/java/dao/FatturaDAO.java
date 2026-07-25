package dao;

import model.CampoNonValidoException;
import model.Fattura;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione della libreria personale e delle {@link Fattura} di acquisto degli utenti.
 */
public interface FatturaDAO {

    /**
     * Recupera la libreria di giochi acquistati da uno specifico utente.
     *
     * @param idUtente L'identificativo univoco dell'utente.
     * @return Un'ArrayList di oggetti {@link Fattura} che compongono la libreria.
     * @throws SQLException Se si verifica un errore di accesso al Database.
     * @throws CampoNonValidoException Se i dati fattura non sono validi.
     */
    ArrayList<Fattura> getLibreriaUtente(int idUtente) throws SQLException, CampoNonValidoException;

    /**
     * Restituisce il numero totale di giochi acquistati da un utente.
     *
     * @param idUtente L'identificativo univoco dell'utente.
     * @return Il numero intero dei giochi acquistati.
     * @throws SQLException Se si verifica un errore con il Database.
     */
    int getNumeroGiochiAcquistati(int idUtente) throws SQLException;

    /**
     * Registra l'avvenuto acquisto inserendo una nuova fattura nel Database.
     *
     * @param fattura L'oggetto {@link Fattura} da memorizzare.
     * @throws SQLException Se la scrittura nel Database fallisce.
     */
    void inserisciFattura(Fattura fattura) throws SQLException;

    /**
     * Gestisce l'operazione di rimborso per un determinato acquisto.
     *
     * @param idFattura L'identificativo della {@link Fattura} da rimborsare.
     * @param idUtente L'identificativo dell'utente che richiede il rimborso.
     * @param importo L'importo economico da restituire.
     * @throws SQLException Se si verifica un errore durante l'aggiornamento dei dati nel Database.
     */
    void effettuaRimborso(int idFattura, int idUtente, int importo) throws SQLException;
}