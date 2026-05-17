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

    //setter
    public void setVoto (int voto){
        if(voto < 0 || voto > 100){
            throw new IllegalArgumentException("Immettere un voto tra 0 e 100.");
        }
        this.voto = voto;
    }

    public void setDescrizione (String descrizione){
        if(descrizione == null || descrizione.trim().isEmpty() || descrizione.length() > 500 ){
            throw new IllegalArgumentException("La descrizione deve essere di un massimo di 500 caratteri");
        }
        this.descrizione = descrizione;
    }

    public void setDifferenzaLike(int differenzaLike) {this.differenzaLike = differenzaLike;}

    //getter
    public int getVoto() {return voto;}
    public String getDescrizione() {return descrizione;}
    public int getDifferenzaLike() {return differenzaLike;}
    public Fattura getFattura() {return fattura;}
}
