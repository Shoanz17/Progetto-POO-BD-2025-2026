package dao;

import model.CampoNonValidoException;
import model.Genere;
import model.Gioco;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione dei generi videoludici e delle relative associazioni ai {@link Gioco}.
 */
public interface GenereDAO {

    /**
     * Inserisce un nuovo {@link Genere} all'interno del Database.
     *
     * @param genere L'oggetto {@link Genere} da creare.
     * @throws SQLException Se si verifica un errore di inserimento nel Database.
     */
    void creaGenere(Genere genere) throws SQLException;

    /**
     * Recupera la lista completa di tutti i generi disponibili nel sistema.
     *
     * @return Un'ArrayList contenente tutti i {@link Genere} registrati.
     * @throws SQLException Se si verifica un errore con il Database.
     * @throws CampoNonValidoException Se si riscontrano dati non validi nel recupero.
     */
    ArrayList<Genere> getListaGeneri() throws SQLException, CampoNonValidoException;

    /**
     * Recupera l'elenco dei generi associati a uno specifico gioco.
     *
     * @param gioco L'oggetto {@link Gioco} di cui si vogliono conoscere i generi.
     * @return Un'ArrayList di {@link Genere} associati al gioco.
     * @throws SQLException Se si verifica un errore di comunicazione con il Database.
     * @throws CampoNonValidoException Se i dati non sono validi.
     */
    ArrayList<Genere> getListaGeneriDaGioco(Gioco gioco) throws SQLException, CampoNonValidoException;

    /**
     * Filtra e restituisce i generi in base a una stringa di ricerca testuale.
     *
     * @param testoRicerca La stringa digitata per filtrare i generi.
     * @return Un'ArrayList di {@link Genere} corrispondenti al criterio di ricerca.
     * @throws SQLException Se si verifica un errore con il Database.
     * @throws CampoNonValidoException Se i dati non sono validi.
     */
    ArrayList<Genere> getGeneriFiltrati(String testoRicerca) throws SQLException, CampoNonValidoException;

    /**
     * Recupera una lista di oggetti {@link Genere} partendo direttamente dai loro nomi testuali.
     *
     * @param listaNomi Un'ArrayList di stringhe contenenti i nomi dei generi.
     * @return L' {@link ArrayList} di oggetti {@link Genere} corrispondenti.
     * @throws SQLException Se si verifica un errore con il Database.
     * @throws CampoNonValidoException Se qualche nome risulta non valido.
     */
    ArrayList<Genere> getGeneriDaListaNomi(ArrayList<String> listaNomi) throws SQLException, CampoNonValidoException;

    /**
     * Associa una lista di generi a uno specifico gioco nel Database.
     *
     * @param idGioco L'identificativo univoco del {@link Gioco}.
     * @param generi Un'ArrayList di {@link Genere} da collegare al gioco.
     * @throws SQLException Se si verifica un errore durante l'operazione di salvataggio dei legami.
     */
    void collegaGeneriAGioco(int idGioco, ArrayList<Genere> generi) throws SQLException;
}