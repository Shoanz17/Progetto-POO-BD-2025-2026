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

    public void registraSviluppatore(String nome, String password, String descrizione) throws CampoNonValidoException {
        Sviluppatore sviluppatore = new Sviluppatore(nome, password, descrizione);

        try {
            sviluppatoreDAO.registraSviluppatore(sviluppatore);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    //controllare anche se quando ci si registra l'account già esiste
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

    public ArrayList<Utente> getUtentiFiltratiAdmin(String testoRicerca, boolean statoBan) throws CampoNonValidoException{
        try{

            return utenteDAO.getUtentiFiltratiAdmin(testoRicerca, statoBan);

        } catch (SQLException e){
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public ArrayList<Sviluppatore> getListaSviluppatoriFiltrati(String testoRicerca) throws CampoNonValidoException{
        try {

            return sviluppatoreDAO.getListaSviluppatoriFiltrati(testoRicerca);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public int getNumeroRecensioniUtente(Utente utenteLoggato) throws CampoNonValidoException {
        return getListaRecensioniUtente(utenteLoggato.getId()).size();
    }

    public void aggiungiSaldo(Utente utenteLoggato, int saldo) throws CampoNonValidoException {

        utenteLoggato.aggiungiSaldo(saldo);

        try {
            utenteDAO.aggiungiSaldo(utenteLoggato.getId(), saldo);
        } catch (SQLException e) {
            utenteLoggato.rimuoviSaldo(saldo);
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void aggiungiSaldo(Utente utenteLoggato, String saldoTesto) throws CampoNonValidoException {
        try {
            if (saldoTesto == null || saldoTesto.trim().isEmpty()) {
                throw new CampoNonValidoException("Scrivere quanto si vuole aggiungere");
            }

            int saldo = Integer.parseInt(saldoTesto.trim());
            utenteLoggato.aggiungiSaldo(saldo);

            utenteDAO.aggiungiSaldo(utenteLoggato.getId(),saldo);

        } catch (NumberFormatException e) {
            throw new CampoNonValidoException("Inserire un numero");
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void setNomeUtente(String nome, Utente utenteLoggato) throws CampoNonValidoException {
        utenteLoggato.setNome(nome);
    }

    public void setPasswordUtente(String password, Utente utenteLoggato) throws CampoNonValidoException {
        utenteLoggato.setPassword(password);
    }

    public void setEmailUtente(String email, Utente utenteLoggato) throws CampoNonValidoException {
        utenteLoggato.setEmail(email);
    }

    public void setGenereUtente(GenereEnum genere, Utente utenteLoggato) {
        utenteLoggato.setGenere(genere);
    }

    public void setDataDiNascitaUtente(String dataDiNascita, Utente utenteLoggato) throws CampoNonValidoException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
            LocalDate dataNascita = LocalDate.parse(dataDiNascita.trim(), formatter);

            utenteLoggato.setDataNascita(dataNascita);
        } catch (DateTimeParseException e) {
            throw new CampoNonValidoException("La data inserita non esiste o non è nel formato dd/MM/yyyy");
        }
    }

    public void salvaModificheProfilo(Utente utenteLoggato) throws CampoNonValidoException {
        try {
            utenteDAO.aggiornaProfiloUtente(utenteLoggato);
        } catch (SQLException e) {
            annullaModifiche(utenteLoggato);
            throw new CampoNonValidoException("Operazione fallita: controlla che l'email non sia già in uso o che i dati siano corretti.");
        }
    }

    public void annullaModifiche(Utente utenteLoggato) {
        try {
            Utente utenteOriginale = utenteDAO.getUtenteById(utenteLoggato.getId());
            utenteLoggato.setNome(utenteOriginale.getNome());
            utenteLoggato.setEmail(utenteOriginale.getEmail());
            utenteLoggato.setPassword(utenteOriginale.getPassword());
            utenteLoggato.setDataNascita(utenteOriginale.getDataNascita());
            utenteLoggato.setGenere(utenteOriginale.getGenere());
        } catch (SQLException | CampoNonValidoException e) {
        }
    }

    public int getIdUtente(Utente u) {return u.getId();}

    public int getNumeroGiochiAcquistatiUtente(Utente u) throws CampoNonValidoException {
        if (u == null) return 0;
        return getLibreriaUtente(u.getId()).size();
    }

    public GenereEnum getGenereUtente(Utente u) {
        return u.getGenere();
    }
    public String getNomeUtente(Utente u) {
        return u.getNome();
    }
    public String getEmailUtente(Utente u) {
        return u.getEmail();
    }
    public LocalDate getDataDiNascitaUtente(Utente u) {
        return u.getDataNascita();
    }
    public int getSaldoUtente(Utente u) {
        return u.getSaldo();
    }

    public ArrayList<Fattura> getLibreriaUtente(int idUtente) throws CampoNonValidoException {
        try {
            return fatturaDAO.getLibreriaUtente(idUtente);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public LocalDate getDataCreazioneAccountUtente(Utente u) {return u.getDataCreazione();}

    public boolean isUtenteBannato(Utente u) {return u.isBannato();}

    public Utente getUtenteById(int idUtente) throws CampoNonValidoException {
        try {
            Utente utenteTrovato = utenteDAO.getUtenteById(idUtente);

            if (utenteTrovato == null) {
                throw new CampoNonValidoException("Nessun utente trovato con questo ID");
            }

            return utenteTrovato;

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void aggiungiSviluppatoreSeguito(Utente utenteloggato, Sviluppatore sviluppatoreSelezionato) throws CampoNonValidoException {
        utenteloggato.addSviluppatoreSeguito(sviluppatoreSelezionato);

        try {
            utenteDAO.inserisciSviluppatoreSeguito(utenteloggato.getId(), sviluppatoreSelezionato.getId());
        } catch (SQLException e) {
            utenteloggato.removeSviluppatoreSeguito(sviluppatoreSelezionato);
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public void rimuoviSviluppatoreSeguito(Utente utenteloggato, Sviluppatore sviluppatoreSelezionato) throws CampoNonValidoException {
        utenteloggato.removeSviluppatoreSeguito(sviluppatoreSelezionato);

        try {
            utenteDAO.eliminaSviluppatoreSeguito(utenteloggato.getId(), sviluppatoreSelezionato.getId());
        } catch (SQLException e) {
            utenteloggato.addSviluppatoreSeguito(sviluppatoreSelezionato);
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public String getDescrizioneSviluppatore(Sviluppatore s) {return s.getDescrizione();}

    public ArrayList<EdizioneGioco> getListaEdizioniSviluppatore(Sviluppatore sviluppatore) throws CampoNonValidoException {
        try {

            return edizioneGiocoDAO.getListaEdizioniSviluppatore(sviluppatore.getId());

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public int getNumeroGiochiRilasciatiSviluppatore(Sviluppatore s) throws CampoNonValidoException {
        try {
            return sviluppatoreDAO.getNumeroGiochiRilasciati(s.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public boolean isSviluppatoreBannato(Sviluppatore sviluppatore) {return sviluppatore.isBannato();}

    public void addStrikeSviluppatore(Sviluppatore sviluppatore) throws CampoNonValidoException {
        sviluppatore.addStrike();

        try {
            sviluppatoreDAO.aggiungiStrike(sviluppatore.getId());
        } catch (SQLException e) {
            sviluppatore.removeStrike();
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void removeStrikeSviluppatore(Sviluppatore sviluppatore) throws CampoNonValidoException {
        sviluppatore.removeStrike();

        try {
            sviluppatoreDAO.rimuoviStrike(sviluppatore.getId());
        } catch (SQLException e) {
            sviluppatore.addStrike();
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public int getStrikeSviluppatore(Sviluppatore sviluppatore) {return sviluppatore.getStrike();}
    public void addStrikeSviluppatoreDaGioco(Gioco gioco) throws CampoNonValidoException {addStrikeSviluppatore(gioco.getSviluppatore());}
    public String getNomeSviluppatore(Sviluppatore s) {return s.getNome();}
    public String getNomeSviluppatoreDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getGioco().getSviluppatore().getNome();}

    public int getPrezzoDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getPrezzo();}
    public PiattaformaDiGioco getPiattaformaDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getPiattaforma();}

    public ArrayList<Genere> getGeneriDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getGioco().getGeneri();}

    public int getPegiDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getGioco().getPegi();}
    public int getMediaVotiEdizioneGioco(EdizioneGioco edizioneGioco) throws SQLException {return recensioneDAO.getMediaVotiEdizioneGioco(edizioneGioco.getId());}
    public Categoria getCategoriaDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getGioco().getCategoria();}
    public LocalDate getDataDiRilascioDaEdizioneGioco(EdizioneGioco edizioneGioco) {return edizioneGioco.getDataRilascio();}


    public void aggiungiAmico(Utente utenteLoggato, Utente utenteSelezionato) throws CampoNonValidoException {
        utenteLoggato.addAmico(utenteSelezionato);

        try {
            utenteDAO.inserisciAmico(utenteLoggato.getId(), utenteSelezionato.getId());
        } catch (SQLException e) {
            utenteLoggato.removeAmico(utenteSelezionato);
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public void rimuoviAmico(Utente utenteLoggato, Utente utenteSelezionato) throws CampoNonValidoException {
        utenteLoggato.removeAmico(utenteSelezionato);

        try {
            utenteDAO.eliminaAmico(utenteLoggato.getId(), utenteSelezionato.getId());
        } catch (SQLException e) {
            utenteLoggato.addAmico(utenteSelezionato);
            throw new CampoNonValidoException("Operazione Fallita");
        }

    }

    public ArrayList<Recensione> getListaRecensioniUtente(int idUtente) throws CampoNonValidoException {
        try {

            return recensioneDAO.getListaRecensioniUtente(idUtente);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public Fattura getFatturaDaRecensione(Recensione r) {
        return r.getFattura();
    }

    public void rimuoviRecensioneSelezionataDaFattura(Fattura fattura) throws CampoNonValidoException {
        if (fattura == null) {
            throw new CampoNonValidoException("Seleziona prima una recensione dalla tabella!");
        }

        try {
            recensioneDAO.eliminaRecensione(fattura.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }

        try {
            if (fattura.getRecensione() != null) {
                fattura.setRecensione(null);
            }
        } catch (Exception e) {
        }
    }

    public ArrayList<Genere> getGeneri() throws CampoNonValidoException {
        try {

            return genereDAO.getListaGeneri();

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void addEdizioneALDB(EdizioneGioco ed) throws CampoNonValidoException {
        try {
            edizioneGiocoDAO.inserisciEdizione(ed);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Impossibile salvare l'edizione nel database: " + e.getMessage());
        }
    }

    public ArrayList<Genere> getGeneriFiltrati(String testoRicerca) throws CampoNonValidoException {
        try {

            return genereDAO.getGeneriFiltrati(testoRicerca);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public ArrayList<Categoria> getCategorie() {
        ArrayList<Categoria> categorie = new ArrayList<>();

        for (Categoria c : Categoria.values()) {
            categorie.add(c);
        }
        return categorie;
    }

    //metodi per prendere dati da un gioco
    public ArrayList<Gioco> getGiochiFiltrati(String testoRicerca) throws CampoNonValidoException {
        try {

            return giocoDAO.getGiochiFiltrati(testoRicerca);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public ArrayList<Gioco> getListaGiochi() throws CampoNonValidoException {
        try {
            return giocoDAO.getListaGiochi();
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
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
    public void updateTitoloGioco(Gioco gioco, String titolo) throws CampoNonValidoException {
        try {

            giocoDAO.updateTitolo(gioco.getId(), titolo);
            gioco.setTitolo(titolo);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void updateCategoriaGioco(Gioco gioco, Categoria categoria) throws CampoNonValidoException {
        try {

            giocoDAO.updateCategoriaGioco(gioco.getId(), categoria.name());
            gioco.setCategoria(categoria);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void updatePegiGioco(Gioco gioco, int pegi) throws CampoNonValidoException {
        try {

            giocoDAO.updatePegiGioco(gioco.getId(), pegi);
            gioco.setPegi(pegi);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void updateGeneriGioco(Gioco gioco, ArrayList<Genere> generi) throws CampoNonValidoException {
        try {

            giocoDAO.updateGeneriGioco(gioco.getId(), generi);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    //metodi per prendere dati dei generi
    public String getNomeGenere(Genere genere) {return genere.getNome();}
    public ArrayList<Genere> getGeneriDaListaNomi(ArrayList<String> listaNomi) throws CampoNonValidoException { //DA FARE con implementazione
        try {

            if(listaNomi == null || listaNomi.isEmpty())
                return new ArrayList<>();

            return genereDAO.getGeneriDaListaNomi(listaNomi);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

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
    public void createPiattaforma(String nome, String produttore, boolean portabile) throws CampoNonValidoException {
        PiattaformaDiGioco piattaformaDiGioco = new PiattaformaDiGioco(nome, produttore, portabile);

        try {

            piattaformaDiGiocoDAO.creaPiattaforma(piattaformaDiGioco);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
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
    public ArrayList<Genere> getGeneriDaFattura(Fattura f) {
        return f.getGioco().getGioco().getGeneri();
    }
    public Sviluppatore getSviluppatoreDaFattura(Fattura f) {return f.getGioco().getGioco().getSviluppatore();}
    public Gioco getGiocoDaFattura(Fattura f) {return f.getGioco().getGioco();}
    public int getVotoDaFattura(Fattura f) {return f.getRecensione().getVoto();}
    public int getDifferenzaLikeDaFattura(Fattura f) {return f.getRecensione().getDifferenzaLike();}
    public Recensione getRecensioneDaFattura(Fattura f) {return f.getRecensione();}
    public String getDescrizioneRecensioneDaFattura(Fattura f) {return f.getRecensione().getDescrizione();}
    public LocalDate getDataAcquistoDaFattura(Fattura f){return (f.getDataAcquisto());}
    public String getKeyDaFattura(Fattura f){return f.getKey();}
    public int getPrezzoAcquistoDaFattura(Fattura f){return f.getPrezzoAcquisto();}
    public ArrayList<Recensione> getRecensioniFiltrateAdmin(String testoRicerca) throws CampoNonValidoException {
        try {

            return recensioneDAO.getRecensioniFiltrateAdmin(testoRicerca);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void rilasciaRecensione(int voto, String testo, Fattura fatturaSelezionata) throws CampoNonValidoException, SQLException {
        if (fatturaSelezionata == null) {
            throw new CampoNonValidoException("Seleziona prima una fattura dalla libreria!");
        }
            recensioneDAO.creaRecensione(fatturaSelezionata.getId(), voto, testo);
    }

    public boolean haGiaRecensito(Utente utente, Fattura fattura) throws CampoNonValidoException {
        if (utente == null || fattura == null) return false;

        ArrayList<Recensione> recensioni = getListaRecensioniUtente(utente.getId());
        for (Recensione r : recensioni) {
            if (r.getFattura().getId() == fattura.getId()) {
                return true;
            }
        }
        return false;
    }

    public void aggiornaRecensione(int voto, String testo, Fattura fatturaSelezionata) throws CampoNonValidoException {
        try {
            recensioneDAO.aggiornaRecensione(fatturaSelezionata.getId(), voto, testo);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public ArrayList<PiattaformaDiGioco> getPiattaformeFiltrate(String testoRicerca) throws CampoNonValidoException {
        try {

            return piattaformaDiGiocoDAO.getPiattaformeFiltrate(testoRicerca);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public ArrayList<PiattaformaDiGioco> getPiattaformeDiGioco() throws CampoNonValidoException {
        try {
            return piattaformaDiGiocoDAO.getListaPiattaforme();
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }


    public boolean isInPromozione(EdizioneGioco edizioneGioco) {
        for (GiocoInPromozione p : edizioneGioco.getGioco().getPromozioni()) {
            if (p.getPromozione().getDataFine().isAfter(LocalDate.now())) {
                return true;
            }
        }
        return false;
    }

    //metodi per prendere dati da promozione
    public String getNomePromozione(Promozione promozione) { return promozione.getNome(); }
    public LocalDate getDataInizioPromozione(Promozione promozione) { return promozione.getDataInizio(); }
    public LocalDate getDataFinePromozione(Promozione promozione) { return promozione.getDataFine(); }
    public ArrayList<Promozione> getPromozioniFiltrateAdmin(String testoRicerca, boolean ordinaPerData) throws CampoNonValidoException {
        try {

            return promozioneDAO.getPromozioniFiltrate(testoRicerca, ordinaPerData);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void createPromozione(String nome, String dataInizioStringa, String dataFineStringa) throws CampoNonValidoException {
        LocalDate dataInizio = convertiDataRigida(dataInizioStringa);
        LocalDate dataFine = convertiDataRigida(dataFineStringa);

        Promozione promozione = new Promozione(nome, dataInizio, dataFine);

        try {

            promozioneDAO.creaPromozione(promozione.getNome(), promozione.getDataInizio(), promozione.getDataFine());

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

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

    public void mettiLikeRecensione(Recensione recensione, Utente utenteLoggato) throws CampoNonValidoException {
        recensione.addLike();

        try {
            recensioneDAO.aggiornaDifferenzaLike(recensione.getFattura().getId(), recensione.getDifferenzaLike());
        } catch (SQLException e) {
            recensione.addDislike();
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

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

    public String getTitoloDaEdizioneGioco(EdizioneGioco edizioneGioco) {
        return edizioneGioco.getGioco().getTitolo();
    }

    public int getPrezzoCarrello(Utente utenteLoggato) {
        try {
            ArrayList<EdizioneGioco> giochi = getGiochiCarrello(utenteLoggato);
            int totale = 0;
            for (EdizioneGioco e : giochi) {
                totale += e.getPrezzo();
            }
            return totale;
        } catch (CampoNonValidoException e) {
            return 0;
        }
    }

    public void rimuoviDalCarrello(Utente utenteLoggato, EdizioneGioco edizioneGioco) throws CampoNonValidoException {
        if (edizioneGioco == null) {
            throw new CampoNonValidoException("Seleziona prima un gioco da rimuovere!");
        }

        try {
            utenteDAO.eliminaCarrello(utenteLoggato.getId(), edizioneGioco.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }

        if (utenteLoggato.getCarrello() != null && utenteLoggato.getCarrello().getListaGiochi() != null) {
            utenteLoggato.getCarrello().getListaGiochi().removeIf(ed -> ed.getId() == edizioneGioco.getId());
        }
    }
    public void acquista(Utente utenteLoggato) throws CampoNonValidoException {
        if (utenteLoggato == null) {
            throw new CampoNonValidoException("Utente non valido!");
        }

        ArrayList<Fattura> libreriaDalDB = getLibreriaUtente(utenteLoggato.getId());

        ArrayList<EdizioneGioco> giochiInCarrello = getGiochiCarrello(utenteLoggato);

        if (giochiInCarrello == null || giochiInCarrello.isEmpty()) {
            throw new CampoNonValidoException("Il carrello è vuoto!");
        }

        for (EdizioneGioco giocoInCarrello : giochiInCarrello) {
            for (Fattura f : libreriaDalDB) {
                if (f.getGioco() != null && f.getGioco().getId() == giocoInCarrello.getId()) {
                    throw new CampoNonValidoException("Impossibile procedere: possiedi già \""
                            + giocoInCarrello.getGioco().getTitolo() + "\" ("
                            + giocoInCarrello.getPiattaforma().getNome() + ") in libreria!");
                }
            }
        }

        int totale = 0;
        for (EdizioneGioco e : giochiInCarrello) {
            totale += e.getPrezzo();
        }

        if (utenteLoggato.getSaldo() < totale) {
            throw new CampoNonValidoException("Saldo insufficiente! Il totale è di " + totale + "€, ma hai solo " + utenteLoggato.getSaldo() + "€.");
        }

        for (EdizioneGioco giocoAcquistato : giochiInCarrello) {
            Fattura nuovaFattura = new Fattura(utenteLoggato, giocoAcquistato, giocoAcquistato.getPrezzo());

            try {
                fatturaDAO.inserisciFattura(nuovaFattura);

                utenteDAO.eliminaCarrello(utenteLoggato.getId(), giocoAcquistato.getId());

                //utenteLoggato.addGioco(nuovaFattura);
                utenteLoggato.rimuoviSaldo(nuovaFattura.getPrezzoAcquisto());

            } catch (SQLException | CampoNonValidoException e) {
                throw new CampoNonValidoException("Operazione Fallita: " + e.getMessage());
            }
        }

        try {
            utenteDAO.svuotaCarrello(utenteLoggato.getId());
            if (utenteLoggato.getCarrello() != null) {
                utenteLoggato.getCarrello().svuotaCarrello();
            }
        } catch (SQLException e) {
        }
    }

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
            return (titolo != null) ? titolo : "Nessuno";
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }
// metodi per la homeSviluppatore CV

    public String getGenereDaGioco(Gioco gioco) {
        String generiUniti = "";
        for (Genere g : gioco.getGeneri()) {

            if (!generiUniti.isEmpty()) {
                generiUniti += ",";
            }
            generiUniti += g.toString();
        }
        return generiUniti;
    }

    public String getStringPiattaformeDaGioco(Gioco gioco) {
        String piattaformeUnite = "";
        for (EdizioneGioco ed : gioco.getEdizioni()) {
            if (!piattaformeUnite.isEmpty()) {
                piattaformeUnite += ",";
            }

            piattaformeUnite += ed.getPiattaforma().getNome();

        }
        return piattaformeUnite;
    }


    public String getPrezzoPrimaEdizioneDaGioco(Gioco gioco) {
        if (!gioco.getEdizioni().isEmpty()) return String.valueOf(gioco.getEdizioni().get(0).getPrezzo());
        return "";
    }

    public String getDataRilascioPrimaEdizioneFormattata(Gioco gioco) {
        if (!gioco.getEdizioni().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return gioco.getEdizioni().get(0).getDataRilascio().format(formatter);
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

    public ArrayList<Genere> getListaGeneriDaGioco(Gioco gioco) {
        return gioco.getGeneri();
    }

    public ArrayList<EdizioneGioco> getEdizioniDaGioco(Gioco gioco) {
        return gioco.getEdizioni();
    }

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

    public Gioco creaNuovoGioco
            (String titolo, int pegi, Categoria categoria, ArrayList<Genere> generi,
             ArrayList<PiattaformaDiGioco> piattaforme, double prezzo, LocalDate dataRilascio, Sviluppatore autore) throws
            CampoNonValidoException {

        Gioco nuovoGioco = new Gioco(titolo, categoria, pegi, autore, generi);


        try {
            int idGiocoGenerato = giocoDAO.inserisciGioco(nuovoGioco);

            genereDAO.collegaGeneriAGioco(idGiocoGenerato, generi);

            for (PiattaformaDiGioco p : piattaforme) {
                EdizioneGioco nuovaEdizione = new EdizioneGioco(nuovoGioco, p, (int) prezzo, dataRilascio);
                nuovoGioco.addEdizione(nuovaEdizione);


                giocoDAO.inserisciEdizione(nuovaEdizione, idGiocoGenerato);
            }

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita!");
        }

        return nuovoGioco;
    }


//    public void caricaPromozioniFittizie() {
//        try {
//            listaPromozioni.add(new Promozione("Natale 2026", LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 31)));
//            listaPromozioni.add(new Promozione("Sconti Estivi", LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 31)));
//            listaPromozioni.add(new Promozione("Gennaio 1st Week", LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 7)));
//        } catch (CampoNonValidoException e) {
//            System.out.println("Errore nei dati fittizi");
//        }
//    }


    public ArrayList<Promozione> getListaPromozioni() throws CampoNonValidoException {
        try {
            return promozioneDAO.getTuttePromozioni();
        } catch (SQLException e) {
            throw new CampoNonValidoException("Errore: Impossibile caricare le promozioni dal server.");
        }
    }

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


    public ArrayList<Sviluppatore> getListaSviluppatoriLog() throws CampoNonValidoException {
        try {
            return sviluppatoreDAO.getListaSviluppatori();

        } catch (SQLException e) {
            throw new CampoNonValidoException("Errore di connessione: impossibile caricare la lista degli sviluppatori dal server.");
        }
    }

    public PiattaformaDiGioco getPiattaformaDaNome(String nomePiattaforma) {
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
}