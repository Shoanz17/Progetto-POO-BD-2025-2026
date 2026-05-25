package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accedi {
    private JPanel accediPanel;
    private JTextField nomeTextBox;
    private JPasswordField passwordTextBox;
    private JButton pulsanteAccedi;
    private JButton pulsanteRegistrati;
    private JLabel nonRegistratoLabel;
    private JLabel labelBenvenuto;
    private JLabel passwordLabel;
    private JLabel nomeLabel;

    private static JFrame accediFrame;

    public static void main(String[] args) {
        new Accedi();
    }

    public Accedi() {
        accediFrame = new JFrame("Accedi");
        accediFrame.setContentPane(accediPanel);
        accediFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        pulsanteRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registrati registratiGUI = new Registrati(accediFrame);
                accediFrame.setVisible(false);
            }
        });

        accediFrame.pack();
        accediFrame.setLocationRelativeTo(null);
        accediFrame.setVisible(true);
    }
}
