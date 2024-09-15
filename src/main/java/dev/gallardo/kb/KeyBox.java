package dev.gallardo.kb;

import dev.gallardo.kb.ui.PasswordInput;
import dev.gallardo.kb.ui.UIKeyBox;
import dev.gallardo.kb.ui.themes.KBLaf;
import dev.gallardo.kb.util.Constants;
import dev.gallardo.kb.util.DBUtil;
import dev.gallardo.kb.util.EncryptionUtil;
import dev.gallardo.kb.util.KeyStoreManager;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class KeyBox {
    public static String masterPassword;

    public static void main(String[] args) {
        IconFontSwing.register(FontAwesome.getIconFont());
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        KBLaf.setup();

        if(isFirstRun()) {
            handleFiles();

            String password = PasswordInput.promptForKeyStorePassword();
            if (password == null || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se proporcionó una contraseña. La aplicación se cerrará.");
                System.exit(1);
            }

            try {
                SecretKey key = EncryptionUtil.generateKey();
                KeyStoreManager keyStoreManager = KeyStoreManager.getInstance(password);
                keyStoreManager.storeKey(key);

                JOptionPane.showMessageDialog(null, "Clave de encriptación generada y almacenada con éxito.");
                Constants.LOGGER.info("Clave de encriptación generada y almacenada con éxito.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al generar o almacenar la clave de cifrado.");
                Constants.LOGGER.error("Error al generar o almacenar la clave de cifrado.", e);
                System.exit(1);
            }
        }

        masterPassword = PasswordInput.promptForKeyStorePassword();
        if (masterPassword == null || masterPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se proporcionó una contraseña. La aplicación se cerrará.", "Error", JOptionPane.ERROR_MESSAGE);
            Constants.LOGGER.error("No se proporcionó una contraseña. La aplicación se cerrará. (Exit Code 1)");
            System.exit(1);
        } else {
            if(!isValidKey(masterPassword)) {
                JOptionPane.showMessageDialog(null, "La contraseña proporcionada no es válida. La aplicación se cerrará.", "Error", JOptionPane.ERROR_MESSAGE);
                Constants.LOGGER.error("La contraseña proporcionada no es válida. La aplicación se cerrará. (Exit Code 1)");
                System.exit(1);
            }
            UIKeyBox.getInstance().setVisible(true);
        }
    }

    private static boolean isValidKey(String key) {
        KeyStoreManager keyStoreManager = KeyStoreManager.getInstance(key);
        try {
            keyStoreManager.loadKey();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void handleFiles() {
        if(!new File(Constants.DB_FILE_PATH).exists()) {
            DBUtil.createFile();
            DBUtil.setup();
            Constants.LOGGER.info("Base de datos creada e inicializada con éxito.");
        } else {
            Constants.LOGGER.info("Se ha encontrado una base de datos.");
        }

        if(!new File(Constants.KEYSTORE_FILE).exists()) {
            try {
                new File(Constants.KEYSTORE_FILE).createNewFile();
                Constants.LOGGER.info("Archivo de almacén de claves creado con éxito.");
            } catch (IOException e) {
                Constants.LOGGER.error("Error al crear el archivo de almacén de claves.");
            }
        }

        Constants.LOGGER.info("Iniciando KeyBox...");
    }

    private static boolean isFirstRun() {
        return !new File(Constants.DB_FILE_PATH).exists();
    }

}
