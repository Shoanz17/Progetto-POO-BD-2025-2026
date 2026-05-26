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

        modificaInformazioniFrame = new JFrame("Modifica le tue informazioni");
        modificaInformazioniFrame.setContentPane(modificaInformazioniPanel);
        modificaInformazioniFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modificaInformazioniFrame.pack();
        modificaInformazioniFrame.setLocationRelativeTo(homeUtente);
        modificaInformazioniFrame.setVisible(true);

        //Pulsante Modifica informazioni
        pulsanteModificaInformazioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                modificaInformazioniFrame.dispose();
            }
        });
    }
}
