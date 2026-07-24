package dao;

import model.CampoNonValidoException;
import model.EdizioneGioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EdizioneGiocoDAO {
    ArrayList<EdizioneGioco> getListaEdizioniSviluppatore(int idSviluppatore) throws SQLException;
    ArrayList<EdizioneGioco> getCatalogoCompleto() throws SQLException, CampoNonValidoException;

    ArrayList<EdizioneGioco> getListaGiochiCarrello(int idUtente) throws SQLException;

    void inserisciEdizione(EdizioneGioco edizioneGioco)throws SQLException;
    ArrayList<EdizioneGioco> getEdizioniDaGioco(int idGioco) throws SQLException;
}
