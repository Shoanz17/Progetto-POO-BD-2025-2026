package model;

import java.util.ArrayList;

public class PiattaformaDiGioco {
    private final String nome;
    private String produttore;
    private final boolean portatile;

    private ArrayList<EdizioneGioco> giochi = new ArrayList<>();

    public PiattaformaDiGioco(String nome, String produttore, boolean portatile){
        if (nome == null || nome.trim().isEmpty() || nome.length() > 20){
            throw new IllegalArgumentException("Nome non valido");
        }
        this.nome = nome;
        setProduttore(produttore);
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

    public void setProduttore(String produttore) {
        if (produttore == null || produttore.trim().isEmpty() || produttore.length() > 20){
            throw new IllegalArgumentException("Produttore non valido");
        }
        this.produttore = produttore;
    }

}
