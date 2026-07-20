package dao;

import model.Utente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UtenteDAO {
    void registraUtente(Utente utente) throws SQLException;

    ArrayList<Utente> getUtentiFiltratiAdmin(String testoRicerca, boolean bannato) throws SQLException;

    Utente getUtenteById(int idUtente) throws SQLException;

    void invertiStatoBan(int idUtente) throws SQLException;

    ArrayList<Utente> getListaUtenti() throws SQLException;
    void aggiornaProfiloUtente(Utente utente) throws SQLException;
    void aggiungiSaldo(int idUtente, int importoDaAggiungere) throws SQLException;

    void inserisciAmico(int idUtente, int idAmico) throws SQLException;
    void eliminaAmico(int idUtente, int idAmico) throws SQLException;
    ArrayList<Utente> getListaAmici(int idUtente) throws SQLException;

    void inserisciSviluppatoreSeguito(int idUtente, int idSviluppatore) throws SQLException;
    void eliminaSviluppatoreSeguito(int idUtente, int idSviluppatore) throws SQLException;

    void inserisciCarrello(int idUtente, int idEdizione) throws SQLException;
    void eliminaCarrello(int idUtente, int idEdizione) throws SQLException;
    void svuotaCarrello(int idUtente) throws SQLException;
}
