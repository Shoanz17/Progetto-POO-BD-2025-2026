package gui;

import javax.swing.*;

public class HomeUtente {

    private JPanel HomeUtente;
    private JTabbedPane TabbedPane;
    private JPanel Catalogo;
    private JPanel Utente;
    private JPanel Libreria;
    private JTextField RicercaLibreria;
    private JList ListaLibreria;
    private JToolBar ToolBarLibreria;
    private JLabel EdizioneLibreria;
    private JLabel PiattaformaDiGiocoLibreria;
    private JLabel DataRilascioLibreria;
    private JLabel PrezzoAcquistoLibreria;
    private JLabel CategoriaLibreria;
    private JLabel PegiLibreria;
    private JLabel KeyLibreria;
    private JLabel DataAcquistoLibreria;
    private JButton PulsanteFiltra;
    private JButton PulsanteResetFiltri;
    private JComboBox CategorieFiltro;
    private JButton DataFiltro;
    private JButton PrezzoAcquistoFiltro;
    private JComboBox CategoriaFiltro;
    private JComboBox PegiFiltro;
    private JButton DataAcquistoFiltro;
    private JButton PulsanteRecensione;
    private JButton copiaKeyButton;
    private JScrollPane ScrollPaneLibreria;
    private JButton PulsanteAggiungiSaldo;
    private JButton PulsanteModificaInformazioni;
    private JList ListaAmiciUtente;
    private JTextField RicercaAmici;
    private JCheckBox CheckBoxAmici;
    private JButton PulsanteVisualizzaRecensioni;
    private JList ListaSviluppatori;
    private JTextField RicercaSviluppatori;
    private JCheckBox CheckBoxSeguiti;
    private JButton PulsanteSegui;
    private JButton PulsanteAggiungiAmico;
    private JLabel DataCreazioneAccount;
    private JLabel TestoBannato;
    private JLabel TestoNome;
    private JLabel TestoGenere;
    private JLabel TestoEmail;
    private JLabel TestoDataDiNascita;
    private JLabel TestoNumeroGiochiAcquistati;
    private JLabel TestoNumeroRecensioniRilasciate;
    private JLabel TestoSaldo;
    private JScrollPane ScrollPaneListaAmici;
    private JScrollPane ScrollPaneListaSviluppatori;
    private JLabel TestoDescrizioneSviluppatore;
    private JLabel TestoGiochiRilasciati;
    private JLabel TestoGiocoPiuVenduto;
    private JLabel TestoGiochiAcquistatiAmico;
    private JLabel TestoNumeroRecensioniAmico;
    private JLabel TestoGenereAmico;
    private JLabel TestoBannatoAmico;
    private JToolBar ToolBarSviluppatore;
    private JToolBar ToolBarAmico;
    private JLabel TestoProfilo;
    private JLabel TestoCommunity;
    private JPanel Carrello;

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomeUtente");
        frame.setContentPane(new HomeUtente().HomeUtente);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
