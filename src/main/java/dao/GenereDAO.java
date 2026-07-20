package dao;

import model.Genere;
import model.Gioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenereDAO {
    ArrayList<Genere> getListaGeneri() throws SQLException;
    ArrayList<Genere> getListaGeneriDaGioco(Gioco gioco) throws SQLException;
}
