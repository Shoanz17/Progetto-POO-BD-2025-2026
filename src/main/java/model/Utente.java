package model;

import java.time.LocalDate;

public class Utente extends Account {
    private GenereEnum genere; //valutare se mettere un char singolo/una lista di valori
    private int saldo;
    private boolean bannato;
    private LocalDate dataNascita;
    private String email;

    //gestione relazionale
    private Carrello carrello;

    public Carrello getCarrello() {return carrello;}
    public void setCarrello(Carrello c) {this.carrello = c;}


    //getter e setter
    public GenereEnum getGenere() { return genere; }
    public int getSaldo() { return saldo; }
    public boolean isBannato() { return bannato; }
    public LocalDate getDataNascita() { return dataNascita; }
    public String getEmail() {
        return email;
    }

    public void setGenere(GenereEnum genere) {this.genere = genere;}

    public void setBannato(boolean bannato) {this.bannato = bannato;}

    public void setDataNascita(LocalDate dataNascita) {
        if(dataNascita.isAfter(LocalDate.now())) throw new IllegalArgumentException("Non puoi essere nato nel futuro (non sono nato ieri!!!)");
        this.dataNascita = dataNascita;
    }

    public void setEmail(String email) {
        //controllo non sia vuota
        if(email == null || email.trim().isBlank()) throw new IllegalArgumentException("L'email non può essere vuota");

        if(email.length() > 50) throw new IllegalArgumentException("Email troppo lunga (per i nostri standard almeno, non ti offendere");

        //questo regex controlla che ci sia: Testo + @ + Testo + . + Testo(di 2 o più lettere) (l'ho trovato su internet)
        String regexEmail = "^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";

        if(!email.matches(regexEmail)) throw new IllegalArgumentException("L'email non è nel formato adatto");

        //se passa tutti i controlli setta la mail
        this.email = email;
    }


    //costruttore chiamato alla creazione di un Utente dalla GUI
    public Utente(String nome, String password, GenereEnum genere, String email, LocalDate dataNascita) {
        super(nome, password);

        setGenere(genere);
        setEmail(email);
        setSaldo(0);
        setBannato(false);
        setDataNascita(dataNascita);
        this.carrello = new Carrello(this); //capire come implementare Carrello in db
    }

    //costruttore chiamato dal DAO quando pesca un Utente dal DB
    public Utente(int id, String nome, String password, LocalDate dataCreazione, GenereEnum genere, String email, LocalDate dataNascita, int saldo, boolean bannato) {
        super(id, nome, password, dataCreazione);

        this.genere = genere; //non c'è più bisogno del set perché sono già dati giusti
        this.email = email;
        this.dataNascita = dataNascita;
        this.saldo = saldo;
        this.bannato = bannato;
        //il carrello qui non va messo perché è il controller a inserirlo poi
    }
}
