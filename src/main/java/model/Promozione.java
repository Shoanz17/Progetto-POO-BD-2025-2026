package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Rappresenta un evento promozionale (es. "Saldi Estivi") creato dagli Admin della piattaforma.
 * Gli sviluppatori possono far accedere i loro giochi a questa promozione,
 * trasformandoli in istanze di {@link GiocoInPromozione}.
 */
public class Promozione {
    private int id;
    private String nome;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    //relazione
    private ArrayList<GiocoInPromozione> giochiInPromozione = new ArrayList<>();

    //costruttore normale
    /**
     * Costruttore per la GUI: Crea una nuova Promozione non ancora salvata nel Database.
     *
     * @param nome Il nome dell'evento promozionale.
     * @param dataInizio La data di inizio della promozione.
     * @param dataFine La data di fine della promozione.
     * @throws CampoNonValidoException Se i nomi sono vuoti/troppo lunghi o se le date sono incoerenti.
     */
    public Promozione(String nome, LocalDate dataInizio, LocalDate dataFine) throws CampoNonValidoException {
        setNome(nome);
        setDataInizio(dataInizio);
        setDataFine(dataFine);
    }

    //costruttore per DAO
    /**
     * Costruttore per il DAO: Ricostruisce una Promozione già esistente prelevata dal Database.
     *
     * @param id L'identificativo univoco generato dal DB.
     * @param nome Il nome della promozione.
     * @param dataInizio La data in cui è iniziata.
     * @param dataFine La data in cui finisce.
     */
    public Promozione(int id, String nome, LocalDate dataInizio, LocalDate dataFine) {
        this.id = id;
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    /**
     * Associa un gioco in sconto ({@link GiocoInPromozione}) a questo evento promozionale.
     *
     * @param giocoInPromozione L'entità che rappresenta il gioco in sconto.
     * @throws CampoNonValidoException Se l'oggetto è nullo o se il gioco vi partecipa già.
     */
    public void addGiocoInPromozione(GiocoInPromozione giocoInPromozione) throws CampoNonValidoException {
        if (giocoInPromozione == null){
            throw new CampoNonValidoException("Gioco in Promozione non esistente");
        }
        if (giochiInPromozione.contains(giocoInPromozione)){
            throw new CampoNonValidoException("Gioco giá presente nella promozione");
        }
        giochiInPromozione.add(giocoInPromozione);
    }

    // Lista get
    public int getId() {return id;}
    public String getNome() {return nome;}
    public LocalDate getDataInizio() {return dataInizio;}
    public LocalDate getDataFine() {return dataFine;}
    public ArrayList<GiocoInPromozione> getGiochiInPromozione() {return giochiInPromozione;}

    // Lista set
    /**
     * Imposta il nome della promozione verificandone i limiti di caratteri.
     *
     * @param nome La stringa rappresentante il nome.
     * @throws CampoNonValidoException Se il nome è nullo, vuoto o supera i 32 caratteri.
     */
    public void setNome(String nome) throws CampoNonValidoException {
        if (nome == null || nome.trim().isEmpty() || nome.length() > 32) {
            throw new CampoNonValidoException("Nome immesso della promozione non valido");
        }
        this.nome = nome;
    }

    public void setDataInizio(LocalDate dataInizio) throws CampoNonValidoException {
        if (dataInizio == null) {
            throw new CampoNonValidoException("Immettere una data di Inizio valida");
        }
        this.dataInizio = dataInizio;
    }

    /**
     * Imposta la data di fine della promozione, garantendo che sia cronologicamente successiva
     * alla data di inizio (se quest'ultima è già stata impostata).
     *
     * @param dataFine La data di termine dell'evento.
     * @throws CampoNonValidoException Se la data è nulla o antecedente alla data di inizio.
     */
    public void setDataFine(LocalDate dataFine) throws CampoNonValidoException {
        if (dataFine == null) {
            throw new CampoNonValidoException("Immettere una data di fine valida");
        }
        if (this.dataInizio != null && dataFine.isBefore(this.dataInizio)) {
            throw new CampoNonValidoException("La data di fine DEVE essere DOPO la data di inizio");
        }
        this.dataFine = dataFine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promozione that = (Promozione) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}