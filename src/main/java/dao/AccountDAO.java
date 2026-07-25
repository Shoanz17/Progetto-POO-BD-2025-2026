package dao;

import model.Account;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione delle operazioni di autenticazione e accesso degli {@link Account}.
 */
public interface AccountDAO {

    /**
     * Verifica le credenziali inserite e consente l'accesso al sistema recuperando l'account corrispondente.
     *
     * @param nome Il nome o username dell'account.
     * @param password La password associata all'account.
     * @return L'oggetto {@link Account} corrispondente se le credenziali sono valide.
     * @throws SQLException Se si verifica un errore durante la comunicazione con il Database.
     */
    Account accedi(String nome, String password) throws SQLException;
}