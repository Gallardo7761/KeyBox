/*
 * Created by JFormDesigner on Sun Sep 15 01:13:31 CEST 2024
 */

package dev.gallardo.kb.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dev.gallardo.kb.common.PasswordEntry;
import net.miginfocom.swing.*;

/**
 * @author jomaa
 */
public class PasswordForm extends JDialog {
    private PasswordEntry passwordEntry;
    private boolean accepted = false;

    public PasswordForm(JFrame parent) {
        super(parent, "Nueva entrada", true);
        initComponents();
    }

    public PasswordForm(JFrame parent, PasswordEntry passwordEntry) {
        super(parent, "Editar entrada", true);
        this.passwordEntry = passwordEntry;
        initComponents();
        titleField.setText(passwordEntry.getTitle());
        userNameField.setText(passwordEntry.getUserName());
        urlField.setText(passwordEntry.getUrl());
        passwordField.setText(passwordEntry.getPassword());
    }

    private void okBtn(ActionEvent e) {
        passwordEntry = new PasswordEntry(
            null,
            titleField.getText(),
            userNameField.getText(),
            urlField.getText(),
            passwordField.getText()
        );
        accepted = true;
        dispose();
    }

    public PasswordEntry getPasswordEntry() {
        return accepted ? passwordEntry : null;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        titleLabel = new JLabel();
        titleField = new JTextField();
        userNameLabel = new JLabel();
        userNameField = new JTextField();
        urlLabel = new JLabel();
        urlField = new JTextField();
        passwordLabel = new JLabel();
        passwordField = new JTextField();
        okBtn = new JButton();

        //======== this ========
        setResizable(false);
        setTitle("Nuevo");
        setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "filly,hidemode 3,gapy 18",
            // columns
            "[fill]" +
            "[grow,fill]",
            // rows
            "rel[fill]rel" +
            "[fill]rel" +
            "[fill]rel" +
            "[]unrel" +
            "[fill]"));

        //---- titleLabel ----
        titleLabel.setText("T\u00edtulo:");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(titleLabel, "cell 0 0,growy");

        //---- titleField ----
        titleField.setPreferredSize(new Dimension(68, 32));
        titleField.setMinimumSize(new Dimension(68, 32));
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(titleField, "cell 1 0");

        //---- userNameLabel ----
        userNameLabel.setText("Usuario:");
        userNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(userNameLabel, "cell 0 1,growy");

        //---- userNameField ----
        userNameField.setPreferredSize(new Dimension(68, 32));
        userNameField.setMinimumSize(new Dimension(68, 32));
        userNameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(userNameField, "cell 1 1");

        //---- urlLabel ----
        urlLabel.setText("URL:");
        urlLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(urlLabel, "cell 0 2,growy");

        //---- urlField ----
        urlField.setPreferredSize(new Dimension(68, 32));
        urlField.setMinimumSize(new Dimension(68, 32));
        urlField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(urlField, "cell 1 2");

        //---- passwordLabel ----
        passwordLabel.setText("Contrase\u00f1a:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(passwordLabel, "cell 0 3,growy");

        //---- passwordField ----
        passwordField.setPreferredSize(new Dimension(68, 32));
        passwordField.setMinimumSize(new Dimension(68, 32));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(passwordField, "cell 1 3");

        //---- okBtn ----
        okBtn.setText("Aceptar");
        okBtn.setFocusable(false);
        okBtn.addActionListener(e -> okBtn(e));
        contentPane.add(okBtn, "cell 1 4,alignx right,growx 0");
        setSize(310, 230);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    private JLabel titleLabel;
    private JTextField titleField;
    private JLabel userNameLabel;
    private JTextField userNameField;
    private JLabel urlLabel;
    private JTextField urlField;
    private JLabel passwordLabel;
    private JTextField passwordField;
    private JButton okBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
