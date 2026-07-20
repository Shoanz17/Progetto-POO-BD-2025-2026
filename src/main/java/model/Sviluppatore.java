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



    public Sviluppatore(String nome,String password, String descrizione) throws CampoNonValidoException
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

    public void addStrike() throws CampoNonValidoException
    {
        if(isBannato())
        {throw new CampoNonValidoException("L'utente è già bannato");}

        this.strike++;
    }

    public void removeStrike() throws CampoNonValidoException {
        if(this.strike <= 0) throw new CampoNonValidoException("Gli strike non possono essere sotto lo 0");
            else
                this.strike--;
    }

    public boolean isBannato(){
        if(this.strike>=3) return true;

        return false;
    }

    public ArrayList<Gioco> getListaGiochi(){return listaGiochi;}
    public ArrayList<Utente> getSeguiti() {return seguiti;}

    public int getFondi(){return fondi;}


    public String getDescrizione() {return descrizione;}
    public void setDescrizione(String descrizione) throws CampoNonValidoException
    {
        if(descrizione == null||descrizione.trim().isEmpty())
        {
            throw new CampoNonValidoException("la descrizione è vuota.");
        }
        else if (descrizione.length()>500)
        throw new CampoNonValidoException("la descrizione è troppo lunga.");

        this.descrizione = descrizione;

    }

    public void addFondi(int importo) throws CampoNonValidoException
    {
        if(importo < 0)throw new CampoNonValidoException("L'importo non può essere negativo");

        this.fondi+=importo;
    }

    public void removeFondi(int importo) throws CampoNonValidoException
    {
        if(importo>this.fondi)throw new CampoNonValidoException("L'importo supera i fondi");
        this.fondi-=importo;
    }

    public void addGioco(Gioco gioco) throws CampoNonValidoException
    {
        if(gioco == null)
            throw new CampoNonValidoException("Il gioco non esiste");

        if(this.listaGiochi.contains(gioco))
            throw new CampoNonValidoException("il gioco è già presente");

        this.listaGiochi.add(gioco);
    }

}


