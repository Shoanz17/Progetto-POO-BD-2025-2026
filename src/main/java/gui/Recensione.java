package gui;

import controller.Controller;
import model.CampoNonValidoException;
import model.Fattura;
import model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Recensione {
    private JPanel recensionePanel;
    private JButton pulsanteRilasciaRecensione;
    private JSpinner spinnerVoto;
    private JTextArea textAreaDescrizione;
    private JLabel testoVoto;
    private JLabel testoNomeGioco;
    private JLabel testoDescrizione;

    public JFrame recensioneFrame;
    private Controller controller;
    private HomeUtente homeUtente;
    private Utente utenteLoggato;
    private Fattura fatturaSelezionata;

    public Recensione(Controller controller, HomeUtente homeUtente, Utente utenteLoggato, Fattura fatturaSelezionata) {

        this.controller = controller;
        this.homeUtente = homeUtente;
        this.utenteLoggato = utenteLoggato;
        this.fatturaSelezionata = fatturaSelezionata;

        configuraInterfaccia();

        associaListenerRilasciaRecensione();

        mostraForm(homeUtente.homeUtenteFrame);
    }

    private void associaListenerRilasciaRecensione(){
        pulsanteRilasciaRecensione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta = 0;
                if (controller.getRecensioneDaFattura(fatturaSelezionata) != null) {
                    risposta = JOptionPane.showConfirmDialog(recensioneFrame, "Sovrascriverai la recensione giá lasciata", "Sicuro?", JOptionPane.YES_NO_OPTION);
                }
                if (risposta == JOptionPane.YES_OPTION) {
                    try {
                        if (controller.getRecensioneDaFattura(fatturaSelezionata) == null) {
                            controller.rilasciaRecensione((Integer) spinnerVoto.getValue(), textAreaDescrizione.getText(), fatturaSelezionata);
                        } else controller.aggiornaRecensione((Integer) spinnerVoto.getValue(), textAreaDescrizione.getText(), fatturaSelezionata);

                        JOptionPane.showMessageDialog(recensioneFrame, "Recensione rilasciata correttamente!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        recensioneFrame.dispose();

                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(recensioneFrame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void configuraInterfaccia() {
        recensioneFrame = new JFrame("Recensione");
        recensioneFrame.setContentPane(recensionePanel);
        recensioneFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        testoNomeGioco.setText(controller.getTitoloDaFattura(fatturaSelezionata));
        configuraSpinnerVoto();
    }

    private void configuraSpinnerVoto(){
        spinnerVoto.setModel(new SpinnerNumberModel(0, 0, 100, 1));
    }

    private void mostraForm(JFrame homeUtente) {
        recensioneFrame.pack();
        recensioneFrame.setLocationRelativeTo(homeUtente);
        recensioneFrame.setVisible(true);
    }

}
