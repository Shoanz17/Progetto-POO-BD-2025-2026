package gui;

import controller.Controller;
import model.Admin;

import javax.swing.*;
import java.awt.*;

public class HomeAdmin {
    private JPanel adminPanel;
    private JTabbedPane tabbedPane1;
    private JButton logoutButton;
    private JTextField textField1;
    private JList list2;
    private JList list3;
    private JButton aggiungiPiattaformaButton;
    private JTextArea textArea1;
    private JCheckBox bannatiCheckBox;
    private JButton aggiungiGenereButton;
    private JTextField textField2;
    private JTextField textField3;
    private JButton bannaSbannaButton;
    private JButton rimborsaButton;
    private JTextField textField4;
    private JCheckBox checkBox1;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JButton rimuoviRecensioneButton;
    private JTable table1;
    private JButton aggiungiPromozioneButton;
    private JTextField textField10;
    private JButton ordinaPerDataButton;
    private JTextField textField11;
    private JCheckBox bannatoCheckBox;
    private JButton aggiungiStrikeButton;
    private JList list7;
    private JTable table2;
    private JButton rimuoviStrikeButton;
    private JList table3;
    private JButton aggiungiButton;
    private JButton rimuoviButton;
    private JButton modificaTitoloButton;
    private JButton modificaCategoriaButton;
    private JButton modificaPegiButton;
    private JButton bannaSviluppatoreButton;
    private JTextArea textArea2;
    private JTextField textField12;
    private JTextArea textArea3;
    private JButton rimuoviRecensioneButton1;
    private JTable table4;
    private JTable table5;
    private JTable table6;
    private JTable table7;

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
