package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Promozione {
    private int id;
    private String nome;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    //relazione
    private ArrayList<GiocoInPromozione> giochiInPromozione = new ArrayList<>();

    //costruttore normale
    public Promozione(String nome, LocalDate dataInizio, LocalDate dataFine) {
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

    public void addGiocoInPromozione(GiocoInPromozione giocoInPromozione){
        if (giocoInPromozione == null){
            throw new IllegalArgumentException("Gioco in Promozione non esistente");
        }
        if (giochiInPromozione.contains(giocoInPromozione)){
            throw new IllegalArgumentException("Gioco giá presente nella promozione");
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
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty() || nome.length() > 32) {
            throw new IllegalArgumentException("Nome immesso della promozione non valido");
        }
        this.nome = nome;
    }

    public void setDataInizio(LocalDate dataInizio) {
        if (dataInizio == null) {
            throw new IllegalArgumentException("Immettere una data di Inizio valida");
        }
        this.dataInizio = dataInizio;
    }

    public void setDataFine(LocalDate dataFine) {
        if (dataFine == null) {
            throw new IllegalArgumentException("Immettere una data di fine valida");
        }
        if (this.dataInizio != null && dataFine.isBefore(this.dataInizio)) {
            throw new IllegalArgumentException("La data di fine DEVE essere DOPO la data di inizio");
        }
        this.dataFine = dataFine;
    }
}