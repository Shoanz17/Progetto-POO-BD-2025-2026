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
                        configuraInterfacciaRecensioni(); // Ricarica la tabella aggiornata

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
