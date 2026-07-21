package dao;

import model.Genere;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenereDao {

    ArrayList<Genere> getListaGeneri()throws SQLException;

    void collegaGeneriAGioco(int idGioco, ArrayList<Genere> generi)throws SQLException;


}

