package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Promozione {
    private int id;
    private String nome;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    //relazione
    private ArrayList<GiocoInPromozione> giochiInPromozione = new ArrayList<>();

    //costruttore normale
    public Promozione(String nome, LocalDate dataInizio, LocalDate dataFine) throws CampoNonValidoException {
        setNome(nome);
        setDataInizio(dataInizio);
        setDataFine(dataFine);
    }

    //costruttore per DAO
    public Promozione(int id, String nome, LocalDate dataInizio, LocalDate dataFine) {
        this.id = id;
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

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