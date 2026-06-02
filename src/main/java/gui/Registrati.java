package gui;

import controller.Controller;
import model.Account;
import model.CampoNonValidoException;
import model.GenereEnum;

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
    private JFormattedTextField dataNascitaTextBox;
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
    private Controller controller;

    public Registrati(Controller controller, JFrame accediGUI) {
        this.controller = controller;
        configuraInterfaccia();

        associaListenerUtenteRadioButton();
        associaListenerSviluppatoreRadioButton();
        associaListenerRegistratiButton(controller, accediGUI);
        //torno al login
        associaListenerIndietroButton(accediGUI);

        mostraForm();

    }

    private void configuraInterfaccia(){
        //riempio il combobox con i dati dall'enumeration (cercato su internet)
        //creo un modello per la combobox che accetta stringhe
        DefaultComboBoxModel<String> modelGenere = new DefaultComboBoxModel<>();

        for (GenereEnum genere : GenereEnum.values()) {
            modelGenere.addElement(genere.name());
        }
        //collego il modello alla combobox
        genereComboBox.setModel(modelGenere);

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
        genereComboBox.setSelectedIndex(-1); //per mettere lo spazio vuoto nel combobox

        //codice per formattare la textbox di data di nascita (cercato su internet)
        try {
            // Il carattere '#' accetta solo numeri, le barre vengono messe da sole
            javax.swing.text.MaskFormatter mascheraData = new javax.swing.text.MaskFormatter("##/##/####");

            // Mostra i trattini bassi dove l'utente deve ancora digitare
            mascheraData.setPlaceholderCharacter('_');

            // Applica la maschera alla tua textbox formattata
            mascheraData.install(dataNascitaTextBox);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    private void associaListenerUtenteRadioButton(){
        utenteRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //nascondi sviluppatore
                descrizioneTextArea.setVisible(false);
                descrizioneTextArea.setText("");
                descrizioneScrollPane.setVisible(false);
                descrizioneLabel.setVisible(false);

                //fai riapparire l'utente
                emailLabel.setVisible(true);
                emailTextBox.setVisible(true);

                genereLabel.setVisible(true);
                genereComboBox.setVisible(true);
                dataNascitaLabel.setVisible(true);
                dataNascitaTextBox.setVisible(true);
                genereComboBox.setSelectedIndex(-1);

                aggiornaGeometriaForm();
            }
        });
    }

    private void associaListenerSviluppatoreRadioButton(){
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

                aggiornaGeometriaForm();
            }
        });
    }

    private void associaListenerRegistratiButton(Controller controller, JFrame accediGUI){
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeTextBox.getText();
                    String password = new String(passwordTextBox.getPassword());

                    if(utenteRadioButton.isSelected()) {
                        //caso utente
                        String email = emailTextBox.getText();
                        String genere = (String) genereComboBox.getSelectedItem();
                        String dataNascita = dataNascitaTextBox.getText();

                        controller.registraUtente(nome, password, genere, email, dataNascita);

                    } else {
                        String descrizione = descrizioneTextArea.getText();

                        controller.registraSviluppatore(nome, password, descrizione);

                    }

                    JOptionPane.showMessageDialog(registratiFrame, "Registrato con successo");
                    accediGUI.setVisible(true);
                    registratiFrame.dispose();

                } catch (CampoNonValidoException ex) {

                    JOptionPane.showMessageDialog(registratiFrame, ex.getMessage());

                }
            }
        });
    }

    private void associaListenerIndietroButton(JFrame accediGUI){
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accediGUI.setVisible(true);
                //registratiFrame.setVisible(false);
                registratiFrame.dispose();
            }
        });
    }

    private void mostraForm(){
        registratiFrame.pack();
        registratiFrame.setLocationRelativeTo(null);
        registratiFrame.setVisible(true);
    }

    private void aggiornaGeometriaForm(){
        registratiFrame.revalidate();
        registratiFrame.repaint();
        registratiFrame.pack();
    }
}
