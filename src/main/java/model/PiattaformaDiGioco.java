package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Rappresenta una piattaforma di gioco all'interno del sistema.
 * Gestisce le informazioni sul produttore, sulla portabilità e l'elenco delle {@link EdizioneGioco} associate.
 */
public class PiattaformaDiGioco {
    private final String nome;
    private String produttore;
    private final boolean portatile;

    private ArrayList<EdizioneGioco> edizioni = new ArrayList<>();

    /**
     * Costruttore per GUI e DAO per creare una nuova piattaforma di gioco specificandone nome, produttore e portabilità.
     *
     * @param nome Il nome della piattaforma.
     * @param produttore Il nome del produttore.
     * @param portatile Un valore booleano che indica se la piattaforma è portatile.
     * @throws CampoNonValidoException Se il nome o il produttore risultano nulli, vuoti o troppo lunghi.
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
     * Aggiunge un'edizione di gioco alla lista delle edizioni supportate da questa piattaforma.
     *
     * @param edizione L'oggetto {@link EdizioneGioco} da aggiungere.
     * @throws CampoNonValidoException Se l'edizione è nulla o se risulta già presente nella lista.
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
     * Imposta o modifica il produttore della piattaforma verificandone la validità.
     *
     * @param produttore Il nome del nuovo produttore.
     * @throws CampoNonValidoException Se il produttore è nullo, vuoto o supera i limiti di lunghezza.
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