package dev.gallardo.kb.util;

import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;

public class UIUtil {
    public static void setTitle(String title, JFrame frame) {
        frame.setTitle(title);
    }

    public static void setButtonIcon(JButton button, IconCode iconCode, float size, Color color) {
        Icon icon = IconFontSwing.buildIcon(iconCode, size, color);
        button.setIcon(icon);
    }

    public static boolean showConfirmDialog(String s) {
        return JOptionPane.showConfirmDialog(null, s, "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static void showInfoDialog(String s) {
        JOptionPane.showMessageDialog(null, s, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
