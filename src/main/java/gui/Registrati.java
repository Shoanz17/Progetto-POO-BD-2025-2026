package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registrati {
    private JPanel registratiPanel;
    private JRadioButton utenteRadioButton;
    private JRadioButton sviluppatoreRadioButton;
    private JLabel benvenutoLabel;
    private JButton indietroButton;
    private JTextField nomeTextBox;
    private JPasswordField passwordTextBox;
    private JButton registratiButton;
    private JTextField emailTextBox;
    private JTextField dataNascitaTextBox;
    private JComboBox genereComboBox;
    private JLabel scegliRegistrazioneLabel;
    private JLabel nomeLabel;
    private JLabel passwordLabel;
    private JLabel emailLabel;
    private JLabel genereLabel;
    private JLabel dataNascitaLabel;
    private JTextArea descrizioneTextArea;
    private JScrollPane descrizioneScrollPane;
    private JLabel descrizioneLabel;

    public JFrame registratiFrame;

    public Registrati(JFrame accediGUI) {
        //costruisco il registratiFrame
        registratiFrame = new JFrame("Registrati");
        registratiFrame.setContentPane(registratiPanel);
        registratiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //dispose on close invece di exit chiude solo la form e non tutto il programma

        //codice per i radio button
        ButtonGroup tipoUtenteGroup = new ButtonGroup();
        tipoUtenteGroup.add(utenteRadioButton);
        tipoUtenteGroup.add(sviluppatoreRadioButton);

        utenteRadioButton.setSelected(true);
        descrizioneTextArea.setVisible(false);
        descrizioneScrollPane.setVisible(false);
        descrizioneLabel.setVisible(false);

        utenteRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //nascondi sviluppatore
                descrizioneTextArea.setVisible(false);
                descrizioneScrollPane.setVisible(false);
                descrizioneLabel.setVisible(false);

                //fai riapparire l'utente
                emailLabel.setVisible(true);
                emailTextBox.setVisible(true);
                emailTextBox.setText("");

                genereLabel.setVisible(true);
                genereComboBox.setVisible(true);
                dataNascitaLabel.setVisible(true);
                dataNascitaTextBox.setVisible(true);

                //aggiorna grandezza finestra
                registratiFrame.revalidate();
                registratiFrame.repaint();
                registratiFrame.pack();
            }
        });
        sviluppatoreRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //nascondi e pulisci utente
                emailLabel.setVisible(false);
                emailTextBox.setVisible(false);
                emailTextBox.setText("");
                genereLabel.setVisible(false);
                genereComboBox.setVisible(false);
                dataNascitaLabel.setVisible(false);
                dataNascitaTextBox.setVisible(false);
                dataNascitaTextBox.setText("");

                //fai apparire sviluppatore
                descrizioneTextArea.setVisible(true);
                descrizioneScrollPane.setVisible(true);
                descrizioneLabel.setVisible(true);

                //aggiorna grandezza finestra
                registratiFrame.revalidate();
                registratiFrame.repaint();
                registratiFrame.pack();
            }
        });

        //torno al login
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accediGUI.setVisible(true);
                //registratiFrame.setVisible(false);
                registratiFrame.dispose();
            }
        });

        registratiFrame.pack();
        registratiFrame.setLocationRelativeTo(null);
        registratiFrame.setVisible(true);
    }
}
