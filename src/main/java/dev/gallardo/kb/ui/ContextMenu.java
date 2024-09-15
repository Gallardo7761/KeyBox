/*
 * Created by JFormDesigner on Sun Sep 15 03:42:06 CEST 2024
 */

package dev.gallardo.kb.ui;

import java.awt.*;
import javax.swing.*;

/**
 * @author jomaa
 */
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
        createItem.setText("Nuevo (Ctrl + N)");
        createItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(createItem);

        //---- editItem ----
        editItem.setText("Editar (Ctrl + E)");
        editItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(editItem);

        //---- deleteItem ----
        deleteItem.setText("Eliminar (Ctrl + D)");
        deleteItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(deleteItem);

        //---- showItem ----
        showItem.setText("Mostrar (Ctrl + S)");
        showItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(showItem);

        //---- copyUserItem ----
        copyUserItem.setText("Copiar usuario (Ctrl + U)");
        copyUserItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(copyUserItem);

        //---- copyPasswordItem ----
        copyPasswordItem.setText("Copiar contrase\u00f1a (Ctrl + C)");
        copyPasswordItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
