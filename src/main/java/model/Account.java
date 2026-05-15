package model;


import org.postgresql.shaded.com.ongres.stringprep.Stringprep;


import java.time.LocalDate;


public class Account {
    // attributi in account:
        private String nome;
        private int id;
        private String password;
        private LocalDate dataCreazione;

    //Creazione del costruttore
    public Account(String nome,int id,String password)
    {
        this.nome = nome;
        this.id = id;
        this.password = password;
        this.dataCreazione = LocalDate.now();
        //localdate mette esattamente la data in cui viene creato l'oggetto

    }
    //costruttore per il DAO
    public Account(String nome,String password)
    {
        this.nome = nome;
        this.password = password;
        this.dataCreazione = LocalDate.now();

    }

    //get e set
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}//nome deve essere grande quanto il var char in db

    public int getId() {return id;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {

        if (password.length()<8) {
            throw new IllegalArgumentException("La password richiede almeno 8 caratteri.");
        }
        this.password = password;
    }

    public LocalDate getDataCreazione() {
        return dataCreazione;
    }

}



