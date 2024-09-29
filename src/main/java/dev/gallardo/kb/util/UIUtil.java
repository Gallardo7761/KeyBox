package dev.gallardo.kb.util;

import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Utility class for UI operations.
 * <p>
 * This class provides methods to set the title of a JFrame, set the icon of a JButton, show a confirmation dialog, show an information dialog, and show an error dialog.
 * <p>
 * The error dialog can be shown with a beep sound.
 * <p>
 * The error dialog can be shown with a list of error messages.
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 * @see jiconfont.swing.IconFontSwing
 * @see javax.swing.JOptionPane
 * @see java.awt.Toolkit
 * @see javax.swing.JFrame
 * @see javax.swing.JButton
 * @see java.awt.Color
 * @see java.util.List
 * @see jiconfont.IconCode
 * @see javax.swing.Icon
 */
@SuppressWarnings("unused")
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

    public static void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrorDialog(String errorMessage, boolean withSound) {
        if (withSound) {
            Toolkit.getDefaultToolkit().beep();
        }
        showErrorDialog(errorMessage);
    }

    public static void showErrorDialog(List<String> errorMessages) {
        StringBuilder sb = new StringBuilder();
        for (String errorMessage : errorMessages) {
            sb.append(errorMessage).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrorDialog(List<String> errorMessages, boolean withSound) {
        if (withSound) {
            Toolkit.getDefaultToolkit().beep();
        }
        showErrorDialog(errorMessages);
    }
}
