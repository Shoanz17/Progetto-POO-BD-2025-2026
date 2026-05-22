package model;

import java.time.LocalDate;

public class Admin extends Account{

    public Admin( String nome, String password) throws CampoNonValidoException
    {super(nome,password);}

    //costruttore per il DAO
    public Admin(int id, String nome, String password, LocalDate dataCreazione)
    {super(nome,id,password,dataCreazione);}


}
