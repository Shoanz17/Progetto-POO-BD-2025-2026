package model;


import org.postgresql.shaded.com.ongres.stringprep.Stringprep;


import java.time.LocalDate;


public abstract class Account {
    // attributi in account:
        private String nome;
        private int id;
        private String password;
        private LocalDate dataCreazione;

    //Creazione del costruttore
    public Account(String nome,String password)
    {
        setNome(nome);
        setPassword(password);
        this.dataCreazione = LocalDate.now();

    }
    //costruttore per il DAO
    public Account(String nome,int id,String password,LocalDate dataCreazione)
    {
        this.nome = nome;
        this.password = password;
        this.id = id;
        this.dataCreazione = dataCreazione;
        //localdate mette esattamente la data in cui viene creato l'oggetto
    }

    //get e set
    public String getNome() {return nome;}
    public void setNome(String nome)
    {
        if(nome == null || nome.length()>24)
        { throw new IllegalArgumentException("il nome che hai scelto \""+ nome +"\" è troppo lungo");}
        this.nome = nome;
    }//nome deve essere grande quanto il var char in db

    public int getId() {return id;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        boolean haMaiuscola = false;
        boolean haNumero = false;
        boolean haSpeciale = false;
        // qui controlliamo se la password contiene almeno 8 caratteri
        if (password == null || password.length()<8) {
            throw new IllegalArgumentException("La password richiede almeno 8 caratteri.");
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
        {throw new IllegalArgumentException
                ("La password deve contenere almeno una maiuscola," +  " un numero e un carattere speciale.");}

        this.password = password;
    }

    public LocalDate getDataCreazione() {return dataCreazione;}


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
}
