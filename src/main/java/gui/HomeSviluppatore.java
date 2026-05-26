package gui;

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
    private JLabel aggCategoria;
    private JTextField textPegi;
    private JLabel aggPegi;
    private JButton aggiungiGiocoButton;
    private JComboBox comboBox1;

    public HomeSviluppatore() {
        listaTitoli.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // questo if serve a evitare che Java esegua il codice due volte
                if (!e.getValueIsAdjusting()) {

                    // prendiamo il gioco cliccato
                    String giocoSelezionato = listaTitoli.getSelectedValue();

                    if (giocoSelezionato != null) {
                        // aggiorniamo le Label in tempo reale con il testo pulito
                        Titolo.setText("Titolo: " + giocoSelezionato);
                        Genere.setText("Genere: Azione");
                        Piattaforma.setText("Piattaforma: PC / Console");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomeSviluppatore");
        frame.setContentPane(new HomeSviluppatore().homeSviluppatore);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}