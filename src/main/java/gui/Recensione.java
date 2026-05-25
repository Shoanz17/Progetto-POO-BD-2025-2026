package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Recensione {
    private JPanel recensionePanel;
    private JButton rilasciaRecensioneButton;
    private JSpinner spinnerVoto;
    private JTextArea textAreaDescrizione;
    private JLabel testoVoto;
    private JLabel testoNomeGioco;
    private JLabel testoDescrizione;

    public JFrame recensioneFrame;

    public Recensione(JFrame homeUtente) {
        spinnerVoto.setModel(new SpinnerNumberModel(0, 0, 100, 1));

        recensioneFrame = new JFrame("Recensione");
        recensioneFrame.setContentPane(recensionePanel);
        recensioneFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        recensioneFrame.pack();
        recensioneFrame.setLocationRelativeTo(homeUtente);
        recensioneFrame.setVisible(true);

        rilasciaRecensioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                recensioneFrame.dispose();
            }
        });
    }

}
