package model;

import java.util.Objects;

public class GiocoInPromozione {
    private int percentuale;

    //relazioni
    private Gioco gioco;
    private Promozione promozione;

    //costruttore normale e per DAO
    public GiocoInPromozione(int percentuale,Gioco gioco, Promozione promozione) throws CampoNonValidoException {

        if(gioco == null){
            throw new CampoNonValidoException("Gioco non esistente");
        }
        if(promozione == null){
            throw new CampoNonValidoException("Promozione non esistente");
        }

        this.gioco = gioco;
        setPercentuale(percentuale);
        this.promozione = promozione;
    }

    //setter
    public void setPercentuale(int percentuale) throws CampoNonValidoException {
        if(percentuale < 0 || percentuale > 100){
            throw new CampoNonValidoException("Lo sconto non puó essere minore di 0 o maggiore di 100");
        }
        this.percentuale = percentuale;
    }

    //getter
    public int getPercentuale() {return percentuale;}
    public Promozione getPromozione() {return promozione;}
    public Gioco getGioco() {return gioco;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GiocoInPromozione that = (GiocoInPromozione) o;
        return gioco.equals(that.gioco) && promozione.equals(that.promozione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gioco, promozione);
    }
}
