package controller;

import dao.*;
import implementazionePostgresDAO.*;
import model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

/**
 * Controller principale dell'applicazione.
 * Gestisce la logica di business e funge da intermediario tra il livello visivo (GUI)
 * e il livello di persistenza dei dati (DAO).
 */
public class Controller {
    private final AccountDAO accountDAO;
    private final UtenteDAO utenteDAO;
    private final SviluppatoreDAO sviluppatoreDAO;
    private final FatturaDAO fatturaDAO;
    private final RecensioneDAO recensioneDAO;
    private final GiocoDAO giocoDAO;
    private final EdizioneGiocoDAO edizioneGiocoDAO;
    private final GenereDAO genereDAO;
    private final PiattaformaDiGiocoDAO piattaformaDiGiocoDAO;
    private final PromozioneDAO promozioneDAO;
    private final GiocoInPromozioneDAO giocoInPromozioneDAO;

    /**
     * Costruisce il Controller inizializzando tutte le interfacce DAO
     * con le relative implementazioni concrete per PostgreSQL.
     */
    public Controller() {
        this.accountDAO = new AccountDAOPostgres();
        this.utenteDAO = new UtenteDAOPostgres();
        this.sviluppatoreDAO = new SviluppatoreDAOPostgres();
        this.fatturaDAO = new FatturaDAOPostgres();
        this.recensioneDAO = new RecensioneDAOPostgres();
        this.giocoDAO = new GiocoDAOPostgres();
        this.edizioneGiocoDAO = new EdizioneGiocoDAOPostgres();
        this.genereDAO = new GenereDAOPostgres();
        this.piattaformaDiGiocoDAO = new PiattaformaDiGiocoDAOPostgres();
        this.promozioneDAO = new PromozioneDAOPostgres();
        this.giocoInPromozioneDAO = new GiocoInPromozioneDAOPostgres();
    }

    public String getNomeAccount(Account account) {return account.getNome();}
    public String getNomeGenereEnum(GenereEnum genere){return genere.name();}

