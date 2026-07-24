package gui;

import controller.Controller;
import model.*;
import model.Recensione;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HomeAdmin {
    private JPanel adminPanel;
    private JTabbedPane tabbedPane;
    private JButton pulsanteLogout;
    private JTextField ricercaUtenti;
    private JList listaGeneri;
    private JButton pulsanteAggiungiPiattaforma;
    private JTextArea descrizioneRecensioneUtenti;
    private JCheckBox checkBoxBannatiUtenti;
    private JButton pulsanteAggiungiGenere;
    private JTextField campoNomeGenere;
    private JTextField ricercaGeneri;
    private JButton pulsanteBannaUtenteUtenti;
    private JButton pulsanteRimborsa;
    private JTextField ricercaPiattaforme;
    private JCheckBox checkBoxPortabilita;
    private JTextField campoNomePiattaforma;
    private JTextField campoProduttore;
    private JTextField campoNomePromozione;
    private JFormattedTextField campoDataInizio;
    private JFormattedTextField campoDataFine;
    private JButton pulsanteRimuoviRecensioneUtenti;
    private JTable tabellaUtenti;
    private JButton pulsanteAggiungiPromozione;
    private JTextField ricercaPromozioni;
    private JButton pulsanteOrdinaPerData;
    private JTextField ricercaSviluppatori;
    private JCheckBox checkBoxBannatiSviluppatori;
    private JButton pulsanteAggiungiStrike;
    private JList listaSviluppatori;
    private JTable tabellaGiochiSviluppatori;
    private JButton pulsanteRimuoviStrike;
    private JList listaGiochi;
    private JButton pulsanteConfermaGeneri;
    private JButton pulsanteModificaTitolo;
    private JButton pulsanteModificaCategoria;
    private JButton pulsanteModificaPegi;
    private JButton pulsanteAggiungiStrikeGiochi;
    private JTextArea descrizioneSviluppatori;
    private JTextField ricercaRecensioni;
    private JTextArea descrizioneRecensioneRecensioni;
    private JButton pulsanteRimuoviRecensioneRecensioni;
    private JTable tabellaRecensioni;
    private JTable tabellaGiochiUtenti;
    private JTable tabellaPromozioni;
    private JTable tabellaPiattaforme;
    private JPanel utenti;
    private JPanel sviluppatori;
    private JPanel giochi;
    private JPanel generi;
    private JPanel piattaforme;
    private JPanel recensioni;
    private JPanel promozioni;
    private JTable tabellaRecensioniUtenti;
    private JButton pulsanteBannaUtenteRecensioni;
    private JTextField ricercaGiochi;
    private JLabel titoloGioco;
    private JLabel categoriaGioco;
    private JLabel pegiGioco;
    private JLabel nomeGenere;
    private JLabel nomePIattaforma;
    private JLabel produttorePiattaforma;
    private JLabel portabilitaPiattaforma;
    private JLabel nomePromozione;
    private JLabel dataInizioPromozione;
    private JLabel dataFinePromozione;
    private JLabel nStrike;
    private JPanel pannelloGeneriGioco;
    private JScrollPane scrollPaneGeneri;

    private boolean ordinaPerDataAttivo = false;

    private JFrame adminFrame;
    private Controller controller;
    private Admin admin;

    //DA FARE:
    //SVILUPPATORI: listener riempi giochi rilasciati
    //GIOCHI: aggiustare aggiungi strike
    //aggiustare grandezza lista e il fatto che se le label sono troppo lunghe cambia la lunghezza di tutto
    //aggiustare assolutamente la lista generi che non si svuota ma solo aggiunge spunte e quindi se clicco conferma (alla quale manca un messaggio di conferma) li aggiunge tutti (quindi funziona)
    //se aggiungo un genere al db non viene aggiunto anche nella lista delle checkbox
    //PIATTAFORME: cambia true e false con si e no
    //RECENSIONI: rimuovi recensione non aggiorna le liste ma funziona
    //non funziona manco la ricerca

    public HomeAdmin(Controller controller, JFrame accediGUI, Admin admin){
        if(controller == null) throw new IllegalArgumentException("Controller passato inesistente");
        if(admin == null) throw new IllegalArgumentException("Admin passato inesistente");
        this.controller = controller;
        this.admin = admin;

        configuraInterfaccia();

        //Utenti
        associaListenerCheckBoxBannatiUtenti();
        associaListenerRicercaUtenti();
        associaListenerTabellaUtenti();
        associaListenerTabellaRecensioniUtenti();
        associaListenerPulsanteBannaUtenteUtenti();
        associaListenerPulsanteRimuoviRecensioneUtente();
        associaListenerPulsanteRimborsaUtente();

        //Sviluppatori
        associaListenerListaSviluppatori();
        associaListenerRicercaSviluppatori();
        associaListenerCheckBoxBannatiSviluppatori();
        associaListenerAggiungiStrike();
        associaListenerRimuoviStrike();

        //Giochi
        associaListenerListaGiochi();
        associaListenerRicercaGiochi();
        associaListenerAggiungiStrikeDaGioco();
        associaListenerModificaTitolo();
        associaListenerModificaCategoria();
        associaListenerModificaPegi();
        associaListenerPulsanteConfermaGeneri();

        //Generi
        associaListenerRicercaGeneri();
        associaListenerPulsanteAggiungiGenere();

        //Piattaforme
        associaListenerRicercaPiattaforme();
        associaListenerAggiungiPiattaforma();

        //Recensioni
        associaListenerRicercaRecensioni();
        associaListenerListaRecensioni();
        associaListenerPulsanteRimuoviRecensioneRecensioni();
        associaListenerPulsanteBannaUtenteRecensioni();

        //Promozioni
        associaListenerRicercaPromozioni();
        associaListenerPulsanteOrdinaPerData();
        associaListenerPulsanteAggiungiPromozione();

        associaListenerPulsanteLogout(accediGUI);

        mostraForm();
    }

    private void configuraInterfaccia(){
        adminFrame = new JFrame("HomeAdmin");
        adminFrame.setContentPane(adminPanel);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setMinimumSize(new Dimension(900, 600));

        configuraPannelloUtenti();
        configuraPannelloSviluppatori();
        configuraPannelloGiochi();
        configuraPannelloGeneri();
        configuraPannelloPiattaforme();
        configuraPannelloRecensioni();
        configuraPannelloPromozioni();

    }

    private void configuraPannelloUtenti(){
        String[] colonneUtenti = {"ID", "Nickname", "Email", "Data di Nascita", "Saldo"};
        configuraTabella(colonneUtenti, tabellaUtenti);
        filtraUtenti(); //riempio la tabella senza filtri

        String[] colonneRecensioni = {"Gioco", "Voto", "Differenza Like", "Oggetto"};
        configuraTabella(colonneRecensioni, tabellaRecensioniUtenti);

        //nascondo la quarta colonna che mi serve solo per prendere velocemente la recensione
        tabellaRecensioniUtenti.getColumnModel().getColumn(3).setMinWidth(0);
        tabellaRecensioniUtenti.getColumnModel().getColumn(3).setMaxWidth(0);
        tabellaRecensioniUtenti.getColumnModel().getColumn(3).setWidth(0);

        String[] colonneGiochi = {"Titolo", "Categoria", "PEGI", "Generi", "Piattaforma", "Prezzo", "Oggetto"};
        configuraTabella(colonneGiochi, tabellaGiochiUtenti);

        //nascondo la settima colonna che mi serve solo per prendere velocemente la fattura
        tabellaGiochiUtenti.getColumnModel().getColumn(6).setMinWidth(0);
        tabellaGiochiUtenti.getColumnModel().getColumn(6).setMaxWidth(0);
        tabellaGiochiUtenti.getColumnModel().getColumn(6).setWidth(0);

    }

    private void configuraTabella(String[] colonne, JTable tabella) {

        DefaultTableModel modelloIniziale = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //rende la tabella non editabile all'utente
            }
        };

        //assegniamo il modello vuoto alla tabella
        tabella.setModel(modelloIniziale);
    }

    //PANNELLO UTENTI

    private void filtraUtenti() {
        String testoRicerca = ricercaUtenti.getText().toLowerCase().trim();
        boolean flag = checkBoxBannatiUtenti.isSelected();

        ArrayList<Object[]> righe = new ArrayList<>();

        try{

            ArrayList<Utente> utentiFiltrati = controller.getUtentiFiltratiAdmin(testoRicerca, flag);

            for(Utente utente : utentiFiltrati){
                Object[] riga = {
                        controller.getIdUtente(utente),
                        controller.getNomeUtente(utente),
                        controller.getEmailUtente(utente),
                        controller.getDataDiNascitaUtente(utente),
                        controller.getSaldoUtente(utente)
                };
                righe.add(riga);
            }
        } catch (CampoNonValidoException e){
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        aggiornaContenutoTabella(tabellaUtenti, righe);
    }

    private void associaListenerCheckBoxBannatiUtenti(){
        checkBoxBannatiUtenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //implementato così perché volevo provare e vedere come funziona l'operatore ternario
                String testo = (checkBoxBannatiUtenti.isSelected()) ? "Sbanna" : "Banna";
                pulsanteBannaUtenteUtenti.setText(testo);
                filtraUtenti();
            }
        });
    }

    private void associaListenerRicercaUtenti(){
        ricercaUtenti.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraUtenti();
            }
        });
    }

    private void associaListenerTabellaUtenti(){
        tabellaUtenti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rigaSelezionata = tabellaUtenti.getSelectedRow();

                if (rigaSelezionata != -1) {
                    int selezione = (int) tabellaUtenti.getValueAt(rigaSelezionata, 0);
                    //Utente utente = controller.getUtenteById(selezione);
                    riempiTabellaGiochiUtente(selezione);
                    riempiTabellaRecensioniUtente(selezione);
                    descrizioneRecensioneUtenti.setText("");
                }

            }
        });
    }

    private void associaListenerTabellaRecensioniUtenti(){
        tabellaRecensioniUtenti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rigaSelezionata = tabellaRecensioniUtenti.getSelectedRow();

                if(rigaSelezionata != -1){

                    Recensione recensione = (Recensione) tabellaRecensioniUtenti.getModel().getValueAt(rigaSelezionata, 3);
                    descrizioneRecensioneUtenti.setText(controller.getDescrizioneRecensione(recensione));

                }
                //else || non dovrebbe mai succedere
                    //JOptionPane.showMessageDialog(adminFrame, "Seleziona una recensione");

            }
        });
    }

    private void riempiTabellaGiochiUtente(int idUtente){
        ArrayList<Object[]> righe = new ArrayList<>();

        try {
            for(Fattura gioco : controller.getLibreriaUtente(idUtente)){
                Object[] riga = {
                        controller.getTitoloDaFattura(gioco),
                        controller.getCategoriaDaFattura(gioco),
                        controller.getPegiDaFattura(gioco),
                        controller.getGeneriDaFattura(gioco), //DA FARE formattare meglio la lista quando sarà funzionante
                        controller.getPiattaformaDaFattura(gioco),
                        controller.getPrezzoAcquistoDaFattura(gioco),
                        gioco
                };
                righe.add(riga);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        aggiornaContenutoTabella(tabellaGiochiUtenti, righe);
    }

    private void riempiTabellaRecensioniUtente(int idUtente){
        ArrayList<Object[]> righe = new ArrayList<>();

        try {
            for(Recensione recensione : controller.getListaRecensioniUtente(idUtente)){ //DA FARE implementazione
                Object[] riga = {
                        controller.getTitoloDaFattura(recensione.getFattura()),
                        controller.getVotoRecensione(recensione),
                        controller.getDifferenzaLikeRecensione(recensione),
                        recensione
                };
                righe.add(riga);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        aggiornaContenutoTabella(tabellaRecensioniUtenti, righe);
    }

    private void associaListenerPulsanteBannaUtenteUtenti(){
        pulsanteBannaUtenteUtenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaUtenti.getSelectedRow();

                if(rigaSelezionata != -1){
                    int risposta = JOptionPane.showConfirmDialog(adminFrame,"Sicuro di voler " + pulsanteBannaUtenteUtenti.getText().toLowerCase() + "re l'utente " + tabellaUtenti.getValueAt(rigaSelezionata, 1) + "?",
                            "Ban", JOptionPane.YES_NO_OPTION);

                    if(risposta == JOptionPane.YES_OPTION) {
                        try {

                            controller.invertiStatoBan((int) tabellaUtenti.getValueAt(rigaSelezionata, 0)); //passa id dell'utente selezionato e banna o sbanna
                            filtraUtenti();

                        } catch (CampoNonValidoException ex) {

                            JOptionPane.showMessageDialog(adminFrame, ex.getMessage());

                        }
                    }

                } else JOptionPane.showMessageDialog(adminFrame, "Nessun utente selezionato", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void associaListenerPulsanteRimuoviRecensioneUtente(){
        pulsanteRimuoviRecensioneUtenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaRecensioniUtenti.getSelectedRow();

                if(rigaSelezionata != -1){

                    Recensione recensione = (Recensione) tabellaRecensioniUtenti.getModel().getValueAt(rigaSelezionata, 3);
                    int idUtente = controller.getIdUtente(controller.getUtenteDaFattura(controller.getFatturaDaRecensione(recensione)));

                    try {

                        controller.rimuoviRecensioneSelezionataDaFattura(controller.getFatturaDaRecensione(recensione));
                        JOptionPane.showMessageDialog(adminFrame, "Recensione rimossa con successo");
                        riempiTabellaRecensioniUtente(idUtente);

                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(adminFrame, ex.getMessage());
                    }

                } else
                    JOptionPane.showMessageDialog(adminFrame, "Seleziona prima una recensione");

            }
        });
    }

    private void associaListenerPulsanteRimborsaUtente(){
        pulsanteRimborsa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaGiochiUtenti.getSelectedRow();

                if(rigaSelezionata != -1){

                    Fattura fattura = (Fattura) tabellaGiochiUtenti.getModel().getValueAt(rigaSelezionata, 6);

                    int risposta = JOptionPane.showConfirmDialog(adminFrame, "Confermi il rimborso di questo gioco?", "Conferma", JOptionPane.YES_NO_OPTION);

                    if(risposta == JOptionPane.YES_OPTION){
                        try{

                            Utente utente = controller.getUtenteDaFattura(fattura);

                            controller.effettuaRimborso(fattura, utente);
                            riempiTabellaGiochiUtente(controller.getIdUtente(controller.getUtenteDaFattura(fattura)));
                            filtraUtenti();

                        } catch (CampoNonValidoException ex) {
                            JOptionPane.showMessageDialog(adminFrame, ex.getMessage());
                        }
                    }
                } else
                    JOptionPane.showMessageDialog(adminFrame, "Seleziona un gioco da rimborsare");
            }
        });
    }

    //PANNELLO SVILUPPATORI

    private void configuraPannelloSviluppatori(){
        filtraSviluppatori(); //riempio lista sviluppatori

        String[] colonneSviluppatori = {"Titolo", "Categoria", "PEGI", "Generi", "Piattaforme"};
        configuraTabella(colonneSviluppatori, tabellaGiochiSviluppatori);
    }

    private void associaListenerListaSviluppatori(){
        listaSviluppatori.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){ //serve a evitare che la funzione parta 2 volte (click e rilascio)

                    Sviluppatore sviluppatoreSelezionato = (Sviluppatore) listaSviluppatori.getSelectedValue();

                    if(sviluppatoreSelezionato != null){
                        descrizioneSviluppatori.setText(controller.getDescrizioneSviluppatore(sviluppatoreSelezionato));
                        aggiornaStrike(sviluppatoreSelezionato);

                        riempiTabellaGiochiSviluppatore(sviluppatoreSelezionato);

                    } else
                        descrizioneSviluppatori.setText("");
                }
            }
        });
    }

    private void riempiTabellaGiochiSviluppatore(Sviluppatore sviluppatore){
        ArrayList<Object[]> righe = new ArrayList<>();

        try {
            for(EdizioneGioco gioco : controller.getListaEdizioniSviluppatore(sviluppatore)){
                Object[] riga = {
                        controller.getTitoloDaEdizioneGioco(gioco),
                        controller.getCategoriaDaEdizioneGioco(gioco),
                        controller.getPegiDaEdizioneGioco(gioco),
                        controller.getGeneriDaEdizioneGioco(gioco), //DA FARE formattare meglio la lista quando sarà funzionante
                        controller.getPiattaformaDaEdizioneGioco(gioco)
                };
                righe.add(riga);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        aggiornaContenutoTabella(tabellaGiochiSviluppatori, righe);
    }

    private void associaListenerRicercaSviluppatori(){
        ricercaSviluppatori.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraSviluppatori();
            }
        });
    }

    private void associaListenerCheckBoxBannatiSviluppatori(){
        checkBoxBannatiSviluppatori.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraSviluppatori();
            }
        });
    }

    private void filtraSviluppatori() {
        String testoRicerca = ricercaSviluppatori.getText().toLowerCase().trim();

        DefaultListModel<Sviluppatore> modelloSviluppatori = new DefaultListModel<>();

        //filtro in base alla checkBox e alla barra di ricerca
        boolean flag = checkBoxBannatiSviluppatori.isSelected();
        try {
            for(Sviluppatore sviluppatore : controller.getListaSviluppatoriFiltrati(testoRicerca)){
                if(controller.isSviluppatoreBannato(sviluppatore) == flag)
                    modelloSviluppatori.addElement(sviluppatore);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        listaSviluppatori.setModel(modelloSviluppatori);

    }

    private void associaListenerAggiungiStrike(){
        pulsanteAggiungiStrike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sviluppatore sviluppatoreSelezionato = (Sviluppatore) listaSviluppatori.getSelectedValue();

                if(sviluppatoreSelezionato != null){
                    try {

                        controller.addStrikeSviluppatore(sviluppatoreSelezionato);
                        aggiornaStrike(sviluppatoreSelezionato);
                        filtraSviluppatori();

                    } catch (CampoNonValidoException ex) {

                        JOptionPane.showMessageDialog(adminFrame, ex.getMessage());

                    }
                } else
                    JOptionPane.showMessageDialog(adminFrame, "Nessuno sviluppatore selezionato!");
            }
        });
    }

    private void associaListenerRimuoviStrike(){
        pulsanteRimuoviStrike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sviluppatore sviluppatoreSelezionato = (Sviluppatore) listaSviluppatori.getSelectedValue();

                if(sviluppatoreSelezionato != null){
                    try {

                        controller.removeStrikeSviluppatore(sviluppatoreSelezionato);
                        aggiornaStrike(sviluppatoreSelezionato);
                        filtraSviluppatori();

                    } catch (CampoNonValidoException ex) {

                        JOptionPane.showMessageDialog(adminFrame, ex.getMessage());

                    }
                } else
                    JOptionPane.showMessageDialog(adminFrame, "Nessuno sviluppatore selezionato!");
            }
        });
    }

    private void aggiornaStrike(Sviluppatore sviluppatore){
        nStrike.setText("Strike: " + controller.getStrikeSviluppatore(sviluppatore));
    }

    //PANNELLO GIOCHI

    private void configuraPannelloGiochi(){
        inizializzaCheckBoxGeneri();
        filtraGiochi();
        svuotaDatiGioco();
    }

    private void inizializzaCheckBoxGeneri(){
        pannelloGeneriGioco.removeAll();
        pannelloGeneriGioco.setLayout(new BoxLayout(pannelloGeneriGioco, BoxLayout.Y_AXIS));

        try {
            for(Genere genere : controller.getGeneri()){
                JCheckBox checkBox = new JCheckBox(controller.getNomeGenere(genere));
                checkBox.setSelected(false);
                pannelloGeneriGioco.add(checkBox);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        pannelloGeneriGioco.revalidate();
        pannelloGeneriGioco.repaint();
    }

    private void filtraGiochi(){
        String testoRicerca = ricercaGiochi.getText().toLowerCase().trim();

        DefaultListModel<Gioco> modelloGiochi = new DefaultListModel<>();

        try {
            ArrayList<Gioco> lista = controller.getGiochiFiltrati(testoRicerca);

            for (Gioco gioco : lista) {
                modelloGiochi.addElement(gioco);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        listaGiochi.setModel(modelloGiochi);

    }

    private void associaListenerListaGiochi(){
        listaGiochi.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){ //serve a evitare che la funzione parta 2 volte (click e rilascio)

                    Gioco giocoSelezionato = (Gioco) listaGiochi.getSelectedValue();

                    if(giocoSelezionato != null){

                        titoloGioco.setText(controller.getTitoloGioco(giocoSelezionato));
                        categoriaGioco.setText(String.valueOf(controller.getCategoriaGioco(giocoSelezionato)));
                        pegiGioco.setText(String.valueOf(controller.getPegiGioco(giocoSelezionato)));
                        popolaListaGeneriGioco(giocoSelezionato);

                    } else
                        svuotaDatiGioco();
                }
            }
        });
    }

    private void popolaListaGeneriGioco(Gioco gioco){
        try {
            ArrayList<Genere> generiGioco = controller.getGeneriDaGioco(gioco);

            ArrayList<String> nomiGeneriGioco = new ArrayList<>();
            for(Genere genere : generiGioco) {
                nomiGeneriGioco.add(controller.getNomeGenere(genere));
            }

            for (Component componente : pannelloGeneriGioco.getComponents()) {

                if (componente instanceof JCheckBox) {
                    JCheckBox casella = (JCheckBox) componente;

                    if (nomiGeneriGioco.contains(casella.getText()))
                        casella.setSelected(true);
                }
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }
    }

    private void svuotaDatiGioco(){
        titoloGioco.setText("");
        categoriaGioco.setText("");
        pegiGioco.setText("");

        //svuoto la lista generi
        for (Component componente : pannelloGeneriGioco.getComponents()) {

            // Verifichiamo che il componente sia una CheckBox
            if (componente instanceof JCheckBox) {
                JCheckBox casella = (JCheckBox) componente;

                // Togliamo la spunta
                casella.setSelected(false);
            }
        }
    }

    private void associaListenerRicercaGiochi(){
        ricercaGiochi.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraGiochi();
            }
        });
    }

    private void associaListenerAggiungiStrikeDaGioco(){
        pulsanteAggiungiStrikeGiochi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gioco giocoSelezionato = (Gioco) listaGiochi.getSelectedValue();

                if(giocoSelezionato != null){
                    try {

                        controller.addStrikeSviluppatoreDaGioco(giocoSelezionato);

                    } catch (CampoNonValidoException ex) {

                        JOptionPane.showMessageDialog(adminFrame, ex.getMessage());

                    }
                } else
                    JOptionPane.showMessageDialog(adminFrame, "Nessuno gioco selezionato!");
            }
        });
    }

    private void associaListenerModificaTitolo(){
        pulsanteModificaTitolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gioco giocoSelezionato = (Gioco) listaGiochi.getSelectedValue();

                if(giocoSelezionato != null){
                    String nuovoTitolo = JOptionPane.showInputDialog(adminFrame,
                            "Inserisci il nuovo titolo del gioco:",
                            "Modifica Titolo",
                            JOptionPane.PLAIN_MESSAGE);

                    if(nuovoTitolo != null && !nuovoTitolo.trim().isEmpty()){
                        try {

                            controller.updateTitoloGioco(giocoSelezionato, nuovoTitolo);
                            titoloGioco.setText(nuovoTitolo);
                            filtraGiochi();

                        } catch (CampoNonValidoException ex) {
                            JOptionPane.showMessageDialog(adminFrame, ex.getMessage());
                        }
                    }
                } else
                    JOptionPane.showMessageDialog(adminFrame, "Nessuno gioco selezionato!");
            }
        });
    }

    private void associaListenerModificaCategoria() {
        pulsanteModificaCategoria.addActionListener(e -> {

            Gioco giocoSelezionato = (Gioco) listaGiochi.getSelectedValue();

            if(giocoSelezionato != null){

                Categoria[] arrayCategorie = controller.getCategorie().toArray(new Categoria[0]);

                JComboBox<Categoria> comboCategoria = new JComboBox<>(arrayCategorie);

                int risposta = JOptionPane.showConfirmDialog(adminFrame,
                        comboCategoria,
                        "Seleziona la nuova categoria",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (risposta == JOptionPane.OK_OPTION) {

                    Categoria nuovaCategoria = (Categoria) comboCategoria.getSelectedItem();

                    try {

                        controller.updateCategoriaGioco(giocoSelezionato, nuovaCategoria);
                        categoriaGioco.setText(nuovaCategoria.name());

                    } catch (CampoNonValidoException ex) {

                        JOptionPane.showMessageDialog(adminFrame, ex.getMessage());

                    }
                }
            } else
                JOptionPane.showMessageDialog(adminFrame, "Nessuno gioco selezionato!");
        });
    }

    private void associaListenerModificaPegi() {
        pulsanteModificaPegi.addActionListener(e -> {

            Gioco giocoSelezionato = (Gioco) listaGiochi.getSelectedValue();

            if(giocoSelezionato != null){

                SpinnerNumberModel modelloSpinner = new SpinnerNumberModel(controller.getPegiGioco(giocoSelezionato), 3, 18, 1);
                JSpinner spinnerPegi = new JSpinner(modelloSpinner);

                int risposta = JOptionPane.showConfirmDialog(adminFrame,
                        spinnerPegi, //passo lo spinner
                        "Imposta il nuovo limite PEGI",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (risposta == JOptionPane.OK_OPTION) {
                    int nuovoPegi = (int) spinnerPegi.getValue();

                    try{

                        controller.updatePegiGioco(giocoSelezionato, nuovoPegi);
                        pegiGioco.setText(String.valueOf(nuovoPegi));

                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(adminFrame, ex.getMessage());
                    }
                }
            } else
                JOptionPane.showMessageDialog(adminFrame, "Nessuno gioco selezionato!");
        });
    }

    private void associaListenerPulsanteConfermaGeneri() {
        pulsanteConfermaGeneri.addActionListener(e -> {

            Gioco giocoSelezionato = (Gioco) listaGiochi.getSelectedValue();

            if (giocoSelezionato == null) {
                JOptionPane.showMessageDialog(adminFrame, "Seleziona un gioco dalla lista prima di confermare le modifiche!");
                return; //interrompe l'esecuzione del metodo
            }

            ArrayList<String> nomiGeneriSpuntati = new ArrayList<>();

            for (Component componente : pannelloGeneriGioco.getComponents()) {

                if (componente instanceof JCheckBox) {
                    JCheckBox casella = (JCheckBox) componente;

                    if (casella.isSelected()) {
                        nomiGeneriSpuntati.add(casella.getText());
                    }
                }
            }

            try {

                controller.updateGeneriGioco(giocoSelezionato, controller.getGeneriDaListaNomi(nomiGeneriSpuntati));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(adminFrame, ex.getMessage());
            }
        });
    }

    //PANNELLO GENERI

    private void configuraPannelloGeneri(){
        filtraGeneri();
        campoNomeGenere.setText("");
    }

    private void associaListenerRicercaGeneri(){
        ricercaGeneri.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraGeneri();
            }
        });
    }

    private void filtraGeneri(){
        String testoRicerca = ricercaGeneri.getText().toLowerCase().trim();

        DefaultListModel<Genere> modelloGeneri = new DefaultListModel<>();

        try {
            ArrayList<Genere> lista = controller.getGeneriFiltrati(testoRicerca);

            for (Genere genere : lista) {
                modelloGeneri.addElement(genere);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        listaGeneri.setModel(modelloGeneri);
    }

    private void associaListenerPulsanteAggiungiGenere(){
        pulsanteAggiungiGenere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    controller.createGenere(campoNomeGenere.getText());
                    filtraGeneri();

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(adminFrame, ex.getMessage());
                }
            }
        });
    }

    //PANNELLO PIATTAFORME
    private void configuraPannelloPiattaforme(){
        String[] colonnePiattaforme = {"Nome", "Produttore", "Portabilità"};
        configuraTabella(colonnePiattaforme, tabellaPiattaforme);
        filtraPiattaforme();

        //DA FARE valutare se fare o no questo metodo per essere user friendly
        svuotaDatiPiattaforma();
    }

    private void filtraPiattaforme(){
        String testoRicerca = ricercaPiattaforme.getText().toLowerCase().trim();

        ArrayList<Object[]> righe = new ArrayList<>();

        try {
            for(PiattaformaDiGioco piattaforma : controller.getPiattaformeFiltrate(testoRicerca)){
                Object[] riga = {
                        controller.getNomePiattaforma(piattaforma),
                        controller.getProduttorePiattaforma(piattaforma),
                        controller.isPortabile(piattaforma)
                };
                righe.add(riga);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        aggiornaContenutoTabella(tabellaPiattaforme, righe);
    }

    private void svuotaDatiPiattaforma(){
        campoNomePiattaforma.setText("");
        campoProduttore.setText("");
        checkBoxPortabilita.setSelected(false);
    }

    private void associaListenerRicercaPiattaforme(){
        ricercaPiattaforme.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraPiattaforme();
            }
        });
    }

    private void associaListenerAggiungiPiattaforma(){
        pulsanteAggiungiPiattaforma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    controller.createPiattaforma(campoNomePiattaforma.getText(), campoProduttore.getText(), checkBoxPortabilita.isSelected());
                    filtraPiattaforme();

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(adminFrame, ex.getMessage());
                }
            }
        });
    }

    //PANNELLO RECENSIONI
    private void configuraPannelloRecensioni(){
        descrizioneRecensioneRecensioni.setText("");
        String[] colonneRecensioni = {"Autore", "Gioco", "Voto", "DifferenzaLike", "Oggetto"};
        configuraTabella(colonneRecensioni, tabellaRecensioni);

        //nascondo la colonna che serve solo per prenderla con facilità
        tabellaRecensioni.getColumnModel().getColumn(4).setMinWidth(0);
        tabellaRecensioni.getColumnModel().getColumn(4).setMaxWidth(0);
        tabellaRecensioni.getColumnModel().getColumn(4).setWidth(0);

        filtraRecensioni();
    }

    private void filtraRecensioni(){
        String testoRicerca = ricercaRecensioni.getText().toLowerCase().trim();

        ArrayList<Object[]> righe = new ArrayList<>();

        try{
            for(Recensione recensione : controller.getRecensioniFiltrateAdmin(testoRicerca)){
                Fattura fattura = controller.getFatturaDaRecensione(recensione);

                Object[] riga = {
                        controller.getNomeUtenteDaFattura(fattura),
                        controller.getTitoloDaFattura(fattura),
                        controller.getVotoRecensione(recensione),
                        controller.getDifferenzaLikeRecensione(recensione),
                        recensione
                };
                righe.add(riga);
            }
        } catch (CampoNonValidoException e){
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        aggiornaContenutoTabella(tabellaRecensioni, righe);
    }

    private void associaListenerRicercaRecensioni(){
        ricercaRecensioni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraRecensioni();
            }
        });
    }

    private void associaListenerListaRecensioni(){
        tabellaRecensioni.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rigaSelezionata = tabellaRecensioni.getSelectedRow();

                if(rigaSelezionata != -1){
                    Recensione recensioneSelezionata = (Recensione) tabellaRecensioni.getValueAt(rigaSelezionata, 4);

                    descrizioneRecensioneRecensioni.setText(controller.getDescrizioneRecensione(recensioneSelezionata));
                }
            }
        });
    }

    private void associaListenerPulsanteRimuoviRecensioneRecensioni(){
        pulsanteRimuoviRecensioneRecensioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaRecensioni.getSelectedRow();

                if(rigaSelezionata != -1){

                    Recensione recensione = (Recensione) tabellaRecensioni.getModel().getValueAt(rigaSelezionata, 4);

                    try{

                        controller.rimuoviRecensioneSelezionataDaFattura(controller.getFatturaDaRecensione(recensione));

                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(adminFrame, ex.getMessage());
                    }
                } else
                    JOptionPane.showMessageDialog(adminFrame, "Seleziona prima una recensione");
            }
        });
    }

    private void associaListenerPulsanteBannaUtenteRecensioni(){
        pulsanteBannaUtenteRecensioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaRecensioni.getSelectedRow();

                if(rigaSelezionata != -1){
                    int risposta = JOptionPane.showConfirmDialog(adminFrame, "Sicuro di voler bannare l'utente " + tabellaRecensioni.getValueAt(rigaSelezionata, 0) + "?",
                            "Ban", JOptionPane.YES_NO_OPTION);

                    if(risposta == JOptionPane.YES_OPTION) {
                        try{

                            Fattura fattura = controller.getFatturaDaRecensione((Recensione) tabellaRecensioni.getModel().getValueAt(rigaSelezionata, 4));
                            Utente utente = controller.getUtenteDaFattura(fattura);
                            controller.setBannatoUtente(utente);

                        } catch (CampoNonValidoException ex) {
                            JOptionPane.showMessageDialog(adminFrame, ex.getMessage());
                        }
                    }
                }
            }
        });
    }

    //PANNELLO PROMOZIONI
    private void configuraPannelloPromozioni(){
        String[] colonnePromozioni = {"Nome", "DataInizio", "DataFine"};
        configuraTabella(colonnePromozioni, tabellaPromozioni);

        applicaMascheraData(campoDataInizio);
        applicaMascheraData(campoDataFine);


        filtraPromozioni();
    }

    private void applicaMascheraData(JFormattedTextField campoData) {
        try {
            // Il carattere '#' accetta solo numeri, le barre vengono messe da sole
            javax.swing.text.MaskFormatter mascheraData = new javax.swing.text.MaskFormatter("##/##/####");

            // Mostra i trattini bassi dove l'utente deve ancora digitare
            mascheraData.setPlaceholderCharacter('_');

            // Applica la maschera al campo formattato
            mascheraData.install(campoData);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    private void filtraPromozioni(){
        String testoRicerca = ricercaPromozioni.getText().toLowerCase().trim();

        ArrayList<Object[]> righe = new ArrayList<>();

        try {
            //il controller restituirà la lista filtrata per testo e ordinata (o meno) per data
            for (Promozione promozione : controller.getPromozioniFiltrateAdmin(testoRicerca, ordinaPerDataAttivo)) {
                Object[] riga = {
                        controller.getNomePromozione(promozione),
                        controller.getDataInizioPromozione(promozione),
                        controller.getDataFinePromozione(promozione)
                };
                righe.add(riga);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(adminFrame, e.getMessage());
        }

        aggiornaContenutoTabella(tabellaPromozioni, righe);
    }

    private void associaListenerRicercaPromozioni(){
        ricercaPromozioni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraPromozioni();
            }
        });
    }

    private void associaListenerPulsanteOrdinaPerData(){
        pulsanteOrdinaPerData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //inverte lo stato dell'ordinamento a ogni click
                ordinaPerDataAttivo = !ordinaPerDataAttivo;

                filtraPromozioni();
            }
        });
    }

    private void associaListenerPulsanteAggiungiPromozione() {
        pulsanteAggiungiPromozione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNomePromozione.getText();
                String dataInizio = campoDataInizio.getText();
                String dataFine = campoDataFine.getText();

                try {
                    controller.createPromozione(nome, dataInizio, dataFine);

                    campoNomePromozione.setText("");
                    campoDataInizio.setText("");
                    campoDataFine.setText("");

                    filtraPromozioni();

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(adminFrame, ex.getMessage());
                }
            }
        });
    }

    private void associaListenerPulsanteLogout(JFrame accediGUI){
        pulsanteLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accediGUI.setVisible(true);
                adminFrame.dispose();
            }
        });
    }

    private void aggiornaContenutoTabella(JTable tabella, ArrayList<Object[]> righe){
        //prendiamo il modello già esistente della tabella facendo il cast
        DefaultTableModel model = (DefaultTableModel) tabella.getModel();

        //cancella tutte le righe vecchie mantenendo intatte le colonne
        model.setRowCount(0);

        for(Object[] riga : righe) model.addRow(riga);
    }

    private void mostraForm(){
        adminFrame.pack();
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

}
