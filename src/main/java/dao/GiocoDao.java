package dao;

import model.EdizioneGioco;
import model.Gioco;
import model.Sviluppatore;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GiocoDao {

    int inserisciGioco(Gioco gioco)throws SQLException;

    void aggiornaGioco(Gioco gioco)throws SQLException;

    void inserisciEdizione(EdizioneGioco edizioneGioco, int idGioco)throws SQLException;

    int getUnitaVendutePerGioco(String titoloGioco)throws SQLException;

    int getGuadagnoTotalePerGioco(String titoloGioco)throws SQLException;

    ArrayList<Gioco> getGiochiSviluppatore(int idSviluppatore)throws SQLException;

}
