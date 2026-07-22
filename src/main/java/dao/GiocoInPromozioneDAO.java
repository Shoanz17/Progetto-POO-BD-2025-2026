package dao;

import model.Gioco;
import model.GiocoInPromozione;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GiocoInPromozioneDAO {

    // Nel DAO
    ArrayList<GiocoInPromozione> getPromozioniPerGioco(Gioco giocoScelto) throws SQLException;
}
