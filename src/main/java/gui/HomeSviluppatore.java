package gui;

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
    private JList<Gioco> listaTitoli; // Resa generica <String> per evitare l'errore del cast1
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
    private JButton aggiungiGiocoButton;
    private JComboBox aggCategoria;
    private JList listaGiochiAggiunti;
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
    private JComboBox comboPiattaforma;
    private JPanel generiPanel;

    private List<JCheckBox> listaCheckboxGeneri = new ArrayList<>();


    private DefaultListModel<Gioco> modelPannelloControllo;
    private DefaultListModel<Gioco> modelLibreria;

    public HomeSviluppatore() {

        selezioneListaLibreria();
        inserimentoGioco();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomeSviluppatore");
        frame.setContentPane(new HomeSviluppatore().homeSviluppatore);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    private void selezioneListaLibreria() {
        listaTitoli.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // questo if serve a evitare di eseguire il codice 2 vpltr
                if (!e.getValueIsAdjusting()) {

                    // prendiamo il gioco cliccato
                    Gioco giocoSelezionato = listaTitoli.getSelectedValue();

                    if (giocoSelezionato != null) {
                        // aggiorniamo le Label in tempo reale
                        Titolo.setText("Titolo: " + giocoSelezionato.getTitolo());
                        lblCategoria.setText("Categoria: " + giocoSelezionato.getCategoria());
                        lblGenere.setText("Genere: " + giocoSelezionato.getGeneri().get(0).toString());
                        Piattaforma.setText("Piattaforma: " + giocoSelezionato.getEdizioni().get(0).getPiattaforma().getNome());
                        pegi.setText("Pegi: " + giocoSelezionato.getPegi());
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
        // carica i valori dell'enum Categoria dentro la ComboBox
        aggCategoria.setModel(new DefaultComboBoxModel<>(Categoria.values()));

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

        String[] generiTest = {
                "Azione", "Avventura", "RPG", "Strategia",
                "Sport", "Corse", "Simulazione", "Picchiaduro",
                "Horror", "Puzzle", "Arcade", "Sparatutto"
        };

        generiPanel.setLayout(new BoxLayout(generiPanel, BoxLayout.Y_AXIS));

        for (String nomeGenere : generiTest) {
            JCheckBox cb = new JCheckBox(nomeGenere);
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

        generiPanel.revalidate();
        generiPanel.repaint();

        // inizializziamo la lista di destra
        modelPannelloControllo = new DefaultListModel<>();
        listaGiochiAggiunti.setModel(modelPannelloControllo);

        // inizializziamo la Libreria e colleghiamola
        modelLibreria = new DefaultListModel<>();
        listaTitoli.setModel(modelLibreria);
    }

    private void configuraAzioneAggiungi() {
        aggiungiGiocoButton.addActionListener(e -> {
            String titolo = textTitolo.getText().trim();
            String genere = textGenere.getText().trim();
            Categoria categoriaEnum = (Categoria) aggCategoria.getSelectedItem();
            String piattaforma = textPiattaforma.getText().trim();
            String pegiStr = textPegi.getText().trim();
            String prezzoStr = textPrezzo.getText().trim();
            String dataRilascio = textDataRilascio.getText().trim(); // ◄ NUOVO CAMPO

            // Controlliamo che anche la data sia compilata
            if (titolo.isEmpty() || piattaforma.isEmpty() ||
                    pegiStr.isEmpty() || prezzoStr.isEmpty() || dataRilascio.isEmpty() || dataRilascio.equals("GG/MM/AAAA")) {

                JOptionPane.showMessageDialog(null, "Per favore, compila tutti i campi, inclusa la data!");
                return;
            }




            try {
                // conversioni dei tipi di dati
                int pegi = Integer.parseInt(pegiStr);
                double prezzo = Double.parseDouble(prezzoStr.replace(",", "."));
                Sviluppatore sviluppatoreLoggato = new Sviluppatore("Sviluppatore test", "uaomA11@", "salve a tutti");

                Genere nuovoGenere = new Genere(genere);
                ArrayList<Genere> listaGeneri = new ArrayList<>();
                listaGeneri.add(nuovoGenere);

                for (JCheckBox cb : listaCheckboxGeneri) {
                    if (cb.isSelected()) {
                        listaGeneri.add(new Genere(cb.getText()));
                    }
                }



                Gioco nuovoGioco = new Gioco(titolo, categoriaEnum, pegi, sviluppatoreLoggato, listaGeneri);
                PiattaformaDiGioco piattaformaTest = new PiattaformaDiGioco(piattaforma,"sony",false);

                // convertiamo la stringa della data in un oggetto LocalDate
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataLocale = LocalDate.parse(dataRilascio, formatter);

                // istanziamo la classe associativa EdizioneGioco
                // convertiamo il prezzo in int con (int) perché il tuo modello richiede un intero
                EdizioneGioco nuovaEdizione = new EdizioneGioco(nuovoGioco, piattaformaTest, (int) prezzo, dataLocale);

                //colleghiamo l'edizione appena creata al gioco
                nuovoGioco.addEdizione(nuovaEdizione);

                modelPannelloControllo.addElement(nuovoGioco);
                modelLibreria.addElement(nuovoGioco);

                pulisciCampiInserimento();

                JOptionPane.showMessageDialog(null, "Gioco ed Edizione inseriti con successo!");

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
        textGenere.setText("");
        textPiattaforma.setText("");
        textPegi.setText("");
        textPrezzo.setText("");
        textDataRilascio.setText("GG/MM/AAAA");
        textDataRilascio.setForeground(java.awt.Color.GRAY);
        aggCategoria.setSelectedIndex(0);
    }
}

