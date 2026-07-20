package dao;

import model.Sviluppatore;

import java.sql.SQLException;

public interface SviluppatoreDAO {
    void registraSviluppatore(Sviluppatore sviluppatore) throws SQLException;
}
