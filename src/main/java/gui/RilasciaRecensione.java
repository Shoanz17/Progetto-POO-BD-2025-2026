package gui;

import controller.Controller;
import model.CampoNonValidoException;
import model.Fattura;
import model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Rappresenta la finestra a comparsa (popup) che permette a un {@link Utente} di valutare un gioco.
 * Questa interfaccia si appoggia a una specifica {@link Fattura} d'acquisto, garantendo alla base
 * che solo chi possiede realmente la copia possa recensirla.
 * Le dinamiche principali gestite da questa View includono:
 * - Raccolta delle opinioni: acquisisce un voto numerico (costretto tra 0 e 100 da uno Spinner) e un parere testuale.
 * - Gestione delle sovrascritture: interroga il {@link Controller} per capire se l'utente ha già valutato
 *   questo acquisto in passato. In caso affermativo, chiede conferma prima di rimpiazzare la vecchia recensione.
 * - Salvataggio: intercetta il click del pulsante e passa i dati
 *   al Controller, lasciando a lui il compito della validazione e della scrittura su database.
 */
public class RilasciaRecensione {
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

    public RilasciaRecensione(Controller controller, HomeUtente homeUtente, Utente utenteLoggato, Fattura fatturaSelezionata) {

        this.controller = controller;
        this.homeUtente = homeUtente;
        this.utenteLoggato = utenteLoggato;
        this.fatturaSelezionata = fatturaSelezionata;

        configuraInterfaccia();
        associaListenerRilasciaRecensione();
        mostraForm(homeUtente.homeUtenteFrame);
    }

    private void associaListenerRilasciaRecensione() {
        pulsanteRilasciaRecensione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int voto = (Integer) spinnerVoto.getValue();
                    String testo = textAreaDescrizione.getText().trim();

                    if (controller.haGiaRecensito(fatturaSelezionata)) {
                        int risposta = JOptionPane.showConfirmDialog(recensioneFrame, "Hai già rilasciato una recensione per questo acquisto. Vuoi sovrascriverla?", "Conferma Sovrascrittura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (risposta != JOptionPane.YES_OPTION) {
                            return;
                        }
                    }

                    controller.rilasciaRecensione(voto, testo, fatturaSelezionata);

                    JOptionPane.showMessageDialog(recensioneFrame, "Recensione salvata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    recensioneFrame.dispose();

                } catch (CampoNonValidoException | SQLException ex) {
                    JOptionPane.showMessageDialog(recensioneFrame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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

    private void configuraSpinnerVoto() {
        spinnerVoto.setModel(new SpinnerNumberModel(0, 0, 100, 1));
    }

    private void mostraForm(JFrame homeUtente) {
        recensioneFrame.pack();
        recensioneFrame.setLocationRelativeTo(homeUtente);
        recensioneFrame.setVisible(true);
    }
}