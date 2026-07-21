package dao;

import model.Recensione;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RecensioneDao {

    ArrayList<Recensione> getRecensioniPerGioco(int idGioco) throws SQLException;

}
