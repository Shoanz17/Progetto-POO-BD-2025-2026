package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificaInformazioni {
    private JPanel modificaInformazioniPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JButton modificaInformazioniButton;
    private JPasswordField passwordField1;

    public JFrame modificaInformazioniFrame;

    public ModificaInformazioni(JFrame homeUtente) {

        modificaInformazioniFrame = new JFrame("Modifica le tue informazioni");
        modificaInformazioniFrame.setContentPane(modificaInformazioniPanel);
        modificaInformazioniFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modificaInformazioniFrame.pack();
        modificaInformazioniFrame.setLocationRelativeTo(homeUtente);
        modificaInformazioniFrame.setVisible(true);

        //Pulsante Modifica informazioni
        modificaInformazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                modificaInformazioniFrame.dispose();
            }
        });
    }
}
