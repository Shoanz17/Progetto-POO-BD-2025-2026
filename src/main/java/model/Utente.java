package model;

import java.time.LocalDate;

public class Utente extends Account {
    private String genere; //valutare se mettere un char singolo/una lista di valori
    private String email;
    private int saldo;
    private boolean bannato;
    private LocalDate dataNascita;

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public boolean isBannato() {
        return bannato;
    }

    public void setBannato(boolean bannato) {
        this.bannato = bannato;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Utente(String genere, String email, String nome, String password) {
        super(nome, password);
        setGenere(genere);
        setEmail(email);
        setSaldo(0);
        setBannato(false);
        //this.dataNascita = LocalDate.of(2000, 10, 10);
        this.carrello = new Carrello(this);
    }
}
