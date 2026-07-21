package dao;

import model.Promozione;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PromozioneDAO {

    void inserisciGiocoInPromozione(int idGioco, int idPromozione, int percentuale)throws SQLException;

    ArrayList<Promozione> getTuttePromozioni()throws SQLException;
}
