package gui;

import controller.Controller;
import model.Admin;
import model.CampoNonValidoException;
import model.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HomeAdmin {
    private JPanel adminPanel;
    private JTabbedPane tabbedPane;
    private JButton pulsanteLogout;
    private JTextField ricercaUtenti;
    private JList listaGeneri;
    private JButton pulsanteAggiungiPiattaforma;
    private JTextArea descrizioneRecensioneUtenti;
    private JCheckBox checkBoxBannatiUtenti;
    private JButton pulsanteAggiungiGenere;
    private JTextField campoNomeGenere;
    private JTextField ricercaGeneri;
    private JButton pulsanteBannaUtenteUtenti;
    private JButton pulsanteRimborsa;
    private JTextField ricercaPiattaforme;
    private JCheckBox checkBoxPortabilita;
    private JTextField campoNomePiattaforma;
    private JTextField campoProduttore;
    private JTextField campoNomePromozione;
    private JTextField campoDataInizio;
    private JTextField campoDataFine;
    private JButton pulsanteRimuoviRecensioneUtenti;
    private JTable tabellaUtenti;
    private JButton pulsanteAggiungiPromozione;
    private JTextField ricercaPromozioni;
    private JButton pulsanteOrdinaPerData;
    private JTextField ricercaSviluppatori;
    private JCheckBox checkBoxBannatiSviluppatori;
    private JButton pulsanteAggiungiStrike;
    private JList listaSviluppatori;
    private JTable tabellaGiochiSviluppatori;
    private JButton pulsanteRimuoviStrike;
    private JList listaGiochi;
    private JButton pulsanteAggiungi;
    private JButton pulsanteRimuovi;
    private JButton pulsanteModificaTitolo;
    private JButton pulsanteModificaCategoria;
    private JButton pulsanteModificaPegi;
    private JButton pulsanteAggiungiStrikeGiochi;
    private JTextArea descrizioneSviluppatori;
    private JTextField ricercaRecensioni;
    private JTextArea descrizioneRecensioneRecensioni;
    private JButton pulsanteRimuoviRecensioneRecensioni;
    private JTable tabellaRecensioni;
    private JTable tabellaGiochiUtenti;
    private JTable tabellaPromozioni;
    private JTable tabellaPiattaforme;
    private JPanel utenti;
    private JPanel sviluppatori;
    private JPanel giochi;
    private JPanel generi;
    private JPanel piattaforme;
    private JPanel recensioni;
    private JPanel promozioni;
    private JTable tabellaRecensioniUtenti;
    private JButton pulsanteBannaUtenteRecensioni;
    private JTextField ricercaGiochi;
    private JLabel titoloGioco;
    private JLabel categoriaGIoco;
    private JLabel pegiGioco;
    private JList listaGeneriGioco;
    private JLabel nomeGenere;
    private JLabel nomePIattaforma;
    private JLabel produttorePiattaforma;
    private JLabel portabilitaPiattaforma;
    private JLabel nomePromozione;
    private JLabel dataInizioPromozione;
    private JLabel dataFinePromozione;

    private JFrame adminFrame;
    private Controller controller;
    private Admin admin;

    public HomeAdmin(Controller controller, JFrame accediGUI, Admin admin){
        if(controller == null) throw new IllegalArgumentException("Controller passato inesistente");
        if(admin == null) throw new IllegalArgumentException("Admin passato inesistente");
        this.controller = controller;
        this.admin = admin;

        configuraInterfaccia();

        associaListenerCheckBoxBannatiUtenti();
        associaListenerRicercaUtenti();
        associaListenerTabellaUtenti();

        associaListenerPulsanteBannaUtenteUtenti();
        associaListenerPulsanteLogout(accediGUI);

        mostraForm();

    }

    private void configuraInterfaccia(){
        adminFrame = new JFrame("HomeAdmin");
        adminFrame.setContentPane(adminPanel);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setMinimumSize(new Dimension(900, 600));

        String[] colonneUtenti = {"ID", "Nickname", "Email", "Data di Nascita", "Saldo"};
        configuraTabella(colonneUtenti, tabellaUtenti);
        filtraUtenti(); //riempio la tabella senza filtri

        String[] colonneRecensioni = {"Voto", "Differenza Like"};
        configuraTabella(colonneRecensioni, tabellaRecensioniUtenti);

        String[] colonneGiochi = {"Titolo", "Categoria", "PEGI", "Generi", "Piattaforma", "Prezzo"};
        configuraTabella(colonneGiochi, tabellaGiochiUtenti);
    }

    private void configuraTabella(String[] colonne, JTable tabella) {

        DefaultTableModel modelloIniziale = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //rende la tabella non editabile all'utente
            }
        };

        //assegniamo il modello vuoto alla tabella
        tabella.setModel(modelloIniziale);
    }

    private void filtraUtenti() {
        String testoRicerca = ricercaUtenti.getText().toLowerCase().trim();

        ArrayList<Object[]> righe = new ArrayList<>();

        //filtro in base alla checkBox e alla barra di ricerca
        boolean flag = checkBoxBannatiUtenti.isSelected();
        for(Utente utente : controller.getListaUtentiLoggati()){
            if(utente.isBannato() == flag && utente.getNome().toLowerCase().contains(testoRicerca)){
                Object[] riga = {
                        utente.getId(),
                        utente.getNome(),
                        utente.getEmail(),
                        utente.getDataNascita(),
                        utente.getSaldo()
                };
                righe.add(riga);
            }
        }

        aggiornaContenutoTabella(tabellaUtenti, righe);
    }

    private void associaListenerCheckBoxBannatiUtenti(){
        checkBoxBannatiUtenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //implementato così perché volevo provare e vedere come funziona l'operatore ternario
                String testo = (checkBoxBannatiUtenti.isSelected()) ? "Sbanna" : "Banna";
                pulsanteBannaUtenteUtenti.setText(testo);
                filtraUtenti();
            }
        });
    }

    private void associaListenerRicercaUtenti(){
        ricercaUtenti.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtraUtenti();
            }
        });
    }

    private void associaListenerTabellaUtenti(){
        tabellaUtenti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rigaSelezionata = tabellaUtenti.getSelectedRow();

                if (rigaSelezionata != -1) {
                    Object selezione = tabellaUtenti.getValueAt(rigaSelezionata, 1);
                    System.out.println("Utente cliccato con il mouse: " + selezione);
                    //riempio le altre liste pescando i dati dal db
                }
            }
        });
    }

    private void associaListenerPulsanteBannaUtenteUtenti(){
        pulsanteBannaUtenteUtenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tabellaUtenti.getSelectedRow();

                if(rigaSelezionata != -1){
                    int risposta = JOptionPane.showConfirmDialog(adminFrame,"Sicuro di voler " + pulsanteBannaUtenteUtenti.getText().toLowerCase() + "re l'utente " + tabellaUtenti.getValueAt(rigaSelezionata, 1) + "?",
                            "Ban", JOptionPane.YES_NO_OPTION);

                    if(risposta == JOptionPane.YES_OPTION) {
                        try {

                            controller.invertiStatoBan((int) tabellaUtenti.getValueAt(rigaSelezionata, 0));
                            filtraUtenti();

                        } catch (CampoNonValidoException ex) {

                            JOptionPane.showMessageDialog(adminFrame, ex.getMessage());

                        }
                    }

                } else JOptionPane.showMessageDialog(adminFrame, "Nessun utente selezionato", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void associaListenerPulsanteLogout(JFrame accediGUI){
        pulsanteLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accediGUI.setVisible(true);
                adminFrame.dispose();
            }
        });
    }

    private void aggiornaContenutoTabella(JTable tabella, ArrayList<Object[]> righe){
        //prendiamo il modello già esistente della tabella facendo il cast
        DefaultTableModel model = (DefaultTableModel) tabella.getModel();

        //cancella tutte le righe vecchie mantenendo intatte le colonne
        model.setRowCount(0);

        for(Object[] riga : righe) model.addRow(riga);
    }

    private void mostraForm(){
        adminFrame.pack();
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

}
