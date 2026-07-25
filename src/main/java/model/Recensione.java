package model;
/**
 * Rappresenta la valutazione e il parere testuale rilasciato da un {@link Utente} in seguito all'acquisto di un gioco.
 * Questa entità è legata a doppio filo alla sua {@link Fattura}, che funge da chiave primaria e garantisce
 * che l'utente stia recensendo esclusivamente un titolo che possiede realmente (niente review bombing da account fake).
 */
public class Recensione {
    private int voto;
    private String descrizione;
    private int differenzaLike;

    //relazione
    private Fattura fattura; //sarà chiave primaria

    /**
     * Costruttore utilizzato dall'applicazione quando un utente pubblica una nuova recensione.
     * La differenza di like/dislike parte automaticamente da zero, come è giusto che sia per i nuovi arrivati.
     *
     * @param voto Il punteggio assegnato al gioco.
     * @param descrizione Il testo che motiva il voto.
     * @param fattura La {@link Fattura} d'acquisto che autorizza e fa da chiave a questa recensione.
     * @throws CampoNonValidoException Se la fattura manca, se il voto sfora i limiti o la descrizione è inaccettabile.
     */
    public Recensione (int voto, String descrizione, Fattura fattura) throws CampoNonValidoException {
        if (fattura == null){
            throw new CampoNonValidoException("Fattura non esistente");
        }

        setVoto(voto);
        setDescrizione(descrizione);
        this.differenzaLike = 0;
        this.fattura = fattura;
    }

    /**
     * Costruttore utilizzato dal DAO per ricostruire una Recensione già presente nel Database.
     *
     * @param voto Il punteggio registrato.
     * @param descrizione Il testo della recensione salvato in precedenza.
     * @param differenzaLike La somma algebrica dei like e dislike accumulati dalla community.
     * @param fattura La {@link Fattura} di riferimento recuperata dal DB.
     */
    public Recensione (int voto, String descrizione, int differenzaLike, Fattura fattura){
        this.voto = voto;
        this.descrizione = descrizione;
        this.differenzaLike = differenzaLike;
        this.fattura = fattura;
    }

    //setter


    /**
     * Imposta o modifica il punteggio della recensione.
     *
     * @param voto L'indice di gradimento dell'utente.
     * @throws CampoNonValidoException Se il voto non è compreso tra 0 e 100 (non siamo su Metacritic, le regole vanno rispettate).
     */
    public void setVoto (int voto) throws CampoNonValidoException {
        if(voto < 0 || voto > 100){
            throw new CampoNonValidoException("Immettere un voto tra 0 e 100.");
        }
        this.voto = voto;
    }

    /**
     * Imposta o modifica il testo della recensione.
     *
     * @param descrizione Il parere testuale dell'utente.
     * @throws CampoNonValidoException Se il testo è nullo, vuoto o sfora il limite dei 500 caratteri (non stiamo mica scrivendo la Divina Commedia).
     */
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

    /**
     * Aggiunge un "mi piace" alla recensione incrementandone la popolarità.
     */
    public void addLike() {
        this.differenzaLike++;
    }

    /**
     * Aggiunge un "non mi piace" alla recensione riducendone la popolarità.
     */
    public void addDislike() {
        this.differenzaLike--;
    }

    @Override
    public String toString() {
        return "Da: " + getFattura().getUtente().getNome();
    }
}
