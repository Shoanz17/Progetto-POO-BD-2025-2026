package dao;

import model.Promozione;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public interface PromozioneDAO {
    void creaPromozione(String nome, LocalDate dataInizio, LocalDate dataFine) throws SQLException;

    void inserisciGiocoInPromozione(int idGioco, int idPromozione, int percentuale)throws SQLException;

    ArrayList<Promozione> getTuttePromozioni()throws SQLException;
    ArrayList<Promozione> getPromozioniFiltrate(String testoRicerca, boolean ordinaPerData) throws SQLException;
}
