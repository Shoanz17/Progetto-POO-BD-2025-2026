package dao;

import model.Utente;
import java.sql.SQLException;

public interface UtenteDAO {

    void aggiornaProfiloUtente(Utente utente) throws SQLException;
    void aggiungiSaldo(int idUtente, int importoDaAggiungere) throws SQLException;

    void insertAmico(int idUtente, int idAmico);
    void deleteAmico(int idUtente, int idAmico);
    void insertSviluppatoreSeguito(int idUtente, int idSviluppatore);
    void deleteSviluppatoreSeguito(int idUtente, int idSviluppatore);

    void insertCarrello(int idUtente, int idEdizione);
    void deleteCarrello(int idUtente, int idEdizione);
    void svuotaCarrello(int idUtente);
}
