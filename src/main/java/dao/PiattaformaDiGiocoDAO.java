package dao;

import model.Gioco;
import model.PiattaformaDiGioco;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PiattaformaDiGiocoDAO {
    void creaPiattaforma(PiattaformaDiGioco piattaforma) throws SQLException;

    ArrayList<PiattaformaDiGioco> getPiattaformeFiltrate(String testoRicerca) throws SQLException;
    ArrayList<PiattaformaDiGioco> getListaPiattaforme() throws SQLException;

    ArrayList<PiattaformaDiGioco> getListaPiattaformeDaGioco(Gioco gioco) throws SQLException;
}
