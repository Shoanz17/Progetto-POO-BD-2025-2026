package controller;

import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Account> listaAccountLoggati = new ArrayList<>();


    public Controller(){
        try {
            creaDatiFittizi();
        } catch (CampoNonValidoException e) {
            e.getMessage();
        }
    }

    private void creaDatiFittizi() throws CampoNonValidoException{
        Utente utente = new Utente("Marco", "Password1!", GenereEnum.Maschio, "marco@gmail.com", LocalDate.of(1999, 10, 12));
        Utente utente1 = new Utente("Mario", "Password2!", GenereEnum.Maschio, "mario@gmail.com", LocalDate.of(2000, 2, 20));
        Utente utente2 = new Utente("Serena", "Password3!", GenereEnum.Femmina, "aa@gmail.com", LocalDate.of(2006, 11, 30));
        Utente utente3 = new Utente("Gustave", "Password4!", GenereEnum.Maschio, "bbbbb@gmail.com", LocalDate.of(1991, 1, 1));
        Utente utente4 = new Utente("Makoto", "Password5!", GenereEnum.Maschio, "ccccccccc@gmail.com", LocalDate.of(1999, 10, 12));
        Utente utente5 = new Utente("Dario", "Password6!", GenereEnum.Altro, "dario@gmail.com", LocalDate.of(1989, 5, 16));

        Sviluppatore sviluppatore = new Sviluppatore("Sega", "SegaTheBest100!!", "Noi facciamo i giochi migliori");

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
        listaAccountLoggati.add(admin);
    }

    public void registraUtente(String nome, String password, String genere, String email, String dataNascita) throws CampoNonValidoException{
        Account.verificaFormatoNome(nome);
        Account.verificaFormatoPassword(password);

        if (genere == null || genere.trim().isEmpty()) throw new CampoNonValidoException("Seleziona un genere valido dalla lista!");

        //converte la stringa nell'enum corrispondente
        GenereEnum genereVero = GenereEnum.valueOf(genere);

        //cercato su internet
        //rimuovo gli spazi e controllo se la lunghezza è giusta e se non sono rimasti trattini
        if (dataNascita.contains("_") || dataNascita.trim().length() < 10) throw new CampoNonValidoException("Inserisci una data di nascita completa!");

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

    public void registraSviluppatore(String nome, String password, String descrizione) throws CampoNonValidoException{
        Account.verificaFormatoNome(nome);
        Account.verificaFormatoPassword(password);

        Sviluppatore sviluppatore = new Sviluppatore(nome, password, descrizione);

        listaAccountLoggati.add(sviluppatore);
    }

    //controllare anche se quando ci si registra l'account già esiste
    public Account accedi(String nome, String password) throws CampoNonValidoException{
        Account.verificaFormatoNome(nome);
        Account.verificaFormatoPassword(password);

        for(Account account : listaAccountLoggati){
            if(account.getNome().equals(nome) && account.getPassword().equals(password)) return account;
        }

        throw new CampoNonValidoException("Nome o password errate");

    }

    //forse private, per ora li cerca in ram ma poi farà una query al DB, se lancio eccezione qui devo toglierla dalle altre funzioni
    public Utente trovaUtentePerId(int idUtente){
        for(Utente utente : getListaUtentiLoggati()){ //qui c'è un doppio ciclo ma andrà risolto col db
            if(utente.getId() == idUtente) return utente;
        }
        return null;
    }

    public void invertiStatoBan(int idUtente) throws CampoNonValidoException{
        Utente utente = trovaUtentePerId(idUtente);

        if(utente == null) throw new CampoNonValidoException("Operazione non andata a buon fine");

        utente.setBannato(!utente.isBannato()); //magari potrei lanciare un eccezione
    }

    //da togliere prima del merge
    public ArrayList<Utente> getListaUtentiLoggati() {
        ArrayList<Utente> listaUtentiLoggati = new ArrayList<>();
        for(Account u : listaAccountLoggati){
            if (u instanceof Utente){
                listaUtentiLoggati.add((Utente)u);
            }
        }
        return listaUtentiLoggati;
    }
}
