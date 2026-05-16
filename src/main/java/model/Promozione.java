package model;

import java.time.LocalDate;

public class Promozione {
    private String nome;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    public Promozione(String nome, LocalDate dataInizio, LocalDate dataFine) {
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

}