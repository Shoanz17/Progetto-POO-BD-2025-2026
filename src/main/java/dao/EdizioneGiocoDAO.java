package dao;

import model.EdizioneGioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EdizioneGiocoDAO {
    ArrayList<model.EdizioneGioco> getCatalogoCompleto() throws SQLException;

    ArrayList<EdizioneGioco> getListaGiochiCarrello(int idUtente) throws SQLException;
}
