package model;


import org.postgresql.shaded.com.ongres.stringprep.Stringprep;

import java.util.ArrayList;
import java.util.Date;

public class Account {

    protected String nome;
    protected int id;
    protected String password;
    protected Date dataCreazione;

    protected ArrayList<Account> seguiti;

    public Account(String nome,int id,String password,Date dataCreazione)
    {
        this.nome = nome;
        this.id = id;
        this.password = password;
        this.dataCreazione = dataCreazione;
        this.seguiti = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
}

