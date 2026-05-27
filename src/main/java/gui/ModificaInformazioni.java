package gui;

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

    public ModificaInformazioni(JFrame homeUtente) {

        configuraInterfaccia();
        mostraForm(homeUtente);

        associaListenerModificaInformazioni();

    }

    private void associaListenerModificaInformazioni(){
        //Pulsante Modifica informazioni
        pulsanteModificaInformazioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                modificaInformazioniFrame.dispose();
            }
        });
    }

    private void configuraInterfaccia() {
        modificaInformazioniFrame = new JFrame("Modifica le tue informazioni");
        modificaInformazioniFrame.setContentPane(modificaInformazioniPanel);
        modificaInformazioniFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void mostraForm(JFrame homeUtente) {
        modificaInformazioniFrame.pack();
        modificaInformazioniFrame.setLocationRelativeTo(homeUtente);
        modificaInformazioniFrame.setVisible(true);
    }
}
