package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Rappresenta la piattaforma per la quale sarà possibile rilasciare una {@link EdizioneGioco}.
 * Può indicare console fisiche (es. PlayStation, Switch) o ecosistemi (es. PC).
 */
public class PiattaformaDiGioco {
    private final String nome;
    private String produttore;
    private final boolean portatile;

    private ArrayList<EdizioneGioco> edizioni = new ArrayList<>();

    /**
     * Costruttore utilizzato dalla GUI per la creazione di una nuova Piattaforma di gioco da zero.
     *
     * @param nome Il nome della piattaforma.
     * @param produttore L'azienda produttrice.
     * @param portatile Specifica se la console è concepita anche per l'uso in mobilità.
     * @throws CampoNonValidoException Se il nome o il produttore superano i 20 caratteri o sono vuoti.
     */
    public PiattaformaDiGioco(String nome, String produttore, boolean portatile) throws CampoNonValidoException {
        if (nome == null || nome.trim().isEmpty() || nome.length() > 20){
            throw new CampoNonValidoException("Nome non valido");
        }
        this.nome = nome;
        setProduttore(produttore);
        this.portatile = portatile;
    }

    /**
     * Associa una nuova edizione di un gioco al parco titoli di questa piattaforma.
     *
     * @param edizione L'edizione di gioco vendibile rilasciata per questa piattaforma.
     * @throws CampoNonValidoException Se l'edizione è nulla o se è già presente nel catalogo della piattaforma.
     */
    public void addEdizione(EdizioneGioco edizione) throws CampoNonValidoException {
        if (edizione == null) {
            throw new CampoNonValidoException("Edizione non esistente");
        }
        if (edizioni.contains(edizione)){
            throw new CampoNonValidoException("L'edizione é giá stata aggiunta");
        }
        edizioni.add(edizione);
    }

    //getter e setter
    public String getNome() {return nome;}
    public String getProduttore() {return produttore;}
    public boolean isPortatile() {return portatile;}
    public ArrayList<EdizioneGioco> getEdizioni() {return edizioni;}

    /**
     * Imposta o aggiorna il nome del produttore della piattaforma.
     *
     * @param produttore Il nome del produttore.
     * @throws CampoNonValidoException Se la stringa inserita è nulla, vuota o supera i 20 caratteri.
     */
    public void setProduttore(String produttore) throws CampoNonValidoException {
        if (produttore == null || produttore.trim().isEmpty() || produttore.length() > 20){
            throw new CampoNonValidoException("Produttore non valido");
        }
        this.produttore = produttore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PiattaformaDiGioco that = (PiattaformaDiGioco) o;
        return Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }

    @Override
    public String toString() {
        return nome;
    }
}
