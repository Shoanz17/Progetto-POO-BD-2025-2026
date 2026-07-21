package dao;

import model.Account;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AccountDAO {
    Account accedi(String nome, String password) throws SQLException;
}
