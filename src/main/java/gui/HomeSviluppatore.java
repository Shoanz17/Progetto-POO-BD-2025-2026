package gui;

import controller.Controller;
import model.*;
//import model.Sviluppatore;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.time.LocalDate;
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
    private JList <Gioco>listaGiochiAggiunti;
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
    private JComboBox modificaCategoriaCombo;

    private List<JCheckBox> listaCheckboxGeneri = new ArrayList<>();
    private List<JCheckBox> listaCheckboxPiattaforma = new ArrayList<>();

    private DefaultListModel<Gioco> modelPannelloControllo;
    private DefaultListModel<Gioco> modelLibreria;
    private Controller controller;

    public HomeSviluppatore() {
        Controller controller = new Controller();
        this.controller = controller;



        popolaListe();
        selezioneListaLibreria();
        pannelloAggMod();
        inserimentoGioco();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomeSviluppatore");
        frame.setContentPane(new HomeSviluppatore().homeSviluppatore);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
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
            }
        }

        // colleghiamo i modelli alle liste visive
        listaTitoli.setModel(modelLibreria);
        listaGiochiAggiunti.setModel(modelPannelloControllo);


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
                        // 1. accumuliamo tutti i generi
                        String generiUniti = "";
                        for (Genere g : giocoSelezionato.getGeneri()) {
                            if (!generiUniti.isEmpty()) {
                                generiUniti += ", "; // aggiunge la virgola tra un genere e l'altro
                            }
                            generiUniti += g.toString();
                        }

                        // 2. accumuliamo tutte le piattaforme dalle edizioni
                        String piattaformeUnite = "";
                        for (EdizioneGioco ed : giocoSelezionato.getEdizioni()) {
                            if (!piattaformeUnite.isEmpty()) {
                                piattaformeUnite += ", "; // Aggiunge la virgola tra le piattaforme
                            }
                            piattaformeUnite += ed.getPiattaforma().getNome();
                        }

                        // 3. aggiorniamo le Label con le stringhe complete
                        Titolo.setText("Titolo: " + giocoSelezionato.getTitolo());
                        lblCategoria.setText("Categoria: " + giocoSelezionato.getCategoria());
                        lblGenere.setText("Genere: " + generiUniti);
                        Piattaforma.setText("Piattaforma: " + piattaformeUnite);
                        pegi.setText("Pegi: " + giocoSelezionato.getPegi());
                    }


                }
            }
        });
    }


    private void pannelloAggMod(){
    listaGiochiAggiunti.addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                Gioco giocoSelezionato = listaGiochiAggiunti.getSelectedValue();
                if(giocoSelezionato!= null)
                {
                    aggModButton.setText("Salva modifiche");
                    textTitolo.setText(giocoSelezionato.getTitolo());
                    textPegi.setText(String.valueOf(giocoSelezionato.getPegi()));
                    aggCategoria.setSelectedItem(giocoSelezionato.getCategoria());

                    if (!giocoSelezionato.getEdizioni().isEmpty()) {
                        EdizioneGioco ed = giocoSelezionato.getEdizioni().get(0);
                        textPrezzo.setText(String.valueOf(ed.getPrezzo()));
                        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        textDataRilascio.setText(ed.getDataRilascio().format(formatter));

                        for(JCheckBox cb : listaCheckboxGeneri ) {cb.setSelected(false);}

                        for(JCheckBox cb : listaCheckboxGeneri ){
                            for(Genere g : giocoSelezionato.getGeneri())
                            {
                            {if(cb.getText().equals(g.toString())){cb.setSelected(true);}}

                            }
                        }


                        for (JCheckBox cbP : listaCheckboxPiattaforma){cbP.setSelected(false);}

                        for(JCheckBox cbP : listaCheckboxPiattaforma)
                        {if(cbP.getText().equals(ed.getPiattaforma().getNome())){cbP.setSelected(true);}}
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
        textDataRilascio.setForeground(java.awt.Color.GRAY);
        textDataRilascio.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textDataRilascio.getText().equals("GG/MM/AAAA")) {
                    textDataRilascio.setText("");
                    textDataRilascio.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textDataRilascio.getText().trim().isEmpty()) {
                    textDataRilascio.setText("GG/MM/AAAA");
                    textDataRilascio.setForeground(java.awt.Color.GRAY);
                }
            }
        });

        /*String[] generiTest = {
                "Azione", "Avventura", "RPG", "Strategia",
                "Sport", "Corse", "Simulazione", "Picchiaduro",
                "Horror", "Puzzle", "Arcade", "Sparatutto"
        };*/

        generiPanel.setLayout(new BoxLayout(generiPanel, BoxLayout.Y_AXIS));

        for (Genere nomeGenere : controller.getGeneri()) {
            JCheckBox cb = new JCheckBox(nomeGenere.toString());
            listaCheckboxGeneri.add(cb);
            generiPanel.add(cb);
        }

        if (generiPanel.getParent() instanceof JViewport) {
            JViewport viewport = (JViewport) generiPanel.getParent();
            if (viewport.getParent() instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) viewport.getParent();


                int larghezzaAttuale = scrollPane.getPreferredSize().width;
                scrollPane.setPreferredSize(new java.awt.Dimension(larghezzaAttuale, 150));
                scrollPane.setMaximumSize(new java.awt.Dimension(larghezzaAttuale, 150));

                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            }
        }

        int altezzaTotale = listaCheckboxGeneri.size() * 25;
        generiPanel.setPreferredSize(new java.awt.Dimension(150, altezzaTotale));

        generiPanel.revalidate();
        generiPanel.repaint();


