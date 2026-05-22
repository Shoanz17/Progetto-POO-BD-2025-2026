package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Utente extends Account {
    private GenereEnum genere; //valutare se mettere un char singolo/una lista di valori
    private int saldo;
    private boolean bannato;
    private LocalDate dataNascita;
    private String email;

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
        if(dataNascita == null) throw new IllegalArgumentException("Data non esistente");
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

    //metodi
    //valutare per entrambi se mettere le eccezioni checked
    public void aggiungiSaldo(int soldiAggiunti) {
        if(soldiAggiunti < 0) throw new IllegalArgumentException("Non è possibile aggiungere saldo negativo");
        this.saldo += soldiAggiunti;
    }

    public void rimuoviSaldo(int soldiTolti) {
        if(soldiTolti > this.saldo) throw new IllegalArgumentException("Saldo insufficiente (come ci sei finito qui? Controller fai il tuo lavoro");
        this.saldo -= soldiTolti;
    }


    //gestione relazionale
    private Carrello carrello;

    public Carrello getCarrello() { return carrello; }
    public void setCarrello(Carrello c) {
        if(c == null) throw new IllegalArgumentException("Il carrello non esiste");

        this.carrello = c;
    }

    private ArrayList<Utente> listaAmici = new ArrayList<>();

    public ArrayList<Utente> getListaAmici() { return listaAmici; }

    public void addAmico(Utente nuovoAmico) {
        //check di validità
        if(nuovoAmico == null) throw new IllegalArgumentException("Mi dispiace ma gli amici immaginari non valgono");
        if(this.equals(nuovoAmico)) throw new IllegalArgumentException("Non puoi far amicizia con te stesso (che cosa triste...)");
        if(this.listaAmici.contains(nuovoAmico)) throw new IllegalArgumentException("Sei già amico con questo utente");

        this.listaAmici.add(nuovoAmico);
    }
    public void removeAmico(Utente amico) {
        //check di validità
        if(amico == null) throw new IllegalArgumentException("Non sei ancora cresciuto abbastanza per abbandonare gli amici immaginari");
        if(!this.listaAmici.contains(amico)) throw new IllegalArgumentException("Non sei amico di questa persona (lo odi così tanto?)");

        this.listaAmici.remove(amico);
    }

    private ArrayList<Fattura> giochiAcquistati = new ArrayList<>(); //o anche libreria

    public ArrayList<Fattura> getGiochiAcquistati() { return giochiAcquistati; }

    public void addGioco(Fattura gioco){
        //check di validità
        if(gioco == null) throw new IllegalArgumentException("Il gioco non esiste");
        if(this.giochiAcquistati.contains(gioco)) throw new IllegalArgumentException("Hai già comprato questa edizione");

        this.giochiAcquistati.add(gioco);
    }

    public void removeGioco(Fattura gioco){ //o rimborso se preferisci
        //check di validità
        if(gioco == null) throw new IllegalArgumentException("Il gioco non esiste");
        if(!this.giochiAcquistati.contains(gioco)) throw new IllegalArgumentException("Questo gioco non fa parte della tua libreria (non provare a scammarci)");

        this.giochiAcquistati.remove(gioco);
    }

    private ArrayList<Sviluppatore> sviluppatoriSeguiti = new ArrayList<>();

    public ArrayList<Sviluppatore> getSviluppatoriSeguiti() { return sviluppatoriSeguiti; }

    public void addSviluppatoreSeguito(Sviluppatore sviluppatore){
        //check di validità
        if(sviluppatore == null) throw new IllegalArgumentException("Lo sviluppatore non esiste");
        if(this.sviluppatoriSeguiti.contains(sviluppatore)) throw new IllegalArgumentException("Segui già questo sviluppatore");

        this.sviluppatoriSeguiti.add(sviluppatore);
    }

    public void removeSviluppatoreSeguito(Sviluppatore sviluppatore){
        //check di validità
        if(sviluppatore == null) throw new IllegalArgumentException("Lo sviluppatore non esiste");
        if(!this.sviluppatoriSeguiti.contains(sviluppatore)) throw new IllegalArgumentException("Già non segui questo sviluppatore (lo odi così tanto?)");

        this.sviluppatoriSeguiti.remove(sviluppatore);
    }



    //costruttore chiamato alla creazione di un Utente dalla GUI
    public Utente(String nome, String password, GenereEnum genere, String email, LocalDate dataNascita) {
        super(nome, password);

        setGenere(genere);
        setEmail(email);
        this.saldo = 0;
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
        this.carrello = null; //il carrello qui non va messo perché è il controller a inserirlo poi
    }
}
