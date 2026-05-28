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
        Utente utente1 = new Utente("Marcoss", "Password1!weq", GenereEnum.Maschio, "marco@gmail.com", LocalDate.of(1999, 10, 12));


        Sviluppatore sviluppatore = new Sviluppatore("Sega", "SegaTheBest100!!", "Noi facciamo i giochi migliori");

        listaAccountLoggati.add(utente);
        listaAccountLoggati.add(utente1);
        listaAccountLoggati.add(sviluppatore);
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

    public ArrayList<Utente> getListaUtentiLoggati() {
        ArrayList<Utente> listaUtentiLoggati = new ArrayList<>();
        for(Account u : listaAccountLoggati){
            if (u instanceof Utente){
                listaUtentiLoggati.add((Utente)u);
            }
        }
        return listaUtentiLoggati;
    }

    public ArrayList<Sviluppatore> getListaSviluppatoriLoggati(){
        ArrayList<Sviluppatore> listaSviluppatoriLoggati = new ArrayList<>();
        for(Account u : listaAccountLoggati){
            if (u instanceof Sviluppatore){
                listaSviluppatoriLoggati.add((Sviluppatore)u);
            }
        }
        return listaSviluppatoriLoggati;
    }

    public int getNumeroRecensioniUtente(Utente utenteLoggato){
        int count = 0;
        for (Fattura fattura : utenteLoggato.getGiochiAcquistati()){
            if (fattura.getRecensione() != null){
                count++;
            }
        }
        return count;
    }

}
