package model;


import java.time.LocalDate;

/**
 * Classe astratta che rappresenta un account generico all'interno della piattaforma.
 * Fornisce gli attributi e i metodi di base che poi verranno ereditati da Utente, Sviluppatore, Admin.
 */

public abstract class Account {
    // attributi in account:
        private String nome;
        private int id;
        private String password;
        private LocalDate dataCreazione;

    /**
     * Costruttore utilizzato per la registrazione di un nuovo account tramite l'interfaccia.
     * La data di creazione viene impostata in automatico nel momento preciso in cui si crea l'account.
     *
     * @param nome     Il nome utente.
     * @param password La password utente.
     * @throws CampoNonValidoException Se il nome o la password non rispettano i vincoli di sicurezza.
     */
    public Account(String nome,String password) throws CampoNonValidoException
    {
        setNome(nome);
        setPassword(password);
        this.dataCreazione = LocalDate.now(); //localdate.now mette esattamente la data in cui viene creato l'oggetto
    }

    /**
     * Costruttore utilizzato esclusivamente dal DAO per costruire un account
     * prendendo i dati che si trovano nel Database.
     *
     * @param nome          Il nome utente salvato nel DB.
     * @param id            L'identificativo univoco del DB.
     * @param password      La password salvata nel DB.
     * @param dataCreazione La data di creazione storica registrata nel DB.
     */
    public Account(String nome,int id,String password,LocalDate dataCreazione)
    {
        this.nome = nome;
        this.password = password;
        this.id = id;
        this.dataCreazione = dataCreazione;
        //localdate mette esattamente la data in cui viene creato l'oggetto
    }

    /**
     * Verifica che il formato del nome rispetti i vincoli e non sia composto da soli spazi vuoti.
     * @param nome La stringa da validare.
     * @throws CampoNonValidoException Se il nome è nullo, vuoto o troppo lungo.
     */
    public static void verificaFormatoNome(String nome) throws CampoNonValidoException{
        if(nome == null || nome.length()>24)
            throw new CampoNonValidoException("il nome che hai scelto \""+ nome +"\" è troppo lungo");
        //.trim serve a rimuovere gli spazi bianchi presenti all'inizio e alla fine di una stringa
        //isEmpty verifica se l'elemento è vuoto
        if(nome.trim().isEmpty())
            throw new CampoNonValidoException("il nome che hai scelto è vuoto ");
    }

    /**
     * Verifica che la password rispetti i criteri di sicurezza:
     * lunghezza tra 8 e 32 caratteri, almeno una maiuscola, un numero e un carattere speciale.
     *
     * @param password La password da validare.
     * @throws CampoNonValidoException Se la password non rispetta i criteri di sicurezza.
     */
    public static void verificaFormatoPassword(String password) throws CampoNonValidoException{
        boolean haMaiuscola = false;
        boolean haNumero = false;
        boolean haSpeciale = false;
        // qui controlliamo se la password contiene almeno 8 caratteri
        if (password == null || password.length()<8||password.length()>32) {
            throw new CampoNonValidoException("La password richiede almeno 8 e massimo 32 caratteri");
        }
        //usiamo il ciclo for per controllare ogni carattere, la password deve avere almeno una maiuscola
        // un numero e un carattere speciale
        for(int i = 0; i < password.length(); i++)
        {
            char lettera = password.charAt(i);
            if(Character.isUpperCase(lettera)) {haMaiuscola = true;}
            else if (Character.isDigit(lettera)) {haNumero = true;}
            else if (!Character.isLetterOrDigit(lettera)&& !Character.isWhitespace(lettera)) {haSpeciale = true;}
        }
        // qui nel ciclo if se non ho maiuscola o numero o speciale facciamo la throw
        if(!haMaiuscola||!haNumero||!haSpeciale)
        {throw new CampoNonValidoException
                ("La password deve contenere almeno una maiuscola," +  " un numero e un carattere speciale.");}
    }

    public String getNome() {return nome;}

    /**
     * *Imposta un nuovo nome utente se il formato è giusto.
     * @param nome Il nuovo nome da impostare.
     * @throws CampoNonValidoException Se il formato non è valido.
     */
    public void setNome(String nome) throws CampoNonValidoException
    {
        verificaFormatoNome(nome);
        this.nome = nome;
    }//nome deve essere grande quanto il var char in db

    public int getId() {return id;}

    public String getPassword() {
        return password;
    }

    /**
     * Imposta una nuova password se rispetta i criteri di sicurezza.
     *
     * @param password La nuova password da impostare.
     * @throws CampoNonValidoException Se i criteri di sicurezza non sono soddisfatti.
     */
    public void setPassword(String password) throws CampoNonValidoException {
        verificaFormatoPassword(password);
        this.password = password;
    }

    public void setId(int id) {this.id = id;}

    public LocalDate getDataCreazione() {return dataCreazione;}

    /**
     * Determina se due account sono considerati uguali basandosi esclusivamente
     * sul loro identificativo (ID) del Database.
     *
     * @param oggetto L'oggetto da confrontare con l'account corrente.
     * @return true se gli ID coincidono.
     */
    @Override
    public boolean equals(Object oggetto)
    {
        if(this == oggetto) return true;

        else if(oggetto == null||getClass()!= oggetto.getClass()) return false;

        Account og = (Account) oggetto;
        return this.id == og.id;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return nome;
    }
}
