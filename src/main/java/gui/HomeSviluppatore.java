package gui;

import controller.Controller;
import model.*;
//import model.Sviluppatore;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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



        popolaListe();
        ricercaListaLib();
        ricercaPannello();
        selezioneListaLibreria();
        graficaLibreriaGen();
        pannelloAggMod();
        inserimentoGioco();
        profilo(controller.getListaSviluppatoriLoggati().get(0));
        gestProfilo(controller.getListaSviluppatoriLoggati().get(0));


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomeSviluppatore");
        frame.setContentPane(new HomeSviluppatore().homeSviluppatore);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100,628);
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

    private void filtraLista(String testoCercato,DefaultListModel<Gioco> modelloDestinazione)
        {
            modelloDestinazione.clear();


            for(Gioco gioco: listaCompletaGiochi) {
                String titolo = gioco.getTitolo().toLowerCase();

                if (titolo.contains(testoCercato)) {
                    modelloDestinazione.addElement(gioco);
                }
            }
        }


    private void ricercaListaLib() {
        barraRicerca.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                String testoCercato = barraRicerca.getText().toLowerCase();
                filtraLista(testoCercato,modelLibreria);
            }
        });
    }

    private void ricercaPannello()
    {
        ricercaPannello.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String testoCercato = ricercaPannello.getText().toLowerCase();
                filtraLista(testoCercato,modelPannelloControllo);
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
                        textAreaGeneri.setText("Genere: " + generiUniti);
                        Piattaforma.setText("Piattaforma: " + piattaformeUnite);
                        pegi.setText("Pegi: " + giocoSelezionato.getPegi());

                    }


                }
            }
        });
    }

    private void graficaLibreriaGen()
    {
        textAreaGeneri.setText("Genere:");
        textAreaGeneri.setLineWrap(true);
        textAreaGeneri.setWrapStyleWord(true);
        textAreaGeneri.setEditable(false);
        textAreaGeneri.setOpaque(false);
        textAreaGeneri.setFont(Titolo.getFont());
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
                            {{if(cb.getText().equals(g.toString())){cb.setSelected(true);}}}
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


        piattaformaPanel.setLayout(new BoxLayout(piattaformaPanel, BoxLayout.Y_AXIS));

        for (PiattaformaDiGioco nomePiattaforma : controller.getPiattaformeDiGioco()) {
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
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataLocale = LocalDate.parse(dataRilascio, formatter);
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



            if (giocoSelezionato!= null)
            {
                giocoSelezionato.setTitolo(titolo);
                giocoSelezionato.setPegi(pegi);
                giocoSelezionato.setCategoria(categoriaEnum);


                for (EdizioneGioco ed : new ArrayList<EdizioneGioco>( giocoSelezionato.getEdizioni()))
                {giocoSelezionato.removeEdizione(ed);}


                for (PiattaformaDiGioco newPiattaforma : listaPiattaforma) {
                    EdizioneGioco edizioneMod = new EdizioneGioco(giocoSelezionato, newPiattaforma, (int) prezzo, dataLocale);
                    giocoSelezionato.addEdizione(edizioneMod);
                }

                for (Genere g : new ArrayList<Genere>( giocoSelezionato.getGeneri()))
                    {giocoSelezionato.removeGenere(g);}

                for(Genere g : listaGeneri)
                    {giocoSelezionato.addGenere(g);}

                JOptionPane.showMessageDialog(null, "Modifiche salvate con Successo!");


            }else

                { Gioco nuovoGioco = new Gioco(titolo, categoriaEnum, pegi, sviluppatoreLoggato, listaGeneri);


                for (PiattaformaDiGioco newPiattaforma : listaPiattaforma) {
                    EdizioneGioco nuovaEdizione = new EdizioneGioco(nuovoGioco, newPiattaforma, (int) prezzo, dataLocale);
                    nuovoGioco.addEdizione(nuovaEdizione);
                }


                modelPannelloControllo.addElement(nuovoGioco);
                modelLibreria.addElement(nuovoGioco);
                JOptionPane.showMessageDialog(null, "Gioco ed Edizione inseriti con successo!");

                }

                pulisciCampiInserimento();
                aggModButton.setText("Aggiungi gioco");


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

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaGiochiAggiunti.clearSelection();
                pulisciCampiInserimento();
                aggModButton.setText("Aggiungi gioco");
            }

    });

        rimuoviGiocoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Stop killing games!");
            }
        });
    }

    private void profilo(Sviluppatore sviluppatoreCorrente)
        {
            nomeSviluppatore.setText(controller.getNomeSviluppatore(sviluppatoreCorrente));
            descrizioneArea.setText("wao siamo il team sega e ci facciamo tante seggi, fasmgneornsdfznfmgsz,xczgmfszxvzz");
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
                JFrame finestraGestione = new JFrame("Modifica Profilo");
                finestraGestione.setSize(500,300);
                finestraGestione.setLayout( new GridLayout(4,2));
//                finestraGestione.pack();
                finestraGestione.setLocationRelativeTo(null);
                finestraGestione.setVisible(true);

                JTextField campoNome = new JTextField(sviluppatoreCorrente.getNome(),15);
                JPanel pannelloNome = new JPanel(new GridBagLayout());
                pannelloNome.add(campoNome);
                finestraGestione.add(new JLabel("Nome:",SwingConstants.CENTER));
                finestraGestione.add(pannelloNome);


                JPasswordField campoPass = new JPasswordField(15);
                JPanel pannelloPass = new JPanel(new GridBagLayout());
                pannelloPass.add(campoPass);
                finestraGestione.add(new JLabel("Nuova password:",SwingConstants.CENTER));
                finestraGestione.add(pannelloPass);


                JTextArea campoDescrizione = new JTextArea(sviluppatoreCorrente.getDescrizione(),4,20);
                JPanel pannelloDescrizione =  new JPanel();
                JScrollPane scrollDescrizione = new JScrollPane(campoDescrizione);
                pannelloDescrizione.add(scrollDescrizione);
                finestraGestione.add(new JLabel("Nuova descrizione:",SwingConstants.CENTER));
                finestraGestione.add(pannelloDescrizione);


                JPanel pannelloSalva = new JPanel(new GridBagLayout());
                JButton pulsanteSalva = new JButton("Salva");
                finestraGestione.add(new JLabel(" ",SwingConstants.CENTER));
                pannelloSalva.add(pulsanteSalva);
                finestraGestione.add(pannelloSalva);

                pulsanteSalva.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            sviluppatoreCorrente.setNome(campoNome.getText());
                            sviluppatoreCorrente.setDescrizione(campoDescrizione.getText());
                            sviluppatoreCorrente.setPassword(new String(campoPass.getPassword()));

                            finestraGestione.dispose();
                        }
                        catch (CampoNonValidoException ex)
                        {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
                        }

                    }
                });


                {
                    try{

                        sviluppatoreCorrente.setNome(campoNome.getText());
                        sviluppatoreCorrente.setDescrizione(campoDescrizione.getText());
                        if(campoPass.getPassword().length > 0)
                        {
                            String nuovaPass = new String(campoPass.getPassword());
                            sviluppatoreCorrente.setPassword(nuovaPass);
                        }

                    }

                    catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog
                                (null,"i dati che hai scelto non sono validi");
                    }

                }

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

