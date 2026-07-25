package dao;

import model.CampoNonValidoException;
import model.Gioco;
import model.GiocoInPromozione;
import model.Promozione;

import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Interfaccia che definisce le operazioni di accesso ai dati (CRUD) per l'entità associativa {@link GiocoInPromozione}.
 * Il suo ruolo principale è quello di interrogare la tabella ponte del database che mappa la relazione
 * molti-a-molti tra un {@link Gioco} e le varie {@link Promozione} a cui partecipa o ha partecipato.
 */
public interface GiocoInPromozioneDAO {

    /**
     * Recupera dal database lo storico di tutti gli sconti e le promozioni a cui un determinato videogioco ha preso parte.
     *
     * @param giocoScelto Il {@link Gioco} di cui si vogliono cercare le promozioni.
     * @return Una lista di oggetti {@link GiocoInPromozione} che descrivono le percentuali di sconto applicate.
     * @throws SQLException Se il database rifiuta la connessione o la query fallisce.
     * @throws CampoNonValidoException Se i dati estratti dal DB risultano incoerenti o corrotti, impedendo la ricostruzione dell'oggetto.
     */
    ArrayList<GiocoInPromozione> getPromozioniPerGioco(Gioco giocoScelto) throws SQLException, CampoNonValidoException;

}
