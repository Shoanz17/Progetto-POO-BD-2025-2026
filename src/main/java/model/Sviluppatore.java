package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Rappresenta un creatore di videogiochi, un account con privilegi di pubblicazione.
 * Estende l'entità base {@link Account} aggiungendo la gestione del portafoglio (fondi),
 * una vetrina pubblica (descrizione), un catalogo di {@link Gioco} pubblicati e un
 * sistema disciplinare a strike (3 strike = ban).
 */
public class Sviluppatore extends Account{
// definiamo le nostre variabili
    private int strike;
    private String descrizione;//massimo 500 caratteri
    private int fondi;
    private ArrayList<Gioco> listaGiochi = new ArrayList<>();
    private ArrayList<Utente> seguiti = new ArrayList<>();


    /**
     * Costruttore utilizzato al momento della registrazione di un nuovo sviluppatore sulla piattaforma.
     * L'account nasce con 0 strike e con 0 fondi.
     *
     * @param nome L'identificativo visibile al pubblico.
     * @param password La password per l'accesso.
     * @param descrizione La bio o presentazione pubblica della casa di sviluppo.
     * @throws CampoNonValidoException Se i parametri base sono vuoti o se la descrizione vìola i limiti di lunghezza.
     */
    public Sviluppatore(String nome,String password, String descrizione) throws CampoNonValidoException
    {
        super(nome,password);
        this.strike = 0;
        setDescrizione(descrizione);
        this.fondi = 0;
    }
    //costruttore per il DAO

    /**
     * Costruttore utilizzato dal DAO per ricostruire un account Sviluppatore già esistente nel Database.
     *
     * @param nome Il nome utente.
     * @param id L'ID univoco assegnato dal DB.
     * @param password L'hash o la password registrata.
     * @param dataCreazione La data di iscrizione alla piattaforma.
     * @param strike Il numero di penalità accumulate.
     * @param descrizione Il testo di presentazione.
     * @param fondi I ricavi totali correnti.
     */
    public Sviluppatore(String nome, int id, String password,
                        LocalDate dataCreazione,int strike,String descrizione,int fondi)
    {
        super(nome,id,password,dataCreazione);
        this.strike = strike;
        this.descrizione = descrizione;
        this.fondi = fondi;
    }
    public int getStrike(){return strike;}


    /**
     * Aggiunge una penalità disciplinare allo sviluppatore (tipicamente assegnata da un Admin).
     * Al raggiungimento del 3° strike, scatta il ban automatico.
     *
     * @throws CampoNonValidoException Se si cerca di infierire su uno sviluppatore già bannato.
     */
    public void addStrike() throws CampoNonValidoException
    {
        if(isBannato())
        {throw new CampoNonValidoException("L'utente è già bannato");}

        this.strike++;
    }

    /**
     * Condona una penalità allo sviluppatore riducendo il conteggio degli strike.
     *
     * @throws CampoNonValidoException Se lo sviluppatore ha già 0 strike (i "buoni per il paradiso" non li diamo qui, gli strike non vanno in negativo).
     */
    public void removeStrike() throws CampoNonValidoException {
        if(this.strike <= 0) throw new CampoNonValidoException("Gli strike non possono essere sotto lo 0");
            else
                this.strike--;
    }

    /**
     * Verifica se lo sviluppatore ha esaurito i suoi tentativi ed è stato allontanato dalla piattaforma.
     *
     * @return true se l'account ha 3 o più strike, false altrimenti.
     */
    public boolean isBannato(){
        if(this.strike>=3) return true;

        return false;
    }

    public ArrayList<Gioco> getListaGiochi(){return listaGiochi;}
    public ArrayList<Utente> getSeguiti() {return seguiti;}

    public int getFondi(){return fondi;}


    public String getDescrizione() {return descrizione;}

    /**
     * Imposta o aggiorna la descrizione pubblica della casa di sviluppo.
     *
     * @param descrizione Il nuovo testo descrittivo.
     * @throws CampoNonValidoException Se il testo è nullo, vuoto o supera il limite massimo dei 500 caratteri.
     */
    public void setDescrizione(String descrizione) throws CampoNonValidoException
    {
        if(descrizione == null||descrizione.trim().isEmpty())
        {
            throw new CampoNonValidoException("la descrizione è vuota.");
        }
        else if (descrizione.length()>500)
        throw new CampoNonValidoException("la descrizione è troppo lunga.");

        this.descrizione = descrizione;

    }

    /**
     * Aumenta il saldo dello sviluppatore, generalmente in seguito all'acquisto di un suo gioco da parte di un {@link Utente}.
     *
     * @param importo La cifra guadagnata da aggiungere ai fondi correnti.
     * @throws CampoNonValidoException Se l'importo passato è negativo (non puoi incassare debiti!).
     */
    public void addFondi(int importo) throws CampoNonValidoException
    {
        if(importo < 0)throw new CampoNonValidoException("L'importo non può essere negativo");

        this.fondi+=importo;
    }


    /**
     * Sottrae fondi dal saldo, ad esempio per prelievi o multe del sistema.
     *
     * @param importo La cifra da scalare dal portafoglio.
     * @throws CampoNonValidoException Se si tenta di prelevare più soldi di quanti se ne possiedono (questa piattaforma non concede prestiti).
     */
    public void removeFondi(int importo) throws CampoNonValidoException
    {
        if(importo>this.fondi)throw new CampoNonValidoException("L'importo supera i fondi");
        this.fondi-=importo;
    }

    /**
     * Registra un nuovo videogioco all'interno del catalogo dello sviluppatore.
     *
     * @param gioco L'istanza del {@link Gioco} da pubblicare.
     * @throws CampoNonValidoException Se il gioco è nullo o se è già presente in catalogo (evitiamo doppioni spudorati).
     */
    public void addGioco(Gioco gioco) throws CampoNonValidoException
    {
        if(gioco == null)
            throw new CampoNonValidoException("Il gioco non esiste");

        if(this.listaGiochi.contains(gioco))
            throw new CampoNonValidoException("il gioco è già presente");

        this.listaGiochi.add(gioco);
    }

}


