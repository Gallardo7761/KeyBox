package dev.gallardo.kb;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightIJTheme;
import dev.gallardo.kb.ui.UIKeyBox;
import dev.gallardo.kb.util.Constants;
import dev.gallardo.kb.util.DBUtil;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.io.File;

public class KeyBox {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
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

    private static void initUI() throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMoonlightIJTheme());
        IconFontSwing.register(FontAwesome.getIconFont());
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        UIKeyBox.getInstance().setVisible(true);
    }
}
