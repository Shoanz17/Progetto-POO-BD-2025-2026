package dao;

import model.CampoNonValidoException;
import model.Gioco;
import model.GiocoInPromozione;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GiocoInPromozioneDAO {
    ArrayList<GiocoInPromozione> getPromozioniPerGioco(Gioco giocoScelto) throws SQLException, CampoNonValidoException;

}
