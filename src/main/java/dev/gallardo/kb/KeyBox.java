package dev.gallardo.kb;

import dev.gallardo.kb.ui.UIKeyBox;
import dev.gallardo.kb.ui.themes.KBLaf;
import dev.gallardo.kb.util.Constants;
import dev.gallardo.kb.util.DBUtil;

import javax.swing.*;
import java.io.File;

public class KeyBox {
    public static void main(String[] args) {
        handleFiles();
        initUI();
    }

    private static void handleFiles() {
        if(!new File(Constants.DB_FILE_PATH).exists()) {
            DBUtil.createFile();
            DBUtil.setup();
            Constants.LOGGER.info("Base de datos creada e inicializada con éxito.");
        } else {
            Constants.LOGGER.info("Se ha encontrado una base de datos.");
        }
        Constants.LOGGER.info("Iniciando KeyBox...");
    }

    private static void initUI() {
        KBLaf.setup();
        UIKeyBox.getInstance().setVisible(true);
    }
}
