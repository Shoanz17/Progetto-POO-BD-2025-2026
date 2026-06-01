package gui;

import controller.Controller;
import model.Admin;

import javax.swing.*;
import java.awt.*;

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
    private JButton pulsanteBanna;
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
    private JButton pulsanteBannaUtente;
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

    public HomeAdmin(Controller controller, JFrame accediGUI, Admin admin){
        adminFrame = new JFrame("HomeAdmin");
        adminFrame.setContentPane(adminPanel);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setMinimumSize(new Dimension(900, 600));
        adminFrame.pack();
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }
}
