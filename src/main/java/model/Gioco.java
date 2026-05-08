package model;

import java.util.ArrayList;
import java.util.Date;

public class Gioco {
    private String titolo;
    private double prezzo;
    private Date dataRilascio;
    private String categoria;
    private int pegi;

    // Relazioni
    private Sviluppatore sviluppatore;
    private ArrayList<Genere> generi = new ArrayList<>();
    private ArrayList<PiattaformaDiGioco> piattaforme = new ArrayList<>();
    private ArrayList<GiocoInPromozione> promozioni = new ArrayList<>();
    private ArrayList<Fattura> fatture = new ArrayList<>();

    public Gioco(String titolo, double prezzo, Date dataRilascio, String categoria, int pegi, Sviluppatore sviluppatore, Genere genere, PiattaformaDiGioco piattaforma) {

        this.titolo = titolo;
        this.prezzo = prezzo;
        this.dataRilascio = dataRilascio;
        this.categoria = categoria;
        this.pegi = pegi;

        this.sviluppatore = sviluppatore;
        generi.add(genere);
        piattaforme.add(piattaforma);
    }

    public void addPromozione(GiocoInPromozione promozione){
        if (promozione == null){
            throw new IllegalArgumentException("Promozione non esistente");
        }
        promozioni.add(promozione);
    }

    public void addFattura(Fattura fattura){
        if (fattura == null){
            throw new IllegalArgumentException("Fattura non esistente");
        }
        fatture.add(fattura);
    }

    public void addGenere(Genere genere){
        if (genere == null){
            throw new IllegalArgumentException("Genere non disponibile");
        }
        generi.add(genere);
    }

    public void addPiattaformaDiGioco(PiattaformaDiGioco piattaforma){
        if (piattaforma == null){
            throw new IllegalArgumentException("Piattaforma di gioco non disponibile");
        }
        piattaforme.add(piattaforma);
    }

    //Lista di get
    public String getTitolo() {return titolo;}
    public double getPrezzo() {return prezzo;}
    public Date getDataRilascio() {return dataRilascio;}
    public String getCategoria() {return categoria;}
    public int getPegi() {return pegi;}
    public Sviluppatore getSviluppatore() {return sviluppatore;}
    public ArrayList<Genere> getGeneri() {return generi;}
    public ArrayList<PiattaformaDiGioco> getPiattaformeDiGioco() {return piattaforme;}

    //set
    public void setPrezzo(double prezzo) {this.prezzo = prezzo;}

}