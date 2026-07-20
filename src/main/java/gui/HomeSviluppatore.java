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
    private JLabel strike;
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
    private JComboBox modificaCategoriaCombo;

    private List<JCheckBox> listaCheckboxGeneri = new ArrayList<>();
    private List<JCheckBox> listaCheckboxPiattaforma = new ArrayList<>();

    private DefaultListModel<Gioco> modelPannelloControllo;
    private DefaultListModel<Gioco> modelLibreria;
    ArrayList<Gioco> listaCompletaGiochi = new ArrayList<>();
    private Controller controller;


    public HomeSviluppatore() {

        Controller controller = new Controller();
        this.controller = controller;

        controller.caricaPromozioniFittizie();

        popolaListe();
        ricercaListaLib();
        ricercaPannello();
        selezioneListaLibreria();
        graficaLibreriaGen();
        pannelloAggMod();
        inserimentoGioco();
        profilo(controller.getListaSviluppatoriLoggati().get(0));
        gestProfilo(controller.getListaSviluppatoriLoggati().get(0));
        reset();
        rimuoviGioco();
        partecipaPromozione();
        gestioneLogout();


//        listaTitoli.clearSelection();
//        informazioniGioco.setVisible(false);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomeSviluppatore");
        frame.setContentPane(new HomeSviluppatore().homeSviluppatore);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1155, 700);
//        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }


    private void popolaListe() {
        // creiamo i modelli corretti
        modelLibreria = new DefaultListModel<>();
        modelPannelloControllo = new DefaultListModel<>();


        // prendiamo i giochi dal controller
        ArrayList<Gioco> tuttiIGiochi = controller.getListaGiochi();


        // riempiamo i modelli con gli oggetti Gioco
        if (tuttiIGiochi != null) {
            for (Gioco gioco : tuttiIGiochi) {
                modelLibreria.addElement(gioco);
                modelPannelloControllo.addElement(gioco);
                listaCompletaGiochi.add(gioco);
            }
        }

        // colleghiamo i modelli alle liste visive
        listaTitoli.setModel(modelLibreria);
        listaGiochiAggiunti.setModel(modelPannelloControllo);

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
//                        informazioniGioco.setVisible(true);
                        // 3. aggiorniamo le Label con le stringhe complete
                        Titolo.setText(controller.getTitoloDaGioco(giocoSelezionato));
                        lblCategoria.setText("Categoria: " + controller.getCategoriaDaGioco(giocoSelezionato));
                        textAreaGeneri.setText("Genere: " + controller.getGenereDaGioco(giocoSelezionato));
                        Piattaforma.setText("Piattaforma: " + controller.getStringPiattaformeDaGioco(giocoSelezionato));
                        pegi.setText("Pegi: " + controller.getPegiDaGioco(giocoSelezionato));
                        promozioniAttive.setText("Promozione: "+ controller.getStringaPromozioniPerGioco(giocoSelezionato));
                        areaRecensioni.setText(controller.getStringaRecensioniPerGioco(giocoSelezionato));
                        areaRecensioni.setCaretPosition(0);


                    }

//                    else {informazioniGioco.setVisible(false);}
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


//        areaRecensioni.setFont(Titolo.getFont());
        areaRecensioni.setEditable(false);
//        areaRecensioni.setLineWrap(true);
//        areaRecensioni.setWrapStyleWord(true);
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

