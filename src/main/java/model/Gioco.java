package model;

import java.util.ArrayList;

/**
 * Rappresenta l'IP (Proprietà Intellettuale) di un Gioco e non la sua copia digitale vendibile
 * Contiene al suo interno lo {@link Sviluppatore} che lo ha ideato, la {@link Categoria},
 * il titolo, il PEGI, i suoi {@link Genere} e le eventuali {@link GiocoInPromozione} a cui partecipa.
 */
public class Gioco {
    private int id;
    private String titolo;
    private Categoria categoria;
    private int pegi;

    // Relazioni
    private final Sviluppatore sviluppatore;
    private ArrayList<Genere> generi = new ArrayList<>();
    private ArrayList<EdizioneGioco> edizioni = new ArrayList<>();
    private ArrayList<GiocoInPromozione> promozioni = new ArrayList<>();


    //Il gioco nasce senza edizioni o promozioni perché queste non posso nascere senza un riferimento a un gioco

    /**
     * Costruttore utilizzato dalla GUI per la creazione di un nuovo Gioco da zero.
     *
     * @param titolo       Titolo scelto per il gioco.
     * @param categoria    Categoria di budget del gioco.
     * @param pegi         Età minima consigliata per il gioco.
     * @param sviluppatore Sviluppatore/Publisher creatore del gioco.
     * @param generi       Lista dei generi assegnati al gioco.
     * @throws CampoNonValidoException Se lo sviluppatore è nullo, la lista generi è vuota o se titolo/pegi violano i vincoli.
     */
    public Gioco(String titolo, Categoria categoria, int pegi, Sviluppatore sviluppatore, ArrayList<Genere> generi) throws CampoNonValidoException {

        if (sviluppatore == null) {
            throw new CampoNonValidoException("Lo sviluppatore non é valido (?)");
        }
        if (generi == null || generi.isEmpty()) {
            throw new CampoNonValidoException("Non esiste gioco senza generi");
        }

        setTitolo(titolo);
        setCategoria(categoria);
        setPegi(pegi);
        this.sviluppatore = sviluppatore;
        this.generi.addAll(generi);
    }

    //Costruttore per Database

    /**
     * Costruttore utilizzato dal DAO per ricostruire un Gioco già esistente nel Database.
     *
     * @param sviluppatore Sviluppatore/publisher del Gioco.
     * @param id           ID univoco generato dal Database.
     * @param titolo       Titolo del Gioco.
     * @param categoria    Categoria del Gioco.
     * @param pegi         Pegi del Gioco.
     * @throws CampoNonValidoException Se il DB restituisce uno sviluppatore nullo (DB Corrotto).
     */
    public Gioco(Sviluppatore sviluppatore, int id, String titolo, Categoria categoria, int pegi) throws CampoNonValidoException {
        if (sviluppatore == null) {
            throw new CampoNonValidoException("DB Corrotto: Sviluppatore mancante!");
        }

        this.sviluppatore = sviluppatore;
        this.id = id;
        this.titolo = titolo;
        this.categoria = categoria;
        this.pegi = pegi;

        //obbligatorio per il controller riempire gli arraylist successivamente a questo
    }

    /**
     * Associa il gioco a una nuova promozione.
     *
     * @param promozione L'istanza della promozione a cui far partecipare il gioco.
     * @throws CampoNonValidoException Se la promozione è nulla o se il gioco vi partecipa già.
     */
    public void addPromozione(GiocoInPromozione promozione) throws CampoNonValidoException {
        if (promozione == null) {
            throw new CampoNonValidoException("Promozione non esistente");
        }
        if (this.promozioni.contains(promozione)) {
            throw new CampoNonValidoException("Il gioco ha già partecipato a questa promozione");
        }
        promozioni.add(promozione);
    }

