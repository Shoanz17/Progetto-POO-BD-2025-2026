package dao;

import model.Sviluppatore;
import model.Utente;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione delle operazioni di persistenza relative agli {@link Utente}.
 */
public interface UtenteDAO {

    /**
     * Registra un nuovo utente all'interno del Database.
     *
     * @param utente L'oggetto {@link Utente} da registrare.
     * @throws SQLException Se si verifica un errore durante l'inserimento nel Database.
     */
    void registraUtente(Utente utente) throws SQLException;

    /**
     * Restituisce una lista di utenti filtrati per nome o stato di ban, utile per la sezione amministrativa.
     *
     * @param testoRicerca La stringa digitata per filtrare gli utenti.
     * @param bannato Un valore booleano per filtrare in base allo stato di ban.
     * @return Un'ArrayList di {@link Utente} filtrati.
     * @throws SQLException Se si verifica un errore con il Database.
     */
    ArrayList<Utente> getUtentiFiltratiAdmin(String testoRicerca, boolean bannato) throws SQLException;

    /**
     * Recupera un utente specifico cercandolo tramite il suo identificativo univoco.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @return L'oggetto {@link Utente} corrispondente.
     * @throws SQLException Se si verifica un errore di accesso al Database.
     */
    Utente getUtenteById(int idUtente) throws SQLException;

    /**
     * Inverte lo stato di ban (da bannato a non bannato e viceversa) per un determinato utente.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @throws SQLException Se l'aggiornamento nel Database fallisce.
     */
    void invertiStatoBan(int idUtente) throws SQLException;

    /**
     * Imposta lo stato di un utente come bannato nel Database.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @throws SQLException Se si verifica un errore di comunicazione con il Database.
     */
    void setBannato(int idUtente) throws SQLException;

    /**
     * Recupera l'elenco completo di tutti gli utenti registrati nel sistema.
     *
     * @return Un'ArrayList contenente tutti gli {@link Utente}.
     * @throws SQLException Se si verifica un errore con il Database.
     */
    ArrayList<Utente> getListaUtenti() throws SQLException;

    /**
     * Aggiorna le informazioni del profilo di un utente nel Database.
     *
     * @param utente L'oggetto {@link Utente} con i dati aggiornati.
     * @throws SQLException Se il salvataggio dei dati fallisce.
     */
    void aggiornaProfiloUtente(Utente utente) throws SQLException;

    /**
     * Aggiunge una quantità di fondi al saldo disponibile di un utente.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @param importoDaAggiungere L'importo economico da accreditare.
     * @throws SQLException Se l'aggiornamento del saldo nel Database fallisce.
     */
    void aggiungiSaldo(int idUtente, int importoDaAggiungere) throws SQLException;

    /**
     * Inserisce un nuovo legame di amicizia tra due utenti nel Database.
     *
     * @param idUtente L'identificativo dell' {@link Utente} principale.
     * @param idAmico L'identificativo dell'utente da aggiungere come amico.
     * @throws SQLException Se l'operazione di inserimento fallisce.
     */
    void inserisciAmico(int idUtente, int idAmico) throws SQLException;

    /**
     * Rimuove il legame di amicizia tra due utenti nel Database.
     *
     * @param idUtente L'identificativo dell' {@link Utente} principale.
     * @param idAmico L'identificativo dell'amico da rimuovere.
     * @throws SQLException Se l'operazione di rimozione fallisce.
     */
    void eliminaAmico(int idUtente, int idAmico) throws SQLException;

    /**
     * Recupera la lista degli amici associati a uno specifico utente.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @return Un'ArrayList di {@link Utente} amici.
     * @throws SQLException Se si verifica un errore con il Database.
     */
    ArrayList<Utente> getListaAmici(int idUtente) throws SQLException;

    /**
     * Recupera la lista degli sviluppatori seguiti da uno specifico utente.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @return Un'ArrayList di {@link Sviluppatore} seguiti.
     * @throws SQLException Se si verifica un errore con il Database.
     */
    ArrayList<Sviluppatore> getListaSeguiti(int idUtente) throws SQLException;

    /**
     * Aggiunge uno sviluppatore all'elenco dei seguiti da parte dell'utente.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @param idSviluppatore L'identificativo dello {@link Sviluppatore} da seguire.
     * @throws SQLException Se l'operazione di inserimento fallisce.
     */
    void inserisciSviluppatoreSeguito(int idUtente, int idSviluppatore) throws SQLException;

    /**
     * Rimuove uno sviluppatore dall'elenco dei seguiti da parte dell'utente.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @param idSviluppatore L'identificativo dello {@link Sviluppatore} da smettere di seguire.
     * @throws SQLException Se l'operazione di rimozione fallisce.
     */
    void eliminaSviluppatoreSeguito(int idUtente, int idSviluppatore) throws SQLException;

    /**
     * Inserisce un'edizione di gioco all'interno del carrello persistente dell'utente.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @param idEdizione L'identificativo dell' {@link model.EdizioneGioco} da aggiungere.
     * @throws SQLException Se l'inserimento nel carrello fallisce.
     */
    void inserisciCarrello(int idUtente, int idEdizione) throws SQLException;

    /**
     * Rimuove un'edizione specifica dal carrello persistente dell'utente.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @param idEdizione L'identificativo dell' {@link model.EdizioneGioco} da rimuovere.
     * @throws SQLException Se la rimozione dal carrello fallisce.
     */
    void eliminaCarrello(int idUtente, int idEdizione) throws SQLException;

    /**
     * Svuota completamente il carrello persistente di uno specifico utente nel Database.
     *
     * @param idUtente L'identificativo dell' {@link Utente}.
     * @throws SQLException Se l'operazione di svuotamento fallisce.
     */
    void svuotaCarrello(int idUtente) throws SQLException;
}