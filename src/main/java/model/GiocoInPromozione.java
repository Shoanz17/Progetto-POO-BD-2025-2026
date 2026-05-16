package model;

public class GiocoInPromozione {
    private int percentuale;

    //relazioni
    private Gioco gioco;
    private Promozione promozione;

    public GiocoInPromozione(int percentuale,Gioco gioco, Promozione promozione){
        setPercentuale(percentuale);

        setGioco(gioco);
        setPromozione(promozione);
    }

}
