package model;

import java.util.ArrayList;

public class PiattaformaDiGioco {
    private final String nome;
    private String produttore;
    private final boolean portatile;

    private ArrayList<EdizioneGioco> edizioni = new ArrayList<>();

    public PiattaformaDiGioco(String nome, String produttore, boolean portatile){
        if (nome == null || nome.trim().isEmpty() || nome.length() > 20){
            throw new IllegalArgumentException("Nome non valido");
        }
        this.nome = nome;
        setProduttore(produttore);
        this.portatile = portatile;
    }

    public void addEdizione(EdizioneGioco edizione){
        if (edizione == null) {
            throw new IllegalArgumentException("Edizione non esistente");
        }
        if (edizioni.contains(edizione)){
            throw new IllegalArgumentException("L'edizione é giá stata aggiunta");
        }
        edizioni.add(edizione);
    }

    //getter e setter
    public String getNome() {return nome;}
    public String getProduttore() {return produttore;}
    public boolean isPortatile() {return portatile;}
    public ArrayList<EdizioneGioco> getEdizioni() {return edizioni;}

    public void setProduttore(String produttore) {
        if (produttore == null || produttore.trim().isEmpty() || produttore.length() > 20){
            throw new IllegalArgumentException("Produttore non valido");
        }
        this.produttore = produttore;
    }

}
