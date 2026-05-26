package gui;

import javax.swing.*;
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
    private JLabel dataCreazioneAccount;
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
    private JButton pulsanteAcquista;
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
    private JTextArea descrizioneSviluppaotreProfilo;
    private JButton pulsanteRimuoviAmico;
    private JButton pulsanteLogout;

    public static JFrame homeUtenteFrame;

    public HomeUtente() {
//        homeUtente = new JFrame("homeUtente");
//        homeUtente.setContentPane(new homeUtente().homeUtente);
//        homeUtente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        homeUtente.pack();
//        homeUtente.setVisible(true);

        SvuotaDettagliLibreria();

        //copia key
        pulsanteCopiaKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyDaCopiare = keyLibreria.getText();
                //l'ho trovato su internet. Permette di copiare un testo nella clipboard di Windows mac e linux)
                java.awt.datatransfer.StringSelection selezione = new java.awt.datatransfer.StringSelection(keyDaCopiare);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selezione, null);
            }
        });

        //logout
        pulsanteLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int risposta = JOptionPane.showConfirmDialog(homeUtenteFrame,"Vuoi uscire?","Logout",JOptionPane.YES_NO_OPTION);
                if(risposta == JOptionPane.YES_OPTION){
                    homeUtenteFrame.dispose();
                    //new gui.Accedi.Accedi().setVisible(true); Voglio aprire form Accedi.
                }
                //else non fa nulla
            }
        });

        //Lascia recensione
        pulsanteRecensione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Recensione recensione = new Recensione(homeUtenteFrame);
            }
        });
    }

    //Svuota i dati della Libreria
    void SvuotaDettagliLibreria() {
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
        pulsanteRecensione.setEnabled(true);
    }

    public static void main(String[] args) {
        homeUtenteFrame = new JFrame("homeUtente");
        homeUtenteFrame.setContentPane(new HomeUtente().homeUtentePanel);
        homeUtenteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeUtenteFrame.pack();
        homeUtenteFrame.setVisible(true);
    }
}
