package dao;

import model.PiattaformaDiGioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PiattaformaDAO {
    ArrayList<PiattaformaDiGioco> getTuttePiattaforme()throws SQLException;

}
