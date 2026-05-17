package model;

public class Recensione {
    private int voto;
    private String descrizione;
    private int differenzaLike;

    //relazione
    private Fattura fattura;

    public Recensione (int voto, String descrizione, Fattura fattura){
        setVoto(voto);
        setDescrizione(descrizione);
        this.differenzaLike = 0;

        if (fattura == null){
            throw new IllegalArgumentException("Fattura non esistente");
        }
        this.fattura = fattura;
    }


}
