package gui;

import controller.Controller;
import model.Admin;

import javax.swing.*;

public class HomeAdmin {
    private JPanel adminPanel;
    private JTabbedPane tabbedPane1;
    private JButton logoutButton;
    private JList list1;
    private JTextField textField1;
    private JButton cercaButton;
    private JList list2;
    private JList list3;
    private JList list4;
    private JButton aggiungiPiattaformaButton;
    private JList list5;
    private JTextArea textArea1;
    private JCheckBox bannatiCheckBox;
    private JButton rimuoviGenereButton;
    private JButton aggiungiGenereButton;
    private JTextField textField2;
    private JTextField textField3;
    private JButton cercaButton1;
    private JButton bannaSbannaButton;
    private JButton rimuoviRecensioneButton;
    private JList list6;
    private JButton rimborsaButton;
    private JTextField textField4;
    private JButton cercaButton2;
    private JButton rimuoviPiattaformaButton;
    private JCheckBox checkBox1;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JButton aggiungiPromozioneATempoButton;

    private JFrame adminFrame;

    public HomeAdmin(Controller controller, JFrame accediGUI, Admin admin){
        adminFrame = new JFrame("HomeAdmin");
        adminFrame.setContentPane(adminPanel);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.pack();
        adminFrame.setVisible(true);
    }
}
