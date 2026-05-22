package model;

import java.util.ArrayList;
import java.util.Objects;

public class PiattaformaDiGioco {
    private final String nome;
    private String produttore;
    private final boolean portatile;

    private ArrayList<EdizioneGioco> edizioni = new ArrayList<>();

    public PiattaformaDiGioco(String nome, String produttore, boolean portatile) throws CampoNonValidoException {
        if (nome == null || nome.trim().isEmpty() || nome.length() > 20){
            throw new CampoNonValidoException("Nome non valido");
        }
        this.nome = nome;
        setProduttore(produttore);
        this.portatile = portatile;
    }

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
}
