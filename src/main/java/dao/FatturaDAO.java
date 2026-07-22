package dao;

import model.Fattura;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FatturaDAO {
    ArrayList<Fattura> getLibreriaUtente(int idUtente) throws SQLException;
    ArrayList<Fattura> getListaFatture() throws SQLException;

    void inserisciFattura(Fattura fattura) throws SQLException;

    void effettuaRimborso(int idFattura, int idUtente, int importo) throws SQLException;
}
