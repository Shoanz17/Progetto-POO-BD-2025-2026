package model;

import java.util.ArrayList;

/**
 * Rappresenta un Genere videoludico (es. Azione, JRPG, Sopravvivenza) utilizzato per categorizzare un {@link Gioco}.
 * Un gioco può appartenere a più generi, e ogni genere mantiene un riferimento ai giochi che ne fanno parte.
 */
public class Genere {

    private int id;
    private String nome;
    private ArrayList<Gioco> listaGiochi = new ArrayList<>();

    // Costruttore per il DAO
    /**
     * Costruttore per il DAO: ricostruisce un Genere già salvato e presente nel Database.
     *
     * @param id L'identificativo univoco generato dal DB.
     * @param nome Il nome del genere.
     */
    public Genere(int id ,String nome)
    {
        this.id = id;
        this.nome = nome;
    }

    /**
     * Costruttore per la GUI: crea un nuovo Genere non ancora inserito nel Database.
     *
     * @param nome Il nome del nuovo genere da creare.
     * @throws CampoNonValidoException Se il nome è vuoto o supera la lunghezza massima consentita.
     */
    public Genere(String nome) throws CampoNonValidoException {
        setNome(nome);
    }

    public int getId() {return id;}

    public ArrayList<Gioco> getListaGiochi() {return new ArrayList<>(listaGiochi);}

    public String getNome() {return nome;}

    /**
     * Imposta o modifica il nome del genere applicando i controlli di validazione del DB.
     *
     * @param nome La stringa rappresentante il genere.
     * @throws CampoNonValidoException Se il nome è nullo, vuoto o supera i 24 caratteri.
     */
    public void setNome(String nome) throws CampoNonValidoException {
        if (nome == null||nome.length() > 24) {
            throw new CampoNonValidoException("il nome che hai scelto \"" + nome + "\" è troppo lungo");
        }
        else if (nome.trim().isEmpty())
            throw new CampoNonValidoException("il nome che hai scelto è vuoto");
        this.nome = nome;
    }

    /**
     * Associa un {@link Gioco} al catalogo di questo genere.
     *
     * @param gioco Il Gioco alla quale applicare il genere.
     * @throws CampoNonValidoException Se il gioco è nullo o è già stato associato in precedenza.
     */
    public void addGioco(Gioco gioco) throws CampoNonValidoException
    {
        if(gioco == null) throw new CampoNonValidoException("Il gioco non esiste");

        if (listaGiochi.contains(gioco))
            throw new CampoNonValidoException("il gioco già appartiene a questo genere");
        this.listaGiochi.add(gioco);
    }

    /**
     * Rimuove un {@link Gioco} dal catalogo di questo genere.
     *
     * @param gioco Il gioco da rimuovere.
     * @throws CampoNonValidoException Se il gioco è nullo o non fa parte di questo genere.
     */
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
        return nome;
    }
}
