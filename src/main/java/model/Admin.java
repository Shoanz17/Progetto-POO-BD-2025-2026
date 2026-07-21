package model;

import java.time.LocalDate;

/**
 * Rappresenta un Amministratore di sistema all'interno della piattaforma.
 * Eredita le caratteristiche e i metodi di base dalla classe astratta {@link Account}.
 */
public class Admin extends Account{

    /**
     * Costruttore utilizzato per registrare un nuovo Amministratore nel sistema.
     * I controlli sui formati e l'assegnazione della data odierna vengono
     * gestiti automaticamente dalla classe padre.
     *
     * @param nome     Il nome scelto per il nuovo amministratore.
     * @param password La password scelta per l'amministratore.
     * @throws CampoNonValidoException Se i parametri non rispettano i criteri di validazione di Account.
     */
    public Admin( String nome, String password) throws CampoNonValidoException
    {super(nome,password);}


    //costruttore per il DAO
    /**
     * Costruttore utilizzato esclusivamente dal DAO per costruire
     * Un Amministratore prendendo i suoi dati dal Database.
     *
     * @param id            L'identificativo univoco dell'amministratore nel DB.
     * @param nome          Il nome utente salvato nel DB.
     * @param password      La password salvata nel DB.
     * @param dataCreazione La data di registrazione storica recuperata dal DB.
     */
    public Admin(int id, String nome, String password, LocalDate dataCreazione)
    {super(nome,id,password,dataCreazione);}


}
