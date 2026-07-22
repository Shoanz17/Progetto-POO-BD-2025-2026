package dao;

import model.EdizioneGioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EdizioneGiocoDAO {
    ArrayList<EdizioneGioco> getListaEdizioniSviluppatore(int idSviluppatore) throws SQLException;
    ArrayList<model.EdizioneGioco> getCatalogoCompleto() throws SQLException;

    ArrayList<EdizioneGioco> getListaGiochiCarrello(int idUtente) throws SQLException;

    void inserisciEdizione(EdizioneGioco edizioneGioco)throws SQLException;

}
