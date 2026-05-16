package model;

public class GiocoInPromozione {
    private int percentuale;

    //relazioni
    private Gioco gioco;
    private Promozione promozione;

    public GiocoInPromozione(int percentuale,Gioco gioco, Promozione promozione){
        setPercentuale(percentuale);

        if(gioco == null){
            throw new IllegalArgumentException("Gioco non esistente");
        }
        this.gioco = gioco;

        if(promozione == null){
            throw new IllegalArgumentException("Promozione non esistente");
        }
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
}
