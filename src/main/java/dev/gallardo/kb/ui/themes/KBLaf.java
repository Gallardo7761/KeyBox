package dev.gallardo.kb.ui.themes;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightIJTheme;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;

/**
 * Custom Look and Feel for the KBLaf theme.
 * This class extends the {@link FlatMoonlightIJTheme} class from FlatLaf.
 * @author Gallardo7761
 */
@SuppressWarnings({"all"})
public class KBLaf extends FlatMoonlightIJTheme {
    public KBLaf() {}

    public static final Color BLUE = Color.decode("#74a0f1");
    public static final Color DARK_BLUE = Color.decode("#222436");
    public static final Color LIGHT_BLUE = Color.decode("#c8d3f5");
    public static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font TABLE_HEADER_FONT = new Font("Segoe UI", Font.PLAIN, 16);

    public static boolean setup() {
        UIManager.put("TitlePane.foreground", LIGHT_BLUE);
        UIManager.put("TitlePane.embeddedForeground", LIGHT_BLUE);
        UIManager.put("TitlePane.iconSize", new Dimension(18,18));
        UIManager.put("TitlePane.buttonSize", new Dimension(24,24));

        UIManager.put("TableHeader.font", TABLE_HEADER_FONT);
        UIManager.put("Table.font", TABLE_FONT);
        UIManager.put("Table.rowHeight", 32);

        UIManager.put("PasswordField.showRevealButton", true);
        UIManager.put("PasswordField.revealButton", IconFontSwing.buildIcon(FontAwesome.EYE, 16, LIGHT_BLUE));
        return setup(new KBLaf());
    }

    public String getName() {
        return "KBLaf";
    }
}
