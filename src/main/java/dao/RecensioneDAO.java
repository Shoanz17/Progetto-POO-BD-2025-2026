package dao;

import model.Recensione;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RecensioneDAO {
    ArrayList<Recensione> getListaRecensioniUtente(int idUtente) throws SQLException;
}
