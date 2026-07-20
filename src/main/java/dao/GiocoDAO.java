package dao;

import model.EdizioneGioco;
import java.sql.SQLException;
import java.util.ArrayList;

public interface GiocoDAO {
    ArrayList<EdizioneGioco> getCatalogoCompleto() throws SQLException;

}