package dao;

import model.Utente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UtenteDAO {
    void registraUtente(Utente utente) throws SQLException;

    ArrayList<Utente> getUtentiFiltratiAdmin(String testoRicerca, boolean bannato) throws SQLException;

    Utente getUtenteById(int idUtente) throws SQLException;
}
