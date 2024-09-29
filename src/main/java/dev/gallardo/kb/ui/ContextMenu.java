/*
 * Created by JFormDesigner on Sun Sep 15 03:42:06 CEST 2024
 */

package dev.gallardo.kb.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Context menu for the main table.<br>
 * This class is a singleton.<br>
 * It contains the following items:<br>
 * - Create<br>
 * - Edit<br>
 * - Delete<br>
 * - Show<br>
 * - Copy user<br>
 * - Copy password<br>
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 * @see JPopupMenu
 * @see JMenuItem
 * @see KeyStroke
 * @see KeyEvent
 * @see Font
 */
@SuppressWarnings("ALL")
public class ContextMenu extends JPopupMenu {
    private static ContextMenu instance = null;

    private ContextMenu() {
        initComponents();
    }

    public static ContextMenu getInstance() {
        if (instance == null) {
            instance = new ContextMenu();
        }
        return instance;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        createItem = new JMenuItem();
        editItem = new JMenuItem();
        deleteItem = new JMenuItem();
        showItem = new JMenuItem();
        copyUserItem = new JMenuItem();
        copyPasswordItem = new JMenuItem();

        //======== this ========

        //---- createItem ----
        createItem.setText("Nuevo");
        createItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        createItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        add(createItem);

        //---- editItem ----
        editItem.setText("Editar");
        editItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        editItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        add(editItem);

        //---- deleteItem ----
        deleteItem.setText("Eliminar");
        deleteItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
        add(deleteItem);

        //---- showItem ----
        showItem.setText("Mostrar");
        showItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        showItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        add(showItem);

        //---- copyUserItem ----
        copyUserItem.setText("Copiar usuario");
        copyUserItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        copyUserItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));
        add(copyUserItem);

        //---- copyPasswordItem ----
        copyPasswordItem.setText("Copiar contrase\u00f1a");
        copyPasswordItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        copyPasswordItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        add(copyPasswordItem);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    protected JMenuItem createItem;
    protected JMenuItem editItem;
    protected JMenuItem deleteItem;
    protected JMenuItem showItem;
    protected JMenuItem copyUserItem;
    protected JMenuItem copyPasswordItem;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
