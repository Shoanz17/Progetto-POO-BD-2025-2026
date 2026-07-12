package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Rappresenta la copia vendibile specifica di un {@link Gioco} per una determinata {@link PiattaformaDiGioco}.
 * Questa entità è quella che viene effettivamente aggiunta al {@link Carrello} e che
 * figura in una {@link Fattura} post-acquisto.
 */
public class EdizioneGioco {
    private int id; //chiave
    private int prezzo;
    private LocalDate dataRilascio;

    //relazioni
    private final Gioco gioco;
    private final PiattaformaDiGioco piattaforma;

    //getter e setter
    public int getId() { return id; }
    public int getPrezzo() { return prezzo; }
    public LocalDate getDataRilascio() { return dataRilascio; }
    public Gioco getGioco() { return gioco; }
    public PiattaformaDiGioco getPiattaforma() { return piattaforma; }

    /**
     * Imposta il prezzo di listino dell'edizione.
     *
     * @param prezzo Il prezzo immesso.
     * @throws CampoNonValidoException Se il prezzo scende sotto lo zero.
     */
    public void setPrezzo(int prezzo) throws CampoNonValidoException {
        if(prezzo < 0) throw new CampoNonValidoException("Stai mettendo un prezzo negativo (Cos'è sei così disperato da addirittura pagare la gente per giocare al tuo gioco?)");

        this.prezzo = prezzo;
    }

    /**
     * Imposta la data di rilascio.
     *
     * @param dataRilascio La data di pubblicazione.
     * @throws CampoNonValidoException Se la data è nulla o antecedente alla creazione del primo videogioco (1952).
     */
    public void setDataRilascio(LocalDate dataRilascio) throws CampoNonValidoException {
        if(dataRilascio == null) throw new CampoNonValidoException("Questa data non esiste");

        LocalDate primoVideogioco = LocalDate.of(1952, 1, 1);
        if(dataRilascio.isBefore(primoVideogioco)) throw new CampoNonValidoException("Non puoi riscrivere la storia (non sei così speciale bro)");

        this.dataRilascio = dataRilascio;
    }

    //metodi
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdizioneGioco that = (EdizioneGioco) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    //costruttore
    //per la GUI
    /**
     * Costruttore utilizzato dala GUI per creare una nuova edizione di gioco da zero.
     *
     * @param gioco L'oggetto Gioco base da cui deriva l'edizione.
     * @param piattaforma La piattaforma sulla quale questa edizione gira.
     * @param prezzo Il prezzo di vendita stabilito.
     * @param dataRilascio La data di messa in commercio dell'edizione.
     * @throws CampoNonValidoException Se il gioco o la piattaforma mancano, se il prezzo è negativo o la data è storicamente impossibile.
     */
    public EdizioneGioco(Gioco gioco, PiattaformaDiGioco piattaforma, int prezzo, LocalDate dataRilascio) throws CampoNonValidoException {
        if(gioco == null) throw new CampoNonValidoException("Il Gioco non esiste");
        if(piattaforma == null) throw new CampoNonValidoException("La piattaforma non esiste");

        this.gioco = gioco;
        this.piattaforma = piattaforma;
        setPrezzo(prezzo);
        setDataRilascio(dataRilascio);
    }

    //per il DB
    /**
     * Costruttore utilizzato dal DAO per ricostruire un Edizione Gioco già esistente nel Database.
     *
     * @param id ID univoco generato dal Database.
     * @param gioco L'istanza del gioco base.
     * @param piattaforma L'istanza della piattaforma di riferimento.
     * @param prezzo Il prezzo di listino.
     * @param dataRilascio La data in cui il gioco è uscito.
     * @throws CampoNonValidoException Se il database restituisce gioco o piattaforma nulle (DB Corrotto).
     */
    public EdizioneGioco(int id, Gioco gioco, PiattaformaDiGioco piattaforma, int prezzo, LocalDate dataRilascio) throws CampoNonValidoException{
        if(gioco == null) throw new CampoNonValidoException("DB Corrotto: Gioco mancante");
        if(piattaforma == null) throw new CampoNonValidoException("DB Corrotto: Piattaforma mancante");

        this.id = id;
        this.gioco = gioco;
        this.piattaforma = piattaforma;
        this.prezzo = prezzo;
        this.dataRilascio = dataRilascio;
    }

    @Override
    public String toString() {
        return getGioco().getTitolo() + " ("+ getPiattaforma() + ")";
    }
}
