package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Promozione {
    private String nome;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    //relazione
    private ArrayList<GiocoInPromozione> giochiInPromozione = new ArrayList<>();

    public Promozione(String nome, LocalDate dataInizio, LocalDate dataFine) {
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public void addGiocoInPromozione(GiocoInPromozione giocoInPromozione){
        if (giocoInPromozione == null){
            throw new IllegalArgumentException("Il gioco in promozione non esiste");
        }
        giochiInPromozione.add(giocoInPromozione);
    }

}