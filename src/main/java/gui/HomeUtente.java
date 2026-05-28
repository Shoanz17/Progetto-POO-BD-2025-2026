package gui;

import controller.Controller;
import model.Sviluppatore;
import model.Utente;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeUtente {
    private JPanel homeUtentePanel;
    private JTabbedPane tabbedPane;
    private JPanel catalogo;
    private JPanel profilo;
    private JPanel libreria;
    private JTextField ricercaLibreria;
    private JList listaLibreria;
    private JToolBar toolBarLibreria;
    private JLabel edizioneLibreria;
    private JLabel piattaformaDiGiocoLibreria;
    private JLabel dataRilascioLibreria;
    private JLabel prezzoAcquistoLibreria;
    private JLabel categoriaLibreria;
    private JLabel pegiLibreria;
    private JLabel keyLibreria;
    private JLabel dataAcquistoLibreria;
    private JButton pulsanteFiltra;
    private JButton pulsanteResetFiltri;
    private JComboBox categorieFiltro;
    private JButton dataFiltro;
    private JButton prezzoAcquistoFiltro;
    private JComboBox categoriaFiltro;
    private JComboBox pegiFiltro;
    private JButton dataAcquistoFiltro;
    private JButton pulsanteRecensione;
    private JButton pulsanteCopiaKey;
    private JScrollPane scrollPaneLibreria;
    private JButton pulsanteAggiungiSaldo;
    private JButton pulsanteModificaInformazioni;
    private JList listaAmiciUtente;
    private JTextField ricercaAmici;
    private JCheckBox checkBoxAmici;
    private JButton pulsanteVisualizzaRecensioni;
    private JList listaSviluppatori;
    private JTextField ricercaSviluppatori;
    private JCheckBox checkBoxSeguiti;
    private JButton pulsanteSegui;
    private JButton pulsanteAggiungiAmico;
    private JLabel testoDataCreazioneAccount;
    private JLabel testoBannato;
    private JLabel testoNome;
    private JLabel testoGenere;
    private JLabel testoEmail;
    private JLabel testoDataDiNascita;
    private JLabel testoNumeroGiochiAcquistati;
    private JLabel testoNumeroRecensioniRilasciate;
    private JLabel testoSaldo;
    private JScrollPane scrollPaneListaAmici;
    private JScrollPane scrollPaneListaSviluppatori;
    private JLabel testoGiochiRilasciati;
    private JLabel testoGiocoPiuVenduto;
    private JLabel testoGiochiAcquistatiAmico;
    private JLabel testoNumeroRecensioniAmico;
    private JLabel testoGenereAmico;
    private JLabel testoBannatoAmico;
    private JToolBar toolBarSviluppatore;
    private JToolBar toolBarAmico;
    private JLabel testoProfilo;
    private JLabel testoCommunity;
    private JPanel carrello;
    private JList listaCatalogo;
    private JButton pulsanteAggiungiAlCarrello;
    private JComboBox piattaformaFiltroCatalogo;
    private JSlider sliderPrezzoCatalogo;
    private JComboBox genereFiltroCatalogo;
    private JComboBox pegiFiltroCatalogo;
    private JButton pulsanteDataDiRilascioFiltroCatalogo;
    private JComboBox categoriaFiltroCatalogo;
    private JCheckBox checkBoxInPromozione;
    private JCheckBox checkBoXSviluppatori;
    private JList listaRecensioniCatalogo;
    private JTextField ricercaCatalogo;
    private JLabel pegiCatalogo;
    private JLabel genereCatalogo;
    private JLabel piattaformaCatalogo;
    private JLabel prezzoCatalogo;
    private JLabel categoriaCatalogo;
    private JTextArea descrizioneRecensioneCatalogo;
    private JButton pulsanteDislike;
    private JButton pulsanteLike;
    private JLabel descrizioneCatalogo;
    private JLabel votoCatalogo;
    private JLabel recensioniCatalogo;
    private JToolBar toolBarCatalogo;
    private JLabel prezzoFiltroCatalogo;
    private JLabel dataDiRilascioCatalogo;
    private JLabel sviluppatoreCatalogo;
    private JLabel valutazioneRecensioneCatalogo;
    private JLabel generiLibreria;
    private JLabel sviluppatoreLibreria;
    private JTextArea descrizioneSviluppatoreProfilo;
    private JButton pulsanteRimuoviAmico;
    private JButton pulsanteLogout;
    private JTable tabellaGiochiCarrello;
    private JButton pulsanteRimuoviCarrello;
    private JLabel testoTotaleCarrello;
    private JButton pulsanteAcquista;
    private JLabel testoMediaVoti;

    public JFrame homeUtenteFrame;
    private Controller controller;
    private Utente utenteLoggato;

    public HomeUtente(Controller controller, JFrame accediGUI, Utente accountLoggato) {
        this.controller = controller;
        this.utenteLoggato = accountLoggato;

        svuotaDettagliLibreria();
        configuraInterfaccia();
        configuraTabellaCarrello();

        associaListenerCopiaKey();
        associaListenerLogout(accediGUI);
        associaListenerRecensione();
        associaListenerModificaInformazioni();
        associaListenerAggiungiSaldo();
        associaListenerRimuoviCarrello();
        associaListenerAcquista();

        associaListenerListaSviluppatori();
        associaListenerListaAmici();

        mostraForm();

    }
    private void configuraTabellaCarrello() {
        String[] colonne = {"Titolo Gioco", "Piattaforma", "Prezzo"};
        DefaultTableModel tabellaCarrello = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabellaGiochiCarrello.setModel(tabellaCarrello);
    }

    private void associaListenerCopiaKey() {
        pulsanteCopiaKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyDaCopiare = keyLibreria.getText();
                //l'ho trovato su internet. Permette di copiare un testo nella clipboard di Windows mac e linux)
                java.awt.datatransfer.StringSelection selezione = new java.awt.datatransfer.StringSelection(keyDaCopiare);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selezione, null);
            }
        });
    }

    private void associaListenerLogout(JFrame accediGUI) {
        pulsanteLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta = JOptionPane.showConfirmDialog(homeUtenteFrame, "Vuoi uscire?", "Logout", JOptionPane.YES_NO_OPTION);
                if (risposta == JOptionPane.YES_OPTION) {
                    homeUtenteFrame.dispose();
                    accediGUI.setVisible(true);
                }
                //else non fa nulla
            }
        });
    }

    private void associaListenerRecensione() {
        pulsanteRecensione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Recensione recensione = new Recensione(homeUtenteFrame);
            }
        });
    }

    private void associaListenerModificaInformazioni() {
        pulsanteModificaInformazioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModificaInformazioni modificaInformazioni = new ModificaInformazioni(homeUtenteFrame);
            }
        });
    }

    private void associaListenerAggiungiSaldo() {
        pulsanteAggiungiSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiungiFondi aggiungiFondi = new AggiungiFondi(homeUtenteFrame);
            }
        });
    }

    private void associaListenerRimuoviCarrello() {
        pulsanteRimuoviCarrello.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaGiochiCarrello.getSelectedRow();

                if (rigaSelezionata != -1) {
                    DefaultTableModel tabellaCarrello = (DefaultTableModel) tabellaGiochiCarrello.getModel();
                    tabellaCarrello.removeRow(rigaSelezionata);
                    ricalcolaTotaleCarrello();
                } else {
                    JOptionPane.showMessageDialog(homeUtenteFrame, "Seleziona prima un gioco dalla tabella per rimuoverlo!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void associaListenerAcquista() {
        pulsanteAcquista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tabellaCarrello = (DefaultTableModel) tabellaGiochiCarrello.getModel();
                double saldoUtente = 0.0; //Aggiunto per testing
                double calcoloTotale = ricalcolaTotaleCarrello();

                if (tabellaCarrello.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(homeUtenteFrame, "Vuoi acquistare il nulla?", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
                else if (calcoloTotale > saldoUtente) {  //saldoUtente va cambiata con la variabile di saldo giusta
                    JOptionPane.showMessageDialog(homeUtenteFrame, "Saldo insufficiente", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    saldoUtente -= calcoloTotale;
                    testoSaldo.setText(String.format("%.2f €", saldoUtente));

                    tabellaCarrello.setRowCount(0);
                    testoTotaleCarrello.setText("0.00 €");

                    JOptionPane.showMessageDialog(homeUtenteFrame, "Acquisto completato, troverai i tuoi giochi con relative Key nella Libreria", "Successo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void associaListenerListaSviluppatori() {
        listaSviluppatori.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Sviluppatore sviluppatoreSelezionato = (Sviluppatore) listaSviluppatori.getSelectedValue();

                if (sviluppatoreSelezionato != null) {
                    descrizioneSviluppatoreProfilo.setText(sviluppatoreSelezionato.getDescrizione());
                    testoGiochiRilasciati.setText("Numero di giochi rilasciati: "+String.valueOf(sviluppatoreSelezionato.getListaGiochi().size()));
                    //testoGiocoPiuVenduto.setText(String.valueOf(controller.getGiocoPiuVendutoSviluppatore(sviluppatoreSelezionato)));  DA FARE CON DAO DISPONIBILE
                }
            }
        });
    }

    private void associaListenerListaAmici(){
        listaAmiciUtente.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Utente utenteSelezionato = (Utente) listaAmiciUtente.getSelectedValue();

                if (utenteSelezionato != null){
                    testoGiochiAcquistatiAmico.setText("Numero giochi acquistati: "+String.valueOf(utenteSelezionato.getGiochiAcquistati().size()));
                    testoNumeroRecensioniAmico.setText("Numero recensioni rilasciate: "+String.valueOf(controller.getNumeroRecensioniUtente(utenteSelezionato)));
                    testoGenereAmico.setText("Genere: "+String.valueOf(utenteSelezionato.getGenere()));
                    if (utenteSelezionato.isBannato() == true){
                        testoBannatoAmico.setText("Bannato: Si");
                    } else testoBannatoAmico.setText("Bannato: No");
                }
            }
        });
    }

    private double ricalcolaTotaleCarrello() {
        double totale = 0.0;
        DefaultTableModel tabellaCarrello = (DefaultTableModel) tabellaGiochiCarrello.getModel();

        for (int i = 0; i < tabellaCarrello.getRowCount(); i++) {
            double prezzo = (Double) tabellaCarrello.getValueAt(i, 2);
            totale += prezzo;
        }
        testoTotaleCarrello.setText(String.format("%.2f €", totale));

        return totale;
    }

    //Svuota i dati della Libreria
    void svuotaDettagliLibreria() {
        edizioneLibreria.setText("-");
        piattaformaDiGiocoLibreria.setText("-");
        prezzoAcquistoLibreria.setText("-");
        keyLibreria.setText("[Key]");
        dataAcquistoLibreria.setText("-");
        generiLibreria.setText("-");
        sviluppatoreLibreria.setText("-");
        pegiLibreria.setText("-");
        categoriaLibreria.setText("-");
        dataRilascioLibreria.setText("-");

        // Disabilita i pulsanti per evitare click nulli
        pulsanteCopiaKey.setEnabled(false);
        pulsanteRecensione.setEnabled(false);
    }

    private void configuraInterfaccia() {
        homeUtenteFrame = new JFrame("Home Utente");
        homeUtenteFrame.setContentPane(homeUtentePanel);
        homeUtenteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        configuraInterfacciaProfilo();
    }

    private void mostraForm() {
        homeUtenteFrame.pack();
        homeUtenteFrame.setLocationRelativeTo(null);
        homeUtenteFrame.setVisible(true);
    }

    private void configuraInterfacciaProfilo() {
        testoNome.setText(utenteLoggato.getNome());
        testoGenere.setText(String.valueOf(utenteLoggato.getGenere()));
        testoEmail.setText(utenteLoggato.getEmail());
        testoDataDiNascita.setText(String.valueOf(utenteLoggato.getDataNascita()));
        testoSaldo.setText(String.format("%d €",utenteLoggato.getSaldo()));
        testoDataCreazioneAccount.setText("Data di creazione dell'account:"+String.valueOf(utenteLoggato.getDataCreazione()));
        testoBannato.setVisible(utenteLoggato.isBannato());
        testoNumeroGiochiAcquistati.setText("Numero giochi acquistati: " +String.valueOf(utenteLoggato.getGiochiAcquistati().size()));
        testoNumeroRecensioniRilasciate.setText("Numero recensioni rilasciate:" +String.valueOf(controller.getNumeroRecensioniUtente(utenteLoggato)));

        DefaultListModel<Utente> modelloListaAmici = new DefaultListModel<>();
        modelloListaAmici.addAll(controller.getListaUtentiLoggati());
        modelloListaAmici.removeElement(utenteLoggato);
        listaAmiciUtente.setModel(modelloListaAmici);

        DefaultListModel<Sviluppatore> modelloListaSviluppatori = new DefaultListModel<>();
        modelloListaSviluppatori.addAll(controller.getListaSviluppatoriLoggati());
        listaSviluppatori.setModel(modelloListaSviluppatori);

        testoGiochiRilasciati.setText("Giochi Rilasciati: -");
        testoGiocoPiuVenduto.setText("Gioco piú venduto: -");

        testoGiochiAcquistatiAmico.setText("Numero giochi acquistati: -");
        testoNumeroRecensioniAmico.setText("Numero recensioni rilasciate: -");
        testoGenereAmico.setText("Genere: -");
        testoBannatoAmico.setText("Bannato: -");

    }

}
