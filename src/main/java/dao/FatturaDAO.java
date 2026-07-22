package dao;

import model.CampoNonValidoException;
import model.Fattura;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FatturaDAO {
    ArrayList<Fattura> getLibreriaUtente(int idUtente) throws SQLException, CampoNonValidoException;

    void inserisciFattura(Fattura fattura) throws SQLException;

    void effettuaRimborso(int idFattura, int idUtente, int importo) throws SQLException;
}
