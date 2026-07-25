package dao;

import model.Promozione;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Interfaccia che definisce le operazioni di accesso ai dati (CRUD) per l'entità {@link Promozione}.
 * Consente di creare nuovi eventi di sconti globali, interrogare lo storico delle promozioni
 * e gestire la tabella ponte che lega i singoli videogiochi agli sconti.
 */
public interface PromozioneDAO {

    /**
     * Inserisce un nuovo evento promozionale nel database.
     *
     * @param nome Il nome commerciale della promozione (es. "Saldi Estivi 2026").
     * @param dataInizio La data di apertura degli sconti.
     * @param dataFine La data in cui gli sconti termineranno.
     * @throws SQLException Se il database rifiuta l'inserimento (es. vincoli violati) o cade la connessione.
     */
    void creaPromozione(String nome, LocalDate dataInizio, LocalDate dataFine) throws SQLException;

    /**
     * Collega un videogioco a una determinata promozione, definendone la percentuale di sconto.
     * Questa operazione va a scrivere direttamente nella tabella associativa del database (l'entità ponte).
     *
     * @param idGioco L'ID univoco del {@link model.Gioco} da mettere in saldo.
     * @param idPromozione L'ID univoco della {@link Promozione} a cui il gioco sta partecipando.
     * @param percentuale Il valore percentuale dello sconto applicato (es. 30 per un -30%).
     * @throws SQLException Se l'inserimento fallisce (es. se si tenta di inserire doppioni o chiavi inesistenti).
     */
    void inserisciGiocoInPromozione(int idGioco, int idPromozione, int percentuale)throws SQLException;

    /**
     * Recupera l'elenco completo di tutte le promozioni (attive, passate e future) registrate a sistema.
     *
     * @return Una lista contenente tutti gli oggetti {@link Promozione} presenti nel database.
     * @throws SQLException Se l'interrogazione al database fallisce.
     */
    ArrayList<Promozione> getTuttePromozioni()throws SQLException;

    /**
     * Estrae dal database una lista di promozioni, filtrandole per nome e permettendo di ordinarle per data.
     * Metodo tipicamente utilizzato per la barra di ricerca e i filtri nella Dashboard dell'Admin.
     *
     * @param testoRicerca La stringa da cercare all'interno del nome della promozione.
     * @param ordinaPerData Flag che indica se ordinare i risultati cronologicamente per data di inizio (true) o lasciare l'ordinamento predefinito del DB (false).
     * @return La lista delle {@link Promozione} che corrispondono ai criteri.
     * @throws SQLException Se l'interrogazione fallisce.
     */
    ArrayList<Promozione> getPromozioniFiltrate(String testoRicerca, boolean ordinaPerData) throws SQLException;
}
