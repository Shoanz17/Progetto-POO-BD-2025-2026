package model;

import java.util.ArrayList;

public class Genere {

    private int id;
    private String nome;
    private ArrayList<Gioco> listaGiochi = new ArrayList<>();

    // Costruttore per il DAO
    public Genere(int id ,String nome)
    {
        this.id = id;
        this.nome = nome;
    }
    public Genere(String nome) throws CampoNonValidoException {
        setNome(nome);
    }

    public int getId() {return id;}

    public ArrayList<Gioco> getListaGiochi() {return listaGiochi;}

    public String getNome() {return nome;}

    public void setNome(String nome) throws CampoNonValidoException {
        if (nome == null||nome.length() > 24) {
            throw new CampoNonValidoException("il nome che hai scelto \"" + nome + "\" è troppo lungo");
        }
        else if (nome.trim().isEmpty())
            throw new CampoNonValidoException("il nome che hai scelto è vuoto");
        this.nome = nome;
    }

    public void addGioco(Gioco gioco) throws CampoNonValidoException
    {
        if(gioco == null) throw new CampoNonValidoException("Il gioco non esiste");

        if (listaGiochi.contains(gioco))
            throw new CampoNonValidoException("il gioco già appartiene a questo genere");
        this.listaGiochi.add(gioco);
    }

    public void removeGioco(Gioco gioco) throws CampoNonValidoException
    {
        if(gioco == null) throw new CampoNonValidoException("Il gioco non esiste");

        if (!listaGiochi.contains(gioco))
            throw new CampoNonValidoException("il gioco non appartiene a questo genere");
        this.listaGiochi.remove(gioco);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        else if (o == null || getClass() != o.getClass()) return false;

        Genere og = (Genere) o;
        return this.id == og.id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
