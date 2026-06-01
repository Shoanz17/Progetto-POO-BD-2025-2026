package gui;

import controller.Controller;
import model.*;

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
    private final Controller controller = new Controller();

    public static void main(String[] args) {
        new Accedi();
    }

    public Accedi() {
        configuraInterfaccia();



        associaListenerAccediButton(controller);
        associaListenerRegistratiButton(controller, accediFrame);


        mostraForm();
    }

    private void configuraInterfaccia(){
        accediFrame = new JFrame("Accedi");
        accediFrame.setContentPane(accediPanel);
        accediFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void associaListenerAccediButton(Controller controller){
        pulsanteAccedi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextBox.getText();
                String password = new String(passwordTextBox.getPassword());

                //ricerca l'account nel DB
                Account accountLoggato = null;
                try {

                    accountLoggato = controller.accedi(nome, password);

                    if(accountLoggato instanceof Utente){
                        JOptionPane.showMessageDialog(accediFrame, "Accesso eseguito, benvenuto " + accountLoggato.getNome(), "Benvenuto!", JOptionPane.INFORMATION_MESSAGE);

                        //apri form utente
                        HomeUtente homeUtente = new HomeUtente(controller, accediFrame, (Utente) accountLoggato);
                        accediFrame.setVisible(false); //potrei fare dispose per risparmiare quei preziosissimi kb di ram e far chiamare il costruttore di questa form al tasto logout
                        //cancello i dati per evitare che quando faccio logout siano ancora scritti
                        nomeTextBox.setText("");
                        passwordTextBox.setText("");
                    }
                    else if(accountLoggato instanceof Sviluppatore){
                        JOptionPane.showMessageDialog(accediFrame, "Accesso eseguito, benvenuto " + accountLoggato.getNome(), "Benvenuto!", JOptionPane.INFORMATION_MESSAGE);
                        //apri form sviluppatore
                    }
                    else if(accountLoggato instanceof Admin){
                        JOptionPane.showMessageDialog(accediFrame, "Accesso eseguito, benvenuto " + accountLoggato.getNome(), "Benvenuto!", JOptionPane.INFORMATION_MESSAGE);

                        //apri form admin
                        HomeAdmin homeAdmin = new HomeAdmin(controller, accediFrame, (Admin) accountLoggato);
                        accediFrame.setVisible(false);

                        nomeTextBox.setText("");
                        passwordTextBox.setText("");
                    }

                } catch (CampoNonValidoException ex) {
                    JOptionPane.showMessageDialog(accediFrame, ex.getMessage());
                }
            }
        });
    }

    private void associaListenerRegistratiButton(Controller controller, JFrame accediFrame){
        pulsanteRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registrati registratiGUI = new Registrati(controller, accediFrame);

                nomeTextBox.setText("");
                passwordTextBox.setText("");
                accediFrame.setVisible(false);
            }
        });
    }

    private void mostraForm(){
        accediFrame.pack();
        accediFrame.setLocationRelativeTo(null);
        accediFrame.setVisible(true);
    }
}
