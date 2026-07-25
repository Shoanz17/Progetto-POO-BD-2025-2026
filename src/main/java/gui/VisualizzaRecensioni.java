package gui;

import controller.Controller;
import model.CampoNonValidoException;
import model.Fattura;
import model.Recensione;
import model.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Rappresenta la finestra personale dedicata all'{@link Utente} per consultare e gestire lo storico delle proprie valutazioni.
 * Questa interfaccia grafica (View) mostra in formato tabellare tutte le {@link Recensione} rilasciate dall'utente loggato,
 * recuperandole tramite le rispettive ricevute d'acquisto ({@link Fattura}).
 * Le dinamiche principali gestite da questa schermata includono:
 * - Lettura dei dettagli: cliccando su una specifica riga della tabella, viene estratto e mostrato il testo completo del parere.
 * - Pentimento e rimozione: l'utente può decidere di cancellare definitivamente una propria recensione tramite l'apposito pulsante.
 * - Ogni operazione di caricamento dati (select) o eliminazione (delete) è rigorosamente delegata al {@link Controller},
 *   che aggiorna il database e conferma il successo dell'operazione alla View per aggiornare la tabella in tempo reale.
 */
public class VisualizzaRecensioni {
    private JPanel visualizzaRecensioniPanel;
    private JTable tabellaRecensioni;
    private JTextArea textDescrizione;
    private JButton pulsanteRimuoviRecensione;

    public JFrame visualizzaRecensioniFrame;
    private Utente utenteLoggato;
    private HomeUtente homeUtente;
    private Controller controller;

    private ArrayList<Fattura> acquistiUtenteConRecensione;

    public VisualizzaRecensioni(Controller controller, HomeUtente homeUtente, Utente utenteLoggato) {

        this.utenteLoggato = utenteLoggato;
        this.homeUtente = homeUtente;
        this.controller = controller;

        configuraInterfaccia();

        associaListenerTabella();
        associaListenerRimuoviRecensione();

        mostraForm(homeUtente.homeUtenteFrame);

    }

    private void configuraInterfaccia() {
        visualizzaRecensioniFrame = new JFrame("Recensioni");
        visualizzaRecensioniFrame.setContentPane(visualizzaRecensioniPanel);
        visualizzaRecensioniFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        configuraInterfacciaRecensioni();
    }

    private void configuraInterfacciaRecensioni() {
        String[] colonne = {"Edizione Gioco", "Piattaforma", "Voto", "Differenza Like"};
        acquistiUtenteConRecensione = new ArrayList<>();

        DefaultTableModel modelloRecensioni = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            for (Recensione r : controller.getListaRecensioniUtente(controller.getIdUtente(utenteLoggato))) {
                Fattura f = controller.getFatturaDaRecensione(r);

                Object[] riga = {controller.getTitoloDaFattura(f), controller.getPiattaformaDaFattura(f), controller.getVotoDaFattura(f), controller.getDifferenzaLikeDaFattura(f)};
                modelloRecensioni.addRow(riga);
                acquistiUtenteConRecensione.add(f);
            }
        } catch (CampoNonValidoException e) {
            JOptionPane.showMessageDialog(visualizzaRecensioniFrame,e.getMessage());
        }
        tabellaRecensioni.setModel(modelloRecensioni);
    }

    private void associaListenerTabella() {
        tabellaRecensioni.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int rigaSelezionata = tabellaRecensioni.getSelectedRow();
                if (rigaSelezionata != -1) {
                    Fattura f = acquistiUtenteConRecensione.get(rigaSelezionata);
                    textDescrizione.setText(controller.getDescrizioneRecensioneDaFattura(f));
                }
            }
        });
    }

    private void associaListenerRimuoviRecensione() {
        pulsanteRimuoviRecensione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaRecensioni.getSelectedRow();
                if (rigaSelezionata != -1) {
                    Fattura f = acquistiUtenteConRecensione.get(rigaSelezionata);
                    try {
                        controller.rimuoviRecensioneSelezionataDaFattura(f);

                        JOptionPane.showMessageDialog(visualizzaRecensioniFrame, "Recensione rimossa con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        textDescrizione.setText("");

                        acquistiUtenteConRecensione.remove(rigaSelezionata);
                        ((DefaultTableModel) tabellaRecensioni.getModel()).removeRow(rigaSelezionata);

                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(visualizzaRecensioniFrame, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(visualizzaRecensioniFrame, "Selezionare una recensione dalla tabella!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void mostraForm(JFrame homeUtente) {
        visualizzaRecensioniFrame.pack();
        visualizzaRecensioniFrame.setLocationRelativeTo(homeUtente);
        visualizzaRecensioniFrame.setVisible(true);
    }
}
