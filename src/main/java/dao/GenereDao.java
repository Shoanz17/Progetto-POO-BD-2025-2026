package dao;

import model.Genere;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenereDao {

    ArrayList<Genere> getTuttiGeneri()throws SQLException;

    void collegaGeneriAGioco(int idGioco, ArrayList<Genere> generi)throws SQLException;


}

