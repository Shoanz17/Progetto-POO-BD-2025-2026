package controller;

import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Account> listaAccountLoggati = new ArrayList<>();
    private ArrayList<Genere> listaGeneri = new ArrayList<>();
    private ArrayList<PiattaformaDiGioco> listaPiattaformeDiGioco = new ArrayList<>();
    private ArrayList<EdizioneGioco> listaEdizioniGiochi = new ArrayList<>();
    private ArrayList<Fattura> listaFatture = new ArrayList<>();

    private ArrayList<String> storicoLike = new ArrayList<>();
    private ArrayList<String> storicoDislike = new ArrayList<>();

    public Controller() {
        try {
            creaDatiFittizi();
        } catch (CampoNonValidoException e) {
            e.getMessage();
        }
    }

    private void creaDatiFittizi() throws CampoNonValidoException {
        Utente utente = new Utente("Marco", "Password1!", GenereEnum.Maschio, "marco@gmail.com", LocalDate.of(1999, 10, 12));
        Utente utente1 = new Utente("Mario", "Password2!", GenereEnum.Maschio, "mario@gmail.com", LocalDate.of(2000, 2, 20));
        Utente utente2 = new Utente("Serena", "Password3!", GenereEnum.Femmina, "aa@gmail.com", LocalDate.of(2006, 11, 30));
        Utente utente3 = new Utente("Gustave", "Password4!", GenereEnum.Maschio, "bbbbb@gmail.com", LocalDate.of(1991, 1, 1));
        Utente utente4 = new Utente("Makoto", "Password5!", GenereEnum.Maschio, "ccccccccc@gmail.com", LocalDate.of(1999, 10, 12));
        Utente utente5 = new Utente("Dario", "Password6!", GenereEnum.Altro, "dario@gmail.com", LocalDate.of(1989, 5, 16));

        Sviluppatore sviluppatore = new Sviluppatore("Sega", "SegaTheBest100!!", "Noi facciamo i giochi migliori");
        Sviluppatore sviluppatore1 = new Sviluppatore("Nintendo", "NintendoTheBest100!!", "Noi facciamo i giochi peggiori");

        Admin admin = new Admin("aa", "Password2@");

        listaAccountLoggati.add(utente);
        listaAccountLoggati.add(utente1);
        listaAccountLoggati.add(utente2);
        listaAccountLoggati.add(utente3);
        listaAccountLoggati.add(utente4);
        listaAccountLoggati.add(utente5);
        utente1.setBannato(true);
        utente3.setBannato(true);
        listaAccountLoggati.add(sviluppatore);
        listaAccountLoggati.add(sviluppatore1);
        listaAccountLoggati.add(admin);

        Genere genere1 = new Genere(0, "Azione");
        Genere genere2 = new Genere(1, "Sopravvivenza");
        Genere genere3 = new Genere(2, "JRPG");

        listaGeneri.add(genere1);
        listaGeneri.add(genere2);
        listaGeneri.add(genere3);


        Gioco gioco = new Gioco("The Witcher 3", Categoria.AAA, 18, sviluppatore1, listaGeneri);
        PiattaformaDiGioco piattaformaDiGioco = new PiattaformaDiGioco("Switch", "Nintendo", true);
        EdizioneGioco edizioneGioco = new EdizioneGioco(gioco, piattaformaDiGioco, 60, LocalDate.of(2015, 10, 12));

        listaPiattaformeDiGioco.add(piattaformaDiGioco);
        listaEdizioniGiochi.add(edizioneGioco);

        Fattura fattura1 = new Fattura(utente, edizioneGioco, 50);

        listaFatture.add(fattura1);
        sviluppatore1.addGioco(gioco);
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

        //aggiungo l'utente al DB
        listaAccountLoggati.add(utente);
    }

    public void registraSviluppatore(String nome, String password, String descrizione) throws CampoNonValidoException {
        Sviluppatore sviluppatore = new Sviluppatore(nome, password, descrizione);

        listaAccountLoggati.add(sviluppatore);
    }

    //controllare anche se quando ci si registra l'account già esiste
    public Account accedi(String nome, String password) throws CampoNonValidoException {
        Account.verificaFormatoNome(nome);
        Account.verificaFormatoPassword(password);

        for (Account account : listaAccountLoggati) {
            if (account.getNome().equals(nome) && account.getPassword().equals(password)) return account;
        }

        throw new CampoNonValidoException("Nome o password errate");

    }

    public ArrayList<Utente> getListaUtentiLoggati() {
        ArrayList<Utente> listaUtentiLoggati = new ArrayList<>();
        for (Account u : listaAccountLoggati) {
            if (u instanceof Utente) {
                listaUtentiLoggati.add((Utente) u);
            }
        }
        return listaUtentiLoggati;
    }

    public ArrayList<Sviluppatore> getListaSviluppatoriLoggati() {
        ArrayList<Sviluppatore> listaSviluppatoriLoggati = new ArrayList<>();
        for (Account u : listaAccountLoggati) {
            if (u instanceof Sviluppatore) {
                listaSviluppatoriLoggati.add((Sviluppatore) u);
            }
        }
        return listaSviluppatoriLoggati;
    }

    public int getNumeroRecensioniUtente(Utente utenteLoggato) {
        return getListaRecensioniUtente(utenteLoggato).size();
    }

    public void aggiungiSaldo(Utente utenteLoggato, int saldo) throws CampoNonValidoException {
        utenteLoggato.aggiungiSaldo(saldo);
    }

    public void aggiungiSaldo(Utente utenteLoggato, String saldoTesto) throws CampoNonValidoException {
        try {
            if (saldoTesto == null || saldoTesto.trim().isEmpty()) {
                throw new CampoNonValidoException("Scrivere quanto si vuole aggiungere");
            }

            int saldo = Integer.parseInt(saldoTesto.trim());
            utenteLoggato.aggiungiSaldo(saldo);

        } catch (NumberFormatException e) {
            throw new CampoNonValidoException("Inserire un numero");
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

    public int getIdUtente(Utente u){return u.getId();}
    public int getNumeroGiochiAcquistatiUtente(Utente u){return u.getGiochiAcquistati().size();}
    public GenereEnum getGenereUtente(Utente u){return u.getGenere();}
    public String getNomeUtente(Utente u){return u.getNome();}
    public String getEmailUtente(Utente u){return u.getEmail();}
    public LocalDate getDataDiNascitaUtente(Utente u){return u.getDataNascita();}
    public int getSaldoUtente(Utente u){return u.getSaldo();}
    public ArrayList<Fattura> getLibreriaUtente(Utente u){return u.getGiochiAcquistati();}
    public LocalDate getDataCreazioneAccountUtente(Utente u){return u.getDataCreazione();}
    public boolean isUtenteBannato(Utente u){return u.isBannato();}
    public Utente getUtenteById(int idUtente){ //DA FARE COL DAO
        for(Account utente : listaAccountLoggati){
            if(utente.getId() == idUtente){
                return (Utente) utente;
            }
        }
        return null;
    }

    public void aggiungiSviluppatoreSeguito(Utente utenteloggato, Sviluppatore sviluppatoreSelezionato) throws CampoNonValidoException {
        utenteloggato.addSviluppatoreSeguito(sviluppatoreSelezionato);
    }

    public void rimuoviSviluppatoreSeguito(Utente utenteloggato, Sviluppatore sviluppatoreSelezionato) throws CampoNonValidoException {
        utenteloggato.removeSviluppatoreSeguito(sviluppatoreSelezionato);
    }

    public String getDescrizioneSviluppatore(Sviluppatore s){return s.getDescrizione();}
    public ArrayList<EdizioneGioco> getListaEdizioniSviluppatore(Sviluppatore s) {
        ArrayList<EdizioneGioco> lista = new ArrayList<>();
        for(EdizioneGioco edizioneGioco : getEdizioniGiochi()){
            if(getNomeSviluppatoreDaEdizioneGioco(edizioneGioco).equals(getNomeSviluppatore(s))) lista.add(edizioneGioco);
        }
        return lista;
    }
    public int getNumeroGiochiRilasciatiSviluppatore(Sviluppatore s){return s.getListaGiochi().size();}
    public boolean isSviluppatoreBannato(Sviluppatore s){return s.isBannato();}
    public void addStrikeSviluppatore(Sviluppatore s) throws CampoNonValidoException {s.addStrike();}
    public void removeStrikeSviluppatore(Sviluppatore s) throws CampoNonValidoException {s.removeStrike();}
    public int getStrikeSviluppatore(Sviluppatore s) {return s.getStrike();}
    public String getNomeSviluppatore(Sviluppatore s){return s.getNome();}
    public String getNomeSviluppatoreDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getGioco().getSviluppatore().getNome();}
    public int getPrezzoDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getPrezzo();}
    public PiattaformaDiGioco getPiattaformaDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getPiattaforma();}
    public ArrayList<Genere> getGeneriDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getGioco().getGeneri();}
    public int getPegiDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getGioco().getPegi();}
    public int getMediaVotiEdizioneGioco(EdizioneGioco edizioneGioco){return 1;} //da fare
    public Categoria getCategoriaDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getGioco().getCategoria();}
    public LocalDate getDataDiRilascioDaEdizioneGioco(EdizioneGioco edizioneGioco){return edizioneGioco.getDataRilascio();}



    public void aggiungiAmico(Utente utenteLoggato, Utente utenteSelezionato) throws CampoNonValidoException {
        utenteLoggato.addAmico(utenteSelezionato);
    }

    public void rimuoviAmico(Utente utenteLoggato, Utente utenteSelezionato) throws CampoNonValidoException {
        utenteLoggato.removeAmico(utenteSelezionato);
    }

    public ArrayList<Recensione> getListaRecensioniUtente(Utente utenteLoggato) {
        ArrayList<Recensione> listaRecensioni = new ArrayList<>();

        for (Fattura f : utenteLoggato.getGiochiAcquistati()) {
            if (f.getRecensione() != null) {
                listaRecensioni.add(f.getRecensione());
            }
        }
        return listaRecensioni;
    }

    public void rimuoviRecensioneSelezionata(Fattura fattura) throws CampoNonValidoException {
        fattura.setRecensione(null);
        //Da eliminare dal database
    }

    public ArrayList<Genere> getGeneri() {
        return listaGeneri;
    }

    public ArrayList<Categoria> getCategorie() {
        ArrayList<Categoria> categorie = new ArrayList<>();

        for (Categoria c : Categoria.values()) {
            categorie.add(c);
        }
        return categorie;
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
        Recensione nuovaRecensione = new Recensione(voto, testo, fatturaSelezionata);

        fatturaSelezionata.setRecensione(nuovaRecensione);

        //da salvare nel dao
    }

    public ArrayList<PiattaformaDiGioco> getPiattaformeDiGioco() {
        return listaPiattaformeDiGioco;
    }

    public ArrayList<EdizioneGioco> getEdizioniGiochi() {
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

    public ArrayList<EdizioneGioco> getCatalogoFiltrato(String testoRicerca, int prezzoMax, Genere genere, Categoria categoria, String pegi, boolean inPromozione, boolean traSeguiti, Utente utenteLoggato, int ordinamentoData) {

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

    public ArrayList<Fattura> getLibreriaFiltrata(String testoRicerca, Utente utenteLoggato, Genere genereFiltro, Categoria categoriaFiltro, String pegiFiltro, int statoDataRilascio, int statoPrezzoFiltro, int statoDataAcquisto) {

        ArrayList<Fattura> listaFiltrata = new ArrayList<>();

        for (Fattura f : utenteLoggato.getGiochiAcquistati()) {
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

    public ArrayList<Utente> getUtentiFiltrati(boolean checkBoxAmici, String testoRicerca, Utente utenteLoggato) {
        ArrayList<Utente> listaFiltrata;

        if (checkBoxAmici) {
            listaFiltrata = utenteLoggato.getListaAmici();
        } else {
            listaFiltrata = getListaUtentiLoggati();
        }

        ArrayList<Utente> listaFinale = new ArrayList<>();
        for (Utente u : listaFiltrata) {
            if (u != utenteLoggato && u.getNome().toLowerCase().contains(testoRicerca)) {
                listaFinale.add(u);
            }
        }

        return listaFinale;
    }

    public ArrayList<Sviluppatore> getSviluppatoriFiltrati(boolean checkBoxSviluppatore, String testoRicerca, Utente utenteLoggato) {
        ArrayList<Sviluppatore> listaFiltrata;

        if (checkBoxSviluppatore) {
            listaFiltrata = utenteLoggato.getSviluppatoriSeguiti();
        } else {
            listaFiltrata = getListaSviluppatoriLoggati();
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

        if (utenteLoggato.getCarrello().getListaGiochi().contains(edizioneGiocoSelezionata)){
            throw new CampoNonValidoException("Hai giá aggiunto questo gioco al carrello");
        }
        if (edizioneGiocoSelezionata == null) {
            throw new CampoNonValidoException("Selezionare un gioco dal catalogo");
        }

        if (utenteLoggato.getCarrello() == null) {
            Carrello nuovoCarrello = new Carrello(utenteLoggato);
            utenteLoggato.setCarrello(nuovoCarrello);
        }

        utenteLoggato.getCarrello().addEdizione(edizioneGiocoSelezionata);
    }

    public ArrayList<Recensione> getRecensioniEdizioneGioco(EdizioneGioco edizioneGioco){
        ArrayList<Recensione> listaRecensioniGioco = new ArrayList<>();
        for (Fattura f : listaFatture){
            if(f.getGioco().equals(edizioneGioco) && f.getRecensione() != null) {
                listaRecensioniGioco.add(f.getRecensione());
            }
        }
        return listaRecensioniGioco;
    }

    public String getDescrizioneRecensione(Recensione recensione){return recensione.getDescrizione();}
    public int getVotoRecensione(Recensione recensione){return recensione.getVoto();}
    public int getDifferenzaLikeRecensione(Recensione recensione){return recensione.getDifferenzaLike();}

    public void mettiLikeRecensione(Recensione recensione, Utente utenteLoggato) throws CampoNonValidoException {

        String ricevutaVoto = utenteLoggato.getId() + "_" + recensione.getFattura().getKey();

        if (storicoLike.contains(ricevutaVoto)) {
            throw new CampoNonValidoException("Hai giá messo Like!");
        }

        if (storicoDislike.contains(ricevutaVoto)) {
            storicoDislike.remove(ricevutaVoto);
            recensione.addLike();
        } else {
            storicoLike.add(ricevutaVoto);
            recensione.addLike();
        }
    }

    public void mettiDislikeRecensione(Recensione recensione, Utente utenteLoggato) throws CampoNonValidoException {

        String ricevutaVoto = utenteLoggato.getId() + "_" + recensione.getFattura().getKey();

        if (storicoDislike.contains(ricevutaVoto)) {
            throw new CampoNonValidoException("Hai già messo Dislike! Review bombing?");
        }

        if (storicoLike.contains(ricevutaVoto)) {
            storicoLike.remove(ricevutaVoto);
            recensione.addDislike();
        } else {
            storicoDislike.add(ricevutaVoto);
            recensione.addDislike();
        }
    }

    public Carrello getCarrelloUtente(Utente utenteLoggato){
        return utenteLoggato.getCarrello();
    }

    public ArrayList<EdizioneGioco> getGiochiCarrello(Utente utenteLoggato){
        return utenteLoggato.getCarrello().getListaGiochi();
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
            listaFatture.add(nuovaFattura);   // SOLO PER TEST
        }

        utenteLoggato.getCarrello().svuotaCarrello();
    }

//    Da fare con DAO
//    public int giocoPiuVendutoSviluppatore(Sviluppatore sviluppatore){
//    }


    public void invertiStatoBan(int idUtente) throws CampoNonValidoException{
        Utente utente = trovaUtentePerId(idUtente);

        if(utente == null) throw new CampoNonValidoException("Operazione non andata a buon fine");

        utente.setBannato(!utente.isBannato()); //magari potrei lanciare un eccezione
    }

    public Utente trovaUtentePerId(int idUtente){
        for(Utente utente : getListaUtentiLoggati()){ //qui c'è un doppio ciclo ma andrà risolto col db
            if(utente.getId() == idUtente) return utente;
        }
        return null;
    }
}
