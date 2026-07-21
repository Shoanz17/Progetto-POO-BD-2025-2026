package controller;

import dao.*;
import model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class Controller {
    private AccountDAO accountDAO;
    private UtenteDAO utenteDAO;
    private SviluppatoreDAO sviluppatoreDAO;
    private AdminDAO adminDAO;
    private FatturaDAO fatturaDAO;
    private RecensioneDAO recensioneDAO;
    private GiocoDAO giocoDAO;
    private EdizioneGiocoDAO edizioneGiocoDAO;
    private GenereDAO genereDAO;
    private PiattaformaDiGiocoDAO piattaformaDiGiocoDAO;

    public Controller() {
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
        //rimuovo gli spazi e controllo se la lunghezza è giusta e se non sono rimasti trattini
        if (dataNascita.contains("_") || dataNascita.trim().length() < 10)
            throw new CampoNonValidoException("Inserisci una data di nascita completa!");

        LocalDate dataNascitaVera;
        try {
            // Configurazione rigida per evitare date inventate (es. 31 Febbraio)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

            //converto la stringa
            dataNascitaVera = LocalDate.parse(dataNascita, formatter);

        } catch (DateTimeParseException e) {
            throw new CampoNonValidoException("La data inserita non esiste nel calendario o non è nel formato dd/MM/yyyy");
        }

        //finalmente creo l'oggetto
        Utente utente = new Utente(nome, password, genereVero, email, dataNascitaVera);

        //DA FARE completare con implementazione
        try {
            utenteDAO.registraUtente(utente);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
        }
    }

    public void registraSviluppatore(String nome, String password, String descrizione) throws CampoNonValidoException {
        Sviluppatore sviluppatore = new Sviluppatore(nome, password, descrizione);

        //DA FARE completare con implementazione
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

    public void annullaModifiche(Utente utenteLoggato) throws CampoNonValidoException {
        try {
            Utente utenteOriginale = utenteDAO.getUtenteById(utenteLoggato.getId());
            utenteLoggato.setNome(utenteOriginale.getNome());
            utenteLoggato.setEmail(utenteOriginale.getEmail());
            utenteLoggato.setPassword(utenteOriginale.getPassword());
            utenteLoggato.setDataNascita(utenteOriginale.getDataNascita());
            utenteLoggato.setGenere(utenteOriginale.getGenere());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public int getIdUtente(Utente u){return u.getId();}
    public int getNumeroGiochiAcquistatiUtente(Utente u){return u.getGiochiAcquistati().size();}
    public GenereEnum getGenereUtente(Utente u){return u.getGenere();}
    public String getNomeUtente(Utente u){return u.getNome();}
    public String getEmailUtente(Utente u){return u.getEmail();}
    public LocalDate getDataDiNascitaUtente(Utente u){return u.getDataNascita();}
    public int getSaldoUtente(Utente u){return u.getSaldo();}
    public ArrayList<Fattura> getLibreriaUtente(int idUtente) throws CampoNonValidoException{
        try {
            return fatturaDAO.getLibreriaUtente(idUtente);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }
    public LocalDate getDataCreazioneAccountUtente(Utente u){return u.getDataCreazione();}
    public boolean isUtenteBannato(Utente u){return u.isBannato();}
    public Utente getUtenteById(int idUtente) throws CampoNonValidoException{ //DA FARE con implementazione
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
            utenteDAO.inserisciSviluppatoreSeguito(utenteloggato.getId(),sviluppatoreSelezionato.getId());
        } catch (SQLException e) {
            utenteloggato.removeSviluppatoreSeguito(sviluppatoreSelezionato);
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public void rimuoviSviluppatoreSeguito(Utente utenteloggato, Sviluppatore sviluppatoreSelezionato) throws CampoNonValidoException {
        utenteloggato.removeSviluppatoreSeguito(sviluppatoreSelezionato);

        try {
            utenteDAO.eliminaSviluppatoreSeguito(utenteloggato.getId(),sviluppatoreSelezionato.getId());
        } catch (SQLException e) {
            utenteloggato.addSviluppatoreSeguito(sviluppatoreSelezionato);
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public String getDescrizioneSviluppatore(Sviluppatore s){return s.getDescrizione();}
    public ArrayList<EdizioneGioco> getListaEdizioniSviluppatore(Sviluppatore sviluppatore) throws CampoNonValidoException{
        try{

            return edizioneGiocoDAO.getListaEdizioniSviluppatore(sviluppatore.getId());

        } catch (SQLException e){
            throw new CampoNonValidoException("Operazione fallita");
        }
    }
    public int getNumeroGiochiRilasciatiSviluppatore(Sviluppatore s){return s.getListaGiochi().size();}
    public boolean isSviluppatoreBannato(Sviluppatore sviluppatore){return sviluppatore.isBannato();}
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

        try{
            sviluppatoreDAO.rimuoviStrike(sviluppatore.getId());
        } catch (SQLException e){
            sviluppatore.addStrike();
            throw new CampoNonValidoException("Operazione fallita");
        }
    }
    public int getStrikeSviluppatore(Sviluppatore sviluppatore) {return sviluppatore.getStrike();}
    public void addStrikeSviluppatoreDaGioco(Gioco gioco) throws CampoNonValidoException { addStrikeSviluppatore(gioco.getSviluppatore()); }
    public String getNomeSviluppatore(Sviluppatore s){return s.getNome();}
    public String getNomeSviluppatoreDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getGioco().getSviluppatore().getNome();}
    public int getPrezzoDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getPrezzo();}
    public PiattaformaDiGioco getPiattaformaDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getPiattaforma();}
    public ArrayList<Genere> getGeneriDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getGioco().getGeneri();}
    public int getPegiDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getGioco().getPegi();}
    public int getMediaVotiEdizioneGioco(EdizioneGioco edizioneGioco) throws SQLException {return recensioneDAO.getMediaVotiEdizioneGioco(edizioneGioco.getId());} //da fare
    public Categoria getCategoriaDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getGioco().getCategoria();}
    public LocalDate getDataDiRilascioDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getDataRilascio();}



    public void aggiungiAmico(Utente utenteLoggato, Utente utenteSelezionato) throws CampoNonValidoException {
        utenteLoggato.addAmico(utenteSelezionato);

        try {
            utenteDAO.inserisciAmico(utenteLoggato.getId(),utenteSelezionato.getId());
        } catch (SQLException e) {
            utenteLoggato.removeAmico(utenteSelezionato);
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public void rimuoviAmico(Utente utenteLoggato, Utente utenteSelezionato) throws CampoNonValidoException {
        utenteLoggato.removeAmico(utenteSelezionato);

        try {
            utenteDAO.eliminaAmico(utenteLoggato.getId(),utenteSelezionato.getId());
        } catch (SQLException e) {
            utenteLoggato.addAmico(utenteSelezionato);
            throw new CampoNonValidoException("Operazione Fallita");
        }

    }

    public ArrayList<Recensione> getListaRecensioniUtente(int idUtente) throws CampoNonValidoException{
        try {

            return recensioneDAO.getListaRecensioniUtente(idUtente);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public Fattura getFatturaDaRecensione(Recensione r){return r.getFattura();}

    public void rimuoviRecensioneSelezionataDaFattura(Fattura fattura) throws CampoNonValidoException {
        fattura.setRecensione(null);

        try {
            recensioneDAO.eliminaRecensione(fattura.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public ArrayList<Genere> getGeneri() throws CampoNonValidoException {
        try {

            return genereDAO.getListaGeneri();

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione fallita");
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
    public ArrayList<Genere> getGeneriDaGioco(Gioco gioco) throws CampoNonValidoException{
        try{

            return genereDAO.getListaGeneriDaGioco(gioco);

        } catch (SQLException e){
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
        try{

            giocoDAO.updatePegiGioco(gioco.getId(), pegi);
            gioco.setPegi(pegi);

        } catch (SQLException e){
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
    public ArrayList<Genere> getGeneriDaFattura(Fattura f) {return f.getGioco().getGioco().getGeneri();}
    public Sviluppatore getSviluppatoreDaFattura(Fattura f) {return f.getGioco().getGioco().getSviluppatore();}
    public Gioco getGiocoDaFattura(Fattura f) {return f.getGioco().getGioco();}
    public int getVotoDaFattura(Fattura f) {return f.getRecensione().getVoto();}
    public int getDifferenzaLikeDaFattura(Fattura f) {return f.getRecensione().getDifferenzaLike();}
    public Recensione getRecensioneDaFattura(Fattura f) {return f.getRecensione();}
    public String getDescrizioneRecensioneDaFattura(Fattura f) {return f.getRecensione().getDescrizione();}
    public LocalDate getDataAcquistoDaFattura(Fattura f){return (f.getDataAcquisto());}
    public String getKeyDaFattura(Fattura f){return f.getKey();}
    public int getPrezzoAcquistoDaFattura(Fattura f){return f.getPrezzoAcquisto();}

    public void rilasciaRecensione(int voto, String testo, Fattura fatturaSelezionata) throws CampoNonValidoException {
        try {
            recensioneDAO.creaRecensione(fatturaSelezionata.getId(),voto,testo);
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }
    public void aggiornaRecensione(int voto, String testo, Fattura fatturaSelezionata) throws CampoNonValidoException {
        try {
            recensioneDAO.aggiornaRecensione(fatturaSelezionata.getId(),voto,testo);
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

    public ArrayList<EdizioneGioco> getEdizioniGiochi() throws CampoNonValidoException {

        ArrayList<EdizioneGioco> listaEdizioniGiochi;
        try {
            listaEdizioniGiochi = edizioneGiocoDAO.getCatalogoCompleto();
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }

        return listaEdizioniGiochi;
    }

    public boolean isInPromozione(EdizioneGioco edizioneGioco) {
        for (GiocoInPromozione p : edizioneGioco.getGioco().getPromozioni()) {
            if (p.getPromozione().getDataFine().isAfter(LocalDate.now())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<EdizioneGioco> getCatalogoFiltrato(String testoRicerca, int prezzoMax, Genere genere, Categoria categoria, String pegi, boolean inPromozione, boolean traSeguiti, Utente utenteLoggato, int ordinamentoData) throws CampoNonValidoException {

        ArrayList<EdizioneGioco> listaEdizioniGiochi;
        try {
            listaEdizioniGiochi = edizioneGiocoDAO.getCatalogoCompleto();
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }

        ArrayList<EdizioneGioco> listaFiltrata = new ArrayList<>();

        for (EdizioneGioco e : listaEdizioniGiochi) {

            if (e.getGioco().getTitolo().toLowerCase().contains(testoRicerca) &&
                    (prezzoMax == -1 || e.getPrezzo() <= prezzoMax) &&
                    (genere == null || (e.getGioco().getGeneri() != null && e.getGioco().getGeneri().contains(genere))) &&
                    (categoria == null || e.getGioco().getCategoria().equals(categoria)) &&
                    (pegi == null || pegi.trim().isEmpty() || String.valueOf(e.getGioco().getPegi()).equals(pegi)) &&
                    (!inPromozione || isInPromozione(e)) &&
                    (!traSeguiti || utenteLoggato.getSviluppatoriSeguiti().contains(e.getGioco().getSviluppatore()))) {

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

    public ArrayList<Fattura> getLibreriaFiltrata(String testoRicerca, Utente utenteLoggato, Genere genereFiltro, Categoria categoriaFiltro, String pegiFiltro, int statoDataRilascio, int statoPrezzoFiltro, int statoDataAcquisto) throws CampoNonValidoException {
        ArrayList<Fattura> libreriaUtente;

        try {
            libreriaUtente = fatturaDAO.getLibreriaUtente(utenteLoggato.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }

        ArrayList<Fattura> listaFiltrata = new ArrayList<>();

        for (Fattura f : libreriaUtente) {
            Gioco giocoBase = getGiocoDaFattura(f); //fatto solo per non scrivere sempre get gioco get gioco

            if (giocoBase.getTitolo().toLowerCase().contains(testoRicerca) &&
                    (genereFiltro == null || (giocoBase.getGeneri() != null && giocoBase.getGeneri().contains(genereFiltro))) && //Controllo se il genere della combobox é contenuto nei generi del gioco
                    (categoriaFiltro == null || giocoBase.getCategoria().equals(categoriaFiltro)) && //controllo che la categoria selezionata sia uguale al gioco
                    (pegiFiltro == null || pegiFiltro.trim().isEmpty() || String.valueOf(getPegiDaFattura(f)).equals(pegiFiltro))) { //controllo ce il pegi sia uguale

                listaFiltrata.add(f); // Se é tutto apposto filtro
            }
        }

        if (statoDataRilascio == 1) {
            listaFiltrata.sort((f1, f2) -> f1.getGioco().getDataRilascio().compareTo(f2.getGioco().getDataRilascio()));
        } else if (statoDataRilascio == 2) {
            listaFiltrata.sort((f1, f2) -> f2.getGioco().getDataRilascio().compareTo(f1.getGioco().getDataRilascio()));
        }

        if (statoPrezzoFiltro == 1) {
            listaFiltrata.sort((f1, f2) -> Integer.compare(f1.getPrezzoAcquisto(), f2.getPrezzoAcquisto()));
        } else if (statoPrezzoFiltro == 2) {
            listaFiltrata.sort((f1, f2) -> Integer.compare(f2.getPrezzoAcquisto(), f1.getPrezzoAcquisto()));
        }

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

        if (utenteLoggato.getCarrello() == null) {
            Carrello nuovoCarrello = new Carrello(utenteLoggato);
            utenteLoggato.setCarrello(nuovoCarrello);
        }

        if (utenteLoggato.getCarrello().getListaGiochi().contains(edizioneGiocoSelezionata)){
            throw new CampoNonValidoException("Hai giá aggiunto questo gioco al carrello");
        }
        if (edizioneGiocoSelezionata == null) {
            throw new CampoNonValidoException("Selezionare un gioco dal catalogo");
        }

        utenteLoggato.getCarrello().addEdizione(edizioneGiocoSelezionata);

        try {
            utenteDAO.inserisciCarrello(utenteLoggato.getId(),edizioneGiocoSelezionata.getId());
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

    public Carrello getCarrelloUtente(Utente utenteLoggato){
        return utenteLoggato.getCarrello();
    }

    public ArrayList<EdizioneGioco> getGiochiCarrello(Utente utenteLoggato) throws CampoNonValidoException{
        try {
            return edizioneGiocoDAO.getListaGiochiCarrello(utenteLoggato.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public String getTitoloDaEdizioneGioco(EdizioneGioco edizioneGioco){
        return edizioneGioco.getGioco().getTitolo();
    }

    public int getPrezzoCarrello(Utente utenteLoggato){
        return utenteLoggato.getCarrello().getTotale();
    }

    public void rimuoviDalCarrello(Utente utenteLoggato, EdizioneGioco edizioneGioco) throws CampoNonValidoException {

        if (utenteLoggato.getCarrello() == null) {
            throw new CampoNonValidoException("Il carrello è già vuoto!");
        }
        utenteLoggato.getCarrello().removeEdizione(edizioneGioco);

        try {
            utenteDAO.eliminaCarrello(utenteLoggato.getId(),edizioneGioco.getId());
        } catch (SQLException e) {
            utenteLoggato.getCarrello().addEdizione(edizioneGioco);
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

    public void acquista(Utente utenteLoggato) throws CampoNonValidoException {
        Carrello carrello = utenteLoggato.getCarrello();

        if (carrello == null || carrello.getListaGiochi().isEmpty()) {
            throw new CampoNonValidoException("Vuoi acquistare il nulla?");
        }


        if (utenteLoggato.getSaldo() < carrello.getTotale()) {
            throw new CampoNonValidoException("Saldo insufficiente.");
        }

        utenteLoggato.rimuoviSaldo(carrello.getTotale());

        for (EdizioneGioco giocoAcquistato : carrello.getListaGiochi()) {
            Fattura nuovaFattura = new Fattura(utenteLoggato, giocoAcquistato, giocoAcquistato.getPrezzo());
            utenteLoggato.addGioco(nuovaFattura);

            giocoAcquistato.getGioco().getSviluppatore().addFondi(nuovaFattura.getPrezzoAcquisto());

            try {
                fatturaDAO.inserisciFattura(nuovaFattura);
            } catch (SQLException e) {
                throw new CampoNonValidoException("Operazione Fallita");
            }
        }

        utenteLoggato.getCarrello().svuotaCarrello();

        try {
            utenteDAO.svuotaCarrello(utenteLoggato.getId());
        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }

//    Da fare con DAO
//    public int giocoPiuVendutoSviluppatore(Sviluppatore sviluppatore){
//    }

    public void invertiStatoBan(int idUtente) throws CampoNonValidoException{
        try {

            utenteDAO.invertiStatoBan(idUtente);

        } catch (SQLException e) {
            throw new CampoNonValidoException("Operazione Fallita");
        }
    }
}
