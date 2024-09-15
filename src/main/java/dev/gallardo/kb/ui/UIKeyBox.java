/*
 * Created by JFormDesigner on Sat Sep 07 03:40:40 CEST 2024
 */

package dev.gallardo.kb.ui;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import dev.gallardo.kb.common.DBChangeListener;
import dev.gallardo.kb.common.KBDBAccessor;
import dev.gallardo.kb.common.PasswordEntry;
import dev.gallardo.kb.ui.models.TablaModel;
import dev.gallardo.kb.ui.themes.KBLaf;
import dev.gallardo.kb.util.Constants;
import dev.gallardo.kb.util.UIUtil;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import net.miginfocom.swing.*;

/**
 * @author jomaa
 */
public class UIKeyBox extends JFrame implements DBChangeListener {
    private static UIKeyBox instance = null;
    private final TablaModel tablaModel = new TablaModel();
    private final KBDBAccessor kbdbAccessor = KBDBAccessor.getInstance();
    private final ContextMenu contextMenu;

    private UIKeyBox() {
        initComponents();
        kbdbAccessor.addDBChangeListener(this);
        IconFontSwing.register(FontAwesome.getIconFont());
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        UIUtil.setTitle("KeyBox v" + Constants.APP_VERSION, this);
        setupShortcuts();
        setCellRenderers();
        contextMenu = ContextMenu.getInstance();
        addContextMenuListeners();
    }

    public static UIKeyBox getInstance() {
        if (instance == null) {
            instance = new UIKeyBox();
        }
        return instance;
    }

    private void createBtn(ActionEvent e) {
        PasswordForm passwordForm = new PasswordForm(this);
        passwordForm.setVisible(true);

        PasswordEntry passwordEntry = passwordForm.getPasswordEntry();
        if (passwordEntry != null) {
            kbdbAccessor.create(passwordEntry);
        }
    }

    private void editBtn(ActionEvent e) {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        PasswordEntry passwordEntry = tablaModel.getPasswordEntryAt(selectedRow);
        PasswordForm passwordForm = new PasswordForm(this, passwordEntry);
        passwordForm.setVisible(true);

        PasswordEntry editedPasswordEntry = passwordForm.getPasswordEntry();
        if (editedPasswordEntry != null) {
            editedPasswordEntry.setPasswordId(passwordEntry.getPasswordId());
            kbdbAccessor.edit(editedPasswordEntry);
        }
    }

    private void deleteBtn(ActionEvent e) {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        PasswordEntry passwordEntry = tablaModel.getPasswordEntryAt(selectedRow);
        if(UIUtil.showConfirmDialog("¿Estás seguro de que deseas eliminar esta entrada?")) {
            kbdbAccessor.delete(passwordEntry);
        }
    }

    @Override
    public void onDBChanged() {
        tablaModel.refreshData();
        tabla.repaint();
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

    private void setCellRenderers() {
        tabla.getColumn("Contraseña").setCellRenderer(new PasswordRenderer());
        tabla.getColumn("Contraseña").setCellEditor(new PasswordEditor());
    }

    private void addContextMenuListeners() {
        contextMenu.createItem.addActionListener(this::createBtn);
        contextMenu.editItem.addActionListener(this::editBtn);
        contextMenu.deleteItem.addActionListener(this::deleteBtn);
    }

    private void tablaMouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            contextMenu.createItem.setVisible(false);
            contextMenu.editItem.setVisible(true);
            contextMenu.deleteItem.setVisible(true);
            contextMenu.showItem.setVisible(true);
            contextMenu.copyUserItem.setVisible(true);
            contextMenu.copyPasswordItem.setVisible(true);
            contextMenu.show(tabla, e.getX(), e.getY());
        }
    }

    private void tablaScrollPaneMouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) {
            contextMenu.createItem.setVisible(true);
            contextMenu.editItem.setVisible(false);
            contextMenu.deleteItem.setVisible(false);
            contextMenu.showItem.setVisible(false);
            contextMenu.copyUserItem.setVisible(false);
            contextMenu.copyPasswordItem.setVisible(false);
            contextMenu.show(tablaScrollPane, e.getX(), e.getY());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        menuBar = new JMenuBar();
        createBtn = new JButton();
        editBtn = new JButton();
        deleteBtn = new JButton();
        tablaScrollPane = new JScrollPane();
        tabla = new JTable(tablaModel);

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/images/keybox_icon_64.png")).getImage());
        setForeground(new Color(0xc8d3f5));
        setPreferredSize(new Dimension(900, 600));
        setMinimumSize(new Dimension(600, 300));
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
            tablaScrollPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    tablaScrollPaneMouseClicked(e);
                }
            });

            //---- tabla ----
            tabla.setFocusable(false);
            tabla.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            tabla.setBorder(BorderFactory.createEmptyBorder());
            tabla.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    tablaMouseClicked(e);
                }
            });
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

class PasswordRenderer extends JPanel implements TableCellRenderer {

    private JPasswordField passwordField;
    private JToggleButton toggleButton;

    public PasswordRenderer() {
        setLayout(new BorderLayout());

        passwordField = new JPasswordField();
        passwordField.setBorder(null); // Quita el borde
        passwordField.setOpaque(false); // Hace el fondo transparente
        passwordField.setMargin(new Insets(0, 0, 0, 0)); // Quita el margen para que se ajuste al botón

        toggleButton = new JToggleButton(
                IconFontSwing.buildIcon(
                        FontAwesome.EYE,
                        16,
                        KBLaf.LIGHT_BLUE
                )
        );

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggleButton.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Mostrar la contraseña
                    toggleButton.setText("Hide");
                } else {
                    passwordField.setEchoChar('*'); // Ocultar la contraseña
                    toggleButton.setText("Show");
                }
            }
        });

        add(passwordField, BorderLayout.CENTER);
        add(toggleButton, BorderLayout.EAST);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof String) {
            passwordField.setText((String) value);
        }

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        return this;
    }
}

class PasswordEditor extends AbstractCellEditor implements TableCellEditor {

    private JPasswordField passwordField;
    private JToggleButton toggleButton;
    private JPanel panel;

    public PasswordEditor() {
        passwordField = new JPasswordField();
        passwordField.setBorder(null); // Quita el borde
        passwordField.setOpaque(false); // Hace el fondo transparente
        passwordField.setMargin(new Insets(0, 0, 0, 0)); // Quita el margen para que se ajuste al botón

        toggleButton = new JToggleButton(
                IconFontSwing.buildIcon(
                        FontAwesome.EYE,
                        16,
                        KBLaf.LIGHT_BLUE
                )
        );
        toggleButton.setBorder(null); // Quita el borde
        toggleButton.setOpaque(false); // Hace el fondo transparente

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggleButton.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Mostrar la contraseña
                    toggleButton.setText("Hide");
                } else {
                    passwordField.setEchoChar('*'); // Ocultar la contraseña
                    toggleButton.setText("Show");
                }
            }
        });

        panel = new JPanel(new BorderLayout());
        panel.add(passwordField, BorderLayout.CENTER);
        panel.add(toggleButton, BorderLayout.EAST);

        panel.setPreferredSize(new Dimension(200, 25));
    }

    @Override
    public Object getCellEditorValue() {
        return new String(passwordField.getPassword());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof String) {
            passwordField.setText((String) value);
        }
        toggleButton.setText(toggleButton.isSelected() ? "Hide" : "Show");

        return panel;
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }
}
