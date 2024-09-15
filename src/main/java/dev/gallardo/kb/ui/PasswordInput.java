/*
 * Created by JFormDesigner on Sun Sep 15 14:28:58 CEST 2024
 */

package dev.gallardo.kb.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dev.gallardo.kb.util.Constants;
import net.miginfocom.swing.*;

/**
 * @author jomaa
 */
public class PasswordInput extends JDialog {
    private String password;

    public PasswordInput(Window owner) {
        super(owner, ModalityType.APPLICATION_MODAL);
        initComponents();
    }

    private void cancel(ActionEvent e) {
        password = null;
        dispose();
    }

    private void ok(ActionEvent e) {
        password = new String(passwordField.getPassword());
        dispose();
    }

    public String getPassword() {
        return password;
    }

    public static String promptForKeyStorePassword() {
        PasswordInput dialog = new PasswordInput(null);
        dialog.setTitle("Usando " + Constants.KEYSTORE_FILE_NAME + " y " + Constants.DB_FILE_NAME);
        dialog.setVisible(true);
        return dialog.getPassword();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        jksPasswordLabel = new JLabel();
        passwordField = new JPasswordField();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "insets dialog,hidemode 3",
                    // columns
                    "[grow,fill]",
                    // rows
                    "[fill]" +
                    "[grow,fill]"));

                //---- jksPasswordLabel ----
                jksPasswordLabel.setText("Contrase\u00f1a del Almac\u00e9n de Claves:");
                jksPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                contentPanel.add(jksPasswordLabel, "cell 0 0");

                //---- passwordField ----
                passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                contentPanel.add(passwordField, "cell 0 1");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]" +
                    "[button,fill]",
                    // rows
                    null));

                //---- okButton ----
                okButton.setText("Aceptar");
                okButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                okButton.addActionListener(e -> ok(e));
                buttonBar.add(okButton, "cell 0 0");

                //---- cancelButton ----
                cancelButton.setText("Cancelar");
                cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                cancelButton.addActionListener(e -> cancel(e));
                buttonBar.add(cancelButton, "cell 1 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(400, 180);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
        getRootPane().setDefaultButton(okButton);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel jksPasswordLabel;
    private JPasswordField passwordField;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