    /**
     * Aggiunge un nuovo genere alla lista dei generi del gioco.
     *
     * @param genere Il genere da aggiungere.
     * @throws CampoNonValidoException Se il genere è nullo o è già presente.
     */
    public void addGenere(Genere genere) throws CampoNonValidoException {
        if (genere == null) {
            throw new CampoNonValidoException("Genere non disponibile");
        }
        if (this.generi.contains(genere)) {
            throw new CampoNonValidoException("Il gioco ha già questo genere");
        }
        generi.add(genere);
    }

    /**
     * Associa una nuova copia vendibile (edizione) al gioco base.
     *
     * @param edizione L'edizione da rilasciare.
     * @throws CampoNonValidoException Se l'edizione è nulla o è già stata aggiunta in precedenza.
     */
    public void addEdizione(EdizioneGioco edizione) throws CampoNonValidoException {
        if (edizione == null) {
            throw new CampoNonValidoException("Edizione di gioco non disponibile");
        }
        if (this.edizioni.contains(edizione)) {
            throw new CampoNonValidoException("Questa edizione del gioco è già uscita");
        }
        edizioni.add(edizione);
    }

    //metodi per admin
    public void removeEdizione(EdizioneGioco edizione) throws CampoNonValidoException {
        if (edizione == null) {
            throw new CampoNonValidoException("Edizione di gioco non disponibile");
        }
        if (!this.edizioni.contains(edizione)) {
            throw new CampoNonValidoException("Questa edizione é giá stata rimossa");
        }
        edizioni.remove(edizione);
    }

    public void removeGenere(Genere genere) throws CampoNonValidoException {
        if (genere == null) {
            throw new CampoNonValidoException("Genere non disponibile");
        }
        if (!this.generi.contains(genere)) {
            throw new CampoNonValidoException("Il gioco giá non ha questo genere");
        }
        generi.remove(genere);
    }

    public void removePromozione(GiocoInPromozione promozione) throws CampoNonValidoException {
        if (promozione == null) {
            throw new CampoNonValidoException("Promozione non esistente");
        }
        if (!this.promozioni.contains(promozione)) {
            throw new CampoNonValidoException("Il gioco giá non ha partecipato a questa promozione");
        }
        promozioni.remove(promozione);
    }

    //Lista di get
    public String getTitolo() {return titolo;}
    public Categoria getCategoria() {return categoria;}
    public int getPegi() {return pegi;}
    public Sviluppatore getSviluppatore() {return sviluppatore;}
    public ArrayList<Genere> getGeneri() {return new ArrayList<>(generi);}
    public ArrayList<EdizioneGioco> getEdizioni() {return new ArrayList<>(edizioni);}
    public ArrayList<GiocoInPromozione> getPromozioni() {return new ArrayList<>(promozioni);}
    public int getId() {return id;}

    //Lista di set

    /**
     * Imposta il titolo del gioco verificandone la lunghezza.
     *
     * @param titolo Il nome del gioco.
     * @throws CampoNonValidoException Se il titolo è vuoto, nullo o supera i 40 caratteri.
     */
    public void setTitolo(String titolo) throws CampoNonValidoException {
        if (titolo == null || titolo.trim().isEmpty() || titolo.length() > 40) {
            throw new CampoNonValidoException("Il titolo massimo 40 caratteri");
        }
        this.titolo = titolo;
    }

    public void setCategoria(Categoria categoria) throws CampoNonValidoException {
        if (categoria == null) {
            throw new CampoNonValidoException("La categoria non puó essere vuota");
        }
        this.categoria = categoria;
    }

    /**
     * Imposta la classificazione PEGI del gioco.
     *
     * @param pegi Valore numerico per l'età minima consentita.
     * @throws CampoNonValidoException Se il valore è inferiore a 3 o superiore a 18.
     */
    public void setPegi(int pegi) throws CampoNonValidoException {
        if (pegi < 3 || pegi > 18) {
            throw new CampoNonValidoException("Il PEGI deve essere tra 3 e 18 anni");
        }
        this.pegi = pegi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gioco gioco = (Gioco) o;
        return id == gioco.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return titolo;
    }
}