package dao;

import model.PiattaformaDiGioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PiattaformaDao {
    ArrayList<PiattaformaDiGioco> getTuttePiattaforme()throws SQLException;

}
