package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Rappresenta un normale fruitore della piattaforma.
 * Estende l'entità base {@link Account} aggiungendo la gestione del portafoglio virtuale (saldo),
 * il sistema di amicizie tra vari {@link Utente}, gli acquisti effettuati tramite il {@link Carrello}
 * (certificati tramite {@link Fattura}) e la possibilità di seguire i propri {@link Sviluppatore} preferiti.
 */
public class Utente extends Account {
    private GenereEnum genere; //valutare se mettere un char singolo/una lista di valori
    private int saldo;
    private boolean bannato;
    private LocalDate dataNascita;
    private String email;

    //getter e setter
    public GenereEnum getGenere() { return genere; }
    public int getSaldo() { return saldo; }
    public boolean isBannato() { return bannato; }
    public LocalDate getDataNascita() { return dataNascita; }
    public String getEmail() {
        return email;
    }

    public void setGenere(GenereEnum genere) {this.genere = genere;}

    public void setBannato(boolean bannato) {this.bannato = bannato;}

    /**
     * Imposta la data di nascita dell'utente effettuando un controllo temporale di base.
     *
     * @param dataNascita La data inserita in fase di registrazione o modifica.
     * @throws CampoNonValidoException Se la data è nulla o se viene dal futuro.
     */
    public void setDataNascita(LocalDate dataNascita) throws CampoNonValidoException {
        if(dataNascita == null) throw new CampoNonValidoException("Data non esistente");
        if(dataNascita.isAfter(LocalDate.now())) throw new CampoNonValidoException("Non puoi essere nato nel futuro (non sono nato ieri!!!)");

        this.dataNascita = dataNascita;
    }

    /**
     * Imposta e valida l'indirizzo email tramite un'espressione regolare (RegEx) standard.
     *
     * @param email L'indirizzo di posta elettronica digitato dall'utente.
     * @throws CampoNonValidoException Se l'email è vuota, supera i 50 caratteri o ha un formato non valido.
     */
    public void setEmail(String email) throws CampoNonValidoException {
        //controllo non sia vuota
        if(email == null || email.trim().isBlank()) throw new CampoNonValidoException("L'email non può essere vuota");

        if(email.length() > 50) throw new CampoNonValidoException("Email troppo lunga (per i nostri standard almeno, non ti offendere");

        //questo regex controlla che ci sia: Testo + @ + Testo + . + Testo(di 2 o più lettere) (l'ho trovato su internet)
        String regexEmail = "^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";

        if(!email.matches(regexEmail)) throw new CampoNonValidoException("L'email non è nel formato adatto");

        //se passa tutti i controlli setta la mail
        this.email = email;
    }

    //metodi
    //valutare per entrambi se mettere le eccezioni checked

    /**
     * Ricarica il portafoglio virtuale dell'utente.
     *
     * @param soldiAggiunti L'importo da aggiungere al saldo corrente.
     * @throws CampoNonValidoException Se si tenta di aggiungere un importo negativo (niente giochetti di reverse-engineering).
     */
    public void aggiungiSaldo(int soldiAggiunti) throws CampoNonValidoException {
        if(soldiAggiunti < 0) throw new CampoNonValidoException("Non è possibile aggiungere saldo negativo");
        this.saldo += soldiAggiunti;
    }

    /**
     * Sottrae fondi dal portafoglio, generalmente richiamato in fase di checkout dal carrello.
     *
     * @param soldiTolti L'importo totale da scalare.
     * @throws CampoNonValidoException Se si tenta di spendere più di quanto si possiede (la piattaforma non concede prestiti).
     */
    public void rimuoviSaldo(int soldiTolti) throws CampoNonValidoException {
        if(soldiTolti > this.saldo) throw new CampoNonValidoException("Saldo insufficiente (come ci sei finito qui? Controller fai il tuo lavoro");
        this.saldo -= soldiTolti;
    }


    //gestione relazionale
    private Carrello carrello;

    public Carrello getCarrello() { return carrello; }

