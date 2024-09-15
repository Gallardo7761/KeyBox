/*
 * Created by JFormDesigner on Sat Sep 07 03:40:40 CEST 2024
 */

package dev.gallardo.kb.ui;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
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

        UIUtil.setTitle("KeyBox v" + Constants.APP_VERSION, this);
        contextMenu = ContextMenu.getInstance();
        addContextMenuListeners();

        IconFontSwing.register(FontAwesome.getIconFont());
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        setIcons();

        setupShortcuts();
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
        int[] selectedRows = tabla.getSelectedRows();
        if (selectedRows.length == 0) {
            return;
        }

        List<PasswordEntry> passwordEntries = Arrays.stream(selectedRows)
            .mapToObj(tablaModel::getPasswordEntryAt)
            .toList();

        if(UIUtil.showConfirmDialog("¿Estás seguro de que deseas eliminar esta/s entrada/s?")) {
            passwordEntries.forEach(kbdbAccessor::delete);
        }
    }

    private void copyPassword() {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        PasswordEntry passwordEntry = tablaModel.getPasswordEntryAt(selectedRow);
        StringSelection stringSelection = new StringSelection(passwordEntry.getPassword());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    private void copyUser() {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        PasswordEntry passwordEntry = tablaModel.getPasswordEntryAt(selectedRow);
        StringSelection stringSelection = new StringSelection(passwordEntry.getUserName());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    private void showPassword() {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        PasswordEntry passwordEntry = tablaModel.getPasswordEntryAt(selectedRow);
        UIUtil.showInfoDialog("Contraseña: " + passwordEntry.getPassword());
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

    private void setIcons() {
        createBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.PLUS, 16, KBLaf.LIGHT_BLUE));
        editBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.PENCIL, 16, KBLaf.LIGHT_BLUE));
        deleteBtn.setIcon(IconFontSwing.buildIcon(FontAwesome.TRASH, 16, KBLaf.LIGHT_BLUE));
        contextMenu.createItem.setIcon(IconFontSwing.buildIcon(FontAwesome.PLUS, 16, KBLaf.LIGHT_BLUE));
        contextMenu.editItem.setIcon(IconFontSwing.buildIcon(FontAwesome.PENCIL, 16, KBLaf.LIGHT_BLUE));
        contextMenu.deleteItem.setIcon(IconFontSwing.buildIcon(FontAwesome.TRASH, 16, KBLaf.LIGHT_BLUE));
        contextMenu.showItem.setIcon(IconFontSwing.buildIcon(FontAwesome.EYE, 16, KBLaf.LIGHT_BLUE));
        contextMenu.copyUserItem.setIcon(IconFontSwing.buildIcon(FontAwesome.USER, 16, KBLaf.LIGHT_BLUE));
        contextMenu.copyPasswordItem.setIcon(IconFontSwing.buildIcon(FontAwesome.KEY, 16, KBLaf.LIGHT_BLUE));
    }

    private void addContextMenuListeners() {
        contextMenu.createItem.addActionListener(this::createBtn);
        contextMenu.editItem.addActionListener(this::editBtn);
        contextMenu.deleteItem.addActionListener(this::deleteBtn);
        contextMenu.showItem.addActionListener(e -> showPassword());
        contextMenu.copyUserItem.addActionListener(e -> copyUser());
        contextMenu.copyPasswordItem.addActionListener(e -> copyPassword());
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
            createBtn.setToolTipText("Ctrl + N");
            createBtn.setMaximumSize(new Dimension(64, 24));
            createBtn.setMinimumSize(new Dimension(64, 24));
            createBtn.setPreferredSize(new Dimension(64, 24));
            createBtn.addActionListener(e -> createBtn(e));
            menuBar.add(createBtn);

            //---- editBtn ----
            editBtn.setText("Editar");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            editBtn.setBorder(new EmptyBorder(1, 1, 1, 1));
            editBtn.setBackground(new Color(0x222436));
            editBtn.setToolTipText("Ctrl + E");
            editBtn.setMaximumSize(new Dimension(64, 24));
            editBtn.setMinimumSize(new Dimension(64, 24));
            editBtn.setPreferredSize(new Dimension(64, 24));
            editBtn.addActionListener(e -> editBtn(e));
            menuBar.add(editBtn);

            //---- deleteBtn ----
            deleteBtn.setText("Eliminar");
            deleteBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            deleteBtn.setBorder(new EmptyBorder(1, 1, 1, 1));
            deleteBtn.setBackground(new Color(0x222436));
            deleteBtn.setToolTipText("Ctrl + D");
            deleteBtn.setMaximumSize(new Dimension(72, 24));
            deleteBtn.setMinimumSize(new Dimension(72, 24));
            deleteBtn.setPreferredSize(new Dimension(72, 24));
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