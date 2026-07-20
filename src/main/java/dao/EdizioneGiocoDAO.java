package dao;

import model.EdizioneGioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EdizioneGiocoDAO {
    ArrayList<EdizioneGioco> getListaEdizioniSviluppatore(int idSviluppatore) throws SQLException;
}
