package model;

public class Recensione {
    private int voto;
    private String descrizione;
    private int differenzaLike;

    //relazione
    private Fattura fattura; //sarà chiave primaria

    public Recensione (int voto, String descrizione, Fattura fattura) throws CampoNonValidoException {
        if (fattura == null){
            throw new CampoNonValidoException("Fattura non esistente");
        }

        setVoto(voto);
        setDescrizione(descrizione);
        this.differenzaLike = 0;
        this.fattura = fattura;
    }

    //Costruttore per il Dao (Solo per la differenzaLike praticamente)
    public Recensione (int voto, String descrizione, int differenzaLike, Fattura fattura){
        this.voto = voto;
        this.descrizione = descrizione;
        this.differenzaLike = differenzaLike;
        this.fattura = fattura;
    }

    //setter
    public void setVoto (int voto) throws CampoNonValidoException {
        if(voto < 0 || voto > 100){
            throw new CampoNonValidoException("Immettere un voto tra 0 e 100.");
        }
        this.voto = voto;
    }

    public void setDescrizione (String descrizione) throws CampoNonValidoException {
        if(descrizione == null || descrizione.trim().isEmpty() || descrizione.length() > 500 ){
            throw new CampoNonValidoException("La descrizione deve essere di un massimo di 500 caratteri");
        }
        this.descrizione = descrizione;
    }

    //getter
    public int getVoto() {return voto;}
    public String getDescrizione() {return descrizione;}
    public int getDifferenzaLike() {return differenzaLike;}
    public Fattura getFattura() {return fattura;}

    //metodi
    public void addLike() {
        this.differenzaLike++;
    }
    public void addDislike() {
        this.differenzaLike--;
    }

    @Override
    public String toString() {
        return "Da: " + getFattura().getUtente().getNome();
    }
}
