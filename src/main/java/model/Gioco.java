package model;

import java.util.ArrayList;

public class Gioco {
    private String titolo;
    private String categoria;
    private int pegi;

    // Relazioni
    private Sviluppatore sviluppatore;
    private ArrayList<Genere> generi = new ArrayList<>();
    private ArrayList<EdizioneGioco> edizioni = new ArrayList<>();
    private ArrayList<GiocoInPromozione> promozioni = new ArrayList<>();
    private ArrayList<Fattura> fatture = new ArrayList<>();

    public Gioco(String titolo, String categoria, int pegi, Sviluppatore sviluppatore, Genere genere, EdizioneGioco edizione) {

        this.titolo = titolo;
        this.categoria = categoria;
        this.pegi = pegi;

        this.sviluppatore = sviluppatore;
        generi.add(genere);
        edizioni.add(edizione);
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

    public void addEdizione(EdizioneGioco edizione){
        if (edizione == null){
            throw new IllegalArgumentException("Edizione di gioco non disponibile");
        }
        edizioni.add(edizione);
    }

    //Lista di get
    public String getTitolo() {return titolo;}
    public String getCategoria() {return categoria;}
    public int getPegi() {return pegi;}
    public Sviluppatore getSviluppatore() {return sviluppatore;}
    public ArrayList<Genere> getGeneri() {return generi;}
    public ArrayList<EdizioneGioco> getEdizioni() {return edizioni;}


}