package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Rappresenta un genere videoludico all'interno della piattaforma.
 * Gestisce l'associazione con i vari {@link Gioco} che appartengono a questa categoria.
 */
public class Genere {

    private int id;
    private String nome;
    private ArrayList<Gioco> listaGiochi = new ArrayList<>();

    // Costruttore per il DAO
    /**
     * Costruttore utilizzato dal DAO per ricostruire un Genere già esistente nel Database.
     *
     * @param id L'identificativo univoco del genere nel Database.
     * @param nome Il nome del genere.
     */
    public Genere(int id ,String nome)
    {
        this.id = id;
        this.nome = nome;
    }

    /**
     * Costruttore utilizzato dalla GUI per creare un nuovo genere da zero.
     *
     * @param nome Il nome da assegnare al genere.
     * @throws CampoNonValidoException Se il nome non rispetta i vincoli di formato o lunghezza.
     */
    public Genere(String nome) throws CampoNonValidoException {
        setNome(nome);
    }

    public int getId() {return id;}

    public ArrayList<Gioco> getListaGiochi() {return listaGiochi;}

    public String getNome() {return nome;}

    /**
     * Imposta o modifica il nome del genere verificandone la validità.
     *
     * @param nome Il nuovo nome del genere.
     * @throws CampoNonValidoException Se il nome è nullo, supera i 24 caratteri o è composto da soli spazi vuoti.
     */
    public void setNome(String nome) throws CampoNonValidoException {
        if (nome == null||nome.length() > 24) {
            throw new CampoNonValidoException("il nome che hai scelto \"" + nome + "\" è troppo lungo");
        }
        else if (nome.trim().isEmpty())
            throw new CampoNonValidoException("il nome che hai scelto è vuoto");
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genere og = (Genere) o;

        if (this.id > 0 && og.id > 0) {
            return this.id == og.id;
        }
        return this.nome != null && og.nome != null && this.nome.equalsIgnoreCase(og.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return this.nome;
    }
}