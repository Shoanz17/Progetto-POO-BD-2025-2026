package model;

import java.util.ArrayList;

public class Gioco {
    private int id;
    private String titolo;
    private Categoria categoria;
    private int pegi;

    // Relazioni
    private final Sviluppatore sviluppatore;
    private ArrayList<Genere> generi = new ArrayList<>();
    private ArrayList<EdizioneGioco> edizioni = new ArrayList<>();
    private ArrayList<GiocoInPromozione> promozioni = new ArrayList<>();

    //Costruttore per la GUI
    //Il gioco nasce senza edizioni o promozioni perché queste non posso nascere senza un riferimento a un gioco
    public Gioco(String titolo, Categoria categoria, int pegi, Sviluppatore sviluppatore, ArrayList<Genere> generi) {

        if (sviluppatore == null) {
            throw new IllegalArgumentException("Lo sviluppatore non é valido (?)");
        }
        if(generi == null || generi.isEmpty()) {
            throw new IllegalArgumentException("Non esiste gioco senza generi");
        }

        setTitolo(titolo);
        setCategoria(categoria);
        setPegi(pegi);
        this.sviluppatore = sviluppatore;
        this.generi.addAll(generi);
    }

    //Costruttore per Database
    public Gioco(Sviluppatore sviluppatore, int id, String titolo, Categoria categoria, int pegi) {
        if (sviluppatore == null) {
            throw new IllegalArgumentException("DB Corrotto: Sviluppatore mancante!");
        }

        this.sviluppatore = sviluppatore;
        this.id = id;
        this.titolo = titolo;
        this.categoria = categoria;
        this.pegi = pegi;

        //obbligatorio per il controller riempire gli arraylist successivamente a questo
    }

    public void addPromozione(GiocoInPromozione promozione){
        if (promozione == null){
            throw new IllegalArgumentException("Promozione non esistente");
        }
        if (this.promozioni.contains(promozione)){
            throw new IllegalArgumentException("Il gioco ha già partecipato ha questa promozione");
        }
        promozioni.add(promozione);
    }

    public void addGenere(Genere genere){
        if (genere == null){
            throw new IllegalArgumentException("Genere non disponibile");
        }
        if (this.generi.contains(genere)){
            throw new IllegalArgumentException("Il gioco ha già questo genere");
        }
        generi.add(genere);
    }

    public void addEdizione(EdizioneGioco edizione){
        if (edizione == null){
            throw new IllegalArgumentException("Edizione di gioco non disponibile");
        }
        if (this.edizioni.contains(edizione)){
            throw new IllegalArgumentException("Questa edizione del gioco è già uscita");
        }
        edizioni.add(edizione);
    }

    //metodi per admin
    public void removeEdizione(EdizioneGioco edizione){
        if (edizione == null){
            throw new IllegalArgumentException("Edizione di gioco non disponibile");
        }
        if (!this.edizioni.contains(edizione)){
            throw new IllegalArgumentException("Questa edizione é giá stata rimossa");
        }
        edizioni.remove(edizione);
    }

    public void removeGenere(Genere genere){
        if (genere == null){
            throw new IllegalArgumentException("Genere non disponibile");
        }
        if (!this.generi.contains(genere)){
            throw new IllegalArgumentException("Il gioco giá non ha questo genere");
        }
        generi.remove(genere);
    }

    public void removePromozione(GiocoInPromozione promozione){
        if (promozione == null){
            throw new IllegalArgumentException("Promozione non esistente");
        }
        if (!this.promozioni.contains(promozione)){
            throw new IllegalArgumentException("Il gioco giá non ha partecipato a questa promozione");
        }
        promozioni.remove(promozione);
    }

    //Lista di get
    public String getTitolo() {return titolo;}
    public Categoria getCategoria() {return categoria;}
    public int getPegi() {return pegi;}
    public Sviluppatore getSviluppatore() {return sviluppatore;}
    public ArrayList<Genere> getGeneri() {return generi;}
    public ArrayList<EdizioneGioco> getEdizioni() {return edizioni;}
    public ArrayList<GiocoInPromozione> getPromozioni() {return promozioni;}
    public int getId() {return id;}

    //Lista di set
    public void setTitolo(String titolo) {
        if (titolo == null || titolo.trim().isEmpty() || titolo.length() > 40){
            throw new IllegalArgumentException("Il titolo massimo 40 caratteri");
        }
        this.titolo = titolo;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoria non puó essere vuota");
        }
        this.categoria = categoria;
    }

    public void setPegi(int pegi) {
        if (pegi < 3 || pegi > 18){
            throw new IllegalArgumentException("Il PEGI deve essere tra 3 e 18 anni");
        }
        this.pegi = pegi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gioco gioco = (Gioco) o;
        return id == gioco.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}