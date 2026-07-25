package model;

import java.util.ArrayList;

/**
 * Rappresenta il carrello di un {@link Utente} all'interno della piattaforma.
 * Gestisce la lista delle {@link EdizioneGioco} aggiunte e il calcolo dinamico del prezzo totale.
 */
public class Carrello {
    private final Utente utente;
    private final ArrayList<EdizioneGioco> listaGiochi = new ArrayList<>();

    // getter e setter
    public Utente getUtente(){ return utente; }
    public ArrayList<EdizioneGioco> getListaGiochi() { return listaGiochi; }

    /**
     * Aggiunge un'edizione di gioco al carrello.
     *
     * @param edizione L'edizione del gioco da aggiungere.
     * @throws CampoNonValidoException Se l'edizione passata è nulla.
     */
    public void addEdizione(EdizioneGioco edizione) throws CampoNonValidoException {
        if(edizione == null) throw new CampoNonValidoException("Questa edizione non esiste (vuoi crearla tu? diventa sviluppatore cliccando qui!!)");
        this.listaGiochi.add(edizione);
    }

    /**
     * Rimuove un'edizione di gioco specifica dal carrello.
     *
     * @param edizione L'edizione del gioco da rimuovere.
     * @throws CampoNonValidoException Se l'edizione è nulla o se non risulta presente all'interno del carrello.
     */
    public void removeEdizione(EdizioneGioco edizione) throws CampoNonValidoException {
        if(edizione == null) throw new CampoNonValidoException("Questa edizione non esiste (per lamentarti compila questo form)");
        if(!this.listaGiochi.contains(edizione)) throw new CampoNonValidoException("Questa edizione non è nel tuo carrello");

        this.listaGiochi.remove(edizione);
    }

    // metodi
    /**
     * Calcola e restituisce il prezzo totale dei giochi presenti nel carrello.
     * Il totale è un attributo derivato e viene calcolato dinamicamente scorrendo la lista in memoria.
     *
     * @return Il costo complessivo delle edizioni nel carrello in formato intero.
     */
    public int getTotale(){
        int totale = 0;
        for (EdizioneGioco edizione : listaGiochi) {
            totale += edizione.getPrezzo();
        }
        return totale;
    }

    /**
     * Svuota completamente il carrello rimuovendo tutte le edizioni di gioco dalla lista in memoria.
     */
    public void svuotaCarrello(){ this.listaGiochi.clear(); }

    // costruttore
    /**
     * Costruttore utilizzato per creare un nuovo carrello associato a un utente.
     *
     * @param utente L'utente proprietario del carrello.
     * @throws CampoNonValidoException Se l'utente passato è nullo (non esiste carrello senza proprietario).
     */
    public Carrello(Utente utente) throws CampoNonValidoException {
        if(utente == null) throw new CampoNonValidoException("L'utente non esiste, non esiste carrello senza proprietario");

        this.utente = utente;
    }
}