//        String[] piattaformaTest = {
//                "PS4", "PS5", "Nintendo Switch", "PC",
//                "WII", "XBOX", "XBOX360"
//        };

        piattaformaPanel.setLayout(new BoxLayout(piattaformaPanel, BoxLayout.Y_AXIS));

        for (PiattaformaDiGioco nomePiattaforma : controller.getListaPiattaforma()) {
            JCheckBox cb = new JCheckBox(nomePiattaforma.toString());
            listaCheckboxPiattaforma.add(cb);
            piattaformaPanel.add(cb);
        }

        if (piattaformaPanel.getParent() instanceof JViewport) {
            JViewport viewport = (JViewport) piattaformaPanel.getParent();
            if (viewport.getParent() instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) viewport.getParent();


                int larghezzaAttuale = scrollPane.getPreferredSize().width;
                scrollPane.setPreferredSize(new java.awt.Dimension(larghezzaAttuale, 150));
                scrollPane.setMaximumSize(new java.awt.Dimension(larghezzaAttuale, 150));

                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            }
        }

        int altezzaTotalePan = listaCheckboxPiattaforma.size() * 25;
        piattaformaPanel.setPreferredSize(new java.awt.Dimension(150, altezzaTotalePan));

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
                int pegi = Integer.parseInt(pegiStr);
                double prezzo = Double.parseDouble(prezzoStr.replace(",", "."));


                // conversioni dei tipi di dati
                Sviluppatore sviluppatoreLoggato = new Sviluppatore("Sviluppatore test", "uaomA11@", "salve a tutti");


                ArrayList<Genere> listaGeneri = new ArrayList<>();


                for (JCheckBox cb : listaCheckboxGeneri) {
                    if (cb.isSelected()) {
                        listaGeneri.add(new Genere(cb.getText()));
                    }
                }

                ArrayList<PiattaformaDiGioco> listaPiattaforma = new ArrayList<>();

                for (JCheckBox cbo : listaCheckboxPiattaforma) {
                    if (cbo.isSelected()) {

                        listaPiattaforma.add(new PiattaformaDiGioco(cbo.getText(), "Generico", false));
                    }
                }


                pulisciCampiInserimento();

                JOptionPane.showMessageDialog(null, "Gioco ed Edizione inseriti con successo!");
            if (giocoSelezionato!= null)
            {
                giocoSelezionato.setTitolo(titolo);
                giocoSelezionato.setPegi(pegi);
                giocoSelezionato.setCategoria(categoriaEnum);
                giocoSelezionato.getGeneri().clear();

            }else{ Gioco nuovoGioco = new Gioco(titolo, categoriaEnum, pegi, sviluppatoreLoggato, listaGeneri);

                // convertiamo la stringa della data in un oggetto LocalDate
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataLocale = LocalDate.parse(dataRilascio, formatter);

                for (PiattaformaDiGioco newPiattaforma : listaPiattaforma) {
                    EdizioneGioco nuovaEdizione = new EdizioneGioco(nuovoGioco, newPiattaforma, (int) prezzo, dataLocale);
                    nuovoGioco.addEdizione(nuovaEdizione);
                }


                modelPannelloControllo.addElement(nuovoGioco);
                modelLibreria.addElement(nuovoGioco);}

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Errore nei dati numerici!\nIl PEGI deve essere un intero.\nIl Prezzo deve essere un numero decimale.");
            }
            catch (CampoNonValidoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
             catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Formato data non valido! Usa il formato GG/MM/AAAA.");
            }
        });
    }

    private void pulisciCampiInserimento() {
        // ripulisce i campi scritti in precedenza
        textTitolo.setText("");
        textPegi.setText("");
        textPrezzo.setText("");
        textDataRilascio.setText("GG/MM/AAAA");
        textDataRilascio.setForeground(java.awt.Color.GRAY);
        aggCategoria.setSelectedIndex(0);

        for (JCheckBox campoVuoto : listaCheckboxGeneri) {
            campoVuoto.setSelected(false);
        }// deseleziona la checkbox dopo che l' utente fa "aggiungi gioco"

        for (JCheckBox campoVuotoP : listaCheckboxPiattaforma) {
            campoVuotoP.setSelected(false);
        }
    }
}

