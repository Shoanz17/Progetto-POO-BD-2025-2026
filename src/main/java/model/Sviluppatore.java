package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Sviluppatore extends Account{
// definiamo le nostre variabili
    private int strike;
    private String descrizione;//massimo 500 caratteri
    private int fondi;
    private ArrayList<Gioco> listaGiochi = new ArrayList<>();
    private ArrayList<Utente> seguiti = new ArrayList<>();



public Sviluppatore(String nome,String password, String descrizione)
{
    super(nome,password);
    this.strike = 0;
    setDescrizione(descrizione);
    this.fondi = 0;
}
//costruttore per il DAO
public Sviluppatore(String nome, int id, String password,
                    LocalDate dataCreazione,int strike,String descrizione,int fondi)
{
    super(nome,id,password,dataCreazione);
    this.strike = strike;
    this.descrizione = descrizione;
    this.fondi = fondi;
}
    public int getStrike(){return strike;}
    public void aggiungiStrike()
    {
        if(this.strike>=3)
        {throw new IllegalArgumentException("l'utente è già bannato");}

        this.strike++;
    }

    public ArrayList<Gioco> getListaGiochi(){return listaGiochi;}
    public ArrayList<Utente> getSeguiti() {return seguiti;}

    public int getFondi(){return fondi;}


    public String getDescrizione() {return descrizione;}
    public void setDescrizione(String descrizione)
    {
        if(descrizione == null||descrizione.trim().isEmpty())
    {
            throw new IllegalArgumentException("la descrizione è vuota.");
    }
        else if (descrizione.length()>500)
        throw new IllegalArgumentException("la descrizione è troppo lunga.");

        this.descrizione = descrizione;

    }

    public void aggiungiFondi(int importo) {this.fondi+=importo;}

    public void aggiungiGioco(Gioco gioco) {this.listaGiochi.add(gioco);}

}


