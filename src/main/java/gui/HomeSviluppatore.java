package gui;

import controller.Controller;
import model.*;
//import model.Sviluppatore;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class HomeSviluppatore {
    private JPanel homeSviluppatore;
    private JTabbedPane finestre;
    private JList<Gioco> listaTitoli; // resa generica <String> per evitare l'errore del cast1
    private JPanel pannelloDettagli;
    private JLabel Titolo;
    private JLabel lblGenere;
    private JLabel Piattaforma;
    private JLabel guadagnoTotale;
    private JLabel unitàVendute;
    private JTextField barraRicerca;
    private JPanel libreria;
    private JPanel pannelloDiControllo;
    private JTabbedPane finestrePannello;
    private JLabel categoria;
    private JTextField textPegi;
    private JLabel aggPegi;
    private JButton aggModButton;
    private JComboBox aggCategoria;
    private JList<Gioco> listaGiochiAggiunti;
    private JTextField textTitolo;
    private JLabel titolo;
    private JLabel genere;
    private JTextField textGenere;
    private JTextField textPiattaforma;
    private JLabel piattaforma;
    private JLabel prezzo;
    private JTextField textPrezzo;
    private JLabel dataDiRilascio;
    private JTextField textDataRilascio;
    private JLabel pegi;
    private JLabel lblCategoria;
    private JPanel generiPanel;
    private JScrollPane checkGenScroll;
    private JScrollPane checkPiattScroll;
    private JPanel piattaformaPanel;
    private JPanel aggiungiGioco;
    private JButton reset;
    private JButton rimuoviGiocoButton;
    private JTextField ricercaPannello;
    private JLabel nomeSviluppatore;
    private JLabel descrizioneSviluppatore;
    private JLabel stabilita;
    private JTextArea descrizioneArea;
    private JButton gestioneProfilo;
    private JLabel seguitiSvilup;
    private JPanel profilo;
    private JLabel fondiSvilup;
    private JTextArea textAreaGeneri;
    private JButton partecipaPromozione;
    private JLabel promozioniAttive;
    private JTextArea areaRecensioni;
    private JPanel informazioniGioco;
    private JButton pulsanteLogout;
    private JLabel strikeSvilupp;
    private JComboBox modificaCategoriaCombo;

    private List<JCheckBox> listaCheckboxGeneri = new ArrayList<>();
    private List<JCheckBox> listaCheckboxPiattaforma = new ArrayList<>();

    private DefaultListModel<Gioco> modelPannelloControllo;
    private DefaultListModel<Gioco> modelLibreria;
    ArrayList<Gioco> listaCompletaGiochi = new ArrayList<>();
    private Controller controller;
    private Sviluppatore sviluppatoreCorrente;
    private JFrame sviluppatoreFrame;


    public HomeSviluppatore(Controller controller, JFrame accediGUI, Sviluppatore sviluppatore) {

        if(controller == null) throw new IllegalArgumentException("Controller passato inesistente");
        if(sviluppatore == null) throw new IllegalArgumentException("Sviluppatore passato inesistente");

        this.controller = controller;
        this.sviluppatoreCorrente = sviluppatore;

        configuraInterfaccia();

        popolaListe();
        ricercaListaLib();
        ricercaPannello();
        selezioneListaLibreria();
        graficaLibreriaGen();
        pannelloAggMod();
        inserimentoGioco();
        profilo(this.sviluppatoreCorrente);
        gestProfilo(this.sviluppatoreCorrente);
        reset();
        rimuoviGioco();
        partecipaPromozione();
        gestioneLogout(accediGUI);


    }


    private void configuraInterfaccia(){
        sviluppatoreFrame = new JFrame("HomeSviluppatore");
        sviluppatoreFrame.setContentPane(homeSviluppatore);
        sviluppatoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sviluppatoreFrame.setSize(1155, 700);
        sviluppatoreFrame.setLocationRelativeTo(null);
        sviluppatoreFrame.setVisible(true);
    }

    private void popolaListe() {
        // creiamo i modelli corretti
        modelLibreria = new DefaultListModel<>();
        modelPannelloControllo = new DefaultListModel<>();

        try {
                ArrayList<Gioco> tuttiIGiochi = controller.getListaGiochiSviluppatore(sviluppatoreCorrente);

                if (tuttiIGiochi != null) {
                    for (Gioco gioco : tuttiIGiochi) {
                        modelLibreria.addElement(gioco);
                        modelPannelloControllo.addElement(gioco);
                        listaCompletaGiochi.add(gioco);
                    }
                }

            listaTitoli.setModel(modelLibreria);
            listaGiochiAggiunti.setModel(modelPannelloControllo);

        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtraLista(String testoCercato, DefaultListModel<Gioco> modelloDestinazione) {
        modelloDestinazione.clear();


        for (Gioco gioco : listaCompletaGiochi) {
            String titolo = controller.getTitoloDaGioco(gioco).toLowerCase();


            if (titolo.contains(testoCercato)) {
                modelloDestinazione.addElement(gioco);
            }
        }
    }


    private void ricercaListaLib() {
        barraRicerca.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                String testoCercato = barraRicerca.getText().toLowerCase();
                filtraLista(testoCercato, modelLibreria);
            }
        });
    }

    private void ricercaPannello() {
        ricercaPannello.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String testoCercato = ricercaPannello.getText().toLowerCase();
                filtraLista(testoCercato, modelPannelloControllo);
            }
        });

    }


    private void selezioneListaLibreria() {
        listaTitoli.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {

                // questo if serve a evitare di eseguire il codice 2 volte
                if (!e.getValueIsAdjusting()) {

                    // prendiamo il gioco cliccato
                    Gioco giocoSelezionato = listaTitoli.getSelectedValue();

                    if (giocoSelezionato != null) {

                        Titolo.setText(controller.getTitoloDaGioco(giocoSelezionato));
                        lblCategoria.setText("Categoria: " + controller.getCategoriaDaGioco(giocoSelezionato));
                        textAreaGeneri.setText("Genere: " + controller.getGenereDaGioco(giocoSelezionato));
                        Piattaforma.setText("Piattaforma: " + controller.getStringPiattaformeDaGioco(giocoSelezionato));
                        pegi.setText("Pegi: " + controller.getPegiDaGioco(giocoSelezionato));

                    try {
                        promozioniAttive.setText("Promozione: "+ controller.getStringaPromozioniPerGioco(giocoSelezionato));
                        guadagnoTotale.setText("Guadagno totale: " + controller.getGuadagnoTotaleDaGioco(giocoSelezionato) + "€");
                        unitàVendute.setText("Unità vendute: " + controller.getUnitaVenduteDaGioco(giocoSelezionato));
                        areaRecensioni.setText(controller.getStringaRecensioniPerGioco(giocoSelezionato));
                        areaRecensioni.setCaretPosition(0);

                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(null, "Impossibile recuperare i dati di vendita: " + ex.getMessage());

                        guadagnoTotale.setText("Guadagno totale: ---");
                        unitàVendute.setText("Unità vendute: ---");
                        areaRecensioni.setText("Errore di connessione: impossibile caricare le recensioni.");
                        promozioniAttive.setText("Nessuna promozione attiva");
                    }

                    }
                }
            }
        });
    }

    private void graficaLibreriaGen() {
        textAreaGeneri.setText("Genere:");
        textAreaGeneri.setLineWrap(true);
        textAreaGeneri.setWrapStyleWord(true);
        textAreaGeneri.setEditable(false);
        textAreaGeneri.setOpaque(false);
        textAreaGeneri.setFont(categoria.getFont());
        textAreaGeneri.setFocusable(false);


        areaRecensioni.setEditable(false);
        areaRecensioni.setOpaque(true);
        areaRecensioni.setFocusable(false);


    }


    private void pannelloAggMod() {
        listaGiochiAggiunti.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Gioco giocoSelezionato = listaGiochiAggiunti.getSelectedValue();

                    if (giocoSelezionato != null) {
                        aggModButton.setText("Salva modifiche");
                        textTitolo.setText(controller.getTitoloDaGioco(giocoSelezionato));
                        textPegi.setText(String.valueOf(controller.getPegiDaGioco(giocoSelezionato)));
                        aggCategoria.setSelectedItem(controller.getCategoriaDaGioco(giocoSelezionato));
                        textPrezzo.setFocusable(false);
                        textPrezzo.setEditable(false);
                        textDataRilascio.setFocusable(false);
                        textDataRilascio.setEditable(false);
                        for (JCheckBox cb : listaCheckboxGeneri) {
                            cb.setSelected(false);
                        }

                        try {

                            textPrezzo.setText(String.valueOf(controller.getPrezzoPrimaEdizioneDaGioco(giocoSelezionato)));
                            textDataRilascio.setText(controller.getDataRilascioPrimaEdizioneFormattata(giocoSelezionato));



                            for (JCheckBox cb : listaCheckboxGeneri) {
                                for (Genere g : controller.getListaGeneriDaGioco(giocoSelezionato)) {
                                    if (cb.getText().equals(g.toString())) {
                                        cb.setSelected(true);
                                    }
                                }
                            }
                        } catch (CampoNonValidoException ex) {
                            System.out.println("Errore nel caricamento del gioco: " + ex.getMessage());
                        }

                        for (JCheckBox cbP : listaCheckboxPiattaforma) {
                            cbP.setSelected(false);
                            cbP.setEnabled(true);
                        }

                        try {
                            for (EdizioneGioco edizioneGioco : controller.getEdizioniDaGioco(giocoSelezionato)) {
                                for (JCheckBox cbP : listaCheckboxPiattaforma) {
                                    if (cbP.getText().equals(controller.getPiattaformaDaEdizioneGioco(edizioneGioco).getNome())) {
                                        cbP.setSelected(true);
                                        cbP.setEnabled(false);
                                    }
                                }
                            }
                        } catch (CampoNonValidoException ex) {
                            System.out.println("Errore nel caricamento del gioco: " + ex.getMessage());
                        }
                    }
                }
            }
        });
    }

    private void inserimentoGioco() {
        inizializzaGraficaControllo();
        configuraAzioneAggiungi();
    }


    private void inizializzaGraficaControllo() {
        // carica i valori dell'enum Categoria dentro la ComboBox dal controller
        aggCategoria.setModel(new DefaultComboBoxModel<>(controller.getCategorie().toArray()));

        textPrezzo.setForeground(Color.GRAY);

        textDataRilascio.setText("GG/MM/AAAA");
        textDataRilascio.setForeground(Color.GRAY);
        textDataRilascio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textDataRilascio.getText().equals("GG/MM/AAAA")) {
                    textDataRilascio.setText("");
                    textDataRilascio.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textDataRilascio.getText().trim().isEmpty()) {
                    textDataRilascio.setText("GG/MM/AAAA");
                    textDataRilascio.setForeground(Color.GRAY);
                }
            }
        });


        generiPanel.setLayout(new BoxLayout(generiPanel, BoxLayout.Y_AXIS));
        try {
            for (Genere nomeGenere : controller.getGeneri()) {
                JCheckBox cb = new JCheckBox(nomeGenere.toString());
                listaCheckboxGeneri.add(cb);
                generiPanel.add(cb);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(null, "Errore nel caricamento dei generi: " + e.getMessage());
        }

        configuraDimensioniScroll(generiPanel, listaCheckboxGeneri.size());


        piattaformaPanel.setLayout(new BoxLayout(piattaformaPanel, BoxLayout.Y_AXIS));

        try {
            for (PiattaformaDiGioco nomePiattaforma : controller.getPiattaformeDiGioco()) {
                JCheckBox cb = new JCheckBox(nomePiattaforma.toString());
                listaCheckboxPiattaforma.add(cb);
                piattaformaPanel.add(cb);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(null, "Errore caricamento piattaforme: " + e.getMessage());
        }

     configuraDimensioniScroll(piattaformaPanel,listaCheckboxPiattaforma.size());
    }



    private void configuraAzioneAggiungi() {

        aggModButton.addActionListener(e -> {
            Gioco giocoSelezionato = listaGiochiAggiunti.getSelectedValue();
            String titolo = textTitolo.getText().trim();
            Categoria categoriaEnum = (Categoria) aggCategoria.getSelectedItem();
            String pegiStr = textPegi.getText().trim();
            String prezzoStr = textPrezzo.getText().trim();
            String dataRilascio = textDataRilascio.getText().trim();

            // controlliamo che anche la data sia compilata
            if (titolo.isEmpty() ||
                    pegiStr.isEmpty() || prezzoStr.isEmpty() || dataRilascio.isEmpty() || dataRilascio.equals("GG/MM/AAAA")) {

                JOptionPane.showMessageDialog(null, "Per favore, compila tutti i campi, inclusa la data!");
                return;
            }


            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataLocale = LocalDate.parse(dataRilascio, formatter);
                int pegi = Integer.parseInt(pegiStr);
                double prezzo = Double.parseDouble(prezzoStr.replace(",", "."));


                ArrayList<Genere> listaGeneri = new ArrayList<>();
                for (JCheckBox cb : listaCheckboxGeneri) {
                    if (cb.isSelected()) {
                        listaGeneri.add(new Genere(cb.getText()));
                    }
                }

                ArrayList<PiattaformaDiGioco> listaPiattaforma = new ArrayList<>();
                for (JCheckBox cbo : listaCheckboxPiattaforma) {
                    if (cbo.isSelected()) {

                        listaPiattaforma.add(controller.getPiattaformaDaNome(cbo.getText()));
                    }
                }


                if (listaPiattaforma.isEmpty() || listaGeneri.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Attenzione: devi selezionare almeno un genere e una piattaforma!");
                    return;
                }

                if (giocoSelezionato != null) {
                    controller.modificaGiocoEsistente
                            (giocoSelezionato, titolo, pegi, categoriaEnum, listaGeneri, listaPiattaforma, prezzo, dataLocale);


                    JOptionPane.showMessageDialog(null, "Modifiche salvate con Successo!");


                } else {

                    Gioco nuovoGioco = controller.creaNuovoGioco
                            (titolo, pegi, categoriaEnum, listaGeneri, listaPiattaforma, prezzo, dataLocale, this.sviluppatoreCorrente);


                    modelPannelloControllo.addElement(nuovoGioco);
                    modelLibreria.addElement(nuovoGioco);
                    listaCompletaGiochi.add(nuovoGioco);
                    JOptionPane.showMessageDialog(null, "Gioco ed Edizione inseriti con successo!");

                }
                listaGiochiAggiunti.clearSelection();
                pulisciCampiInserimento();
                aggModButton.setText("Aggiungi gioco");


            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Errore nei dati numerici!\nIl PEGI deve essere un intero.\nIl Prezzo deve essere un numero decimale.");
            } catch (CampoNonValidoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Formato data non valido! Usa il formato GG/MM/AAAA.");
            }
        });

    }

    private void reset() {
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaGiochiAggiunti.clearSelection();
                pulisciCampiInserimento();
                aggModButton.setText("Aggiungi gioco");
            }

        });
    }

    private void rimuoviGioco() {
        rimuoviGiocoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Stop killing games!");
            }
        });
    }


    private void profilo(Sviluppatore sviluppatoreCorrente) {
        nomeSviluppatore.setText(controller.getNomeSviluppatore(sviluppatoreCorrente));
        descrizioneArea.setText(controller.getDescrizioneSviluppatore(sviluppatoreCorrente));
        descrizioneArea.setEditable(false);
        descrizioneArea.setLineWrap(true);
        descrizioneArea.setWrapStyleWord(true);
        descrizioneArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        descrizioneArea.setFocusable(false);

        fondiSvilup.setText("Fondi: " + controller.getFondiSviluppatore(sviluppatoreCorrente) + "€");
        strikeSvilupp.setText("Strike: " + controller.getStrikeSviluppatore(sviluppatoreCorrente));
        seguitiSvilup.setText("Seguiti: " + controller.getSeguitiSviluppatore(sviluppatoreCorrente));

    }


    private void gestProfilo(Sviluppatore sviluppatoreCorrente) {
        gestioneProfilo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog finestraGestione = new JDialog();
                finestraGestione.setTitle("Modifica profilo");
                finestraGestione.setModal(true);
                finestraGestione.setSize(500, 300);
                finestraGestione.setLayout(new GridLayout(4, 2));
                finestraGestione.setLocationRelativeTo(null);

                JTextField campoNome = new JTextField(controller.getNomeSviluppatore(sviluppatoreCorrente), 15);
                JPanel pannelloNome = new JPanel(new GridBagLayout());
                pannelloNome.add(campoNome);
                finestraGestione.add(new JLabel("Nome:", SwingConstants.CENTER));
                finestraGestione.add(pannelloNome);


                JPasswordField campoPass = new JPasswordField(15);
                JPanel pannelloPass = new JPanel(new GridBagLayout());
                pannelloPass.add(campoPass);
                finestraGestione.add(new JLabel("Nuova password:", SwingConstants.CENTER));
                finestraGestione.add(pannelloPass);


                JTextArea campoDescrizione = new JTextArea(controller.getDescrizioneSviluppatore(sviluppatoreCorrente), 4, 20);
                JPanel pannelloDescrizione = new JPanel();
                JScrollPane scrollDescrizione = new JScrollPane(campoDescrizione);
                pannelloDescrizione.add(scrollDescrizione);
                finestraGestione.add(new JLabel("Nuova descrizione:", SwingConstants.CENTER));
                finestraGestione.add(pannelloDescrizione);


                JPanel pannelloSalva = new JPanel(new GridBagLayout());
                JButton pulsanteSalva = new JButton("Salva");
                finestraGestione.add(new JLabel(" ", SwingConstants.CENTER));
                pannelloSalva.add(pulsanteSalva);
                finestraGestione.add(pannelloSalva);

                pulsanteSalva.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {

                            String nome = campoNome.getText();
                            String descrizione = campoDescrizione.getText();
                            String password = new String(campoPass.getPassword());

                            controller.aggiornaProfiloSviluppatore(sviluppatoreCorrente, nome, descrizione, password);

                            finestraGestione.dispose();
                        } catch (CampoNonValidoException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                });


                finestraGestione.setVisible(true);
                profilo(sviluppatoreCorrente);
            }
        });
    }


    private void partecipaPromozione() {
        partecipaPromozione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Gioco giocoSelezionato = listaGiochiAggiunti.getSelectedValue();

                if (giocoSelezionato == null) {
                    JOptionPane.showMessageDialog(null, "Attenzione: seleziona prima un gioco dalla lista!");
                    return;
                }

                JDialog dialogPartecipa = new JDialog();
                dialogPartecipa.setTitle("Partecipa a Promozione");
                dialogPartecipa.setModal(true);
                dialogPartecipa.setSize(400, 200);
                dialogPartecipa.setLocationRelativeTo(null);
                dialogPartecipa.setLayout(new GridLayout(3, 2, 40, 40));

                String[] nomiPromozioni = new String[0];
                ArrayList<Promozione> promozioniEsistenti ;

                try {
                    promozioniEsistenti = controller.getListaPromozioni();
                    nomiPromozioni = new String[promozioniEsistenti.size()];

                    for (int i = 0; i < promozioniEsistenti.size(); i++) {
                        nomiPromozioni[i] = promozioniEsistenti.get(i).getNome();
                    }

                } catch (CampoNonValidoException es) {
                    JOptionPane.showMessageDialog(null, "Errore caricamento promozioni: " + es.getMessage());
                    return;
                }

                JComboBox<String> comboPromozioni = new JComboBox<>(nomiPromozioni);
                JTextField textSconto = new JTextField(5);

                JButton btnAnnulla = new JButton("Annulla");
                JButton btnConferma = new JButton("Conferma");

                dialogPartecipa.add(new JLabel("Seleziona Promozione:", SwingConstants.CENTER));
                dialogPartecipa.add(comboPromozioni);

                dialogPartecipa.add(new JLabel("Sconto (%):", SwingConstants.CENTER));
                dialogPartecipa.add(textSconto);

                dialogPartecipa.add(btnAnnulla);
                dialogPartecipa.add(btnConferma);

                btnAnnulla.addActionListener(e2 -> dialogPartecipa.dispose());

                btnConferma.addActionListener(e2 -> {
                    try {
                        int indiceScelto = comboPromozioni.getSelectedIndex();
                        Promozione promoScelta = promozioniEsistenti.get(indiceScelto);

                        int percentualeSconto = Integer.parseInt(textSconto.getText());


                        controller.partecipaAPromozione(giocoSelezionato, promoScelta, percentualeSconto);

                        JOptionPane.showMessageDialog(null, "Gioco inserito in promozione con successo!");
                        dialogPartecipa.dispose();

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Inserisci un numero intero valido per lo sconto (es. 20)!");
                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage());
                    }
                });

                dialogPartecipa.setVisible(true);
            }
        });
    }

    private void gestioneLogout(JFrame accediGUI) {
        pulsanteLogout.addActionListener(e -> {
            int conferma = JOptionPane.showConfirmDialog(
                    null,
                    "Sei sicuro di voler effettuare il logout?",
                    "Conferma Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (conferma == JOptionPane.YES_OPTION) {

                accediGUI.setVisible(true);
                sviluppatoreFrame.dispose();
                
            }
        });
    }

    private void pulisciCampiInserimento() {
        // ripulisce i campi scritti in precedenza
        textTitolo.setText("");
        textPegi.setText("");
        textPrezzo.setText("");
        textDataRilascio.setText("GG/MM/AAAA");
        textDataRilascio.setForeground(Color.GRAY);
        aggCategoria.setSelectedIndex(0);

        textPrezzo.setFocusable(true);
        textPrezzo.setEditable(true);
        textDataRilascio.setFocusable(true);
        textDataRilascio.setEditable(true);

        for (JCheckBox campoVuoto : listaCheckboxGeneri) {
            campoVuoto.setSelected(false);
        }

        for (JCheckBox campoVuotoP : listaCheckboxPiattaforma) {
            campoVuotoP.setSelected(false);
            campoVuotoP.setEnabled(true);

        }
    }

    private void configuraDimensioniScroll(JPanel pannello, int numeroElementi) {
        if (pannello.getParent() instanceof JViewport viewport) {
            if (viewport.getParent() instanceof JScrollPane scrollPane) {
                int larghezzaAttuale = scrollPane.getPreferredSize().width;
                scrollPane.setPreferredSize(new Dimension(larghezzaAttuale, 150));
                scrollPane.setMaximumSize(new Dimension(larghezzaAttuale, 150));
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            }
        }
        int altezzaTotale = numeroElementi * 25;
        pannello.setPreferredSize(new Dimension(150, altezzaTotale));
        pannello.revalidate();
        pannello.repaint();
    }
}

