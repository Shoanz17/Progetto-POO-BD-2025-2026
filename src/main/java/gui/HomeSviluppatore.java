package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class HomeSviluppatore {
    private JPanel homeSviluppatore;
    private JTabbedPane Insights;
    private JList listaTitoli;
    private JPanel pannelloDettagli;
    private JLabel Titolo;
    private JLabel Genere;


    public HomeSviluppatore() {
        listaTitoli.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //questo if serve a evitare che Java esegua il codice due volte
                if (!e.getValueIsAdjusting()) {

                    //prendiamo il gioco cliccato
                    String giocoSelezionato = (String) listaTitoli.getSelectedValue();

                    if (giocoSelezionato != null) {
                        //aggiorniamo le Label in tempo reale
                        Titolo.setText("Titolo: " + giocoSelezionato);
                        Genere.setText("Genere: Azione");
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