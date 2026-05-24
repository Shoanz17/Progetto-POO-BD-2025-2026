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

    public static void main(String[] args) {
        JFrame frame = new JFrame("HomeUtente");
        frame.setContentPane(new HomeUtente().HomeUtente);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
