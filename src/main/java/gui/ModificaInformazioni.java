package gui;

import controller.Controller;
import model.CampoNonValidoException;
import model.GenereEnum;
import model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificaInformazioni {
    private JPanel modificaInformazioniPanel;
    private JTextField textFieldNome;
    private JTextField textFieldEmail;
    private JComboBox comboBoxGenere;
    private JButton pulsanteModificaInformazioni;
    private JPasswordField passwordFieldPassword;
    private JTextField textFieldDataDiNascita;
    private JLabel testoNome;
    private JLabel testoEmail;
    private JLabel testoGenere;
    private JLabel testoPassword;
    private JLabel testoDataDiNascita;

    public JFrame modificaInformazioniFrame;
    private Utente utenteLoggato;
    private HomeUtente homeUtente;
    private Controller controller;

    public ModificaInformazioni(Controller controller, HomeUtente homeUtente, Utente utenteLoggato) {

        this.utenteLoggato = utenteLoggato;
        this.homeUtente = homeUtente;
        this.controller = controller;

        configuraInterfaccia();

        associaListenerModificaInformazioni();

        mostraForm(homeUtente.homeUtenteFrame);

    }

    private void associaListenerModificaInformazioni() {
        //Pulsante Modifica informazioni
        pulsanteModificaInformazioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String password = new String(passwordFieldPassword.getPassword());
                    if (!textFieldNome.getText().trim().isEmpty()) {
                        controller.setNomeUtente(textFieldNome.getText(), utenteLoggato);
                    }
                    if (!password.trim().isEmpty()) {
                        controller.setPasswordUtente(password, utenteLoggato);
                    }
                    if (!textFieldEmail.getText().trim().isEmpty()) {
                        controller.setEmailUtente(textFieldEmail.getText(), utenteLoggato);
                    }
                    if (comboBoxGenere.getSelectedItem() != null) {
                        controller.setGenereUtente((GenereEnum) comboBoxGenere.getSelectedItem(), utenteLoggato);
                    }
                    if (!textFieldDataDiNascita.getText().trim().isEmpty()) {
                        controller.setDataDiNascitaUtente(textFieldDataDiNascita.getText(), utenteLoggato);
                    }

                    controller.salvaModificheProfilo(utenteLoggato);

                    homeUtente.configuraTestoInformazioniPersonali();
                    modificaInformazioniFrame.dispose();
                } catch (CampoNonValidoException ex) {
                    try {
                        controller.annullaModifiche(utenteLoggato);
                    } catch (CampoNonValidoException exc) {
                        JOptionPane.showMessageDialog(modificaInformazioniFrame,exc.getMessage());
                    }
                    JOptionPane.showMessageDialog(modificaInformazioniFrame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void configuraInterfaccia() {
        modificaInformazioniFrame = new JFrame("Modifica le tue informazioni");
        modificaInformazioniFrame.setContentPane(modificaInformazioniPanel);
        modificaInformazioniFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        configuraComboBoxGenere();
    }

    private void configuraComboBoxGenere() {
        DefaultComboBoxModel<GenereEnum> modelGenere = new DefaultComboBoxModel<>();
        for (GenereEnum genere : GenereEnum.values()) {
            modelGenere.addElement(genere);
        }
        comboBoxGenere.setModel(modelGenere);
        comboBoxGenere.setSelectedIndex(-1);
    }

    private void mostraForm(JFrame homeUtente) {
        modificaInformazioniFrame.pack();
        modificaInformazioniFrame.setLocationRelativeTo(homeUtente);
        modificaInformazioniFrame.setVisible(true);
    }
}
