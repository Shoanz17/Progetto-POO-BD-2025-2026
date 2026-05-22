package model;

import java.util.ArrayList;

public class Carrello {
    private final Utente utente; //chiave primaria e secondaria
    private final ArrayList<EdizioneGioco> listaGiochi = new ArrayList<>();

    //getter e setter
    public Utente getUtente(){ return utente; }
    public ArrayList<EdizioneGioco> getListaGiochi() { return listaGiochi; }

    public void addEdizione(EdizioneGioco edizione){
        if(edizione == null) throw new IllegalArgumentException("Questa edizione non esiste (vuoi crearla tu? diventa sviluppatore cliccando qui!!)");
        this.listaGiochi.add(edizione);
    }

    public void removeEdizione(EdizioneGioco edizione){
        if(edizione == null) throw new IllegalArgumentException("Questa edizione non esiste (per lamentarti compila questo form)");
        if(!this.listaGiochi.contains(edizione)) throw new IllegalArgumentException("Questa edizione non è nel tuo carrello");

        this.listaGiochi.remove(edizione);
    }

    //metodi
    //totale è un attributo derivato e quindi non esisterà mai se non al di fuori di questo metodo. Ogni volta che mi serve lo calcolerò da qui
    public int getTotale(){
        int totale = 0;
        for (EdizioneGioco edizione : listaGiochi) {
            totale += edizione.getPrezzo();
        }
        return totale;
    }
    public void svuotaCarrello(){ this.listaGiochi.clear(); }

    //costruttore
    public Carrello(Utente utente){
        if(utente == null) throw new IllegalArgumentException("L'utente non esiste, non esiste carrello senza proprietario");

        this.utente = utente;
    }
}
