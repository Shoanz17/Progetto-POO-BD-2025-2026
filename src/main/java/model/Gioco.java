package model;

import java.util.ArrayList;

/**
 * Rappresenta un videogioco pubblicato da uno {@link Sviluppatore}.
 * Questa entità fa da "contenitore" concettuale: definisce le informazioni generali (titolo, PEGI, categoria)
 * e raggruppa le varie {@link EdizioneGioco} (le copie effettivamente acquistabili sulle varie console),
 * i {@link Genere} di appartenenza e le eventuali partecipazioni a una {@link Promozione} tramite {@link GiocoInPromozione}.
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

    /**
     * Costruttore utilizzato dalla GUI per creare un nuovo gioco da zero al momento della pubblicazione.
     * Nota: un gioco nasce senza {@link EdizioneGioco} e senza {@link GiocoInPromozione},
     * poiché queste entità richiedono che il gioco esista già per potersi agganciare.
     *
     * @param titolo Il nome del videogioco.
     * @param categoria La {@link Categoria} del gioco (es. Tripla A, Indie).
     * @param pegi Il rating PEGI per fasce d'età.
     * @param sviluppatore Lo {@link Sviluppatore} che lo sta pubblicando.
     * @param generi La lista di {@link Genere} iniziali del gioco.
     * @throws CampoNonValidoException Se lo sviluppatore manca o se si tenta di creare un gioco senza nemmeno un genere.
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
     * Attenzione: questo costruttore assegna solo i dati anagrafici base. Sarà compito del Controller o del DAO
     * riempire le liste (edizioni, generi, promozioni) in un secondo momento con apposite query.
     *
     * @param sviluppatore L'istanza dello {@link Sviluppatore} creatore.
     * @param id L'ID univoco assegnato dal database.
     * @param titolo Il titolo del gioco.
     * @param categoria La {@link Categoria} assegnata.
     * @param pegi Il rating PEGI.
     * @throws CampoNonValidoException Se il database restituisce uno sviluppatore nullo (DB Corrotto).
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
     * Collega il gioco a una nuova promozione attiva.
     *
     * @param promozione L'oggetto {@link GiocoInPromozione} che fa da ponte.
     * @throws CampoNonValidoException Se la promozione non esiste o se il gioco sta già partecipando a quello sconto (niente doppi sconti furbetti).
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
     * Aggiunge un nuovo genere alla lista delle categorie di questo gioco.
     *
     * @param genere Il {@link Genere} da aggiungere.
     * @throws CampoNonValidoException Se il genere non esiste o se è già stato assegnato a questo gioco.
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
     * Registra l'uscita di una nuova edizione (es.Il porting per una nuova console).
     *
     * @param edizione La nuova {@link EdizioneGioco} da mettere in vendita.
     * @throws CampoNonValidoException Se l'edizione è inesistente o se si cerca di rilasciare una copia già uscita.
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

    /**
     * Sovrascrive in blocco l'intera lista dei generi associati al gioco (utile per gli aggiornamenti dal DB o modifiche maggiori).
     *
     * @param generi La nuova lista di {@link Genere}.
     * @throws CampoNonValidoException Se la lista passata è vuota o nulla (il gioco deve avere un'identità).
     */
    public void setListaGeneri(ArrayList<Genere> generi) throws CampoNonValidoException {
        if(generi == null || generi.isEmpty()) throw new CampoNonValidoException("Non esiste gioco senza generi");

        this.generi = generi;
    }
    //metodi per admin

    /**
     * Rimuove un'edizione specifica dal gioco. Tipicamente utilizzato da un Admin per moderazione o rimozione forzata dallo store.
     *
     * @param edizione L'{@link EdizioneGioco} da eliminare.
     * @throws CampoNonValidoException Se l'edizione è inesistente o se era già stata epurata in precedenza.
     */
    public void removeEdizione(EdizioneGioco edizione) throws CampoNonValidoException {
        if (edizione == null) {
            throw new CampoNonValidoException("Edizione di gioco non disponibile");
        }
        if (!this.edizioni.contains(edizione)) {
            throw new CampoNonValidoException("Questa edizione é giá stata rimossa");
        }
        edizioni.remove(edizione);
    }

    /**
     * Rimuove un genere dalla lista del videogioco (metodo per Admin).
     *
     * @param genere Il {@link Genere} da levare.
     * @throws CampoNonValidoException Se il genere passato è nullo o non faceva parte dei generi del gioco.
     */
    public void removeGenere(Genere genere) throws CampoNonValidoException {
        if (genere == null) {
            throw new CampoNonValidoException("Genere non disponibile");
        }
        if (!this.generi.contains(genere)) {
            throw new CampoNonValidoException("Il gioco giá non ha questo genere");
        }
        generi.remove(genere);
    }

    /**
     * Interrompe la partecipazione del gioco a una specifica promozione (metodo per Admin).
     *
     * @param promozione L'oggetto {@link GiocoInPromozione} da scollegare.
     * @throws CampoNonValidoException Se si tenta di rimuovere una promozione a cui il gioco non ha mai preso parte.
     */
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
    public String getTitolo() {
        return titolo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getPegi() {
        return pegi;
    }

    public Sviluppatore getSviluppatore() {
        return sviluppatore;
    }

    public ArrayList<Genere> getGeneri() {
        return generi;
    }

    public ArrayList<EdizioneGioco> getEdizioni() {
        return edizioni;
    }

    public ArrayList<GiocoInPromozione> getPromozioni() {
        return promozioni;
    }

    public int getId() {
        return id;
    }

    //Lista di set

    /**
     * Imposta o modifica il titolo del videogioco.
     *
     * @param titolo Il nuovo nome del gioco.
     * @throws CampoNonValidoException Se il titolo è vuoto, nullo o troppo lungo (limite DB a 40 caratteri).
     */
    public void setTitolo(String titolo) throws CampoNonValidoException {
        if (titolo == null || titolo.trim().isEmpty() || titolo.length() > 40) {
            throw new CampoNonValidoException("Il titolo massimo 40 caratteri");
        }
        this.titolo = titolo;
    }

    /**
     * Imposta o modifica la categoria di appartenenza (budget/scala del gioco).
     *
     * @param categoria La nuova {@link Categoria}.
     * @throws CampoNonValidoException Se la categoria passata è nulla.
     */
    public void setCategoria(Categoria categoria) throws CampoNonValidoException {
        if (categoria == null) {
            throw new CampoNonValidoException("La categoria non puó essere vuota");
        }
        this.categoria = categoria;
    }

    /**
     * Imposta la classificazione PEGI per fasce d'età.
     *
     * @param pegi L'età minima consigliata (valori validi: 3, 7, 12, 16, 18).
     * @throws CampoNonValidoException Se il valore esce dal range sensato europeo (tra 3 e 18 anni).
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
        return this.titolo;
    }

}