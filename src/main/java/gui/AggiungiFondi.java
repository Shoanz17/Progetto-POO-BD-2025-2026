package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggiungiFondi {

    private JPanel aggiungiFondiPanel;
    private JButton pulsante5Euro;
    private JButton pulsante15Euro;
    private JButton pulsante30Euro;
    private JButton pulsante60Euro;
    private JButton pulsante80Euro;
    private JTextField textFieldAltro;
    private JButton pulsanteAggiungi;
    private JLabel testoAggiunta;
    private JLabel testoAltro;
    private JLabel testo€;

    public JFrame aggiungiFondiFrame;

    public AggiungiFondi(JFrame homeUtente){

        aggiungiFondiFrame = new JFrame("Aggiungi fondi");
        aggiungiFondiFrame.setContentPane(aggiungiFondiPanel);
        aggiungiFondiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        aggiungiFondiFrame.pack();
        aggiungiFondiFrame.setLocationRelativeTo(homeUtente);
        aggiungiFondiFrame.setVisible(true);


        //pulsanti
        pulsante5Euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiungiFondiFrame.dispose();
            }
        });
        pulsante15Euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiungiFondiFrame.dispose();
            }
        });
        pulsante30Euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiungiFondiFrame.dispose();
            }
        });
        pulsante60Euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiungiFondiFrame.dispose();
            }
        });
        pulsante80Euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiungiFondiFrame.dispose();
            }
        });
        pulsanteAggiungi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiungiFondiFrame.dispose();
            }
        });
    }
}
