package model;

import java.util.ArrayList;

public class PiattaformaDiGioco {
    private int id;
    private final String nome;
    private String produttore;
    private boolean portatile;

    private ArrayList<EdizioneGioco> giochi = new ArrayList<>();

    public PiattaformaDiGioco(String nome, String produttore, boolean portatile){
        this.nome = nome;
        this.produttore = produttore;
        this.portatile = portatile;
    }

    public void addGioco(EdizioneGioco gioco){
        if (gioco == null) {
            throw new IllegalArgumentException("Gioco non esistente");
        }
        giochi.add(gioco);
    }

    //getter e setter
    public String getNome() {return nome;}
    public String getProduttore() {return produttore;}
    public boolean isPortatile() {return portatile;}
    public ArrayList<EdizioneGioco> getGiochi() {return giochi;}
    public int getId() {return id;}

    public void setProduttore(String produttore) {
        this.produttore = produttore;
    }
    public void setId(int id) {
        this.id = id;
    }
}
