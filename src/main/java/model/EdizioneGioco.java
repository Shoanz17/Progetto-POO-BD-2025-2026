package model;

import java.time.LocalDate;
import java.util.Objects;

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

    public void setPrezzo(int prezzo) throws CampoNonValidoException {
        if(prezzo < 0) throw new CampoNonValidoException("Stai mettendo un prezzo negativo (Cos'è sei così disperato da addirittura pagare la gente per giocare al tuo gioco?)");

        this.prezzo = prezzo;
    }

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

        //return id == that.id;
        // confrontiamo il gioco e la piattaforma anziché l'id
        return gioco.equals(that.gioco) &&
                piattaforma.equals(that.piattaforma);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    //costruttore
    //per la GUI
    public EdizioneGioco(Gioco gioco, PiattaformaDiGioco piattaforma, int prezzo, LocalDate dataRilascio) throws CampoNonValidoException {
        if(gioco == null) throw new CampoNonValidoException("Il Gioco non esiste");
        if(piattaforma == null) throw new CampoNonValidoException("La piattaforma non esiste");

        this.gioco = gioco;
        this.piattaforma = piattaforma;
        setPrezzo(prezzo);
        setDataRilascio(dataRilascio);
    }

    //per il DB
    public EdizioneGioco(int id, Gioco gioco, PiattaformaDiGioco piattaforma, int prezzo, LocalDate dataRilascio) throws CampoNonValidoException{
        if(gioco == null) throw new CampoNonValidoException("DB Corrotto: Gioco mancante");
        if(piattaforma == null) throw new CampoNonValidoException("DB Corrotto: Piattaforma mancante");

        this.id = id;
        this.gioco = gioco;
        this.piattaforma = piattaforma;
        this.prezzo = prezzo;
        this.dataRilascio = dataRilascio;
    }
}
