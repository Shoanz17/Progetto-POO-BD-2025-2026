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
}