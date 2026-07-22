package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
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

        associaListenerListaCatalogo();
        associaListenerRicercaCatalogo();
        associaListenerSliderPrezzoCatalogo();
        associaListenerComboBoxPiattaformaCatalogo();
        associaListenerComboBoxCategoriaCatalogo();
        associaListenerComboBoxGenereCatalogo();
        associaListenerComboBoxPegiCatalogo();
        associaListenerDataFiltroCatalogo();
        associaListenerCheckBoxPromozioneCatalogo();
        associaListenerCheckBoxSviluppatoriSeguitiCatalogo();

        associaListenerAggiungiAlCarrello();

        associaListenerListaRecensioni();
        associaListenerMettiLike();
        associaListenerMettiDislike();


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
                configuraInterfacciaCatalogo();
                configuraInterfacciaLibreria();
                configuraInterfacciaProfilo();
                configuraInterfacciaCarrello();
            }
        });
    }

    //Catalogo
    private void associaListenerListaCatalogo(){
        listaCatalogo.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                EdizioneGioco edizioneGiocoSelezionata = (EdizioneGioco) listaCatalogo.getSelectedValue();

                if (edizioneGiocoSelezionata != null) {
                    sviluppatoreCatalogo.setText("Sviluppatore: " + controller.getNomeSviluppatoreDaEdizioneGioco(edizioneGiocoSelezionata));
                    prezzoCatalogo.setText("Prezzo: " + controller.getPrezzoDaEdizioneGioco(edizioneGiocoSelezionata) + "€");
                    piattaformaCatalogo.setText("Piattaforma: " + controller.getPiattaformaDaEdizioneGioco(edizioneGiocoSelezionata));
                    genereCatalogo.setText("Generi: " + controller.getGeneriDaEdizioneGioco(edizioneGiocoSelezionata));
                    pegiCatalogo.setText("Pegi: " + controller.getPegiDaEdizioneGioco(edizioneGiocoSelezionata));
                    categoriaCatalogo.setText("Categoria: " + controller.getCategoriaDaEdizioneGioco(edizioneGiocoSelezionata));
                    try {
                        testoMediaVoti.setText("Media voti: " + controller.getMediaVotiEdizioneGioco(edizioneGiocoSelezionata));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(homeUtenteFrame,"Operazione Fallita");
                    }
                    dataDiRilascioCatalogo.setText("Data di rilascio: " + controller.getDataDiRilascioDaEdizioneGioco(edizioneGiocoSelezionata));

                    pulsanteAggiungiAlCarrello.setEnabled(true);

                    ArrayList<model.Recensione> listaRecensioniGiocoSelezionato = null;
                    try {
                        listaRecensioniGiocoSelezionato = controller.getRecensioniEdizioneGioco(edizioneGiocoSelezionata);
                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(homeUtenteFrame,ex.getMessage());
                    }
                    DefaultListModel<model.Recensione> modelloListaRecensioni = new DefaultListModel<>();
                    modelloListaRecensioni.addAll(listaRecensioniGiocoSelezionato);
                    listaRecensioniCatalogo.setModel(modelloListaRecensioni);

                }
            }
        });
    }

    private void associaListenerRicercaCatalogo() {
        ricercaCatalogo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraCatalogo();
            }
        });
    }

    private final int[] fascePrezzo = {0, 15, 35, 60, 90, -1};

    private void associaListenerSliderPrezzoCatalogo() {
        sliderPrezzoCatalogo.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                filtraCatalogo();
            }
        });
    }

    private void associaListenerComboBoxPiattaformaCatalogo() {
        piattaformaFiltroCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraCatalogo();
            }
        });
    }

    private void associaListenerComboBoxGenereCatalogo() {
        genereFiltroCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraCatalogo();
            }
        });
    }

    private void associaListenerComboBoxPegiCatalogo() {
        pegiFiltroCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraCatalogo();
            }
        });
    }

    private void associaListenerDataFiltroCatalogo() {
        pulsanteDataDiRilascioFiltroCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                statoDataRilascio = statoDataRilascio + 1;
                if (statoDataRilascio == 3) {
                    statoDataRilascio = 0;
                }

                if (statoDataRilascio == 0) {
                    pulsanteDataDiRilascioFiltroCatalogo.setText("DataRilascio");
                } else if (statoDataRilascio == 1) {
                    pulsanteDataDiRilascioFiltroCatalogo.setText("DataRilascio ↑");
                } else if (statoDataRilascio == 2) {
                    pulsanteDataDiRilascioFiltroCatalogo.setText("DataRilascio ↓");
                }

                filtraCatalogo();
            }
        });
    }

    private void associaListenerComboBoxCategoriaCatalogo() {
        categoriaFiltroCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraCatalogo();
            }
        });
    }

    private void associaListenerCheckBoxPromozioneCatalogo() {
        checkBoxInPromozione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraCatalogo();
            }
        });
    }

    private void associaListenerCheckBoxSviluppatoriSeguitiCatalogo() {
        checkBoXSviluppatori.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraCatalogo();
            }
        });
    }

    private void associaListenerAggiungiAlCarrello(){
        pulsanteAggiungiAlCarrello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EdizioneGioco giocoScelto = (EdizioneGioco) listaCatalogo.getSelectedValue();
                    controller.aggiungiAlCarrello(utenteLoggato, giocoScelto);
                    JOptionPane.showMessageDialog(homeUtenteFrame, "Gioco aggiunto al carrello", "Successo", JOptionPane.INFORMATION_MESSAGE);

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(homeUtenteFrame, ex.getMessage(), "Errore", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private  void associaListenerListaRecensioni(){
        listaRecensioniCatalogo.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                model.Recensione recensioneSelezionata = (model.Recensione) listaRecensioniCatalogo.getSelectedValue();

                if (recensioneSelezionata != null){
                    descrizioneRecensioneCatalogo.setText(controller.getDescrizioneRecensione(recensioneSelezionata));
                    votoCatalogo.setText("Voto: " + controller.getVotoRecensione(recensioneSelezionata));
                    valutazioneRecensioneCatalogo.setText("Differenza Like: " + controller.getDifferenzaLikeRecensione(recensioneSelezionata));

                    pulsanteLike.setEnabled(true);
                    pulsanteDislike.setEnabled(true);

                    pulsanteLike.setVisible(true);
                    pulsanteDislike.setVisible(true);
                }

            }
        });
    }

    private void associaListenerMettiLike(){
        pulsanteLike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.mettiLikeRecensione((model.Recensione) listaRecensioniCatalogo.getSelectedValue(), utenteLoggato);
                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(homeUtenteFrame,ex.getMessage());
                }
                valutazioneRecensioneCatalogo.setText("Differenza Like: " + controller.getDifferenzaLikeRecensione((model.Recensione) listaRecensioniCatalogo.getSelectedValue()));

                pulsanteLike.setVisible(false);
                pulsanteDislike.setVisible(true);
            }
        });
    }

    private void associaListenerMettiDislike(){
        pulsanteDislike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.mettiDislikeRecensione((model.Recensione) listaRecensioniCatalogo.getSelectedValue(), utenteLoggato);
                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(homeUtenteFrame,ex.getMessage());
                }
                valutazioneRecensioneCatalogo.setText("Differenza Like: " + controller.getDifferenzaLikeRecensione((model.Recensione) listaRecensioniCatalogo.getSelectedValue()));

                pulsanteDislike.setVisible(false);
                pulsanteLike.setVisible(true);
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

    private void associaListenerListaLibreria() {
        listaLibreria.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Fattura fatturaSelezionata = (Fattura) listaLibreria.getSelectedValue();

                if (fatturaSelezionata != null) {
                    edizioneLibreria.setText("Titolo: " + controller.getTitoloDaFattura(fatturaSelezionata));
                    piattaformaDiGiocoLibreria.setText("Piattaforma: " + controller.getPiattaformaDaFattura(fatturaSelezionata));
                    dataRilascioLibreria.setText("Data di rilascio: " + String.valueOf(controller.getDataRilascioDaFattura(fatturaSelezionata)));
                    categoriaLibreria.setText("Categoria: " + String.valueOf(controller.getCategoriaDaFattura(fatturaSelezionata)));
                    pegiLibreria.setText("Pegi: " + String.valueOf(controller.getPegiDaFattura(fatturaSelezionata)));
                    generiLibreria.setText("Generi: " + String.valueOf(controller.getGeneriDaFattura(fatturaSelezionata)));
                    sviluppatoreLibreria.setText("Sviluppatore: " + String.valueOf(controller.getSviluppatoreDaFattura(fatturaSelezionata)));

                    dataAcquistoLibreria.setText("Data d'acquisto: " + String.valueOf(controller.getDataAcquistoDaFattura(fatturaSelezionata)));
                    keyLibreria.setText(controller.getKeyDaFattura(fatturaSelezionata));
                    prezzoAcquistoLibreria.setText("Prezzo: " + String.valueOf(controller.getPrezzoAcquistoDaFattura(fatturaSelezionata) + "€"));

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
                //l'ho trovato su internet. Permette di copiare un testo nella clipboard di Windows mac e linux
                java.awt.datatransfer.StringSelection selezione = new java.awt.datatransfer.StringSelection(keyDaCopiare);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selezione, null);
            }
        });
    }

    private void associaListenerRicercaLibreria() {
        ricercaLibreria.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraLibreria();
            }
        });
    }

    private void associaListenerDataFiltro() {
        dataFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statoPrezzoFiltro = 0;
                prezzoAcquistoFiltro.setText("Prezzo");
                statoDataAcquisto = 0;
                dataAcquistoFiltro.setText("DataAcquisto");

                statoDataRilascio = statoDataRilascio + 1;
                if (statoDataRilascio == 3) {
                    statoDataRilascio = 0;
                }

                if (statoDataRilascio == 0) {
                    dataFiltro.setText("DataRilascio");
                } else if (statoDataRilascio == 1) {
                    dataFiltro.setText("DataRilascio ↑");
                } else if (statoDataRilascio == 2) {
                    dataFiltro.setText("DataRilascio ↓");
                }

                filtraLibreria();
            }
        });
    }

    private void associaListenerPrezzoAcquistoFiltro() {
        prezzoAcquistoFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statoDataRilascio = 0;
                dataFiltro.setText("DataRilascio");
                statoDataAcquisto = 0;
                dataAcquistoFiltro.setText("DataAcquisto");

                statoPrezzoFiltro = statoPrezzoFiltro + 1;
                if (statoPrezzoFiltro == 3) {
                    statoPrezzoFiltro = 0;
                }

                if (statoPrezzoFiltro == 0) {
                    prezzoAcquistoFiltro.setText("Prezzo");
                } else if (statoPrezzoFiltro == 1) {
                    prezzoAcquistoFiltro.setText("Prezzo↑");
                } else if (statoPrezzoFiltro == 2) {
                    prezzoAcquistoFiltro.setText("Prezzo↓");
                }

                filtraLibreria();
            }
        });
    }

    private void associaListenerDataAcquistoFiltro() {
        dataAcquistoFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statoPrezzoFiltro = 0;
                prezzoAcquistoFiltro.setText("Prezzo");
                statoDataRilascio = 0;
                dataFiltro.setText("DataRilascio");

                statoDataAcquisto = statoDataAcquisto + 1;

                if (statoDataAcquisto == 3) {
                    statoDataAcquisto = 0;
                }

                if (statoDataAcquisto == 0) {
                    dataAcquistoFiltro.setText("DataAcquisto");
                } else if (statoDataAcquisto == 1) {
                    dataAcquistoFiltro.setText("DataAcquisto↑");
                } else if (statoDataAcquisto == 2) {
                    dataAcquistoFiltro.setText("DataAcquisto↓");
                }

                filtraLibreria();
            }
        });
    }

    private void associaListenerComboBoxGenere() {
        genereFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraLibreria();
            }
        });
    }

    private void associaListenerComboBoxCategoria() {
        categoriaFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraLibreria();
            }
        });
    }

    private void associaListenerComboBoxPegi() {
        pegiFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraLibreria();
            }
        });
    }

    private void associaListenerPulsanteResetFiltro() {
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
    }

    ;
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
                    descrizioneSviluppatoreProfilo.setText(controller.getDescrizioneSviluppatore(sviluppatoreSelezionato));
                    testoGiochiRilasciati.setText("Numero di giochi rilasciati: " + String.valueOf(controller.getNumeroGiochiRilasciatiSviluppatore(sviluppatoreSelezionato)));
                    try {
                        testoGiocoPiuVenduto.setText(String.valueOf(controller.getGiocoPiuVendutoSviluppatore(sviluppatoreSelezionato)));
                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(homeUtenteFrame,ex.getMessage());
                    }
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

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Hai seguito " + (controller.getNomeSviluppatore((Sviluppatore) listaSviluppatori.getSelectedValue())));
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

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Hai rimosso " + (controller.getNomeSviluppatore((Sviluppatore) listaSviluppatori.getSelectedValue())));
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

        boolean checkBoxSviluppatoriSeguiti = checkBoxSeguiti.isSelected();

        ArrayList<Sviluppatore> listaFiltrata = null;
        try {
            listaFiltrata = controller.getSviluppatoriFiltrati(checkBoxSviluppatoriSeguiti, testoRicerca, utenteLoggato);
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(homeUtenteFrame,e.getMessage());
        }

        for (Sviluppatore s : listaFiltrata) {
            modelloFiltrato.addElement(s);
        }
        listaSviluppatori.setModel(modelloFiltrato);

    }

    private void associaListenerListaUtenti() {
        listaUtente.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Utente utenteSelezionato = (Utente) listaUtente.getSelectedValue();

                if (utenteSelezionato != null) {
                    testoGiochiAcquistatiUtenteSelezionato.setText("Numero giochi acquistati: " + String.valueOf(controller.getNumeroGiochiAcquistatiUtente(utenteSelezionato)));
                    try {
                        testoNumeroRecensioniUtenteSelezionato.setText("Numero recensioni rilasciate: " + String.valueOf(controller.getNumeroRecensioniUtente(utenteSelezionato)));
                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(homeUtenteFrame,ex.getMessage());
                    }
                    testoGenereUtenteSelezionato.setText("Genere: " + String.valueOf(controller.getGenereUtente(utenteSelezionato)));
                    if (controller.isUtenteBannato(utenteSelezionato)){
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

        boolean checkBoxAmiciFiltro = checkBoxAmici.isSelected();

        ArrayList<Utente> listaFiltrata = null;
        try {
            listaFiltrata = controller.getUtentiFiltrati(checkBoxAmiciFiltro, testoRicerca, utenteLoggato);
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(homeUtenteFrame,e.getMessage());
        }
        for (Utente u : listaFiltrata) {
            modelloFiltrato.addElement(u);
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

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Hai aggiunto " + (controller.getNomeUtente((Utente) listaUtente.getSelectedValue())));
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

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Hai rimosso " + (controller.getNomeUtente((Utente) listaUtente.getSelectedValue())));
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
                    try {
                        EdizioneGioco giocoDaRimuovere = controller.getGiochiCarrello(utenteLoggato).get(rigaSelezionata);

                        controller.rimuoviDalCarrello(utenteLoggato, giocoDaRimuovere);

                        configuraInterfacciaCarrello();

                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(homeUtenteFrame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
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
                try {
                    controller.acquista(utenteLoggato);

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Acquisto completato! Troverai i tuoi giochi con relative Key nella Libreria.", "Successo", JOptionPane.INFORMATION_MESSAGE);

                    configuraInterfacciaCarrello();
                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(homeUtenteFrame, ex.getMessage(), "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }


    //Metodi utilizzati dalle interfacce

    private void configuraInterfacciaCatalogo() {
        sviluppatoreCatalogo.setText("-");
        prezzoCatalogo.setText("-");
        piattaformaCatalogo.setText("-");
        genereCatalogo.setText("-");
        pegiCatalogo.setText("-");
        categoriaCatalogo.setText("-");
        testoMediaVoti.setText("-");
        dataDiRilascioCatalogo.setText("-");
        prezzoFiltroCatalogo.setText("Prezzo: Nessun limite");

        votoCatalogo.setText("-");
        valutazioneRecensioneCatalogo.setText("-");

        pulsanteAggiungiAlCarrello.setEnabled(false);
        pulsanteLike.setEnabled(false);
        pulsanteDislike.setEnabled(false);

        configuraComboBoxPiattaformaCatalogo();
        configuraComboBoxGenereCatalogo();
        configuraComboBoxPegiCatalogo();
        configuraComboBoxCategoriaCatalogo();

        filtraCatalogo();
    }

    private void configuraComboBoxPiattaformaCatalogo() {
        DefaultComboBoxModel<PiattaformaDiGioco> modelPiattaforma = new DefaultComboBoxModel<>();

        try {
            modelPiattaforma.addAll(controller.getPiattaformeDiGioco());
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(homeUtenteFrame,e.getMessage());
        }

        piattaformaFiltroCatalogo.setModel(modelPiattaforma);
        piattaformaFiltroCatalogo.setSelectedIndex(-1);
    }

    private void configuraComboBoxGenereCatalogo() {
        DefaultComboBoxModel<Genere> modelGenere = new DefaultComboBoxModel<>();

        try {
            modelGenere.addAll(controller.getGeneri());
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(homeUtenteFrame,e.getMessage());
        }

        genereFiltroCatalogo.setModel(modelGenere);
        genereFiltroCatalogo.setSelectedIndex(-1);
    }

    private void configuraComboBoxPegiCatalogo() {
        pegiFiltroCatalogo.setSelectedIndex(-1);
    }

    private void configuraComboBoxCategoriaCatalogo() {
        DefaultComboBoxModel<Categoria> modelCategoria = new DefaultComboBoxModel<>();

        modelCategoria.addAll(controller.getCategorie());

        categoriaFiltroCatalogo.setModel(modelCategoria);
        categoriaFiltroCatalogo.setSelectedIndex(-1);
    }

    private void filtraCatalogo() {

        int indiceSelezionato = sliderPrezzoCatalogo.getValue() / 20;
        if (indiceSelezionato < 0 || indiceSelezionato > 5) {
            indiceSelezionato = 0;
        }
        int prezzoSelezionato = fascePrezzo[indiceSelezionato];

        if (prezzoSelezionato == -1) {
            prezzoFiltroCatalogo.setText("Prezzo: Nessun limite");
        } else {
            prezzoFiltroCatalogo.setText("Prezzo: fino a " + prezzoSelezionato + " €");
        }

        String testoRicerca = ricercaCatalogo.getText().toLowerCase().trim();

        Genere genereScelto = null;
        if (genereFiltroCatalogo.getSelectedIndex() != -1) {
            genereScelto = (Genere) genereFiltroCatalogo.getSelectedItem();
        }

        Categoria categoriaScelta = null;
        if (categoriaFiltroCatalogo.getSelectedIndex() != -1) {
            categoriaScelta = (Categoria) categoriaFiltroCatalogo.getSelectedItem();
        }

        String pegiScelto = null;
        if (pegiFiltroCatalogo.getSelectedIndex() != -1) {
            pegiScelto = String.valueOf(pegiFiltroCatalogo.getSelectedItem());
        }

        boolean inPromozione = checkBoxInPromozione.isSelected();
        boolean traSeguiti = checkBoXSviluppatori.isSelected();

        ArrayList<EdizioneGioco> risultati = null;
        try {
            risultati = controller.getCatalogoFiltrato(testoRicerca, prezzoSelezionato, genereScelto, categoriaScelta, pegiScelto, inPromozione, traSeguiti, utenteLoggato, statoDataRilascio);
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(homeUtenteFrame,e.getMessage());
        }

        DefaultListModel<EdizioneGioco> modelloFiltrato = new DefaultListModel<>();
        for (EdizioneGioco e : risultati) {
            modelloFiltrato.addElement(e);
        }
        listaCatalogo.setModel(modelloFiltrato);
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

        try {
            modelGenere.addAll(controller.getGeneri());
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(homeUtenteFrame,e.getMessage());
        }

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

    private void filtraLibreria() {

        String testoRicerca = ricercaLibreria.getText().toLowerCase().trim();
        DefaultListModel<Fattura> modelloFiltrato = new DefaultListModel<>();

        Genere genereScelto = null;
        if (genereFiltro.getSelectedIndex() != -1) {
            genereScelto = (Genere) genereFiltro.getSelectedItem();
        }

        Categoria categoriaScelta = null;
        if (categoriaFiltro.getSelectedIndex() != -1) {
            categoriaScelta = (Categoria) categoriaFiltro.getSelectedItem();
        }

        String pegiScelto = null;
        if (pegiFiltro.getSelectedIndex() != -1) {
            pegiScelto = String.valueOf(pegiFiltro.getSelectedItem());
        }

        ArrayList<Fattura> listaFiltrata = null;
        try {
            listaFiltrata = controller.getLibreriaFiltrata(testoRicerca, utenteLoggato, genereScelto, categoriaScelta, pegiScelto, statoDataRilascio, statoPrezzoFiltro, statoDataAcquisto);
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(homeUtenteFrame,e.getMessage());
        }

        for (Fattura f : listaFiltrata) {
            modelloFiltrato.addElement(f);
        }

        listaLibreria.setModel(modelloFiltrato);

    }

    private void configuraInterfacciaProfilo() {
        configuraTestoInformazioniPersonali();
        configuraTestoSaldo();

        testoDataCreazioneAccount.setText("Data di creazione dell'account:" + String.valueOf(controller.getDataCreazioneAccountUtente(utenteLoggato)));
        testoBannato.setVisible(controller.isUtenteBannato(utenteLoggato));
        testoNumeroGiochiAcquistati.setText("Numero giochi acquistati: " + String.valueOf(controller.getNumeroGiochiAcquistatiUtente(utenteLoggato)));
        try {
            testoNumeroRecensioniRilasciate.setText("Numero recensioni rilasciate:" + String.valueOf(controller.getNumeroRecensioniUtente(utenteLoggato)));
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(homeUtenteFrame,e.getMessage());
        }

        filtraUtenti(); //Filtro campo vuoto quindi stampa tutti
        filtraSviluppatori(); //stessa cosa di sopra

        testoGiochiRilasciati.setText("Giochi Rilasciati: -");
        testoGiocoPiuVenduto.setText("Gioco piú venduto: -");

        testoGiochiAcquistatiUtenteSelezionato.setText("Numero giochi acquistati: -");
        testoNumeroRecensioniUtenteSelezionato.setText("Numero recensioni rilasciate: -");
        testoGenereUtenteSelezionato.setText("Genere: -");
        testoBannatoUtente.setText("Bannato: -");

    }

    void configuraTestoInformazioniPersonali() {
        testoNome.setText("Nome: " + controller.getNomeUtente(utenteLoggato));
        testoGenere.setText("Genere: " + controller.getGenereUtente(utenteLoggato));
        testoEmail.setText("Email: " + controller.getEmailUtente(utenteLoggato));
        testoDataDiNascita.setText("Data di nascita: " + controller.getDataDiNascitaUtente(utenteLoggato));
    }

    void configuraTestoSaldo() {
        testoSaldo.setText(String.format("Saldo: %d €", controller.getSaldoUtente(utenteLoggato)));
    }

    private void configuraInterfacciaCarrello() {

        String[] colonne = {"Titolo Gioco", "Piattaforma", "Prezzo"};
        DefaultTableModel tabellaCarrello = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (controller.getCarrelloUtente(utenteLoggato) != null) {

            ArrayList<EdizioneGioco> giochiNelCarrello = null;
            try {
                giochiNelCarrello = controller.getGiochiCarrello(utenteLoggato);
            } catch (CampoNonValidoException e) {
                JOptionPane.showMessageDialog(homeUtenteFrame, e.getMessage());
            }

            for (EdizioneGioco ed : giochiNelCarrello) {
                Object[] riga = {
                        controller.getTitoloDaEdizioneGioco(ed),
                        controller.getPiattaformaDaEdizioneGioco(ed),
                        controller.getPrezzoDaEdizioneGioco(ed) + "€"
                };
                tabellaCarrello.addRow(riga);
            }
        }

        tabellaGiochiCarrello.setModel(tabellaCarrello);
        testoTotaleCarrello.setText(String.valueOf(controller.getPrezzoCarrello(utenteLoggato)) + "€");
    }

    private void mostraForm() {
        homeUtenteFrame.pack();
        homeUtenteFrame.setLocationRelativeTo(null);
        homeUtenteFrame.setVisible(true);
    }
}
