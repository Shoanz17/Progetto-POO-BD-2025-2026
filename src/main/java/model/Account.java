package model;


import org.postgresql.shaded.com.ongres.stringprep.Stringprep;


import java.util.Date;

public class Account {
    // attributi in account:
    protected String nome;
    protected int id;
    protected String password;
    protected Date dataCreazione;

    //Creazione del costruttore
    public Account(String nome,int id,String password,Date dataCreazione)
    {
        this.nome = nome;
        this.id = id;
        this.password = password;
        this.dataCreazione = new Date();//costruisce l'oggetto data,che da la data nell' istante in cui viene creata

    }
    //get e set
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

}


//mancano metodi per cambiare sia il nome che la password