//
                        textPrezzo.setText(String.valueOf(controller.getPrezzoPrimaEdizioneDaGioco(giocoSelezionato)));
                        textDataRilascio.setText(controller.getDataRilascioPrimaEdizioneFormattata(giocoSelezionato));


                        for (JCheckBox cb : listaCheckboxGeneri) {
                            cb.setSelected(false);
                        }

                        for (JCheckBox cb : listaCheckboxGeneri) {
                            for (Genere g : controller.getListaGeneriDaGioco(giocoSelezionato)) {
                                {
                                    if (cb.getText().equals(g.toString())) {
                                        cb.setSelected(true);
                                    }
                                }
                            }
                        }


                        for (JCheckBox cbP : listaCheckboxPiattaforma) {
                            cbP.setSelected(false);
                            cbP.setEnabled(true);
                        }

                        for (EdizioneGioco edizioneGioco : controller.getEdizioniDaGioco(giocoSelezionato)) {
                            for (JCheckBox cbP : listaCheckboxPiattaforma) {
                                if (cbP.getText().equals(controller.getPiattaformaDaEdizioneGioco(edizioneGioco).getNome())) {
                                    cbP.setSelected(true);
                                    cbP.setEnabled(false);
                                }

                            }
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

        for (Genere nomeGenere : controller.getGeneri()) {
            JCheckBox cb = new JCheckBox(nomeGenere.toString());
            listaCheckboxGeneri.add(cb);
            generiPanel.add(cb);
        }

        if (generiPanel.getParent() instanceof JViewport viewport) {
            if (viewport.getParent() instanceof JScrollPane scrollPane) {

                int larghezzaAttuale = scrollPane.getPreferredSize().width;
                scrollPane.setPreferredSize(new Dimension(larghezzaAttuale, 150));
                scrollPane.setMaximumSize(new Dimension(larghezzaAttuale, 150));

                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            }
        }

        int altezzaTotale = listaCheckboxGeneri.size() * 25;
        generiPanel.setPreferredSize(new Dimension(150, altezzaTotale));

        generiPanel.revalidate();
        generiPanel.repaint();


        piattaformaPanel.setLayout(new BoxLayout(piattaformaPanel, BoxLayout.Y_AXIS));

        for (PiattaformaDiGioco nomePiattaforma : controller.getPiattaformeDiGioco()) {
            JCheckBox cb = new JCheckBox(nomePiattaforma.toString());
            listaCheckboxPiattaforma.add(cb);
            piattaformaPanel.add(cb);
        }

        if (piattaformaPanel.getParent() instanceof JViewport viewport) {
            if (viewport.getParent() instanceof JScrollPane scrollPane) {


                int larghezzaAttuale = scrollPane.getPreferredSize().width;
                scrollPane.setPreferredSize(new Dimension(larghezzaAttuale, 150));
                scrollPane.setMaximumSize(new Dimension(larghezzaAttuale, 150));

                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            }
        }

        int altezzaTotalePan = listaCheckboxPiattaforma.size() * 25;
        piattaformaPanel.setPreferredSize(new Dimension(150, altezzaTotalePan));

        piattaformaPanel.revalidate();
        piattaformaPanel.repaint();

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


                Sviluppatore sviluppatoreLoggato = controller.getListaSviluppatoriLoggati().getFirst();


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
                            (titolo, pegi, categoriaEnum, listaGeneri, listaPiattaforma, prezzo, dataLocale, sviluppatoreLoggato);


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

                ArrayList<Promozione> promozioniEsistenti = controller.getListaPromozioni();
                String[] nomiPromozioni = new String[promozioniEsistenti.size()];
                for (int i = 0; i < promozioniEsistenti.size(); i++) {
                    nomiPromozioni[i] = promozioniEsistenti.get(i).getNome();
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

    private void gestioneLogout() {
        pulsanteLogout.addActionListener(e -> {
            // Mostra un popup di conferma (opzionale ma molto carino per l'utente)
            int conferma = JOptionPane.showConfirmDialog(
                    null,
                    "Sei sicuro di voler effettuare il logout?",
                    "Conferma Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (conferma == JOptionPane.YES_OPTION) {
                // 1. Trova e chiude definitivamente la finestra della HomeSviluppatore
                // Sostituisci "homeSviluppatore" col nome del tuo JPanel principale se diverso
                JFrame frameCorrente = (JFrame) SwingUtilities.getWindowAncestor(homeSviluppatore);
                if (frameCorrente != null) {
                    frameCorrente.dispose();
                }

                // 2. Apre una nuova finestra di Login pulita
                new Accedi();
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

        for (JCheckBox campoVuoto : listaCheckboxGeneri) {
            campoVuoto.setSelected(false);
        }// deseleziona la checkbox dopo che l' utente fa "aggiungi gioco"

        for (JCheckBox campoVuotoP : listaCheckboxPiattaforma) {
            campoVuotoP.setSelected(false);
            campoVuotoP.setEnabled(true);

        }
    }
}

