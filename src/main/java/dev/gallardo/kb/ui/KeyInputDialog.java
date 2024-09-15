package dev.gallardo.kb.ui;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.util.Base64;

public class KeyInputDialog {

    public static String promptForKeyStorePassword() {
        return JOptionPane.showInputDialog(null, "Ingrese la contraseña para el Almacén de Claves:", "Desbloquear almacén", JOptionPane.PLAIN_MESSAGE);
    }

    public static SecretKey promptForEncryptionKey() {
        String keyInput = JOptionPane.showInputDialog(null, "Ingrese la clave de encriptación (Base64):");

        if (keyInput != null && !keyInput.isEmpty()) {
            try {
                byte[] keyBytes = Base64.getDecoder().decode(keyInput);
                return new SecretKeySpec(keyBytes, "AES");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Clave inválida. Por favor, ingrese una clave válida.");
            }
        }
        return null;
    }
}
