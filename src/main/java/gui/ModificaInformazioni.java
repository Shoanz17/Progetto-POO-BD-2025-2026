package gui;

import controller.Controller;
import model.CampoNonValidoException;
import model.GenereEnum;
import model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Schermata per la modifica delle informazioni personali dell'{@link Utente}.
 * Permette di aggiornare nome, password, email, genere e data di nascita tramite il {@link Controller}.
 */
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
        pulsanteModificaInformazioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = textFieldNome.getText().trim();
                    String password = new String(passwordFieldPassword.getPassword()).trim();
                    String email = textFieldEmail.getText().trim();
                    GenereEnum genere = (GenereEnum) comboBoxGenere.getSelectedItem();
                    String dataNascita = textFieldDataDiNascita.getText().trim();

                    controller.salvaModificheProfilo(utenteLoggato, nome, password, email, genere, dataNascita);

                    homeUtente.configuraInterfacciaProfilo();
                    modificaInformazioniFrame.dispose();
                    JOptionPane.showMessageDialog(modificaInformazioniFrame, "Profilo aggiornato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

                } catch (CampoNonValidoException ex) {
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
        modelGenere.addAll(controller.getListaGeneriEnum());
        comboBoxGenere.setModel(modelGenere);
        comboBoxGenere.setSelectedIndex(-1);
    }

    private void mostraForm(JFrame homeUtente) {
        modificaInformazioniFrame.pack();
        modificaInformazioniFrame.setLocationRelativeTo(homeUtente);
        modificaInformazioniFrame.setVisible(true);
    }
}
