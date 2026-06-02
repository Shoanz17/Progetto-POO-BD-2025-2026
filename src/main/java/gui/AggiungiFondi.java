package gui;

import controller.Controller;
import model.CampoNonValidoException;
import model.Utente;

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
    private Utente utenteLoggato;
    private Controller controller;
    private HomeUtente homeUtente;

    public AggiungiFondi(Controller controller, HomeUtente homeUtente, Utente utenteLoggato) {
        this.utenteLoggato = utenteLoggato;
        this.controller = controller;
        this.homeUtente = homeUtente;

        configuraInterfaccia();

        associaListenerPulsante5Euro();
        associaListenerPulsante15Euro();
        associaListenerPulsante30Euro();
        associaListenerPulsante60Euro();
        associaListenerPulsante80Euro();
        associaListenerPulsanteAggiungi();

        mostraForm(homeUtente.homeUtenteFrame);
    }

    //pulsanti
    private void associaListenerPulsante5Euro() {
        pulsante5Euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.aggiungiSaldo(utenteLoggato, 5);

                    homeUtente.configuraTestoSaldo();
                    aggiungiFondiFrame.dispose();

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(aggiungiFondiFrame, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void associaListenerPulsante15Euro() {
        pulsante15Euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.aggiungiSaldo(utenteLoggato, 15);

                    homeUtente.configuraTestoSaldo();
                    aggiungiFondiFrame.dispose();

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(aggiungiFondiFrame, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void associaListenerPulsante30Euro() {
        pulsante30Euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.aggiungiSaldo(utenteLoggato, 30);

                    homeUtente.configuraTestoSaldo();
                    aggiungiFondiFrame.dispose();

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(aggiungiFondiFrame, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void associaListenerPulsante60Euro() {
        pulsante60Euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.aggiungiSaldo(utenteLoggato, 60);

                    homeUtente.configuraTestoSaldo();
                    aggiungiFondiFrame.dispose();

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(aggiungiFondiFrame, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private void associaListenerPulsante80Euro() {
        pulsante80Euro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.aggiungiSaldo(utenteLoggato, 80);

                    homeUtente.configuraTestoSaldo();
                    aggiungiFondiFrame.dispose();

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(aggiungiFondiFrame, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void associaListenerPulsanteAggiungi() {
        pulsanteAggiungi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.aggiungiSaldo(utenteLoggato, textFieldAltro.getText());

                    homeUtente.configuraTestoSaldo();
                    aggiungiFondiFrame.dispose();

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(aggiungiFondiFrame, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void mostraForm(JFrame homeUtente) {
        aggiungiFondiFrame.pack();
        aggiungiFondiFrame.setLocationRelativeTo(homeUtente);
        aggiungiFondiFrame.setVisible(true);
    }

    private void configuraInterfaccia() {
        aggiungiFondiFrame = new JFrame("Aggiungi fondi");
        aggiungiFondiFrame.setContentPane(aggiungiFondiPanel);
        aggiungiFondiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
