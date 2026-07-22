package dao;

import model.Sviluppatore;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SviluppatoreDAO {
    void registraSviluppatore(Sviluppatore sviluppatore) throws SQLException;

    ArrayList<Sviluppatore> getListaSviluppatoriFiltrati(String testoRicerca) throws SQLException;
    ArrayList<Sviluppatore> getListaSviluppatori() throws SQLException;
    int getNumeroGiochiRilasciati(int idSviluppatore) throws SQLException;

    void aggiungiStrike(int idSviluppatore) throws SQLException;
    void rimuoviStrike(int idSviluppatore) throws SQLException;

    void aggiornaProfilo(Sviluppatore sviluppatore)throws SQLException;

    String getGiocoPiuVendutoSviluppatore(int idSviluppatore) throws SQLException;
}
