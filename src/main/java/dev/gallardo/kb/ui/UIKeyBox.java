/*
 * Created by JFormDesigner on Sat Sep 07 03:40:40 CEST 2024
 */

package dev.gallardo.kb.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import dev.gallardo.kb.ui.models.TablaModel;
import dev.gallardo.kb.util.Constants;
import dev.gallardo.kb.util.UIUtil;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import net.miginfocom.swing.*;

/**
 * @author jomaa
 */
public class UIKeyBox extends JFrame {
    private static UIKeyBox instance = null;

    private UIKeyBox() {
        initComponents();
        IconFontSwing.register(FontAwesome.getIconFont());
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        UIUtil.setTitle("KeyBox v" + Constants.APP_VERSION, this);
    }

    public static UIKeyBox getInstance() {
        if (instance == null) {
            instance = new UIKeyBox();
        }
        return instance;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        menuBar = new JMenuBar();
        createBtn = new JButton();
        editBtn = new JButton();
        deleteBtn = new JButton();
        tablaScrollPane = new JScrollPane();
        TablaModel model = new TablaModel();
        tabla = new JTable(model);

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/images/keybox_icon_64.png")).getImage());
        setForeground(new Color(0xc8d3f5));
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "insets 0,hidemode 3,gapy 0",
            // columns
            "[fill,grow]",
            // rows
            "[grow,fill]"));

        //======== menuBar ========
        {

            //---- createBtn ----
            createBtn.setText("Nuevo");
            createBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            createBtn.setBorder(new EmptyBorder(1, 1, 1, 1));
            createBtn.setBackground(new Color(0x222436));
            createBtn.setMaximumSize(new Dimension(64, 32));
            createBtn.setMinimumSize(new Dimension(64, 32));
            createBtn.setPreferredSize(new Dimension(64, 32));
            createBtn.setToolTipText("Ctrl + N");
            menuBar.add(createBtn);

            //---- editBtn ----
            editBtn.setText("Editar");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            editBtn.setBorder(new EmptyBorder(1, 1, 1, 1));
            editBtn.setBackground(new Color(0x222436));
            editBtn.setMaximumSize(new Dimension(64, 32));
            editBtn.setMinimumSize(new Dimension(64, 32));
            editBtn.setPreferredSize(new Dimension(64, 32));
            editBtn.setToolTipText("Ctrl + E");
            menuBar.add(editBtn);

            //---- deleteBtn ----
            deleteBtn.setText("Eliminar");
            deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            deleteBtn.setBorder(new EmptyBorder(1, 1, 1, 1));
            deleteBtn.setBackground(new Color(0x222436));
            deleteBtn.setMaximumSize(new Dimension(64, 32));
            deleteBtn.setMinimumSize(new Dimension(64, 32));
            deleteBtn.setPreferredSize(new Dimension(64, 32));
            deleteBtn.setToolTipText("Ctrl + D");
            menuBar.add(deleteBtn);
        }
        setJMenuBar(menuBar);

        //======== tablaScrollPane ========
        {
            tablaScrollPane.setFocusable(false);
            tablaScrollPane.setBorder(BorderFactory.createEmptyBorder());

            //---- tabla ----
            tabla.setFocusable(false);
            tabla.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            tabla.setBorder(BorderFactory.createEmptyBorder());
            tablaScrollPane.setViewportView(tabla);
        }
        contentPane.add(tablaScrollPane, "cell 0 0");
        setSize(900, 600);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    private JMenuBar menuBar;
    private JButton createBtn;
    private JButton editBtn;
    private JButton deleteBtn;
    private JScrollPane tablaScrollPane;
    private JTable tabla;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
