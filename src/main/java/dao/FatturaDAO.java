package dao;

import model.Fattura;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FatturaDAO {
    ArrayList<Fattura> getLibreriaUtente(int idUtente) throws SQLException;
    ArrayList<Fattura> getListaFatture() throws SQLException;
}
