package dev.gallardo.kb.ui.themes;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightIJTheme;

import javax.swing.*;
import java.awt.*;

public class KBLaf extends FlatMoonlightIJTheme {
    public KBLaf() {}

    public static final Color BLUE = Color.decode("#74a0f1");
    public static final Color DARK_BLUE = Color.decode("#222436");
    public static final Color LIGHT_BLUE = Color.decode("#c8d3f5");
    public static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font TABLE_HEADER_FONT = new Font("Segoe UI", Font.PLAIN, 18);

    public static boolean setup() {
        UIManager.put("TitlePane.foreground", LIGHT_BLUE);
        UIManager.put("TitlePane.embeddedForeground", LIGHT_BLUE);
        UIManager.put("TableHeader.font", TABLE_HEADER_FONT);
        UIManager.put("Table.font", TABLE_FONT);
        return setup(new KBLaf());
    }

    public String getName() {
        return "KBLaf";
    }
}
