package model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Rappresenta la ricevuta d'acquisto di una determinata {@link EdizioneGioco} da parte di un {@link Utente}.
 * Questa entità funge da prova di possesso, contiene la chiave (key) di attivazione del gioco e
 * può essere collegata a una singola {@link Recensione} rilasciata dall'utente.
 */
public class Fattura {
    private int id;
    private String key;
    private LocalDate dataAcquisto;
    private int prezzoAcquisto;

    //relazioni
    private final Utente utente;
    private final EdizioneGioco gioco;
    private Recensione recensione;

    //getter e setter
    public int getId() {
        return id;
    }
    public String getKey() {
        return key;
    }
    public LocalDate getDataAcquisto() {
        return dataAcquisto;
    }
    public int getPrezzoAcquisto() {
        return prezzoAcquisto;
    }
    public Utente getUtente() {
        return utente;
    }
    public EdizioneGioco getGioco() {
        return gioco;
    }
    public Recensione getRecensione() {return recensione;}

    /**
     * Imposta il prezzo effettivo a cui è stato acquistato il gioco.
     *
     * @param prezzo Il prezzo pagato al momento dell'acquisto (eventualmente scontato).
     * @throws CampoNonValidoException Se il prezzo scende sotto lo zero.
     */
    public void setPrezzo(int prezzo) throws CampoNonValidoException {
        if(prezzo < 0) throw new CampoNonValidoException("Prezzo negativo");

        this.prezzoAcquisto = prezzo;
    }

    /**
     * Collega una recensione a questa specifica fattura d'acquisto.
     *
     * @param recensione La {@link Recensione} lasciata dall'utente per questo acquisto.
     * @throws CampoNonValidoException Se la recensione passata non è valida.
     */
    public void setRecensione(Recensione recensione) throws CampoNonValidoException {
        this.recensione = recensione;
    }
    //metodi
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fattura fattura = (Fattura) o;

        return id == fattura.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Verifica che la stringa passata sia un UUID valido e la imposta come chiave.
     *
     * @param key La chiave di attivazione da verificare.
     * @throws CampoNonValidoException Se la chiave è nulla, vuota o non rispetta il formato UUID standard (DB Corrotto).
     */
    private void checkAndSetKey(String key) throws CampoNonValidoException {
        if(key == null || key.trim().isEmpty()) throw new CampoNonValidoException("Chiave non esistente/non valida");

        try{
            UUID.fromString(key);
        } catch(IllegalArgumentException e){
            throw new CampoNonValidoException("DB corrotto: Formato key sbagliato");
        }

        this.key = key;
    }

    /**
     * Verifica la coerenza temporale della data d'acquisto.
     *
     * @param dataAcquisto La data in cui è stata emessa la fattura.
     * @throws CampoNonValidoException Se la data è nulla, se viene dal futuro o se è antecedente alla nascita dei videogiochi.
     */
    private void checkAndSetDataAcquisto(LocalDate dataAcquisto) throws CampoNonValidoException {
        if(dataAcquisto == null) throw new CampoNonValidoException("Data non esistente");

        if(dataAcquisto.isAfter(LocalDate.now())) throw new CampoNonValidoException("DB corrotto: la fattura viene dal futuro");

        LocalDate primoGioco = LocalDate.of(1952,1,1);
        if(dataAcquisto.isBefore(primoGioco)) throw new CampoNonValidoException("DB corrotto: questa fattura vuole riscrivere la storia (la data è troppo antica)");

        this.dataAcquisto = dataAcquisto;
    }

    /**
     * Costruttore utilizzato dal sistema al momento dell'acquisto di un gioco.
     * Genera automaticamente la data d'acquisto (oggi) e una nuova chiave UUID univoca.
     *
     * @param utente L'{@link Utente} che sta effettuando l'acquisto.
     * @param gioco L'{@link EdizioneGioco} che viene acquistata.
     * @param prezzo Il prezzo effettivamente pagato al checkout.
     * @throws CampoNonValidoException Se l'utente o il gioco sono nulli, o se il prezzo è negativo.
     */
    public Fattura(Utente utente, EdizioneGioco gioco, int prezzo) throws CampoNonValidoException {
        if(utente == null) throw new CampoNonValidoException("Utente non esistente");
        if(gioco == null) throw new CampoNonValidoException("Gioco non esistente");

        this.utente = utente;
        this.gioco = gioco;
        this.key = UUID.randomUUID().toString();
        this.dataAcquisto = LocalDate.now();
        setPrezzo(prezzo);
    }

    /**
     * Costruttore utilizzato dal DAO per ricostruire una Fattura già esistente nel Database.
     *
     * @param id ID univoco generato dal Database.
     * @param utente L'{@link Utente} a cui è intestata la fattura.
     * @param gioco L'{@link EdizioneGioco} acquistata.
     * @param prezzo Il prezzo effettivo pagato e registrato nello storico.
     * @param key La chiave UUID di attivazione del gioco.
     * @param dataAcquisto La data in cui è stato completato l'ordine.
     * @throws CampoNonValidoException Se il database restituisce utente o gioco nulli, se la chiave è malformata o se la data è impossibile (DB Corrotto).
     */
    public Fattura(int id, Utente utente, EdizioneGioco gioco, int prezzo, String key, LocalDate dataAcquisto) throws CampoNonValidoException {
        if(utente == null) throw new CampoNonValidoException("Utente non esistente");
        if(gioco == null) throw new CampoNonValidoException("Gioco non esistente");

        checkAndSetKey(key);
        checkAndSetDataAcquisto(dataAcquisto);

        this.id = id;
        this.utente = utente;
        this.gioco = gioco;
        this.prezzoAcquisto = prezzo;
    }

    @Override
    public String toString() {
        return gioco.getGioco().getTitolo() +" ("+ gioco.getPiattaforma().getNome() + ")";
    }
}
