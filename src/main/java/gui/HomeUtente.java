package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class HomeUtente {
    private JPanel homeUtentePanel;
    private JTabbedPane tabbedPane;
    private JPanel catalogo;
    private JPanel profilo;
    private JPanel libreria;
    private JTextField ricercaLibreria;
    private JList listaLibreria;
    private JToolBar toolBarLibreria;
    private JLabel edizioneLibreria;
    private JLabel piattaformaDiGiocoLibreria;
    private JLabel dataRilascioLibreria;
    private JLabel prezzoAcquistoLibreria;
    private JLabel categoriaLibreria;
    private JLabel pegiLibreria;
    private JLabel keyLibreria;
    private JLabel dataAcquistoLibreria;
    private JButton pulsanteResetFiltri;
    private JComboBox genereFiltro;
    private JButton dataFiltro;
    private JButton prezzoAcquistoFiltro;
    private JComboBox categoriaFiltro;
    private JComboBox pegiFiltro;
    private JButton dataAcquistoFiltro;
    private JButton pulsanteRecensione;
    private JButton pulsanteCopiaKey;
    private JScrollPane scrollPaneLibreria;
    private JButton pulsanteAggiungiSaldo;
    private JButton pulsanteModificaInformazioni;
    private JList listaUtente;
    private JTextField ricercaUtenti;
    private JCheckBox checkBoxAmici;
    private JButton pulsanteVisualizzaRecensioni;
    private JList listaSviluppatori;
    private JTextField ricercaSviluppatori;
    private JCheckBox checkBoxSeguiti;
    private JButton pulsanteSegui;
    private JButton pulsanteAggiungiAmicoSelezionato;
    private JLabel testoDataCreazioneAccount;
    private JLabel testoBannato;
    private JLabel testoNome;
    private JLabel testoGenere;
    private JLabel testoEmail;
    private JLabel testoDataDiNascita;
    private JLabel testoNumeroGiochiAcquistati;
    private JLabel testoNumeroRecensioniRilasciate;
    private JLabel testoSaldo;
    private JScrollPane scrollPaneListaAmici;
    private JScrollPane scrollPaneListaSviluppatori;
    private JLabel testoGiochiRilasciati;
    private JLabel testoGiocoPiuVenduto;
    private JLabel testoGiochiAcquistatiUtenteSelezionato;
    private JLabel testoNumeroRecensioniUtenteSelezionato;
    private JLabel testoGenereUtenteSelezionato;
    private JLabel testoBannatoUtente;
    private JToolBar toolBarSviluppatore;
    private JToolBar toolBarAmico;
    private JLabel testoProfilo;
    private JLabel testoCommunity;
    private JPanel carrello;
    private JList listaCatalogo;
    private JButton pulsanteAggiungiAlCarrello;
    private JComboBox piattaformaFiltroCatalogo;
    private JSlider sliderPrezzoCatalogo;
    private JComboBox genereFiltroCatalogo;
    private JComboBox pegiFiltroCatalogo;
    private JButton pulsanteDataDiRilascioFiltroCatalogo;
    private JComboBox categoriaFiltroCatalogo;
    private JCheckBox checkBoxInPromozione;
    private JCheckBox checkBoXSviluppatori;
    private JList listaRecensioniCatalogo;
    private JTextField ricercaCatalogo;
    private JLabel pegiCatalogo;
    private JLabel genereCatalogo;
    private JLabel piattaformaCatalogo;
    private JLabel prezzoCatalogo;
    private JLabel categoriaCatalogo;
    private JTextArea descrizioneRecensioneCatalogo;
    private JButton pulsanteDislike;
    private JButton pulsanteLike;
    private JLabel descrizioneCatalogo;
    private JLabel votoCatalogo;
    private JLabel recensioniCatalogo;
    private JToolBar toolBarCatalogo;
    private JLabel prezzoFiltroCatalogo;
    private JLabel dataDiRilascioCatalogo;
    private JLabel sviluppatoreCatalogo;
    private JLabel valutazioneRecensioneCatalogo;
    private JLabel generiLibreria;
    private JLabel sviluppatoreLibreria;
    private JTextArea descrizioneSviluppatoreProfilo;
    private JButton pulsanteRimuoviAmicoSelezionato;
    private JButton pulsanteLogout;
    private JTable tabellaGiochiCarrello;
    private JButton pulsanteRimuoviCarrello;
    private JLabel testoTotaleCarrello;
    private JButton pulsanteAcquista;
    private JLabel testoMediaVoti;
    private JButton pulsanteSmettiDiSeguire;

    public JFrame homeUtenteFrame;
    private Controller controller;
    private Utente utenteLoggato;

    private int statoDataRilascio = 0;
    private int statoPrezzoFiltro = 0;
    private int statoDataAcquisto = 0;

    public HomeUtente(Controller controller, JFrame accediGUI, Utente accountLoggato) {
        this.controller = controller;
        this.utenteLoggato = accountLoggato;

        //metodi base per il funzionamento
        configuraInterfaccia();
        associaListenerLogout(accediGUI);
        associaListenerTabbedPane();

        //Catalogo

        //Libreria

        associaListenerRilasciaRecensione();

        associaListenerListaLibreria();
        associaListenerCopiaKey();
        associaListenerRicercaLibreria();
        associaListenerDataFiltro();
        associaListenerPrezzoAcquistoFiltro();
        associaListenerDataAcquistoFiltro();
        associaListenerComboBoxGenere();
        associaListenerComboBoxCategoria();
        associaListenerComboBoxPegi();
        associaListenerPulsanteResetFiltro();

        //Profilo

        associaListenerModificaInformazioni();
        associaListenerVisualizzaRecensioni();
        associaListenerAggiungiSaldo();


        associaListenerListaSviluppatori();
        associaListenerRicercaSviluppatori();
        associaListenerCheckBoxSviluppatori();
        associaListenerPulsanteSegui();
        associaListenerPulsanteSmettiDiSeguire();

        associaListenerListaUtenti();
        associaListenerRicercaUtenti();
        associaListenerCheckBoxUtenti();
        associaListenerPulsanteAggiungiAmicoSelezionato();
        associaListenerPulsanteRimuoviAmicoSelezionato();

        //Carrello
        associaListenerRimuoviCarrello();
        associaListenerAcquista();

        //fine

        mostraForm();
    }

    //metodi base per il funzionamento
    private void configuraInterfaccia() {
        homeUtenteFrame = new JFrame("Home Utente");
        homeUtenteFrame.setContentPane(homeUtentePanel);
        homeUtenteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configuraInterfacciaCatalogo();
        configuraInterfacciaLibreria();
        configuraInterfacciaProfilo();
        configuraInterfacciaCarrello();
    }

    private void associaListenerLogout(JFrame accediGUI) {
        pulsanteLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta = JOptionPane.showConfirmDialog(homeUtenteFrame, "Vuoi uscire?", "Logout", JOptionPane.YES_NO_OPTION);
                if (risposta == JOptionPane.YES_OPTION) {
                    homeUtenteFrame.dispose();
                    accediGUI.setVisible(true);
                }
                //else non fa nulla
            }
        });
    }

    private void associaListenerTabbedPane() {
        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                //configuraInterfacciaCatalogo();
                configuraInterfacciaLibreria();
                configuraInterfacciaProfilo();
                configuraInterfacciaCarrello();
            }
        });
    }

    //Libreria

    private void associaListenerRilasciaRecensione() {
        pulsanteRecensione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Recensione recensione = new Recensione(controller, HomeUtente.this, utenteLoggato, (Fattura) listaLibreria.getSelectedValue());
            }
        });
    }

    private void associaListenerListaLibreria(){
        listaLibreria.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Fattura fatturaSelezionata = (Fattura) listaLibreria.getSelectedValue();

                if (fatturaSelezionata != null){
                    edizioneLibreria.setText("Titolo: " + controller.getTitoloDaFattura(fatturaSelezionata));
                    piattaformaDiGiocoLibreria.setText("Piattaforma: "+ controller.getPiattaformaDaFattura(fatturaSelezionata));
                    dataRilascioLibreria.setText("Data di rilascio: " + controller.getDataRilascioDaFattura(fatturaSelezionata));
                    categoriaLibreria.setText("Categoria: " + controller.getCategoriaDaFattura(fatturaSelezionata));
                    pegiLibreria.setText("Pegi: " + controller.getPegiDaFattura(fatturaSelezionata));
                    generiLibreria.setText("Generi: " + controller.getGeneriDaFattura(fatturaSelezionata));
                    sviluppatoreLibreria.setText("Sviluppatore: " + controller.getSviluppatoreDaFattura(fatturaSelezionata));

                    dataAcquistoLibreria.setText("Data d'acquisto: " + String.valueOf(fatturaSelezionata.getDataAcquisto()));
                    keyLibreria.setText(fatturaSelezionata.getKey());
                    prezzoAcquistoLibreria.setText("Prezzo: " + String.valueOf(fatturaSelezionata.getPrezzoAcquisto())+"€");

                    pulsanteCopiaKey.setEnabled(true);
                    pulsanteRecensione.setEnabled(true);
                }

            }
        });
    }

    private void associaListenerCopiaKey() {
        pulsanteCopiaKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyDaCopiare = keyLibreria.getText();
                //l'ho trovato su internet. Permette di copiare un testo nella clipboard di Windows mac e linux)
                java.awt.datatransfer.StringSelection selezione = new java.awt.datatransfer.StringSelection(keyDaCopiare);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selezione, null);
            }
        });
    }

    private void filtraLibreria() {
        String testoRicerca = ricercaLibreria.getText().toLowerCase().trim();
        DefaultListModel<Fattura> modelloFiltrato = new DefaultListModel<>();
        ArrayList<Fattura> listaPartenza = utenteLoggato.getGiochiAcquistati();

        ArrayList<Fattura> listaFiltrata = new ArrayList<>();

        for (Fattura f : listaPartenza) {
            Gioco giocoBase = controller.getGiocoDaFattura(f); //fatto solo per non scrivere sempre get gioco get gioco

            if (giocoBase.getTitolo().toLowerCase().contains(testoRicerca) &&
                    (genereFiltro.getSelectedIndex() == -1 || (giocoBase.getGeneri() != null && giocoBase.getGeneri().contains(genereFiltro.getSelectedItem()))) && //Controllo se il genere della combobox é contenuto nei generi del gioco
                    (categoriaFiltro.getSelectedIndex() == -1 || giocoBase.getCategoria().equals(categoriaFiltro.getSelectedItem())) && //controllo che la categoria selezionata sia uguale al gioco
                    (pegiFiltro.getSelectedIndex() == -1 || String.valueOf(giocoBase.getPegi()).equals(pegiFiltro.getSelectedItem().toString()))) { //controllo ce il pegi sia uguale

                listaFiltrata.add(f); // Se é tutto apposto filtro
            }
        }

        if (statoDataRilascio == 1){
            listaFiltrata.sort((f1, f2) -> f1.getGioco().getDataRilascio().compareTo(f2.getGioco().getDataRilascio()));
        }else if (statoDataRilascio == 2){
            listaFiltrata.sort((f1,f2) -> f2.getGioco().getDataRilascio().compareTo(f1.getGioco().getDataRilascio()));
        }

        if (statoPrezzoFiltro == 1){
            listaFiltrata.sort((f1,f2) -> Integer.compare(f1.getPrezzoAcquisto(), f2.getPrezzoAcquisto()));
        }else if (statoPrezzoFiltro == 2){
            listaFiltrata.sort((f1,f2) -> Integer.compare(f2.getPrezzoAcquisto(), f1.getPrezzoAcquisto()));
        }

        if (statoDataAcquisto == 1){
            listaFiltrata.sort((f1,f2) -> f1.getDataAcquisto().compareTo(f2.getDataAcquisto()));
        }else if(statoDataAcquisto == 2){
            listaFiltrata.sort((f1,f2) -> f2.getDataAcquisto().compareTo(f1.getDataAcquisto()));
        }

        for (Fattura f : listaFiltrata) {
            modelloFiltrato.addElement(f);
        }

        listaLibreria.setModel(modelloFiltrato);
    }

    private void associaListenerRicercaLibreria(){
        ricercaLibreria.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraLibreria();
            }
        });
    }

    private void associaListenerDataFiltro(){
        dataFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statoPrezzoFiltro = 0;
                prezzoAcquistoFiltro.setText("Prezzo");
                statoDataAcquisto = 0;
                dataAcquistoFiltro.setText("DataAcquisto");

                statoDataRilascio = statoDataRilascio + 1;
                if (statoDataRilascio == 3){
                    statoDataRilascio = 0;
                }

                if (statoDataRilascio == 0){
                    dataFiltro.setText("DataRilascio");
                } else if (statoDataRilascio == 1){
                    dataFiltro.setText("DataRilascio ↑");
                } else if (statoDataRilascio == 2){
                    dataFiltro.setText("DataRilascio ↓");
                }

                filtraLibreria();
            }
        });
    }
    private void associaListenerPrezzoAcquistoFiltro(){
        prezzoAcquistoFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statoDataRilascio = 0;
                dataFiltro.setText("DataRilascio");
                statoDataAcquisto = 0;
                dataAcquistoFiltro.setText("DataAcquisto");

                statoPrezzoFiltro = statoPrezzoFiltro + 1;
                if (statoPrezzoFiltro == 3){
                    statoPrezzoFiltro = 0;
                }

                if (statoPrezzoFiltro == 0){
                    prezzoAcquistoFiltro.setText("Prezzo");
                } else if (statoPrezzoFiltro == 1){
                    prezzoAcquistoFiltro.setText("Prezzo↑");
                } else if (statoPrezzoFiltro == 2){
                    prezzoAcquistoFiltro.setText("Prezzo↓");
                }

                filtraLibreria();
            }
        });
    }
    private void associaListenerDataAcquistoFiltro(){
        dataAcquistoFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statoPrezzoFiltro = 0;
                prezzoAcquistoFiltro.setText("Prezzo");
                statoDataRilascio = 0;
                dataFiltro.setText("DataRilascio");

                statoDataAcquisto = statoDataAcquisto + 1;

                if (statoDataAcquisto == 3){
                    statoDataAcquisto = 0;
                }

                if (statoDataAcquisto == 0){
                    dataAcquistoFiltro.setText("DataAcquisto");
                }else if(statoDataAcquisto == 1){
                    dataAcquistoFiltro.setText("DataAcquisto↑");
                }else if(statoDataAcquisto == 2){
                    dataAcquistoFiltro.setText("DataAcquisto↓");
                }

                filtraLibreria();
            }
        });
    }

    private void associaListenerComboBoxGenere(){
        genereFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraLibreria();
            }
        });
    }

    private void associaListenerComboBoxCategoria(){
        categoriaFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraLibreria();
            }
        });
    }
    private void associaListenerComboBoxPegi(){
        pegiFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraLibreria();
            }
        });
    }

    private void associaListenerPulsanteResetFiltro(){
        pulsanteResetFiltri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ricercaLibreria.setText("");

                genereFiltro.setSelectedIndex(-1);
                categoriaFiltro.setSelectedIndex(-1);
                pegiFiltro.setSelectedIndex(-1);

                dataFiltro.setText("DataRilascio");
                prezzoAcquistoFiltro.setText("Prezzo");
                dataAcquistoFiltro.setText("DataAcquisto");

                filtraLibreria();
            }
        });
    };
    //Profilo

    private void associaListenerModificaInformazioni() {
        pulsanteModificaInformazioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModificaInformazioni modificaInformazioni = new ModificaInformazioni(controller, HomeUtente.this, utenteLoggato);
            }
        });
    }

    private void associaListenerVisualizzaRecensioni() {
        pulsanteVisualizzaRecensioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizzaRecensioni visualizzaRecensioni = new VisualizzaRecensioni(controller, HomeUtente.this, utenteLoggato);
            }
        });
    }

    private void associaListenerAggiungiSaldo() {
        pulsanteAggiungiSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiungiFondi aggiungiFondi = new AggiungiFondi(controller, HomeUtente.this, utenteLoggato);
            }
        });
    }

    private void associaListenerListaSviluppatori() {
        listaSviluppatori.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Sviluppatore sviluppatoreSelezionato = (Sviluppatore) listaSviluppatori.getSelectedValue();

                if (sviluppatoreSelezionato != null) {
                    descrizioneSviluppatoreProfilo.setText(sviluppatoreSelezionato.getDescrizione());
                    testoGiochiRilasciati.setText("Numero di giochi rilasciati: " + String.valueOf(sviluppatoreSelezionato.getListaGiochi().size()));
                    //testoGiocoPiuVenduto.setText(String.valueOf(controller.getGiocoPiuVendutoSviluppatore(sviluppatoreSelezionato)));  DA FARE CON DAO DISPONIBILE
                }
            }
        });
    }

    private void associaListenerRicercaSviluppatori() {
        ricercaSviluppatori.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraSviluppatori();
            }
        });
    }

    private void associaListenerCheckBoxSviluppatori() {
        checkBoxSeguiti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraSviluppatori();
            }
        });
    }

    private void associaListenerPulsanteSegui() {
        pulsanteSegui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.aggiungiSviluppatoreSeguito(utenteLoggato, (Sviluppatore) listaSviluppatori.getSelectedValue());

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Hai seguito " + ((Sviluppatore) listaSviluppatori.getSelectedValue()).getNome());
                    filtraSviluppatori();
                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(homeUtenteFrame, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void associaListenerPulsanteSmettiDiSeguire() {
        pulsanteSmettiDiSeguire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.rimuoviSviluppatoreSeguito(utenteLoggato, (Sviluppatore) listaSviluppatori.getSelectedValue());

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Hai rimosso " + ((Sviluppatore) listaSviluppatori.getSelectedValue()).getNome());
                    filtraSviluppatori();
                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(homeUtenteFrame, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void filtraSviluppatori() {
        String testoRicerca = ricercaSviluppatori.getText().toLowerCase().trim();
        DefaultListModel<Sviluppatore> modelloFiltrato = new DefaultListModel<>();
        ArrayList<Sviluppatore> listaFiltrata;

        if (checkBoxSeguiti.isSelected()) {
            listaFiltrata = utenteLoggato.getSviluppatoriSeguiti();
        } else {
            listaFiltrata = controller.getListaSviluppatoriLoggati();
        }

        for (Sviluppatore s : listaFiltrata) {
            if (s.getNome().toLowerCase().contains(testoRicerca)) {
                modelloFiltrato.addElement(s);
            }
        }
        listaSviluppatori.setModel(modelloFiltrato);
    }

    private void associaListenerListaUtenti() {
        listaUtente.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Utente utenteSelezionato = (Utente) listaUtente.getSelectedValue();

                if (utenteSelezionato != null) {
                    testoGiochiAcquistatiUtenteSelezionato.setText("Numero giochi acquistati: " + String.valueOf(utenteSelezionato.getGiochiAcquistati().size()));
                    testoNumeroRecensioniUtenteSelezionato.setText("Numero recensioni rilasciate: " + String.valueOf(controller.getNumeroRecensioniUtente(utenteSelezionato)));
                    testoGenereUtenteSelezionato.setText("Genere: " + String.valueOf(utenteSelezionato.getGenere()));
                    if (utenteSelezionato.isBannato() == true) {
                        testoBannatoUtente.setText("Bannato: Si");
                    } else testoBannatoUtente.setText("Bannato: No");
                }
            }
        });
    }

    private void associaListenerRicercaUtenti() {
        ricercaUtenti.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraUtenti();
            }
        });
    }

    private void filtraUtenti() {
        String testoRicerca = ricercaUtenti.getText().toLowerCase().trim();
        DefaultListModel<Utente> modelloFiltrato = new DefaultListModel<>();
        ArrayList<Utente> listaFiltrata;

        if (checkBoxAmici.isSelected()) {
            listaFiltrata = utenteLoggato.getListaAmici();
        } else {
            listaFiltrata = controller.getListaUtentiLoggati();
        }

        for (Utente u : listaFiltrata) {
            if (u != utenteLoggato && u.getNome().toLowerCase().contains(testoRicerca)) {
                modelloFiltrato.addElement(u);
            }
        }
        listaUtente.setModel(modelloFiltrato);
    }

    private void associaListenerCheckBoxUtenti() {
        checkBoxAmici.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraUtenti();
            }
        });
    }

    private void associaListenerPulsanteAggiungiAmicoSelezionato() {
        pulsanteAggiungiAmicoSelezionato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.aggiungiAmico(utenteLoggato, (Utente) listaUtente.getSelectedValue());

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Hai aggiunto " + ((Utente) listaUtente.getSelectedValue()).getNome());
                    filtraUtenti();
                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(homeUtenteFrame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void associaListenerPulsanteRimuoviAmicoSelezionato() {
        pulsanteRimuoviAmicoSelezionato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.rimuoviAmico(utenteLoggato, (Utente) listaUtente.getSelectedValue());

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Hai rimosso " + ((Utente) listaUtente.getSelectedValue()).getNome());
                    filtraUtenti();
                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(homeUtenteFrame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }

    //Carrello
    private void associaListenerRimuoviCarrello() {
        pulsanteRimuoviCarrello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaGiochiCarrello.getSelectedRow();

                if (rigaSelezionata != -1) {
                    DefaultTableModel tabellaCarrello = (DefaultTableModel) tabellaGiochiCarrello.getModel();
                    tabellaCarrello.removeRow(rigaSelezionata);
                    ricalcolaTotaleCarrello();
                } else {
                    JOptionPane.showMessageDialog(homeUtenteFrame, "Seleziona prima un gioco dalla tabella per rimuoverlo!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void associaListenerAcquista() {
        pulsanteAcquista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tabellaCarrello = (DefaultTableModel) tabellaGiochiCarrello.getModel();
                double saldoUtente = 0.0; //Aggiunto per testing
                double calcoloTotale = ricalcolaTotaleCarrello();

                if (tabellaCarrello.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(homeUtenteFrame, "Vuoi acquistare il nulla?", "Attenzione", JOptionPane.WARNING_MESSAGE);
                } else if (calcoloTotale > saldoUtente) {  //saldoUtente va cambiata con la variabile di saldo giusta
                    JOptionPane.showMessageDialog(homeUtenteFrame, "Saldo insufficiente", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    saldoUtente -= calcoloTotale;
                    testoSaldo.setText(String.format("%.2f €", saldoUtente));

                    tabellaCarrello.setRowCount(0);
                    testoTotaleCarrello.setText("0.00 €");

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Acquisto completato, troverai i tuoi giochi con relative Key nella Libreria", "Successo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private double ricalcolaTotaleCarrello() {
        double totale = 0.0;
        DefaultTableModel tabellaCarrello = (DefaultTableModel) tabellaGiochiCarrello.getModel();

        for (int i = 0; i < tabellaCarrello.getRowCount(); i++) {
            double prezzo = (Double) tabellaCarrello.getValueAt(i, 2);
            totale += prezzo;
        }
        testoTotaleCarrello.setText(String.format("%.2f €", totale));

        return totale;
    }


    //Metodi utilizzati dalle interfacce

    private void configuraInterfacciaCatalogo(){
        sviluppatoreCatalogo.setText("-");
        prezzoCatalogo.setText("-");
        piattaformaCatalogo.setText("-");
        genereCatalogo.setText("-");
        pegiCatalogo.setText("-");
        categoriaCatalogo.setText("-");
        testoMediaVoti.setText("-");
        dataDiRilascioCatalogo.setText("-");

        prezzoFiltroCatalogo.setText("Prezzo: ");

        configuraComboBoxPiattaformaCatalogo();
        configuraComboBoxGenereCatalogo();
        configuraComboBoxPegiCatalogo();
        configuraComboBoxCategoriaCatalogo();
    }

    private void configuraComboBoxPiattaformaCatalogo(){
        DefaultComboBoxModel<PiattaformaDiGioco> modelPiattaforma = new DefaultComboBoxModel<>();

        modelPiattaforma.addAll(controller.getPiattaformeDiGioco());

        piattaformaFiltroCatalogo.setModel(modelPiattaforma);
        piattaformaFiltroCatalogo.setSelectedIndex(-1);
    }

    private void configuraComboBoxGenereCatalogo(){
        DefaultComboBoxModel<Genere> modelGenere = new DefaultComboBoxModel<>();

        modelGenere.addAll(controller.getGeneri());

        genereFiltroCatalogo.setModel(modelGenere);
        genereFiltroCatalogo.setSelectedIndex(-1);
    }

    private void configuraComboBoxPegiCatalogo(){
        pegiFiltroCatalogo.setSelectedIndex(-1);
    }

    private void configuraComboBoxCategoriaCatalogo(){
        DefaultComboBoxModel<Categoria> modelCategoria = new DefaultComboBoxModel<>();

        modelCategoria.addAll(controller.getCategorie());

        categoriaFiltroCatalogo.setModel(modelCategoria);
        categoriaFiltroCatalogo.setSelectedIndex(-1);
    }

    private void configuraInterfacciaLibreria() {
        edizioneLibreria.setText("-");
        piattaformaDiGiocoLibreria.setText("-");
        prezzoAcquistoLibreria.setText("-");
        keyLibreria.setText("[Key]");
        dataAcquistoLibreria.setText("-");
        generiLibreria.setText("-");
        sviluppatoreLibreria.setText("-");
        pegiLibreria.setText("-");
        categoriaLibreria.setText("-");
        dataRilascioLibreria.setText("-");

        // Disabilita i pulsanti per evitare click nulli
        pulsanteCopiaKey.setEnabled(false);
        pulsanteRecensione.setEnabled(false);

        configuraComboBoxGenere();
        configuraComboBoxCategoria();
        configuraComboBoxPegi();

        filtraLibreria();

    }

    private void configuraComboBoxGenere() {
        DefaultComboBoxModel<Genere> modelGenere = new DefaultComboBoxModel<>();

        modelGenere.addAll(controller.getGeneri());

        genereFiltro.setModel(modelGenere);
        genereFiltro.setSelectedIndex(-1);

    }

    private void configuraComboBoxCategoria() {
        DefaultComboBoxModel<Categoria> modelCategoria = new DefaultComboBoxModel<>();

        modelCategoria.addAll(controller.getCategorie());

        categoriaFiltro.setModel(modelCategoria);
        categoriaFiltro.setSelectedIndex(-1);
    }

    private void configuraComboBoxPegi() {
        pegiFiltro.setSelectedIndex(-1);
    }

    private void mostraForm() {
        homeUtenteFrame.pack();
        homeUtenteFrame.setLocationRelativeTo(null);
        homeUtenteFrame.setVisible(true);
    }

    private void configuraInterfacciaProfilo() {
        configuraTestoInformazioniPersonali();
        configuraTestoSaldo();

        testoDataCreazioneAccount.setText("Data di creazione dell'account:" + String.valueOf(utenteLoggato.getDataCreazione()));
        testoBannato.setVisible(utenteLoggato.isBannato());
        testoNumeroGiochiAcquistati.setText("Numero giochi acquistati: " + String.valueOf(utenteLoggato.getGiochiAcquistati().size()));
        testoNumeroRecensioniRilasciate.setText("Numero recensioni rilasciate:" + String.valueOf(controller.getNumeroRecensioniUtente(utenteLoggato)));

        filtraUtenti(); //Filtro campo vuoto quindi stampa tutti
        filtraSviluppatori(); //stessa cosa di sopra

        testoGiochiRilasciati.setText("Giochi Rilasciati: -");
        testoGiocoPiuVenduto.setText("Gioco piú venduto: -");

        testoGiochiAcquistatiUtenteSelezionato.setText("Numero giochi acquistati: -");
        testoNumeroRecensioniUtenteSelezionato.setText("Numero recensioni rilasciate: -");
        testoGenereUtenteSelezionato.setText("Genere: -");
        testoBannatoUtente.setText("Bannato: -");

    }

    void configuraTestoSaldo() {
        testoSaldo.setText(String.format("Saldo: %d €", utenteLoggato.getSaldo()));
    }

    void configuraTestoInformazioniPersonali() {
        testoNome.setText("Nome: " + utenteLoggato.getNome());
        testoGenere.setText("Genere: " + String.valueOf(utenteLoggato.getGenere()));
        testoEmail.setText("Email: " + utenteLoggato.getEmail());
        testoDataDiNascita.setText("Data di nascita: " + String.valueOf(utenteLoggato.getDataNascita()));
    }

    private void configuraInterfacciaCarrello() {
        String[] colonne = {"Titolo Gioco", "Piattaforma", "Prezzo"};
        DefaultTableModel tabellaCarrello = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabellaGiochiCarrello.setModel(tabellaCarrello);
    }
}
