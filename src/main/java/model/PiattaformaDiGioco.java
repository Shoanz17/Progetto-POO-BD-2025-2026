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
}
