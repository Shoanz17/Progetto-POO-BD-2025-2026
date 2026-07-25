package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Rappresenta una promozione commerciale all'interno della piattaforma.
 * Gestisce i dettagli temporali, il nome identificativo
 * e l'elenco dei {@link GiocoInPromozione} associati.
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
     * Costruttore per la GUI che crea una nuova promozione specificandone nome, data di inizio e data di fine.
     *
     * @param nome Il nome identificativo della promozione.
     * @param dataInizio La data di inizio della promozione.
     * @param dataFine La data di conclusione della promozione.
     * @throws CampoNonValidoException Se il nome non è valido o se le date non rispettano i vincoli logici.
     */
    public Promozione(String nome, LocalDate dataInizio, LocalDate dataFine) throws CampoNonValidoException {
        setNome(nome);
        setDataInizio(dataInizio);
        setDataFine(dataFine);
    }

    //costruttore per DAO
    /**
     * Costruttore utilizzato dal DAO per ricostruire una Promozione già esistente nel Database.
     *
     * @param id L'identificativo univoco della promozione nel Database.
     * @param nome Il nome della promozione.
     * @param dataInizio La data di inizio registrata.
     * @param dataFine La data di fine registrata.
     */
    public Promozione(int id, String nome, LocalDate dataInizio, LocalDate dataFine) {
        this.id = id;
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    /**
     * Aggiunge un elemento {@link GiocoInPromozione} alla lista dei giochi inclusi in questa promozione.
     *
     * @param giocoInPromozione L'oggetto {@link GiocoInPromozione} da associare.
     * @throws CampoNonValidoException Se l'oggetto passato è nullo o se il gioco è già presente nella promozione.
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
     * Imposta o modifica il nome della promozione verificandone la validità.
     *
     * @param nome Il nuovo nome della promozione.
     * @throws CampoNonValidoException Se il nome è nullo, vuoto o supera i 32 caratteri.
     */
    public void setNome(String nome) throws CampoNonValidoException {
        if (nome == null || nome.trim().isEmpty() || nome.length() > 32) {
            throw new CampoNonValidoException("Nome immesso della promozione non valido");
        }
        this.nome = nome;
    }

    /**
     * Imposta o modifica la data di inizio della promozione.
     *
     * @param dataInizio La nuova data di inizio.
     * @throws CampoNonValidoException Se la data passata è nulla.
     */
    public void setDataInizio(LocalDate dataInizio) throws CampoNonValidoException {
        if (dataInizio == null) {
            throw new CampoNonValidoException("Immettere una data di Inizio valida");
        }
        this.dataInizio = dataInizio;
    }

    /**
     * Imposta o modifica la data di fine della promozione, verificando che sia successiva a quella di inizio.
     *
     * @param dataFine La nuova data di conclusione.
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