    /**
     * Associa un {@link Carrello} personale all'utente.
     *
     * @param c L'istanza del carrello.
     * @throws CampoNonValidoException Se il carrello passato è nullo.
     */
    public void setCarrello(Carrello c) throws CampoNonValidoException {
        if(c == null) throw new CampoNonValidoException("Il carrello non esiste");

        this.carrello = c;
    }

    private ArrayList<Utente> listaAmici = new ArrayList<>();

    public ArrayList<Utente> getListaAmici() { return listaAmici; }

    /**
     * Aggiunge un altro {@link Utente} alla lista contatti (amici) di questo account.
     *
     * @param nuovoAmico L'utente da aggiungere.
     * @throws CampoNonValidoException Se si cerca di aggiungere un utente nullo, se stessi, o qualcuno già presente in lista.
     */
    public void addAmico(Utente nuovoAmico) throws CampoNonValidoException {
        //check di validità
        if(nuovoAmico == null) throw new CampoNonValidoException("Mi dispiace ma gli amici immaginari non valgono");
        if (this.getId() == nuovoAmico.getId()) throw new CampoNonValidoException("Non puoi far amicizia con te stesso (che cosa triste...)");
        if(this.listaAmici.contains(nuovoAmico)) throw new CampoNonValidoException("Sei già amico con questo utente");

        this.listaAmici.add(nuovoAmico);
    }

    /**
     * Rimuove un {@link Utente} dalla propria lista amici.
     *
     * @param amico L'ex-amico da eliminare dalla lista.
     * @throws CampoNonValidoException Se l'utente passato è nullo o se non era presente in lista.
     */
    public void removeAmico(Utente amico) throws CampoNonValidoException {
        //check di validità
        if(amico == null) throw new CampoNonValidoException("Non sei ancora cresciuto abbastanza per abbandonare gli amici immaginari");
        if(!this.listaAmici.contains(amico)) throw new CampoNonValidoException("Non sei amico di questa persona (lo odi così tanto?)");

        this.listaAmici.remove(amico);
    }

    private ArrayList<Fattura> giochiAcquistati = new ArrayList<>(); //o anche libreria

    public ArrayList<Fattura> getGiochiAcquistati() { return giochiAcquistati; }

    /**
     * Registra un nuovo acquisto aggiungendo la relativa {@link Fattura} alla libreria personale dell'utente.
     *
     * @param gioco La ricevuta dell'edizione appena acquistata.
     * @throws CampoNonValidoException Se la fattura è nulla o se l'utente sta cercando di ricomprare esattamente la stessa copia.
     */
    public void addGioco(Fattura gioco) throws CampoNonValidoException {
        //check di validità
        if(gioco == null) throw new CampoNonValidoException("Il gioco non esiste");
        if(this.giochiAcquistati.contains(gioco)) throw new CampoNonValidoException("Hai già comprato " + gioco.getGioco().getGioco().getTitolo() + " per" + gioco.getGioco().getPiattaforma().getNome());

        this.giochiAcquistati.add(gioco);
    }

    /**
     * Rimuove una {@link Fattura} dalla libreria (tipicamente usato in caso di rimborsi o rimozione forzata da parte di un Admin).
     *
     * @param gioco La ricevuta del gioco da rimuovere.
     * @throws CampoNonValidoException Se la fattura è nulla o non fa parte della libreria di questo utente.
     */
    public void removeGioco(Fattura gioco) throws CampoNonValidoException { //o rimborso se preferisci
        //check di validità
        if(gioco == null) throw new CampoNonValidoException("Il gioco non esiste");
        if(!this.giochiAcquistati.contains(gioco)) throw new CampoNonValidoException("Questo gioco non fa parte della tua libreria (non provare a scammarci)");

        this.giochiAcquistati.remove(gioco);
    }

    private ArrayList<Sviluppatore> sviluppatoriSeguiti = new ArrayList<>();

    public ArrayList<Sviluppatore> getSviluppatoriSeguiti() { return sviluppatoriSeguiti; }