    /**
     * Registra un nuovo utente nel sistema validando prima i dati inseriti.
     *
     * @param nome Il nome dell'utente.
     * @param password La password scelta.
     * @param genere Il genere sotto forma di stringa.
     * @param email L'indirizzo email.
     * @param dataNascita La data di nascita formattata testualmente.
     * @throws CampoNonValidoException Se uno o più parametri non rispettano i vincoli o se il DAO fallisce.
     */
    public void registraUtente(String nome, String password, String genere, String email, String dataNascita) throws CampoNonValidoException {
        Account.verificaFormatoNome(nome);
        Account.verificaFormatoPassword(password);

        if (genere == null || genere.trim().isEmpty())
            throw new CampoNonValidoException("Seleziona un genere valido dalla lista!");

        //converte la stringa nell'enum corrispondente
        GenereEnum genereVero = GenereEnum.valueOf(genere);

        //cercato su internet
        LocalDate dataNascitaVera = convertiDataRigida(dataNascita);

        //finalmente creo l'oggetto
        Utente utente = new Utente(nome, password, genereVero, email, dataNascitaVera);

        //DA FARE completare con implementazione
        try {
            utenteDAO.registraUtente(utente);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Converte in modo rigoroso una stringa in un oggetto {@link LocalDate}, impedendo date inesistenti (es. 31 Febbraio).
     *
     * @param testoData La data in formato "dd/MM/uuuu".
     * @return L'oggetto LocalDate risultante dalla conversione.
     * @throws CampoNonValidoException Se la data è incompleta o non valida per il calendario gregoriano.
     */
    private LocalDate convertiDataRigida(String testoData) throws CampoNonValidoException {
        if (testoData == null || testoData.contains("_") || testoData.trim().length() < 10) {
            throw new CampoNonValidoException("Inserisci la data completa!");
        }

        try {
            // Configurazione rigida per evitare date inventate (es. 31 Febbraio)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            return LocalDate.parse(testoData.trim(), formatter);

        } catch (DateTimeParseException e) {
            throw new CampoNonValidoException("La data inserita non esiste nel calendario!");
        }
    }

    /**
     * Registra un nuovo account sviluppatore nel sistema.
     *
     * @param nome Il nome dello sviluppatore.
     * @param password La password scelta.
     * @param descrizione La descrizione del profilo sviluppatore.
     * @throws CampoNonValidoException Se i parametri non sono validi o se l'inserimento DAO fallisce.
     */
    public void registraSviluppatore(String nome, String password, String descrizione) throws CampoNonValidoException {
        Sviluppatore sviluppatore = new Sviluppatore(nome, password, descrizione);

        try {
            sviluppatoreDAO.registraSviluppatore(sviluppatore);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Gestisce il processo di login, verificando il formato delle credenziali prima di interrogare il DB.
     *
     * @param nome Il nome dell'account inserito.
     * @param password La password inserita.
     * @return L'{@link Account} loggato se le credenziali sono corrette.
     * @throws CampoNonValidoException Se il formato è errato o se l'account non viene trovato.
     */
    public Account accedi(String nome, String password) throws CampoNonValidoException {
        Account.verificaFormatoNome(nome);
        Account.verificaFormatoPassword(password);

        try {
            Account accountTrovato = accountDAO.accedi(nome, password);

            if (accountTrovato == null) {
                throw new CampoNonValidoException("Nome o password errate");
            }

            return accountTrovato;

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Cerca gli utenti nel sistema applicando un filtro sul nome e sullo stato di ban (Admin).
     * @param testoRicerca Il testo da cercare nel nome.
     * @param statoBan Booleano che indica se cercare tra i bannati o meno.
     * @return Lista degli {@link Utente} corrispondenti ai filtri.
     * @throws CampoNonValidoException In caso di errore DAO.
     */
    public ArrayList<Utente> getUtentiFiltratiAdmin(String testoRicerca, boolean statoBan) throws CampoNonValidoException{
        try{
            return utenteDAO.getUtentiFiltratiAdmin(testoRicerca, statoBan);
        } catch (SQLException e){
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Cerca gli sviluppatori filtrandoli per nome testuale.
     * @param testoRicerca Il testo da ricercare.
     * @return Lista di {@link Sviluppatore} filtrata.
     * @throws CampoNonValidoException In caso di errore DAO.
     */
    public ArrayList<Sviluppatore> getListaSviluppatoriFiltrati(String testoRicerca) throws CampoNonValidoException{
        try {
            return sviluppatoreDAO.getListaSviluppatoriFiltrati(testoRicerca);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Restituisce il numero totale di recensioni rilasciate da un utente.
     * @param utenteLoggato L'{@link Utente} di cui calcolare le recensioni.
     * @return Numero di recensioni scritte.
     */
    public int getNumeroRecensioniUtente(Utente utenteLoggato) throws CampoNonValidoException {
        try {
            return recensioneDAO.getNumeroRecensioni(utenteLoggato.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Aggiunge fondi al portafoglio dell'utente passando l'intero numerico.
     * @param utenteLoggato L'{@link Utente} loggato.
     * @param saldo Valore intero da aggiungere.
     */
    public void aggiungiSaldo(Utente utenteLoggato, int saldo) throws CampoNonValidoException {
        try {
            utenteDAO.aggiungiSaldo(utenteLoggato.getId(), saldo);
            utenteLoggato.aggiungiSaldo(saldo);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Aggiunge fondi al portafoglio dell'utente effettuando prima il parsing della stringa testuale.
     * @param utenteLoggato L'{@link Utente} loggato.
     * @param saldoTesto Importo digitato in formato testuale.
     * @throws CampoNonValidoException Se l'input non è un numero valido.
     */
    public void aggiungiSaldo(Utente utenteLoggato, String saldoTesto) throws CampoNonValidoException {
        try {
            if (saldoTesto == null || saldoTesto.trim().isEmpty()) {
                throw new CampoNonValidoException("Scrivere quanto si vuole aggiungere");
            }
            int saldo = Integer.parseInt(saldoTesto.trim());

            utenteDAO.aggiungiSaldo(utenteLoggato.getId(),saldo);
            utenteLoggato.aggiungiSaldo(saldo);

        } catch (NumberFormatException e) {
            throw new CampoNonValidoException("Inserire un numero");
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Aggiorna e salva le modifiche al profilo utente solo per i campi che sono stati modificati.
     * @param utenteLoggato L'oggetto {@link Utente} attuale.
     * @param nuovoNome Il nuovo nome (vuoto per non modificare).
     * @param nuovaPassword La nuova password (vuoto per non modificare).
     * @param nuovaEmail La nuova email (vuoto per non modificare).
     * @param nuovoGenere Il nuovo {@link GenereEnum} (vuoto per non modificare).
     * @param nuovaData La nuova data (vuota per non modificare).
     */
    public void salvaModificheProfilo(Utente utenteLoggato, String nuovoNome, String nuovaPassword, String nuovaEmail, GenereEnum nuovoGenere, String nuovaData) throws CampoNonValidoException {

        String nomeFinale = utenteLoggato.getNome();
        if (!nuovoNome.isEmpty()) {
            nomeFinale = nuovoNome;
        }

        String passFinale = utenteLoggato.getPassword();
        if (!nuovaPassword.isEmpty()) {
            passFinale = nuovaPassword;
        }

        String emailFinale = utenteLoggato.getEmail();
        if (!nuovaEmail.isEmpty()) {
            emailFinale = nuovaEmail;
        }

        GenereEnum genereFinale = utenteLoggato.getGenere();
        if (nuovoGenere != null) {
            genereFinale = nuovoGenere;
        }

        LocalDate dataFinale = utenteLoggato.getDataNascita();
        if (!nuovaData.isEmpty()) {
            dataFinale = convertiDataRigida(nuovaData);
        }

        Utente clonePerDB = new Utente(utenteLoggato.getId(), nomeFinale, passFinale, utenteLoggato.getDataCreazione(), genereFinale, emailFinale, dataFinale, utenteLoggato.getSaldo(), utenteLoggato.isBannato());

        try {
            utenteDAO.aggiornaProfiloUtente(clonePerDB);

            utenteLoggato.setNome(nomeFinale);
            utenteLoggato.setPassword(passFinale);
            utenteLoggato.setEmail(emailFinale);
            utenteLoggato.setGenere(genereFinale);
            utenteLoggato.setDataNascita(dataFinale);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public int getIdUtente(Utente u) {return u.getId();}

    public int getNumeroGiochiAcquistatiUtente(Utente u) throws CampoNonValidoException {
        try {
            return fatturaDAO.getNumeroGiochiAcquistati(u.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public GenereEnum getGenereUtente(Utente u) { return u.getGenere(); }
    public String getNomeUtente(Utente u) { return u.getNome(); }
    public String getEmailUtente(Utente u) { return u.getEmail(); }
    public LocalDate getDataDiNascitaUtente(Utente u) { return u.getDataNascita(); }
    public int getSaldoUtente(Utente u) { return u.getSaldo(); }

    /**
     * Restituisce la lista di tutti i generi personali selezionabili.
     * @return Lista dei {@link GenereEnum}.
     */
    public ArrayList<GenereEnum> getListaGeneriEnum (){
        ArrayList<GenereEnum> listaGeneriEnum = new ArrayList<>();

        for (GenereEnum genere : GenereEnum.values()) {
            listaGeneriEnum.add(genere);
        }
        return listaGeneriEnum;
    }

    /**
     * Recupera lo storico degli acquisti (libreria/fatture) dell'utente specificato.
     * @param idUtente L'id dell'utente.
     * @return Lista di {@link Fattura}.
     */
    public ArrayList<Fattura> getLibreriaUtente(int idUtente) throws CampoNonValidoException {
        try {
            return fatturaDAO.getLibreriaUtente(idUtente);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public LocalDate getDataCreazioneAccountUtente(Utente u) {return u.getDataCreazione();}
    public boolean isUtenteBannato(Utente u) {return u.isBannato();}

    /**
     * Registra l'operazione in cui un utente inizia a seguire uno sviluppatore.
     * @param utenteloggato L'utente che compie l'azione.
     * @param sviluppatoreSelezionato Lo sviluppatore da seguire.
     */
    public void aggiungiSviluppatoreSeguito(Utente utenteloggato, Sviluppatore sviluppatoreSelezionato) throws CampoNonValidoException {
        try {
            utenteDAO.inserisciSviluppatoreSeguito(utenteloggato.getId(), sviluppatoreSelezionato.getId());
            utenteloggato.addSviluppatoreSeguito(sviluppatoreSelezionato);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    /**
     * Rimuove uno sviluppatore dalla lista dei seguiti di un utente e aggiorna il Database.
     *
     * @param utenteloggato L'{@link Utente} che compie l'azione.
     * @param sviluppatoreSelezionato Lo {@link Sviluppatore} da smettere di seguire.
     * @throws CampoNonValidoException Se l'operazione nel Database fallisce.
     */
    public void rimuoviSviluppatoreSeguito(Utente utenteloggato, Sviluppatore sviluppatoreSelezionato) throws CampoNonValidoException {
        try {
            utenteDAO.eliminaSviluppatoreSeguito(utenteloggato.getId(), sviluppatoreSelezionato.getId());
            utenteloggato.removeSviluppatoreSeguito(sviluppatoreSelezionato);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    /**
     * Recupera la descrizione di uno sviluppatore.
     *
     * @param s L'oggetto {@link Sviluppatore}.
     * @return La stringa contenente la descrizione dello sviluppatore.
     */
    public String getDescrizioneSviluppatore(Sviluppatore s) {return s.getDescrizione();}

    /**
     * Recupera tutte le edizioni di giochi pubblicate da uno specifico sviluppatore.
     *
     * @param sviluppatore L'oggetto {@link Sviluppatore} di cui cercare i giochi.
     * @return Un' ArrayList di {@link EdizioneGioco} appartenenti allo sviluppatore.
     * @throws CampoNonValidoException Se il recupero dal Database fallisce.
     */
    public ArrayList<EdizioneGioco> getListaEdizioniSviluppatore(Sviluppatore sviluppatore) throws CampoNonValidoException {
        try {
            return edizioneGiocoDAO.getListaEdizioniSviluppatore(sviluppatore.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Recupera il numero totale di giochi rilasciati da uno sviluppatore.
     *
     * @param s L'oggetto {@link Sviluppatore}.
     * @return Il numero intero di giochi rilasciati.
     * @throws CampoNonValidoException Se la query al Database fallisce.
     */
    public int getNumeroGiochiRilasciatiSviluppatore(Sviluppatore s) throws CampoNonValidoException {
        try {
            return sviluppatoreDAO.getNumeroGiochiRilasciati(s.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    /**
     * Verifica se uno sviluppatore è attualmente bannato.
     *
     * @param sviluppatore L'oggetto {@link Sviluppatore} da controllare.
     * @return true se lo sviluppatore è bannato, false altrimenti.
     */
    public boolean isSviluppatoreBannato(Sviluppatore sviluppatore) {return sviluppatore.isBannato();}

    /**
     * Aggiunge uno "strike" allo sviluppatore e aggiorna il Database.
     *
     * @param sviluppatore L'oggetto {@link Sviluppatore} da penalizzare.
     * @throws CampoNonValidoException Se l'aggiornamento nel Database fallisce.
     */
    public void addStrikeSviluppatore(Sviluppatore sviluppatore) throws CampoNonValidoException {
        sviluppatore.addStrike();
        try {
            sviluppatoreDAO.aggiungiStrike(sviluppatore.getId());
        } catch (SQLException e) {
            sviluppatore.removeStrike();
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Rimuove uno "strike" allo sviluppatore e aggiorna il Database.
     *
     * @param sviluppatore L'oggetto {@link Sviluppatore} a cui rimuovere la penalità.
     * @throws CampoNonValidoException Se l'aggiornamento nel Database fallisce.
     */
    public void removeStrikeSviluppatore(Sviluppatore sviluppatore) throws CampoNonValidoException {
        sviluppatore.removeStrike();
        try {
            sviluppatoreDAO.rimuoviStrike(sviluppatore.getId());
        } catch (SQLException e) {
            sviluppatore.addStrike();
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Recupera il numero attuale di strike accumulati da uno sviluppatore.
     *
     * @param sviluppatore L'oggetto {@link Sviluppatore}.
     * @return Il numero intero di strike.
     */
    public int getStrikeSviluppatore(Sviluppatore sviluppatore) {return sviluppatore.getStrike();}
    /**
     * Aggiunge uno strike allo sviluppatore partendo da un suo gioco specifico.
     *
     * @param gioco L'oggetto {@link Gioco} il cui sviluppatore subirà lo strike.
     * @throws CampoNonValidoException Se l'aggiornamento nel Database fallisce.
     */
    public void addStrikeSviluppatoreDaGioco(Gioco gioco) throws CampoNonValidoException {addStrikeSviluppatore(gioco.getSviluppatore());}
    public String getNomeSviluppatore(Sviluppatore s) {return s.getNome();}
    public String getNomeSviluppatoreDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getGioco().getSviluppatore().getNome();}

    public Gioco getGiocoDaEdizione(EdizioneGioco edizioneGioco) { return edizioneGioco.getGioco(); }

    /**
     * Recupera il prezzo di una specifica edizione di gioco, applicando automaticamente
     * lo sconto percentuale se il gioco è attualmente in promozione.
     *
     * @param edizioneGioco L'oggetto {@link EdizioneGioco}.
     * @return Il prezzo finale in formato intero (scontato o di listino).
     */
    public int getPrezzoDaEdizioneGioco(EdizioneGioco edizioneGioco) throws CampoNonValidoException {
        int prezzoBase = edizioneGioco.getPrezzo();

        try {
            ArrayList<GiocoInPromozione> promozioniDelGioco = giocoInPromozioneDAO.getPromozioniPerGioco(edizioneGioco.getGioco());

            for (GiocoInPromozione p : promozioniDelGioco) {
                if (!p.getPromozione().getDataFine().isBefore(LocalDate.now())) {

                    int percentualeSconto = p.getPercentuale();
                    return prezzoBase - (prezzoBase * percentualeSconto / 100);
                }
            }
        } catch (SQLException e) {
            System.out.println("Operazione Fallita");
        }

        return prezzoBase;
    }

    public PiattaformaDiGioco getPiattaformaDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getPiattaforma();}
    public ArrayList<Genere> getGeneriDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getGioco().getGeneri();}
    public int getPegiDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getGioco().getPegi();}
    /**
     * Calcola la media voti delle recensioni associate a una specifica edizione di gioco.
     *
     * @param edizioneGioco L'oggetto {@link EdizioneGioco}.
     * @return La media dei voti in formato intero.
     * @throws SQLException Se si verifica un errore durante il calcolo nel Database.
     */
    public int getMediaVotiEdizioneGioco(EdizioneGioco edizioneGioco) throws CampoNonValidoException {
        try {
            return recensioneDAO.getMediaVotiEdizioneGioco(edizioneGioco.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public Categoria getCategoriaDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getGioco().getCategoria();}
    public LocalDate getDataDiRilascioDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getDataRilascio();}

    /**
     * Collega un utente come amico di un altro nel Database e nel Model.
     *
     * @param utenteLoggato L'{@link Utente} che invia o conferma l'amicizia.
     * @param utenteSelezionato L'{@link Utente} da aggiungere agli amici.
     * @throws CampoNonValidoException Se l'inserimento nel Database fallisce.
     */
    public void aggiungiAmico(Utente utenteLoggato, Utente utenteSelezionato) throws CampoNonValidoException {
        try {
            utenteDAO.inserisciAmico(utenteLoggato.getId(), utenteSelezionato.getId());
            utenteLoggato.addAmico(utenteSelezionato);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    /**
     * Scioglie il legame d'amicizia tra due utenti.
     *
     * @param utenteLoggato L'{@link Utente} che rimuove l'amicizia.
     * @param utenteSelezionato L'{@link Utente} da rimuovere dalla lista amici.
     * @throws CampoNonValidoException Se l'eliminazione nel Database fallisce.
     */
    public void rimuoviAmico(Utente utenteLoggato, Utente utenteSelezionato) throws CampoNonValidoException {
        try {
            utenteDAO.eliminaAmico(utenteLoggato.getId(), utenteSelezionato.getId());
            utenteLoggato.removeAmico(utenteSelezionato);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    /**
     * Recupera tutte le recensioni rilasciate da uno specifico utente.
     *
     * @param idUtente L'identificativo dell'{@link Utente}.
     * @return Un'ArrayList di {@link Recensione} scritte dall'utente.
     * @throws CampoNonValidoException Se il recupero dei dati fallisce.
     */
    public ArrayList<Recensione> getListaRecensioniUtente(int idUtente) throws CampoNonValidoException {
        try {
            return recensioneDAO.getListaRecensioniUtente(idUtente);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public Fattura getFatturaDaRecensione(Recensione r) { return r.getFattura(); }

    /**
     * Elimina una recensione associata a una determinata fattura di acquisto.
     *
     * @param fattura L'oggetto {@link Fattura} di cui eliminare la recensione.
     * @throws CampoNonValidoException Se l'eliminazione nel Database fallisce.
     */
    public void rimuoviRecensioneSelezionataDaFattura(Fattura fattura) throws CampoNonValidoException {
        try {
            recensioneDAO.eliminaRecensione(fattura.getId());
            fattura.setRecensione(null);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    /**
     * Recupera la lista di tutti i generi videoludici disponibili.
     *
     * @return Un'ArrayList di tutti gli oggetti {@link Genere}.
     * @throws CampoNonValidoException Se il recupero dei dati fallisce.
     */
    public ArrayList<Genere> getGeneri() throws CampoNonValidoException {
        try {
            return genereDAO.getListaGeneri();
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Recupera una lista di generi filtrata in base a una stringa di ricerca.
     *
     * @param testoRicerca La stringa da cercare nei nomi dei generi.
     * @return Un'ArrayList di {@link Genere} corrispondenti.
     * @throws CampoNonValidoException Se il recupero dei dati fallisce.
     */
    public ArrayList<Genere> getGeneriFiltrati(String testoRicerca) throws CampoNonValidoException {
        try {
            return genereDAO.getGeneriFiltrati(testoRicerca);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Genera e restituisce una lista contenente tutte le categorie enumerabili disponibili.
     *
     * @return Un'ArrayList di {@link Categoria}.
     */
    public ArrayList<Categoria> getCategorie() {
        ArrayList<Categoria> categorie = new ArrayList<>();
        for (Categoria c : Categoria.values()) {
            categorie.add(c);
        }
        return categorie;
    }

    //metodi per prendere dati da un gioco
    /**
     * Recupera una lista di giochi filtrata in base a una stringa di ricerca nel titolo.
     *
     * @param testoRicerca La stringa da cercare.
     * @return Un'ArrayList di {@link Gioco} corrispondenti.
     * @throws CampoNonValidoException Se il recupero dal Database fallisce.
     */
    public ArrayList<Gioco> getGiochiFiltrati(String testoRicerca) throws CampoNonValidoException {
        try {
            return giocoDAO.getGiochiFiltrati(testoRicerca);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public String getTitoloGioco(Gioco gioco) {return gioco.getTitolo();}
    public Categoria getCategoriaGioco(Gioco gioco) {return gioco.getCategoria();}
    public int getPegiGioco(Gioco gioco) {return gioco.getPegi();}

    public ArrayList<Genere> getGeneriDaGioco(Gioco gioco) throws CampoNonValidoException {
        try {
            return genereDAO.getListaGeneriDaGioco(gioco);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    //metodi per modificare un gioco
    /**
     * Aggiorna il titolo di un gioco sia nel Model che nel Database.
     *
     * @param gioco L'oggetto {@link Gioco} da aggiornare.
     * @param titolo Il nuovo titolo da impostare.
     * @throws CampoNonValidoException Se l'aggiornamento nel Database fallisce.
     */
    public void updateTitoloGioco(Gioco gioco, String titolo) throws CampoNonValidoException {
        try {
            giocoDAO.updateTitolo(gioco.getId(), titolo);
            gioco.setTitolo(titolo);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Aggiorna la categoria di un gioco sia nel Model che nel Database.
     *
     * @param gioco L'oggetto {@link Gioco} da aggiornare.
     * @param categoria La nuova {@link Categoria} da impostare.
     * @throws CampoNonValidoException Se l'aggiornamento nel Database fallisce.
     */
    public void updateCategoriaGioco(Gioco gioco, Categoria categoria) throws CampoNonValidoException {
        try {
            giocoDAO.updateCategoriaGioco(gioco.getId(), categoria.name());
            gioco.setCategoria(categoria);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Aggiorna il PEGI di un gioco sia nel Model che nel Database.
     *
     * @param gioco L'oggetto {@link Gioco} da aggiornare.
     * @param pegi Il nuovo valore intero del PEGI.
     * @throws CampoNonValidoException Se l'aggiornamento nel Database fallisce.
     */
    public void updatePegiGioco(Gioco gioco, int pegi) throws CampoNonValidoException {
        try {
            giocoDAO.updatePegiGioco(gioco.getId(), pegi);
            gioco.setPegi(pegi);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Aggiorna i generi associati a un gioco nel Database.
     *
     * @param gioco L'oggetto {@link Gioco} da aggiornare.
     * @param generi Un'ArrayList contenente i nuovi {@link Genere}.
     * @throws CampoNonValidoException Se l'aggiornamento nel Database fallisce.
     */
    public void updateGeneriGioco(Gioco gioco, ArrayList<Genere> generi) throws CampoNonValidoException {
        try {
            giocoDAO.updateGeneriGioco(gioco.getId(), generi);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    //metodi per prendere dati dei generi
    public String getNomeGenere(Genere genere) {return genere.getNome();}

    /**
     * Converte un ArrayList testuale con i nomi dei generi in un ArrayList di oggetti {@link Genere}.
     *
     * @param listaNomi Un'ArrayList di stringhe rappresentanti i nomi dei generi.
     * @return Un'ArrayList di oggetti {@link Genere} estratti dal Database.
     * @throws CampoNonValidoException Se l'operazione nel Database fallisce.
     */
    public ArrayList<Genere> getGeneriDaListaNomi(ArrayList<String> listaNomi) throws CampoNonValidoException {
        try {
            if(listaNomi == null || listaNomi.isEmpty())
                return new ArrayList<>();

            return genereDAO.getGeneriDaListaNomi(listaNomi);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Crea un nuovo genere e lo inserisce nel Database.
     *
     * @param nome Il nome del nuovo genere da creare.
     * @throws CampoNonValidoException Se l'inserimento fallisce o se il nome non è valido.
     */
    public void createGenere(String nome) throws CampoNonValidoException {
        Genere genere = new Genere(nome);
        try {
            genereDAO.creaGenere(genere);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    //metodi per prendere dati da Piattaforma
    public String getNomePiattaforma(PiattaformaDiGioco piattaformaDiGioco) { return piattaformaDiGioco.getNome(); }
    public String getProduttorePiattaforma(PiattaformaDiGioco piattaformaDiGioco) {return piattaformaDiGioco.getProduttore();}
    public boolean isPortabile(PiattaformaDiGioco piattaformaDiGioco) {return piattaformaDiGioco.isPortatile();}

    /**
     * Crea una nuova piattaforma validando l'assenza di doppioni prima di salvarla nel DB.
     *
     * @param nome Il nome della nuova piattaforma.
     * @param produttore Il produttore della piattaforma.
     * @param portabile true se portatile, false altrimenti.
     * @throws CampoNonValidoException Se la piattaforma esiste già o se l'inserimento fallisce.
     */
    public void createPiattaforma(String nome, String produttore, boolean portabile) throws CampoNonValidoException {
        controlloNomePiattaforma(nome);
        PiattaformaDiGioco piattaformaDiGioco = new PiattaformaDiGioco(nome.trim(), produttore, portabile);

        try {
            piattaformaDiGiocoDAO.creaPiattaforma(piattaformaDiGioco);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Controlla se esiste già una piattaforma registrata con il nome fornito.
     *
     * @param nome Il nome da verificare.
     * @throws CampoNonValidoException Se la piattaforma risulta già presente nel sistema.
     */
    private void controlloNomePiattaforma(String nome) throws CampoNonValidoException {
        ArrayList<PiattaformaDiGioco> piattaforme = getPiattaformeDiGioco();
        for (PiattaformaDiGioco p : piattaforme) {
            if (p.getNome().equalsIgnoreCase(nome.trim())) {
                throw new CampoNonValidoException("Esiste già una piattaforma con questo nome!");
            }
        }
    }

    // metodi per prendere dati da una fattura
    public String getTitoloDaFattura(Fattura f) {return f.getGioco().getGioco().getTitolo();}
    public String getPiattaformaDaFattura(Fattura f) {return f.getGioco().getPiattaforma().getNome();}
    public LocalDate getDataRilascioDaFattura(Fattura f) {return f.getGioco().getDataRilascio();}
    public Categoria getCategoriaDaFattura(Fattura f) {return f.getGioco().getGioco().getCategoria();}
    public int getPegiDaFattura(Fattura f) {return f.getGioco().getGioco().getPegi();}
    public Utente getUtenteDaFattura(Fattura fattura) { return fattura.getUtente(); }
    public String getNomeUtenteDaFattura(Fattura fattura) { return getUtenteDaFattura(fattura).getNome(); }
    public ArrayList<Genere> getGeneriDaFattura(Fattura f) {return f.getGioco().getGioco().getGeneri();}
    public Sviluppatore getSviluppatoreDaFattura(Fattura f) {return f.getGioco().getGioco().getSviluppatore();}
    public int getVotoDaFattura(Fattura f) {return f.getRecensione().getVoto();}
    public int getDifferenzaLikeDaFattura(Fattura f) {return f.getRecensione().getDifferenzaLike();}
    public String getDescrizioneRecensioneDaFattura(Fattura f) {return f.getRecensione().getDescrizione();}
    public LocalDate getDataAcquistoDaFattura(Fattura f){return (f.getDataAcquisto());}
    public String getKeyDaFattura(Fattura f){return f.getKey();}
    public int getPrezzoAcquistoDaFattura(Fattura f){return f.getPrezzoAcquisto();}

    /**
     * Recupera una lista di recensioni filtrate tramite una stringa di ricerca, utile per gli Admin.
     *
     * @param testoRicerca La stringa da cercare nelle recensioni.
     * @return Un'ArrayList di {@link Recensione} filtrate.
     * @throws CampoNonValidoException Se il recupero dal Database fallisce.
     */
    public ArrayList<Recensione> getRecensioniFiltrateAdmin(String testoRicerca) throws CampoNonValidoException {
        try {
            return recensioneDAO.getRecensioniFiltrateAdmin(testoRicerca);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Crea e registra una nuova recensione associandola alla fattura d'acquisto.
     *
     * @param voto Il voto assegnato (da 1 a 100).
     * @param testo La descrizione testuale della recensione.
     * @param fatturaSelezionata L'oggetto {@link Fattura} per cui si sta recensendo.
     * @throws CampoNonValidoException Se la validazione fallisce.
     * @throws SQLException Se l'inserimento nel Database fallisce.
     */
    public void rilasciaRecensione(int voto, String testo, Fattura fatturaSelezionata) throws CampoNonValidoException, SQLException {
        recensioneDAO.creaRecensione(fatturaSelezionata.getId(), voto, testo);
    }

    /**
     * Controlla se per un determinato acquisto è già stata rilasciata una recensione.
     *
     * @param fattura L'oggetto {@link Fattura} di acquisto.
     * @return true se la recensione esiste già, false altrimenti.
     */
    public boolean haGiaRecensito(Fattura fattura) {
        if (fattura.getRecensione() != null){
            return true;
        }
        return false;
    }

    /**
     * Recupera una lista di piattaforme filtrata in base a una stringa di ricerca.
     *
     * @param testoRicerca La stringa da cercare tra le piattaforme.
     * @return Un'ArrayList di {@link PiattaformaDiGioco} filtrate.
     * @throws CampoNonValidoException Se il recupero dal Database fallisce.
     */
    public ArrayList<PiattaformaDiGioco> getPiattaformeFiltrate(String testoRicerca) throws CampoNonValidoException {
        try {
            return piattaformaDiGiocoDAO.getPiattaformeFiltrate(testoRicerca);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Recupera la lista completa di tutte le piattaforme di gioco registrate.
     *
     * @return Un'ArrayList di tutte le {@link PiattaformaDiGioco}.
     * @throws CampoNonValidoException Se il recupero fallisce.
     */
    public ArrayList<PiattaformaDiGioco> getPiattaformeDiGioco() throws CampoNonValidoException {
        try {
            return piattaformaDiGiocoDAO.getListaPiattaforme();
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    /**
     * Verifica se una determinata edizione di gioco possiede una promozione correntemente attiva.
     *
     * @param edizioneGioco L'oggetto {@link EdizioneGioco} da controllare.
     * @return true se è presente almeno una promozione in corso, false altrimenti.
     */
    public boolean isInPromozione(EdizioneGioco edizioneGioco) throws CampoNonValidoException {
        try {
            ArrayList<GiocoInPromozione> promozioniDelGioco = giocoInPromozioneDAO.getPromozioniPerGioco(edizioneGioco.getGioco());

            for (GiocoInPromozione p : promozioniDelGioco) {
                if (!p.getPromozione().getDataFine().isBefore(LocalDate.now())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Operazione Fallita");
        }

        return false;
    }
    //metodi per prendere dati da promozione
    public String getNomePromozione(Promozione promozione) { return promozione.getNome(); }
    public LocalDate getDataInizioPromozione(Promozione promozione) { return promozione.getDataInizio(); }
    public LocalDate getDataFinePromozione(Promozione promozione) { return promozione.getDataFine(); }

    /**
     * Recupera una lista di promozioni filtrata (usato dall'Admin) con opzione di ordinamento per data.
     *
     * @param testoRicerca Il testo da cercare nel nome della promozione.
     * @param ordinaPerData true se i risultati devono essere ordinati cronologicamente.
     * @return Un'ArrayList di {@link Promozione} filtrate.
     * @throws CampoNonValidoException Se la query al Database fallisce.
     */
    public ArrayList<Promozione> getPromozioniFiltrateAdmin(String testoRicerca, boolean ordinaPerData) throws CampoNonValidoException {
        try {
            return promozioneDAO.getPromozioniFiltrate(testoRicerca, ordinaPerData);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Controlla se esiste già nel sistema una promozione con il nome inserito.
     *
     * @param nome Il nome della promozione da controllare.
     * @throws CampoNonValidoException Se il nome è già in uso.
     */
    private void controlloNomePromozione(String nome) throws CampoNonValidoException {
        ArrayList<Promozione> promozioni = getListaPromozioni();
        for (Promozione p : promozioni) {
            if (p.getNome().equalsIgnoreCase(nome.trim())) {
                throw new CampoNonValidoException("Esiste già una promozione con questo nome!");
            }
        }
    }

    /**
     * Crea e registra una nuova promozione nel sistema effettuando il parsing delle date fornite.
     *
     * @param nome Il nome della campagna promozionale.
     * @param dataInizioStringa La data di inizio in formato testuale.
     * @param dataFineStringa La data di fine (scadenza) in formato testuale.
     * @throws CampoNonValidoException Se i formati data sono invalidi, se il nome esiste o se il DB fallisce.
     */
    public void createPromozione(String nome, String dataInizioStringa, String dataFineStringa) throws CampoNonValidoException {
        controlloNomePromozione(nome);

        LocalDate dataInizio = convertiDataRigida(dataInizioStringa);
        LocalDate dataFine = convertiDataRigida(dataFineStringa);

        Promozione promozione = new Promozione(nome.trim(), dataInizio, dataFine);

        try {
            promozioneDAO.creaPromozione(promozione.getNome(), promozione.getDataInizio(), promozione.getDataFine());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    /**
     * Compone i filtri applicati dall'utente ed estrae un
     * sottoinsieme dal catalogo totale. Supporta l'ordinamento dinamico per data di rilascio.
     *
     * @param testoRicerca Il testo da cercare nel titolo del gioco.
     * @param prezzoMax Il prezzo massimo consentito per il filtro (-1 per indicare nessun limite).
     * @param piattaformaScelta L'oggetto {@link PiattaformaDiGioco} da filtrare (null per ignorare).
     * @param genereScelto L'oggetto {@link Genere} da filtrare (null per ignorare).
     * @param categoriaScelta La {@link Categoria} da filtrare (null per ignorare).
     * @param pegiScelto Il PEGI scelto tramite interfaccia (formato stringa, o null per ignorare).
     * @param inPromozione true se si vogliono mostrare esclusivamente giochi con promozione attiva.
     * @param traSeguiti true se si vogliono mostrare solo giochi creati dagli sviluppatori seguiti.
     * @param utenteLoggato L'oggetto {@link Utente} correntemente loggato per il controllo dei seguiti.
     * @param ordinamentoData Intero che indica l'ordinamento (1 crescente, 2 decrescente).
     * @return Un'ArrayList di {@link EdizioneGioco} che soddisfano i criteri imposti.
     * @throws CampoNonValidoException Se il recupero del catalogo completo genera errori nel Database.
     */
    public ArrayList<EdizioneGioco> getCatalogoFiltrato(String testoRicerca, int prezzoMax, PiattaformaDiGioco piattaformaScelta, Genere genereScelto, Categoria categoriaScelta, String pegiScelto, boolean inPromozione, boolean traSeguiti, Utente utenteLoggato, int ordinamentoData) throws CampoNonValidoException {

        ArrayList<EdizioneGioco> catalogoCompleto;
        try {
            catalogoCompleto = edizioneGiocoDAO.getCatalogoCompleto();
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }

        ArrayList<EdizioneGioco> listaFiltrata = new ArrayList<>();

        for (EdizioneGioco e : catalogoCompleto) {
            Gioco gioco = e.getGioco();

            if (gioco.getTitolo().toLowerCase().contains(testoRicerca.toLowerCase()) &&
                    (prezzoMax == -1 || e.getPrezzo() <= prezzoMax) &&
                    (piattaformaScelta == null || e.getPiattaforma().getNome().equalsIgnoreCase(piattaformaScelta.getNome())) &&
                    (genereScelto == null || gioco.getGeneri().contains(genereScelto)) &&
                    (categoriaScelta == null || gioco.getCategoria().equals(categoriaScelta)) &&
                    (pegiScelto == null || pegiScelto.trim().isEmpty() || String.valueOf(gioco.getPegi()).equals(pegiScelto)) &&
                    (!inPromozione || isInPromozione(e)) &&
                    (!traSeguiti || utenteLoggato.getSviluppatoriSeguiti().contains(gioco.getSviluppatore()))) {

                listaFiltrata.add(e);
            }
        }

        if (ordinamentoData == 1) {
            listaFiltrata.sort((e1, e2) -> e1.getDataRilascio().compareTo(e2.getDataRilascio()));
        } else if (ordinamentoData == 2) {
            listaFiltrata.sort((e1, e2) -> e2.getDataRilascio().compareTo(e1.getDataRilascio()));
        }

        return listaFiltrata;
    }

    /**
     * Applica i criteri di ricerca alla libreria personale dell'utente,
     * filtrandoli per generi, PEGI, e categorie, e supportando tre diverse tipologie di ordinamento.
     *
     * @param testoRicerca Il testo cercato nel titolo del gioco.
     * @param utenteLoggato L'oggetto {@link Utente} di cui si sta guardando la libreria.
     * @param genereScelto L'oggetto {@link Genere} scelto (null per nessun filtro di genere).
     * @param categoriaScelta La {@link Categoria} scelta (null per nessuna categoria).
     * @param pegiScelto Il valore PEGI scelto (in formato stringa).
     * @param statoDataRilascio Valore di ordinamento per data di rilascio (1 crescente, 2 decrescente).
     * @param statoPrezzoFiltro Valore di ordinamento per prezzo di acquisto (1 crescente, 2 decrescente).
     * @param statoDataAcquisto Valore di ordinamento per data di acquisto (1 crescente, 2 decrescente).
     * @return Un'ArrayList di {@link Fattura} che compongono la libreria filtrata e ordinata.
     * @throws CampoNonValidoException Se il recupero della libreria fallisce.
     */
    public ArrayList<Fattura> getLibreriaFiltrata(String testoRicerca, Utente utenteLoggato, Genere genereScelto, Categoria categoriaScelta, String pegiScelto, int statoDataRilascio, int statoPrezzoFiltro, int statoDataAcquisto) throws CampoNonValidoException {
        ArrayList<Fattura> libreriaUtente;

        try {
            libreriaUtente = fatturaDAO.getLibreriaUtente(utenteLoggato.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }

        ArrayList<Fattura> listaFiltrata = new ArrayList<>();

        for (Fattura f : libreriaUtente) {
            Gioco giocoBase = f.getGioco().getGioco();

            if (giocoBase.getTitolo().toLowerCase().contains(testoRicerca.toLowerCase()) &&
                    (genereScelto == null || giocoBase.getGeneri().contains(genereScelto)) &&
                    (categoriaScelta == null || giocoBase.getCategoria().equals(categoriaScelta)) &&
                    (pegiScelto == null || pegiScelto.trim().isEmpty() || String.valueOf(giocoBase.getPegi()).equals(pegiScelto))) {

                listaFiltrata.add(f);
            }
        }

        // Ordinamento Data Rilascio
        if (statoDataRilascio == 1) {
            listaFiltrata.sort((f1, f2) -> f1.getGioco().getDataRilascio().compareTo(f2.getGioco().getDataRilascio()));
        } else if (statoDataRilascio == 2) {
            listaFiltrata.sort((f1, f2) -> f2.getGioco().getDataRilascio().compareTo(f1.getGioco().getDataRilascio()));
        }

        // Ordinamento Prezzo Acquisto
        if (statoPrezzoFiltro == 1) {
            listaFiltrata.sort((f1, f2) -> Integer.compare(f1.getPrezzoAcquisto(), f2.getPrezzoAcquisto()));
        } else if (statoPrezzoFiltro == 2) {
            listaFiltrata.sort((f1, f2) -> Integer.compare(f2.getPrezzoAcquisto(), f1.getPrezzoAcquisto()));
        }

        // Ordinamento Data Acquisto
        if (statoDataAcquisto == 1) {
            listaFiltrata.sort((f1, f2) -> f1.getDataAcquisto().compareTo(f2.getDataAcquisto()));
        } else if (statoDataAcquisto == 2) {
            listaFiltrata.sort((f1, f2) -> f2.getDataAcquisto().compareTo(f1.getDataAcquisto()));
        }

        return listaFiltrata;
    }

    //CV

    /**
     * Recupera una lista di utenti filtrata in base a una chiave di ricerca testuale.
     * Permette di limitare la ricerca ai soli amici dell'utente loggato, escludendo sempre se stessi dai risultati.
     *
     * @param checkBoxAmici Se true, la ricerca avviene solo tra gli amici; se false, si estende a tutti gli iscritti.
     * @param testoRicerca La stringa da cercare nel nome utente.
     * @param utenteLoggato L'{@link Utente} che sta effettuando la ricerca.
     * @return Una lista di {@link Utente} corrispondente ai criteri.
     * @throws CampoNonValidoException Se l'interrogazione al database fallisce.
     */
    public ArrayList<Utente> getUtentiFiltrati(boolean checkBoxAmici, String testoRicerca, Utente utenteLoggato) throws CampoNonValidoException {
        ArrayList<Utente> listaFiltrata;

        if (checkBoxAmici) {
            try {
                listaFiltrata = utenteDAO.getListaAmici(utenteLoggato.getId());
            } catch (SQLException e) {
                throw new CampoNonValidoException("Operazione Fallita");
            }
        } else {
            try {
                listaFiltrata = utenteDAO.getListaUtenti();
            } catch (SQLException e) {
                throw new CampoNonValidoException("Operazione Fallita");
            }
        }

        ArrayList<Utente> listaFinale = new ArrayList<>();
        for (Utente u : listaFiltrata) {
            if (u != utenteLoggato && u.getNome().toLowerCase().contains(testoRicerca)) {
                listaFinale.add(u);
            }
        }

        return listaFinale;
    }

    /**
     * Recupera una lista di sviluppatori filtrata in base a una chiave testuale.
     * Offre la possibilità di cercare solo tra le software house attualmente seguite dall'utente.
     *
     * @param checkBoxSviluppatore Se true, filtra solo tra i seguiti; se false, cerca tra tutti gli sviluppatori.
     * @param testoRicerca La stringa da cercare nel nome dello sviluppatore.
     * @param utenteLoggato L'{@link Utente} che sta effettuando la ricerca.
     * @return La lista degli {@link Sviluppatore} trovati.
     * @throws CampoNonValidoException Se la query fallisce.
     */
    public ArrayList<Sviluppatore> getSviluppatoriFiltrati(boolean checkBoxSviluppatore, String testoRicerca, Utente utenteLoggato) throws CampoNonValidoException {
        ArrayList<Sviluppatore> listaFiltrata;

        if (checkBoxSviluppatore) {
            try {
                listaFiltrata = utenteDAO.getListaSeguiti(utenteLoggato.getId());
            } catch (SQLException e) {
                throw new CampoNonValidoException("Operazione Fallita");
            }
        } else {
            try {
                listaFiltrata = sviluppatoreDAO.getListaSviluppatori();
            } catch (SQLException e) {
                throw new CampoNonValidoException("Operazione Fallita");
            }
        }

        ArrayList<Sviluppatore> listaFinale = new ArrayList<>();
        for (Sviluppatore s : listaFiltrata) {
            if (s.getNome().toLowerCase().contains(testoRicerca)) {
                listaFinale.add(s);
            }
        }
        return listaFinale;
    }

    /**
     * Inserisce una copia del gioco all'interno del {@link Carrello} dell'utente.
     * Esegue i controlli anti-doppione: blocca l'inserimento se l'utente possiede già il gioco o se l'ha già messo nel carrello.
     *
     * @param utenteLoggato L'{@link Utente} acquirente.
     * @param edizioneGiocoSelezionata L'{@link EdizioneGioco} da acquistare.
     * @throws CampoNonValidoException Se i controlli di possesso falliscono o se il database rifiuta l'inserimento.
     */
    public void aggiungiAlCarrello(Utente utenteLoggato, EdizioneGioco edizioneGiocoSelezionata) throws CampoNonValidoException {
        if (edizioneGiocoSelezionata == null) {
            throw new CampoNonValidoException("Selezionare prima un gioco dal catalogo!");
        }

        ArrayList<Fattura> libreriaUtente = getLibreriaUtente(utenteLoggato.getId());
        for (Fattura f : libreriaUtente) {
            if (f.getGioco() != null && f.getGioco().getId() == edizioneGiocoSelezionata.getId()) {
                throw new CampoNonValidoException("Possiedi già " + edizioneGiocoSelezionata.getGioco().getTitolo() + " (" + edizioneGiocoSelezionata.getPiattaforma().getNome() + ") nella tua libreria!");
            }
        }

        if (utenteLoggato.getCarrello() == null) {
            Carrello nuovoCarrello = new Carrello(utenteLoggato);
            utenteLoggato.setCarrello(nuovoCarrello);
        }

        if (utenteLoggato.getCarrello().getListaGiochi().contains(edizioneGiocoSelezionata)) {
            throw new CampoNonValidoException("Hai già aggiunto questo gioco al carrello!");
        }

        utenteLoggato.getCarrello().addEdizione(edizioneGiocoSelezionata);

        try {
            utenteDAO.inserisciCarrello(utenteLoggato.getId(), edizioneGiocoSelezionata.getId());
        } catch (SQLException e) {
            utenteLoggato.getCarrello().removeEdizione(edizioneGiocoSelezionata);
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public ArrayList<Recensione> getRecensioniEdizioneGioco(EdizioneGioco edizioneGioco) throws CampoNonValidoException {
        try {
            return recensioneDAO.getListaRecensioniEdizione(edizioneGioco.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public String getDescrizioneRecensione(Recensione recensione){return recensione.getDescrizione();}
    public int getVotoRecensione(Recensione recensione){return recensione.getVoto();}
    public int getDifferenzaLikeRecensione(Recensione recensione){return recensione.getDifferenzaLike();}

    /**
     * Aggiunge un voto positivo a una specifica recensione.
     * Aggiorna prima la RAM e tenta il salvataggio sul DB; in caso di errore esegue un rollback manuale.
     *
     * @param recensione La {@link Recensione} da valutare.
     * @param utenteLoggato L'{@link Utente} che sta votando.
     * @throws CampoNonValidoException Se l'aggiornamento sul database fallisce.
     */
    public void mettiLikeRecensione(Recensione recensione, Utente utenteLoggato) throws CampoNonValidoException {
        recensione.addLike();

        try {
            recensioneDAO.aggiornaDifferenzaLike(recensione.getFattura().getId(), recensione.getDifferenzaLike());
        } catch (SQLException e) {
            recensione.addDislike();
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    /**
     * Aggiunge un voto negativo a una specifica recensione, con rollback automatico in caso di errore SQL.
     *
     * @param recensione La {@link Recensione} da valutare.
     * @param utenteLoggato L'{@link Utente} che sta votando.
     * @throws CampoNonValidoException Se l'aggiornamento sul database fallisce.
     */
    public void mettiDislikeRecensione(Recensione recensione, Utente utenteLoggato) throws CampoNonValidoException {
        recensione.addDislike();
        try {
            recensioneDAO.aggiornaDifferenzaLike(recensione.getFattura().getId(), recensione.getDifferenzaLike());
        } catch (SQLException e) {
            recensione.addLike();
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public Carrello getCarrelloUtente(Utente utenteLoggato) {
        return utenteLoggato.getCarrello();
    }

    public ArrayList<EdizioneGioco> getGiochiCarrello(Utente utenteLoggato) throws CampoNonValidoException {
        try {
            return edizioneGiocoDAO.getListaGiochiCarrello(utenteLoggato.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public EdizioneGioco getGiocoDaCarrello(Utente utenteLoggato, int indice) throws CampoNonValidoException {
        return utenteLoggato.getCarrello().getListaGiochi().get(indice);
    }

    public String getTitoloDaEdizioneGioco(EdizioneGioco edizioneGioco) {
        return edizioneGioco.getGioco().getTitolo();
    }

    /**
     * Calcola il prezzo totale del carrello tenendo conto delle promozioni attive.
     */
    public int getPrezzoCarrello(Utente utenteLoggato) throws CampoNonValidoException {
        int totale = 0;

        if (utenteLoggato.getCarrello() != null && utenteLoggato.getCarrello().getListaGiochi() != null) {
            for (EdizioneGioco edizione : utenteLoggato.getCarrello().getListaGiochi()) {
                totale += getPrezzoDaEdizioneGioco(edizione);
            }
        }

        return totale;
    }
    /**
     * Rimuove una copia del gioco dal carrello, eliminandola sia dalla memoria locale che dalla tabella del database.
     *
     * @param utenteLoggato L'utente proprietario del carrello.
     * @param edizioneGioco L'edizione da scartare.
     * @throws CampoNonValidoException Se l'operazione di DELETE fallisce sul DB.
     */
    public void rimuoviDalCarrello(Utente utenteLoggato, EdizioneGioco edizioneGioco) throws CampoNonValidoException {
        try {
            utenteDAO.eliminaCarrello(utenteLoggato.getId(), edizioneGioco.getId());

            ArrayList<EdizioneGioco> listaGiochi = utenteLoggato.getCarrello().getListaGiochi();

            for (int i = 0; i < listaGiochi.size(); i++) {
                if (listaGiochi.get(i).getId() == edizioneGioco.getId()) {
                    listaGiochi.remove(i);
                    break;
                }
            }
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    /**
     * Finalizza il checkout per tutti i giochi presenti nel carrello.
     * Verifica la disponibilità dei fondi, scala il saldo,
     * genera e salva le singole ricevute ({@link Fattura}) e infine svuota il carrello.
     *
     * @param utenteLoggato L'{@link Utente} che effettua il pagamento.
     * @throws CampoNonValidoException Se il carrello è vuoto, il saldo è insufficiente o la transazione sul DB fallisce.
     */
    public void acquista(Utente utenteLoggato) throws CampoNonValidoException {

        if (utenteLoggato.getCarrello() == null || utenteLoggato.getCarrello().getListaGiochi().isEmpty()) {
            throw new CampoNonValidoException("Il carrello è vuoto!");
        }

        int veroTotale = getPrezzoCarrello(utenteLoggato);

        if (utenteLoggato.getSaldo() < veroTotale) {
            throw new CampoNonValidoException("Saldo insufficiente! Il totale è di " + veroTotale + "€, ma hai solo " + utenteLoggato.getSaldo() + "€.");
        }

        ArrayList<EdizioneGioco> giochiInCarrello = utenteLoggato.getCarrello().getListaGiochi();
        try {
            for (EdizioneGioco gioco: giochiInCarrello) {

                int prezzoFattura = getPrezzoDaEdizioneGioco(gioco);

                Fattura nuovaFattura = new Fattura(utenteLoggato, gioco, prezzoFattura);

                fatturaDAO.inserisciFattura(nuovaFattura);

                utenteLoggato.rimuoviSaldo(nuovaFattura.getPrezzoAcquisto());
            }

            utenteDAO.svuotaCarrello(utenteLoggato.getId());
            utenteLoggato.getCarrello().svuotaCarrello();

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita: impossibile completare l'acquisto.");
        }
    }

    /**
     * Avvia la procedura di rimborso delegando al DAO l'eliminazione della fattura e il riaccredito dei fondi.
     *
     * @param fattura La ricevuta d'acquisto da annullare.
     * @param utente L'utente che riceverà il rimborso.
     * @throws CampoNonValidoException Se l'operazione sul database fallisce.
     */
    public void effettuaRimborso(Fattura fattura, Utente utente) throws CampoNonValidoException {
        try {

            fatturaDAO.effettuaRimborso(fattura.getId(), utente.getId(), fattura.getPrezzoAcquisto());

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public String getGiocoPiuVendutoSviluppatore(Sviluppatore sviluppatore) throws CampoNonValidoException {
        try {
            String titolo = sviluppatoreDAO.getGiocoPiuVendutoSviluppatore(sviluppatore.getId());
            if (titolo == null){
                return titolo = "Nessuno";
            }
            return titolo;
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    // metodi per la homeSviluppatore CV

    /**
     * Formatta l'elenco dei generi di un videogioco unendoli in un'unica stringa separata da virgole (es. "Azione, Avventura").
     * Ideale per la visualizzazione compatta all'interno delle tabelle GUI.
     *
     * @param gioco Il {@link Gioco} di riferimento.
     * @return La stringa concatenata dei generi.
     */
    public String getGenereDaGioco(Gioco gioco){
        String generiUniti = "";

        try {
            ArrayList<Genere> generiDB = getListaGeneriDaGioco(gioco);

            for (Genere g : generiDB) {
                if (!generiUniti.isEmpty()) {
                    generiUniti += ", ";
                }
                generiUniti += g.toString();
            }
        } catch (CampoNonValidoException e) {
            System.out.println("Errore nel recupero dei generi: " + e.getMessage());
        }

        return generiUniti;
    }

    /**
     * Restituisce una stringa riassuntiva contenente i nomi di tutte le console su cui il gioco è disponibile (es. "PC, Xbox").
     *
     * @param gioco Il videogioco in esame.
     * @return I nomi delle piattaforme concatenati da virgole.
     */
    public String getStringPiattaformeDaGioco(Gioco gioco) {
        String piattaformeUnite = "";

        try {
            ArrayList<EdizioneGioco> edizioniDB = getEdizioniDaGioco(gioco);

            for (EdizioneGioco ed : edizioniDB) {
                if (!piattaformeUnite.isEmpty()) {
                    piattaformeUnite += ", ";
                }
                piattaformeUnite += ed.getPiattaforma().getNome();
            }
        } catch (CampoNonValidoException e) {
            System.out.println("Errore nel recupero delle piattaforme: " + e.getMessage());
        }

        return piattaformeUnite;
    }

    /**
     * Recupera il prezzo di lancio associato alla prima edizione fisica o digitale del videogioco.
     *
     * @param gioco Il gioco di riferimento.
     * @return Il prezzo come stringa, oppure una stringa vuota in caso di assenza di edizioni.
     * @throws CampoNonValidoException Se l'interrogazione fallisce.
     */
    public String getPrezzoPrimaEdizioneDaGioco(Gioco gioco) throws CampoNonValidoException{
        try {
            ArrayList<EdizioneGioco> edizioni = getEdizioniDaGioco(gioco);

            if (!edizioni.isEmpty()) {
                return String.valueOf(edizioni.getFirst().getPrezzo());
            }
        } catch (CampoNonValidoException e) {
            System.out.println("Errore nel caricamento del prezzo: " + e.getMessage());
        }
        return "";
    }

    public String getDataRilascioPrimaEdizioneFormattata(Gioco gioco)throws CampoNonValidoException{
        try {
            ArrayList<EdizioneGioco> edizioni = getEdizioniDaGioco(gioco);

            if (!edizioni.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return edizioni.getFirst().getDataRilascio().format(formatter);
            }
        } catch (CampoNonValidoException e) {
            System.out.println("Errore nel caricamento della data: " + e.getMessage());
        }
        return "";
    }

    public String getTitoloDaGioco(Gioco gioco) {
        return gioco.getTitolo();
    }

    public Categoria getCategoriaDaGioco(Gioco gioco) {
        return gioco.getCategoria();
    }

    public int getPegiDaGioco(Gioco gioco) {
        return gioco.getPegi();
    }

    public ArrayList<Genere> getListaGeneriDaGioco(Gioco gioco)throws CampoNonValidoException{
        try {
            return genereDAO.getListaGeneriDaGioco(gioco);
        } catch (Exception e) {
            System.out.println("Errore nel recupero dei generi: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<EdizioneGioco> getEdizioniDaGioco(Gioco giocoSelezionato) throws CampoNonValidoException{
        try {
            return edizioneGiocoDAO.getEdizioniDaGioco(giocoSelezionato.getId());
        } catch (SQLException e) {
            System.out.println("Errore nel recupero edizioni: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Sovrascrive i dati anagrafici (inclusa la password se fornita) dello sviluppatore.
     *
     * @param sviluppatore Lo {@link Sviluppatore} che effettua la modifica.
     * @param nuovoNome Il nuovo nome della software house.
     * @param nuovaDescrizione La nuova presentazione per la vetrina.
     * @param nuovaPassword La nuova password (se vuota, non viene modificata).
     * @throws CampoNonValidoException Se l'aggiornamento sul database fallisce.
     */
    public void aggiornaProfiloSviluppatore
    (Sviluppatore sviluppatore, String nuovoNome, String nuovaDescrizione, String nuovaPassword) throws
            CampoNonValidoException {
        sviluppatore.setNome(nuovoNome);
        sviluppatore.setDescrizione(nuovaDescrizione);


        if (nuovaPassword != null && !nuovaPassword.isEmpty()) {
            sviluppatore.setPassword(nuovaPassword);
        }

        try {
            sviluppatoreDAO.aggiornaProfilo(sviluppatore);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita!");
        }

    }

    /**
     * Applica modifiche globali a un videogioco già esistente. Oltre ad aggiornare le informazioni base (Titolo, PEGI),
     * riallinea le dipendenze per i Generi e genera automaticamente nuove {@link EdizioneGioco} se vengono fornite piattaforme non ancora pubblicate.
     *
     * @param gioco L'istanza base da aggiornare.
     * @param titolo Il nuovo titolo.
     * @param pegi Il nuovo limite di età.
     * @param categoria La nuova scala produttiva.
     * @param generi L'intera lista di generi (che sovrascriverà quella vecchia).
     * @param piattaforme La lista delle console in cui il gioco deve figurare.
     * @param prezzo Il prezzo base da assegnare alle eventuali nuove edizioni.
     * @param dataRilascio La data in cui usciranno i nuovi porting.
     * @throws CampoNonValidoException Se una qualsiasi operazione SQL del processo fallisce.
     */
    public void modificaGiocoEsistente(Gioco gioco, String titolo, int pegi, Categoria categoria,
                                       ArrayList<Genere> generi, ArrayList<PiattaformaDiGioco> piattaforme,
                                       double prezzo, LocalDate dataRilascio) throws CampoNonValidoException {

        gioco.setTitolo(titolo);
        gioco.setPegi(pegi);
        gioco.setCategoria(categoria);

        try {
            giocoDAO.aggiornaGioco(gioco);
            genereDAO.collegaGeneriAGioco(gioco.getId(), generi);

            ArrayList<PiattaformaDiGioco> piattaformeEsistenti = piattaformaDiGiocoDAO.getListaPiattaformeDaGioco(gioco);

            for (PiattaformaDiGioco p : piattaforme) {

                if (!piattaformeEsistenti.contains(p)) {

                    EdizioneGioco nuovaEdizione = new EdizioneGioco(gioco, p, (int) prezzo, dataRilascio);
                    gioco.addEdizione(nuovaEdizione);

                    edizioneGiocoDAO.inserisciEdizione(nuovaEdizione);
                }
            }

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita! " + e.getMessage());
        }

        updateGeneriGioco(gioco, generi);
    }

    /**
     * Pubblica un videogioco ex novo sulla piattaforma. Esegue in blocco la registrazione dell'entità genitore,
     * il collegamento con la tabella generi e l'istanziazione di tutte le edizioni (sulle varie console selezionate).
     *
     * @param titolo Il nome del gioco.
     * @param pegi La classificazione dell'età.
     * @param categoria La categoria (es. Tripla A).
     * @param generi Le etichette identificative del gioco.
     * @param piattaforme Le console per il quale uscirà al D1.
     * @param prezzo Il prezzo di listino.
     * @param dataRilascio La data di commercializzazione.
     * @param autore Lo {@link Sviluppatore} che finanzia la pubblicazione.
     * @return L'istanza completa del {@link Gioco} appena caricato a sistema.
     * @throws CampoNonValidoException Se l'elaborazione di uno di questi record fallisce lato database.
     */
    public Gioco creaNuovoGioco(String titolo, int pegi, Categoria categoria, ArrayList<Genere> generi,
                                ArrayList<PiattaformaDiGioco> piattaforme, double prezzo, LocalDate dataRilascio, Sviluppatore autore) throws CampoNonValidoException {

        Gioco giocoTemporaneo = new Gioco(titolo, categoria, pegi, autore, generi);
        int idGiocoGenerato;

        try {
            idGiocoGenerato = giocoDAO.inserisciGioco(giocoTemporaneo);
            genereDAO.collegaGeneriAGioco(idGiocoGenerato, generi);

            Gioco giocoDefinitivo = new Gioco(autore, idGiocoGenerato, titolo, categoria, pegi);

            for (Genere g : generi) {
                giocoDefinitivo.getGeneri().add(g);
            }

            for (PiattaformaDiGioco p : piattaforme) {
                EdizioneGioco nuovaEdizione = new EdizioneGioco(giocoDefinitivo, p, (int) prezzo, dataRilascio);

                giocoDefinitivo.addEdizione(nuovaEdizione);
                giocoDAO.inserisciEdizione(nuovaEdizione, idGiocoGenerato);
            }


            return giocoDefinitivo;

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita! " + e.getMessage());
        }
    }

    public ArrayList<Promozione> getListaPromozioni() throws CampoNonValidoException {
        try {
            return promozioneDAO.getTuttePromozioni();
        } catch (SQLException e) {
            throw new CampoNonValidoException("Errore: Impossibile caricare le promozioni dal server.");
        }
    }

    /**
     * Iscrive formalmente un gioco a un evento promozionale definendo il margine di sconto.
     *
     * @param gioco Il videogioco in esame.
     * @param promozione La sessione di sconti scelta.
     * @param percentualeSconto Il valore dello sconto, che deve rientrare tra 1 e 100.
     * @throws CampoNonValidoException Se le soglie di sconto non sono rispettate o se il DAO fallisce l'inserimento.
     */
    public void partecipaAPromozione(Gioco gioco, Promozione promozione, int percentualeSconto) throws
            CampoNonValidoException {

        if (percentualeSconto <= 0 || percentualeSconto >= 100) {
            throw new CampoNonValidoException("La percentuale di sconto deve essere compresa tra 1 e 99!");
        }

        try {
            promozioneDAO.inserisciGiocoInPromozione(gioco.getId(), promozione.getId(), percentualeSconto);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita!");
        }
    }

    /**
     * Restituisce una vista testuale comprensiva di tutte le promozioni a cui il gioco partecipa.
     *
     * @param giocoScelto Il videogioco di riferimento.
     * @return Una stringa formattata (es. "Saldi Invernali (-30%), Saldi Estivi (-50%)").
     * @throws CampoNonValidoException Se l'interrogazione al database fallisce.
     */
    public String getStringaPromozioniPerGioco(Gioco giocoScelto) throws CampoNonValidoException {
        String risultato = "";

        try {

            ArrayList<GiocoInPromozione> scontiDelGioco = giocoInPromozioneDAO.getPromozioniPerGioco(giocoScelto);

            for (GiocoInPromozione sconto : scontiDelGioco) {

                if (!risultato.isEmpty()) {
                    risultato += ", ";
                }

                risultato += sconto.getPromozione().getNome() + " (-" + sconto.getPercentuale() + "%)";
            }

        } catch (SQLException e) {
            throw new CampoNonValidoException("Impossibile recuperare le promozioni dal database.");
        }

        if (risultato.isEmpty()) {
            return "Nessuna promozione attiva";
        }

        return risultato;
    }


    public void invertiStatoBan(int idUtente) throws CampoNonValidoException {
        try {

            utenteDAO.invertiStatoBan(idUtente);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    /**
     * Applica forzatamente la sospensione (ban) sull'account di un utente, con fallback in caso di anomalia SQL.
     *
     * @param utente L'{@link Utente} da bannare.
     * @throws CampoNonValidoException Se il ban non viene accettato dal DB.
     */
    public void setBannatoUtente(Utente utente) throws CampoNonValidoException {
        boolean flag = utente.isBannato();
        utente.setBannato(true);

        try {

            utenteDAO.setBannato(utente.getId());

        } catch (SQLException e) {
            utente.setBannato(flag);
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public int getUnitaVenduteDaGioco(Gioco gioco) throws CampoNonValidoException {
        try {
            return giocoDAO.getUnitaVendutePerGioco(gioco.getTitolo());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operzione fallita!");
        }
    }

    public int getGuadagnoTotaleDaGioco(Gioco gioco) throws CampoNonValidoException {
        try {
            return giocoDAO.getGuadagnoTotalePerGioco(gioco.getTitolo());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita!");
        }
    }


    public ArrayList<Recensione> getRecensioniGioco(Gioco gioco) throws CampoNonValidoException {
        try {
            return recensioneDAO.getRecensioniPerGioco(gioco.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita!");
        }
    }

    /**
     * Formatta l'intero catalogo di recensioni associate a un videogioco in una lunga stringa
     * pronta per essere impaginata dentro un'area di testo nella View dello sviluppatore.
     *
     * @param giocoScelto Il videogioco recensito.
     * @return Il blocco di testo contenente tutti i giudizi e i voti della community.
     * @throws CampoNonValidoException Se la raccolta dati dal DB fallisce.
     */
    public String getStringaRecensioniPerGioco(Gioco giocoScelto) throws CampoNonValidoException {

        String risultato = "";

        ArrayList<Recensione> recensioniDelGioco = getRecensioniGioco(giocoScelto);

        for (Recensione recensione : recensioniDelGioco) {


            risultato += recensione + "\n";
            risultato += "Voto: " + recensione.getVoto() + "/100";
            risultato += "  (Utilità: " + recensione.getDifferenzaLike() + ")\n";
            risultato += "\"" + recensione.getDescrizione() + "\"\n";
            risultato += "--------------------------------------------------\n\n";

        }

        if (risultato.isEmpty()) {
            return "Ancora nessuna recensione per questo titolo.";
        }

        return risultato;
    }


    public ArrayList<Gioco> getListaGiochiSviluppatore(Sviluppatore sviluppatore) throws
            CampoNonValidoException {
        try {
            return giocoDAO.getGiochiSviluppatore(sviluppatore.getId());

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operzione fallita!");
        }
    }


    public PiattaformaDiGioco getPiattaformaDaNome(String nomePiattaforma)throws CampoNonValidoException {
        try {
            ArrayList<PiattaformaDiGioco> tutteLePiattaforme = getPiattaformeDiGioco();

            for (PiattaformaDiGioco p : tutteLePiattaforme) {
                if (p.getNome().equalsIgnoreCase(nomePiattaforma)) {
                    return p;
                }
            }
        } catch (CampoNonValidoException e) {
            System.out.println("Errore durante la ricerca della piattaforma: " + e.getMessage());
        }
        return null;
    }


    public String getFondiSviluppatore(Sviluppatore sviluppatore){
        return String.valueOf(sviluppatore.getFondi());
    }



    public String getSeguitiSviluppatore(Sviluppatore sviluppatore) {
        try {
            int numeroSeguaci = sviluppatoreDAO.getNumeroSeguaci(sviluppatore.getId());
            return String.valueOf(numeroSeguaci);
        } catch (Exception e) {
            System.out.println("Errore nel conteggio dei seguaci: " + e.getMessage());
            return "0";
        }
    }

    /**
     * Effettua una ricerca testuale rapida (solo in memoria RAM) tra i giochi pubblicati da un determinato sviluppatore.
     *
     * @param sviluppatore La casa produttrice di cui filtrare il catalogo.
     * @param testoCercato Il titolo del gioco da cercare.
     * @return L'elenco dei {@link Gioco} corrispondenti.
     * @throws CampoNonValidoException Se l'interrogazione propedeutica della libreria giochi fallisce.
     */
    public ArrayList<Gioco> cercaGiochiSviluppatore(Sviluppatore sviluppatore, String testoCercato) throws CampoNonValidoException {
        ArrayList<Gioco> tuttiIGiochi = getListaGiochiSviluppatore(sviluppatore);
        ArrayList<Gioco> risultati = new ArrayList<>();

        if (testoCercato == null || testoCercato.trim().isEmpty()) {
            return tuttiIGiochi;
        }

        String ricercaLower = testoCercato.toLowerCase();
        for (Gioco gioco : tuttiIGiochi) {
            if (gioco.getTitolo().toLowerCase().contains(ricercaLower)) {
                risultati.add(gioco);
            }
        }

        return risultati;
    }

}