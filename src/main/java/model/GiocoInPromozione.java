package model;

import java.util.Objects;

/**
 * Rappresenta l'associazione tra un {@link Gioco} e una {@link Promozione} attiva,
 * definendo la percentuale di sconto applicata a quel determinato gioco.
 */
public class GiocoInPromozione {
    private int percentuale;

    //relazioni
    private Gioco gioco;
    private Promozione promozione;

    //costruttore normale e per DAO
    /**
     * Costruttore utilizzato dalla GUI e DAO per associare un gioco a una promozione specificando la percentuale di sconto.
     *
     * @param percentuale La percentuale di sconto da applicare.
     * @param gioco L'oggetto {@link Gioco} oggetto dello sconto.
     * @param promozione L'oggetto {@link Promozione} associata.
     * @throws CampoNonValidoException Se il gioco o la promozione sono nulli, o se la percentuale non è valida.
     */
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
    /**
     * Imposta o modifica la percentuale di sconto.
     *
     * @param percentuale Il nuovo valore dello sconto.
     * @throws CampoNonValidoException Se la percentuale è inferiore a 0 o superiore a 100.
     */
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