package gui;

import controller.Controller;
import model.CampoNonValidoException;
import model.Fattura;
import model.Recensione;
import model.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

        DefaultTableModel modelloRecensioni = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Recensione r : controller.getListaRecensioniUtente(utenteLoggato)) {
            Fattura f = controller.getFatturaDaRecensione(r);

            Object[] riga = {controller.getTitoloDaFattura(f), controller.getPiattaformaDaFattura(f), controller.getVotoDaFattura(f), controller.getDifferenzaLikeDaFattura(f)};
            modelloRecensioni.addRow(riga);
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
                    try { // Temporaneo
                        controller.rimuoviRecensioneSelezionata(f);

                        JOptionPane.showMessageDialog(visualizzaRecensioniFrame, "Recensione rimossa!");
                        textDescrizione.setText("");
                        configuraInterfacciaRecensioni();

                    } catch (CampoNonValidoException ex) {
                        JOptionPane.showMessageDialog(visualizzaRecensioniFrame, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
                    }
                } else
                    JOptionPane.showMessageDialog(visualizzaRecensioniFrame, "Selezionare una recensione!", "Errore", JOptionPane.ERROR_MESSAGE);

            }
        });
    }

    private void mostraForm(JFrame homeUtente) {
        visualizzaRecensioniFrame.pack();
        visualizzaRecensioniFrame.setLocationRelativeTo(homeUtente);
        visualizzaRecensioniFrame.setVisible(true);
    }
}
