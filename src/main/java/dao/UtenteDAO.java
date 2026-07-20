package dao;

import model.Utente;

import java.sql.SQLException;

public interface UtenteDAO {
    void registraUtente(Utente utente) throws SQLException;
}
