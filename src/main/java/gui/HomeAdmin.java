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
    private JList list4;
    private JButton aggiungiPiattaformaButton;
    private JList list5;
    private JTextArea textArea1;
    private JCheckBox bannatiCheckBox;
    private JButton aggiungiGenereButton;
    private JTextField textField2;
    private JTextField textField3;
    private JButton alfabeticoButton;
    private JButton bannaSbannaButton;
    private JList list6;
    private JButton rimborsaButton;
    private JTextField textField4;
    private JButton rimuoviPiattaformaButton;
    private JCheckBox checkBox1;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JButton rimuoviRecensioneButton;
    private JTable table1;
    private JList list1;
    private JButton aggiungiPromozioneButton;
    private JTextField textField10;
    private JButton ordinaPerDataButton;
    private JTextField textField11;
    private JCheckBox bannatoCheckBox;
    private JButton bannaSbannaButton1;
    private JList list7;
    private JList list8;

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
