package dao;

import model.Account;

import java.sql.SQLException;

public interface AccountDAO {
    Account accedi(String nome, String password) throws SQLException;
}
