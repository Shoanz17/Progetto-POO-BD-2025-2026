package gui;

import model.Categoria;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class HomeSviluppatore {
    private JPanel homeSviluppatore;
    private JTabbedPane finestre;
    private JList<String> listaTitoli; // Resa generica <String> per evitare l'errore del cast!
    private JPanel pannelloDettagli;
    private JLabel Titolo;
    private JLabel Genere;
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
    private JList list1;
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


    private DefaultListModel<String> modelPannelloControllo;
    private DefaultListModel<String> modelLibreria; // Il motore grafico per la lista della Libreria

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
                    String giocoSelezionato = listaTitoli.getSelectedValue();

                    if (giocoSelezionato != null) {
                        // aggiorniamo le Label in tempo reale
                        Titolo.setText("Titolo: " + giocoSelezionato);
                        Genere.setText("Genere: Azione");
                        Piattaforma.setText("Piattaforma: PC / Console");
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

        // inizializziamo la lista di destra
        modelPannelloControllo = new DefaultListModel<>();
        list1.setModel(modelPannelloControllo);

        // Inizializziamo la Libreria e colleghiamola
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
            if (titolo.isEmpty() || genere.isEmpty() || piattaforma.isEmpty() ||
                    pegiStr.isEmpty() || prezzoStr.isEmpty() || dataRilascio.isEmpty() || dataRilascio.equals("GG/MM/AAAA")) { // ◄ AGGIUNTA QUI

                JOptionPane.showMessageDialog(null, "Per favore, compila tutti i campi, inclusa la data!");
                return;
            }

            try {
                // conversioni dei tipi di dati
                int pegi = Integer.parseInt(pegiStr);
                double prezzo = Double.parseDouble(prezzoStr.replace(",", "."));

                // formattare in modo che si veda nella lista
                String rigaLista = titolo; // ◄ MODIFICATO: Adesso passa solo il titolo!
                modelPannelloControllo.addElement(rigaLista);

                // aggiunge lo stesso titolo appena creato anche nella JList della Libreria
                modelLibreria.addElement(titolo);

                pulisciCampiInserimento();

                JOptionPane.showMessageDialog(null, "Gioco ed Edizione inseriti con successo!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Errore nei dati numerici!\nIl PEGI deve essere un intero.\nIl Prezzo deve essere un numero decimale.");
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

