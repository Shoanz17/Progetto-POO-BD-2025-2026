package model;

import java.util.Objects;

public class GiocoInPromozione {
    private int id;
    private int percentuale;

    //relazioni
    private Gioco gioco;
    private Promozione promozione;

    //costruttore normale
    public GiocoInPromozione(int percentuale,Gioco gioco, Promozione promozione){

        if(gioco == null){
            throw new IllegalArgumentException("Gioco non esistente");
        }
        if(promozione == null){
            throw new IllegalArgumentException("Promozione non esistente");
        }

        this.gioco = gioco;
        setPercentuale(percentuale);
        this.promozione = promozione;
    }

    //costruttore per DAO
    public GiocoInPromozione(int id, int percentuale,Gioco gioco, Promozione promozione){

        if(gioco == null){
            throw new IllegalArgumentException("Gioco non esistente");
        }
        if(promozione == null){
            throw new IllegalArgumentException("Promozione non esistente");
        }

        this.id = id;
        this.gioco = gioco;
        this.percentuale = percentuale;
        this.promozione = promozione;
    }

    //setter
    public void setPercentuale(int percentuale) {
        if(percentuale < 0 || percentuale > 100){
            throw new IllegalArgumentException("Lo sconto non puó essere minore di 0 o maggiore di 100");
        }
        this.percentuale = percentuale;
    }

    //getter
    public int getPercentuale() {return percentuale;}
    public Promozione getPromozione() {return promozione;}
    public Gioco getGioco() {return gioco;}
    public int getId() {return id;}

    @Override
    public boolean equals(Object o) {
        if(this == o)return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiocoInPromozione that = (GiocoInPromozione) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
