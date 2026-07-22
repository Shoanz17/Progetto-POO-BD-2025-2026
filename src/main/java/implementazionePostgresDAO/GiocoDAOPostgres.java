package implementazionePostgresDAO;

import dao.GiocoDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GiocoDAOPostgres implements GiocoDAO {

    private Connection connection;

    public GiocoDAOPostgres() {
        try {
            this.connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Gioco> getGiochiFiltrati(String testoRicerca) throws SQLException {
        ArrayList<Gioco> giochiFiltrati = new ArrayList<>();

        // Usiamo ILIKE per una ricerca case-insensitive (ignora maiuscole/minuscole).
        String query = "SELECT idGioco, titolo, categoria, pegi, idSviluppatore " +
                "FROM gioco " +
                "WHERE titolo ILIKE ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            // Aggiungiamo i "%" prima e dopo il testo cercato.
            // In questo modo, se cerchi "Mario", troverà "Super Mario Bros" ma anche "Mario Kart"
            stmt.setString(1, "%" + testoRicerca + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    try {

                        int idSviluppatore = rs.getInt("idSviluppatore");
                        Sviluppatore sviluppatore = new Sviluppatore("Sconosciuto", idSviluppatore, "dummyPassword", LocalDate.now(), 0, "N/A", 0);
                        int idGioco = rs.getInt("idGioco");
                        String titolo = rs.getString("titolo");
                        Categoria categoria = Categoria.valueOf(rs.getString("categoria")); // Conversione stringa -> Enum
                        int pegi = rs.getInt("pegi");
                        Gioco gioco = new Gioco(sviluppatore, idGioco, titolo, categoria, pegi);
                        giochiFiltrati.add(gioco);

                    } catch (CampoNonValidoException ex) {
                        System.err.println("Errore caricamento gioco filtrato (ID " + rs.getInt("idGioco") + "): " + ex.getMessage());
                    }
                }
            }
        }

        return giochiFiltrati;
    }


    @Override
    public void updateTitolo(int idGioco, String titolo) throws SQLException {
        String query = "UPDATE gioco SET titolo = ? WHERE idGioco = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, titolo, idGioco);
    }

    @Override
    public void updateCategoriaGioco(int idGioco, String nomeCategoria) throws SQLException {
        String query = "UPDATE gioco SET categoria = ? WHERE idGioco = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, nomeCategoria, idGioco);
    }

    @Override
    public void updatePegiGioco(int idGioco, int pegi) throws SQLException {
        String query = "UPDATE gioco SET pegi = ? WHERE idGioco = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(query, pegi, idGioco);
    }

    @Override
    public void updateGeneriGioco(int idGioco, ArrayList<Genere> nuoviGeneri) throws SQLException {

        //Rimuoviamo i vecchi generi con una riga sola
        String queryDelete = "DELETE FROM gioco_genere WHERE idGioco = ?";
        ConnessioneDatabase.getInstance().eseguiUpdate(queryDelete, idGioco);

        if (nuoviGeneri == null || nuoviGeneri.isEmpty()) {
            return;
        }

        // Inseriamo i nuovi usando il for
        String queryInsert = "INSERT INTO gioco_genere (idGioco, idGenere) VALUES (?, (SELECT idGenere FROM genere WHERE nome = ?))";

        for (Genere genere : nuoviGeneri) {
            // Ripetiamo l'inserimento per ogni genere selezionato in un'unica riga!
            ConnessioneDatabase.getInstance().eseguiUpdate(queryInsert, idGioco, genere.getNome());
        }
    }

    @Override
    public ArrayList<Gioco> getListaGiochi() throws SQLException {
        ArrayList<Gioco> listaGiochi = new ArrayList<>();

        // Query pulita per prendere tutti i giochi presenti nel database
        String query = "SELECT idGioco, titolo, categoria, pegi, idSviluppatore FROM gioco";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        // Dato che non ci sono parametri (?), possiamo eseguire la query direttamente
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                try {
                    // Usiamo di nuovo i dati fittizi per creare un oggetto leggero e agganciare l'ID
                    int idSviluppatore = rs.getInt("idSviluppatore");
                    Sviluppatore sviluppatore = new Sviluppatore("Sconosciuto", idSviluppatore, "dummyPassword", LocalDate.now(), 0, "N/A", 0);

                    int idGioco = rs.getInt("idGioco");
                    String titolo = rs.getString("titolo");
                    Categoria categoria = Categoria.valueOf(rs.getString("categoria")); // Conversione stringa -> Enum
                    int pegi = rs.getInt("pegi");

                    Gioco gioco = new Gioco(sviluppatore, idGioco, titolo, categoria, pegi);

                    // Aggiungiamo il gioco trovato alla lista
                    listaGiochi.add(gioco);

                } catch (CampoNonValidoException ex) {
                    // Se un singolo gioco dovesse essere corrotto nel DB, lo ignoriamo e la lista continua a caricarsi
                    System.err.println("Errore caricamento gioco (ID " + rs.getInt("idGioco") + "): " + ex.getMessage());
                }
            }
        }
        return listaGiochi;
    }

    @Override
    public void aggiornaGioco(Gioco gioco) throws SQLException {
        // Scriviamo la query per aggiornare tutti i campi modificabili in un colpo solo
        String query = "UPDATE gioco SET titolo = ?, categoria = ?, pegi = ? WHERE idGioco = ?";

        // Dato che Categoria è un Enum in Java, usiamo .name() per trasformarlo
        // nell'esatta stringa richiesta dal DB (es. "INDIE", "AAA")
        String categoriaNome = gioco.getCategoria().name();

        ConnessioneDatabase.getInstance().eseguiUpdate(
                query,
                gioco.getTitolo(),
                categoriaNome,
                gioco.getPegi(),
                gioco.getId()
        );
    }

    @Override
    public int inserisciGioco(Gioco gioco) throws SQLException {
        String query = "INSERT INTO gioco (titolo, categoria, pegi, idSviluppatore) VALUES (?, ?, ?, ?)";

        int idGenerato = -1; // Valore di default in caso di problemi

        Connection conn = ConnessioneDatabase.getInstance().connection;

        // Passiamo Statement.RETURN_GENERATED_KEYS per dirgli di catturare l'ID seriale appena creato  d
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Inseriamo i 4 parametri al posto dei punti interrogativi
            stmt.setString(1, gioco.getTitolo());
            stmt.setString(2, gioco.getCategoria().name());
            stmt.setInt(3, gioco.getPegi());
            stmt.setInt(4, gioco.getSviluppatore().getId());

            // Eseguiamo l'inserimento sul database
            stmt.executeUpdate();

            // Recuperiamo l'ID generato automaticamente da Postgres
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    // Leggiamo il numero generato e lo salviamo nella variabile
                    idGenerato = rs.getInt(1);
                }
            }
        }

        // Restituiamo il nuovo ID al programma
        return idGenerato;
    }

    @Override
    public void inserisciEdizione(EdizioneGioco edizioneGioco, int idGioco) throws SQLException {
        // Controlla che le colonne nel DB si chiamino piattaforma, prezzo e data_rilascio
        String query = "INSERT INTO edizione_gioco (idGioco, piattaforma_di_gioco, prezzo, dataRilascio) VALUES (?, ?, ?, ?)";

        // Convertiamo la piattaforma (che immagino sia un Enum) in stringa con .name()
        String nomePiattaforma = edizioneGioco.getPiattaforma().getNome();

        // Usiamo il metodo del tuo amico Antonio passandogli i dati veri della tua classe
        ConnessioneDatabase.getInstance().eseguiUpdate(
                query,
                idGioco,
                nomePiattaforma,                      // Invece del nome, passiamo la piattaforma
                edizioneGioco.getPrezzo(),            // Il prezzo che c'era già
                edizioneGioco.getDataRilascio()       // La data di rilascio
        );
    }

    @Override
    public int getUnitaVendutePerGioco(String titoloGioco) throws SQLException {

        // La query esatta basata sul tuo schema!
        String query = "SELECT COUNT(*) " +
                "FROM fattura f " +
                "JOIN edizione_gioco eg ON f.idEdizione = eg.idEdizione " +
                "JOIN gioco g ON eg.idGioco = g.idGioco " +
                "WHERE g.titolo = ?";

        int unitaVendute = 0; // Se non ci sono vendite, restituirà 0

        Connection conn = ConnessioneDatabase.getInstance().connection;

        // Usiamo PreparedStatement perché dobbiamo leggere i dati, non scriverli
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            // Sostituiamo il punto interrogativo con il titolo passato al metodo
            stmt.setString(1, titoloGioco);

            // Eseguiamo la query e "catturiamo" la risposta nel ResultSet
            try (ResultSet rs = stmt.executeQuery()) {

                // Se c'è un risultato (e con COUNT(*) c'è sempre, al massimo è 0)
                if (rs.next()) {
                    unitaVendute = rs.getInt(1); // Leggiamo il conteggio
                }
            }
        }

        return unitaVendute;
    }

    @Override
    public int getGuadagnoTotalePerGioco(String titoloGioco) throws SQLException {

        // Query semplice con il SUM classico
        String query = "SELECT SUM(f.prezzoAcquisto) " +
                "FROM fattura f " +
                "JOIN edizione_gioco eg ON f.idEdizione = eg.idEdizione " +
                "JOIN gioco g ON eg.idGioco = g.idGioco " +
                "WHERE g.titolo = ?";

        int guadagnoTotale = 0;

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, titoloGioco);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Se SUM restituisce NULL, rs.getInt() lo trasforma in 0 in automatico
                    guadagnoTotale = rs.getInt(1);
                }
            }
        }

        return guadagnoTotale;
    }

    @Override
    public ArrayList<Gioco> getGiochiSviluppatore(int idSviluppatore) throws SQLException {

        // 1. Prepariamo la lista vuota
        ArrayList<Gioco> listaGiochi = new ArrayList<>();

        // 2. Prepariamo la query
        String query = "SELECT idGioco, titolo, categoria, pegi FROM gioco WHERE idSviluppatore = ?";

        Connection conn = ConnessioneDatabase.getInstance().connection;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            // Passiamo l'ID alla query
            stmt.setInt(1, idSviluppatore);

            try (ResultSet rs = stmt.executeQuery()) {

                // 3. Inizia il tuo ciclo perfetto
                while (rs.next()) {

                    int idGioco = rs.getInt("idGioco");
                    String titolo = rs.getString("titolo");
                    String categoriaString = rs.getString("categoria");
                    int pegi = rs.getInt("pegi");

                    // Ricordati di sistemare la creazione di questo oggetto in base alla tua classe Sviluppatore!
                    Sviluppatore sviluppatoreObj = new Sviluppatore("Sconosciuto", idSviluppatore, "dummyPassword", LocalDate.now(), 0, "N/A", 0);
                    // sviluppatoreObj.setId(idSviluppatore);

                    try {
                        // Creiamo il gioco usando il tuo costruttore ufficiale
                        Gioco giocoCorrente = new Gioco(sviluppatoreObj, idGioco, titolo, Categoria.valueOf(categoriaString), pegi);

                        listaGiochi.add(giocoCorrente);

                    } catch (CampoNonValidoException e) {
                        System.out.println("Errore nel caricamento del gioco: " + e.getMessage());
                    }
                }
            }
        }

        // 4. Restituiamo la lista al programma
        return listaGiochi;
    }
}