package dao;

import model.Gioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GiocoDAO {
    ArrayList<Gioco> getListaGiochi() throws SQLException;
}