    /**
     * Inizia a seguire un nuovo {@link Sviluppatore} per supportarlo e tenersi aggiornato sui suoi giochi.
     *
     * @param sviluppatore Lo studio di sviluppo da aggiungere ai seguiti.
     * @throws CampoNonValidoException Se lo sviluppatore è nullo o se è già nella lista dei seguiti.
     */
    public void addSviluppatoreSeguito(Sviluppatore sviluppatore) throws CampoNonValidoException {
        //check di validità
        if(sviluppatore == null) throw new CampoNonValidoException("Lo sviluppatore non esiste");
        if(this.sviluppatoriSeguiti.contains(sviluppatore)) throw new CampoNonValidoException("Segui già questo sviluppatore");

        this.sviluppatoriSeguiti.add(sviluppatore);
    }

    /**
     * Smette di seguire uno {@link Sviluppatore}.
     *
     * @param sviluppatore Lo studio di sviluppo da rimuovere.
     * @throws CampoNonValidoException Se lo sviluppatore è nullo o non era presente nella lista dei seguiti.
     */
    public void removeSviluppatoreSeguito(Sviluppatore sviluppatore) throws CampoNonValidoException {
        //check di validità
        if(sviluppatore == null) throw new CampoNonValidoException("Lo sviluppatore non esiste");
        if(!this.sviluppatoriSeguiti.contains(sviluppatore)) throw new CampoNonValidoException("Già non segui questo sviluppatore (lo odi così tanto?)");

        this.sviluppatoriSeguiti.remove(sviluppatore);
    }



    //costruttore chiamato alla creazione di un Utente dalla GUI

    /**
     * Costruttore utilizzato dalla GUI al momento della registrazione di un nuovo utente.
     * Genera un account "pulito": saldo a zero, non bannato, e vi collega automaticamente un nuovo {@link Carrello} vuoto.
     *
     * @param nome Il nome utente per il login.
     * @param password La password per l'accesso.
     * @param genere L'identificativo anagrafico del genere.
     * @param email L'indirizzo di posta elettronica.
     * @param dataNascita La data di nascita (fondamentale per i controlli PEGI futuri).
     * @throws CampoNonValidoException Se i controlli di validazione (es. formato email, data nel futuro) falliscono.
     */
    public Utente(String nome, String password, GenereEnum genere, String email, LocalDate dataNascita) throws CampoNonValidoException {
        super(nome, password);

        setGenere(genere);
        setEmail(email);
        this.saldo = 0;
        setBannato(false);
        setDataNascita(dataNascita);
        this.carrello = new Carrello(this); //capire come implementare Carrello in db
    }

    //costruttore chiamato dal DAO quando pesca un Utente dal DB

    /**
     * Costruttore utilizzato dal DAO per ricostruire un account Utente prelevato dal Database.
     * A differenza del costruttore base, questo setta direttamente i valori storici (saldo, stato ban)
     * ma NON istanzia il carrello: sarà il Controller/DAO ad agganciarlo in un secondo momento.
     *
     * @param id L'ID univoco nel DB.
     * @param nome Il nome utente.
     * @param password L'hash o la password registrata.
     * @param dataCreazione La data d'iscrizione alla piattaforma.
     * @param genere Il sesso/genere registrato.
     * @param email La mail dell'utente.
     * @param dataNascita La data di nascita.
     * @param saldo L'importo attuale del portafoglio.
     * @param bannato Lo stato disciplinare dell'account.
     */
    public Utente(int id, String nome, String password, LocalDate dataCreazione, GenereEnum genere, String email, LocalDate dataNascita, int saldo, boolean bannato) {
        super(nome, id, password, dataCreazione);

        this.genere = genere; //non c'è più bisogno del set perché sono già dati giusti
        this.email = email;
        this.dataNascita = dataNascita;
        this.saldo = saldo;
        this.bannato = bannato;
        this.carrello = null; //il carrello qui non va messo perché è il controller a inserirlo poi
    }

}
