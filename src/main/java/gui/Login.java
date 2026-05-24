package gui;

import javax.swing.*;

public class Login {
    private JPanel LoginPanel;
    private JTextField nomeTextBox;
    private JTextField passwordTextBox;
    private JButton pulsanteAccedi;
    private JButton pulsanteRegistrati;
    private JLabel nonRegistratoLabel;
    private JLabel labelBenvenuto;
    private JLabel passwordLabel;
    private JLabel nomeLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
