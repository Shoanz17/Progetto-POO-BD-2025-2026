package model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Fattura {
    private int id;
    private String key;
    private LocalDate dataAcquisto;
    private int prezzoAcquisto;

    //relazioni
    private final Utente utente;
    private final EdizioneGioco gioco;

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

    public void setPrezzo(int prezzo){
        if(prezzo < 0) throw new IllegalArgumentException("Prezzo negativo");

        this.prezzoAcquisto = prezzo;
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

    //serve per essere sicuri che la key passata sia adeguata
    private void checkAndSetKey(String key){
        if(key == null || key.trim().isEmpty()) throw new IllegalArgumentException("Chiave non esistente/non valida");

        try{
            UUID.fromString(key);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("DB corrotto: Formato key sbagliato");
        }

        this.key = key;
    }

    private void checkAndSetDataAcquisto(LocalDate dataAcquisto){
        if(dataAcquisto == null) throw new IllegalArgumentException("Data non esistente");

        if(dataAcquisto.isAfter(LocalDate.now())) throw new IllegalArgumentException("DB corrotto: la fattura viene dal futuro");

        LocalDate primoGioco = LocalDate.of(1952,1,1);
        if(dataAcquisto.isBefore(primoGioco)) throw new IllegalArgumentException("DB corrotto: questa fattura vuole riscrivere la storia (la data è troppo antica)");

        this.dataAcquisto = dataAcquisto;
    }

    //costruttore
    public Fattura(Utente utente, EdizioneGioco gioco, int prezzo){
        if(utente == null) throw new IllegalArgumentException("Utente non esistente");
        if(gioco == null) throw new IllegalArgumentException("Gioco non esistente");

        this.utente = utente;
        this.gioco = gioco;
        this.key = UUID.randomUUID().toString();
        this.dataAcquisto = LocalDate.now();
        setPrezzo(prezzo);
    }

    //costruttore per il DB
    public Fattura(int id, Utente utente, EdizioneGioco gioco, int prezzo, String key, LocalDate dataAcquisto){
        if(utente == null) throw new IllegalArgumentException("Utente non esistente");
        if(gioco == null) throw new IllegalArgumentException("Gioco non esistente");

        checkAndSetKey(key);
        checkAndSetDataAcquisto(dataAcquisto);

        this.id = id;
        this.utente = utente;
        this.gioco = gioco;
        this.prezzoAcquisto = prezzo;
    }
}
