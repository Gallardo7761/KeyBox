/*
 * Created by JFormDesigner on Sat Sep 07 03:40:40 CEST 2024
 */

package dev.gallardo.kb.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.TableCellRenderer;

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
        setupShortcuts();
        tabla.getColumn("").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox()));
        tabla.getColumnModel().getColumn(4).setMinWidth(32);
        tabla.getColumnModel().getColumn(4).setMaxWidth(32);
    }

    public static UIKeyBox getInstance() {
        if (instance == null) {
            instance = new UIKeyBox();
        }
        return instance;
    }

    private void createBtn(ActionEvent e) {

    }

    private void editBtn(ActionEvent e) {

    }

    private void deleteBtn(ActionEvent e) {

    }

    private void setupShortcuts() {
        // Shortcut for "Nuevo" (Ctrl + N)
        KeyStroke keyStrokeCreate = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        Action createAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBtn.doClick(); // Simular clic en el botón "Nuevo"
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeCreate, "createAction");
        getRootPane().getActionMap().put("createAction", createAction);

        // Shortcut for "Editar" (Ctrl + E)
        KeyStroke keyStrokeEdit = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK);
        Action editAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBtn.doClick();  // Simular clic en el botón "Editar"
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeEdit, "editAction");
        getRootPane().getActionMap().put("editAction", editAction);

        // Shortcut for "Eliminar" (Ctrl + D)
        KeyStroke keyStrokeDelete = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK);
        Action deleteAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBtn.doClick();  // Simular clic en el botón "Eliminar"
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeDelete, "deleteAction");
        getRootPane().getActionMap().put("deleteAction", deleteAction);
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
            menuBar.setBorder(BorderFactory.createEmptyBorder());

            //---- createBtn ----
            createBtn.setText("Nuevo");
            createBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            createBtn.setBorder(new EmptyBorder(1, 1, 1, 1));
            createBtn.setBackground(new Color(0x222436));
            createBtn.setMaximumSize(new Dimension(64, 24));
            createBtn.setMinimumSize(new Dimension(64, 24));
            createBtn.setPreferredSize(new Dimension(64, 24));
            createBtn.setToolTipText("Ctrl + N");
            createBtn.addActionListener(e -> createBtn(e));
            menuBar.add(createBtn);

            //---- editBtn ----
            editBtn.setText("Editar");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            editBtn.setBorder(new EmptyBorder(1, 1, 1, 1));
            editBtn.setBackground(new Color(0x222436));
            editBtn.setMaximumSize(new Dimension(64, 24));
            editBtn.setMinimumSize(new Dimension(64, 24));
            editBtn.setPreferredSize(new Dimension(64, 24));
            editBtn.setToolTipText("Ctrl + E");
            editBtn.addActionListener(e -> editBtn(e));
            menuBar.add(editBtn);

            //---- deleteBtn ----
            deleteBtn.setText("Eliminar");
            deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            deleteBtn.setBorder(new EmptyBorder(1, 1, 1, 1));
            deleteBtn.setBackground(new Color(0x222436));
            deleteBtn.setMaximumSize(new Dimension(64, 24));
            deleteBtn.setMinimumSize(new Dimension(64, 24));
            deleteBtn.setPreferredSize(new Dimension(64, 24));
            deleteBtn.setToolTipText("Ctrl + D");
            deleteBtn.addActionListener(e -> deleteBtn(e));
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
        contentPane.add(tablaScrollPane, "cell 0 0,gapy 0");
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

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            JOptionPane.showMessageDialog(button, label + ": Ouch!");
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
