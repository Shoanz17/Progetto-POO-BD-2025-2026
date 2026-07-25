package dao;

import model.CampoNonValidoException;
import model.Gioco;
import model.PiattaformaDiGioco;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia che definisce le operazioni di accesso ai dati per l'entità {@link PiattaformaDiGioco}.
 * Gestisce il salvataggio e il recupero dal database delle console o sistemi (es. PC, PlayStation, Xbox)
 * su cui vengono pubblicate le varie edizioni dei videogiochi.
 */
public interface PiattaformaDiGiocoDAO {
    /**
     * Inserisce una nuova piattaforma di gioco all'interno del database.
     *
     * @param piattaforma L'oggetto {@link PiattaformaDiGioco} da salvare a sistema.
     * @throws SQLException Se il database rifiuta l'inserimento o cade la connessione.
     */
    void creaPiattaforma(PiattaformaDiGioco piattaforma) throws SQLException;

    /**
     * Recupera dal database una lista di piattaforme filtrate in base a una chiave di ricerca.
     * Viene tipicamente utilizzato dalla barra di ricerca nella GUI dell'Admin.
     *
     * @param testoRicerca Il testo parziale da cercare all'interno del nome o del produttore della piattaforma.
     * @return Una lista di {@link PiattaformaDiGioco} che corrispondono ai criteri.
     * @throws SQLException Se la query fallisce.
     * @throws CampoNonValidoException Se i dati estratti dal DB sono corrotti o incoerenti e impediscono la creazione dell'oggetto.
     */
    ArrayList<PiattaformaDiGioco> getPiattaformeFiltrate(String testoRicerca) throws SQLException, CampoNonValidoException;

    /**
     * Recupera l'elenco completo di tutte le piattaforme registrate a sistema.
     * Metodo utile per popolare le tendine di selezione (ComboBox) in fase di creazione di una nuova edizione.
     * @return La lista completa di tutti gli oggetti {@link PiattaformaDiGioco} presenti nel DB.
     * @throws SQLException Se l'interrogazione al database fallisce.
     */
    ArrayList<PiattaformaDiGioco> getListaPiattaforme() throws SQLException;

    /**
     * Recupera l'elenco di tutte le piattaforme su cui è sbarcato un determinato videogioco.
     * Il DAO si occupa di risalire alle piattaforme passando attraverso le edizioni associate al gioco.
     * @param gioco Il {@link Gioco} di cui si vogliono conoscere le console di rilascio.
     * @return Una lista di {@link PiattaformaDiGioco} compatibili con il titolo passato.
     * @throws SQLException Se l'interrogazione al database fallisce.
     */
    ArrayList<PiattaformaDiGioco> getListaPiattaformeDaGioco(Gioco gioco) throws SQLException;
}
