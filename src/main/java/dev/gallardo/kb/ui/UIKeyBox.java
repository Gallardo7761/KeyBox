/*
 * Created by JFormDesigner on Sat Sep 07 03:40:40 CEST 2024
 */

package dev.gallardo.kb.ui;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.border.*;

import dev.gallardo.kb.KeyBox;
import dev.gallardo.kb.common.*;
import dev.gallardo.kb.ui.models.TablaModel;
import dev.gallardo.kb.ui.themes.KBLaf;
import dev.gallardo.kb.util.*;
import dev.gallardo.kb.validators.PasswordEntryValidator;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.miginfocom.swing.*;

/**
 * Main window of the application.
 * <p>
 * This class is a singleton, so it can only be instantiated once.
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 * @see KBDBAccessor
 * @see PasswordEntry
 * @see TablaModel
 * @see PasswordForm
 * @see PasswordEntryValidator
 * @see PasswordEncryptionUtil
 * @see KeyStoreManager
 * @see UserInterfaceUtil
 */
@SuppressWarnings("ALL")
public class UIKeyBox extends JFrame implements DBChangeListener {
    private static UIKeyBox instance = null;
    private final TablaModel tablaModel = new TablaModel();
    private final KBDBAccessor kbdbAccessor = KBDBAccessor.getInstance();
    private final ContextMenu contextMenu;
    private final PasswordEncryptionUtil encryptionService;

    private UIKeyBox() {
        initComponents();
        kbdbAccessor.addDBChangeListener(this);

        UserInterfaceUtil.setTitle("KeyBox v" + Constants.APP_VERSION, this);
        contextMenu = ContextMenu.getInstance();
        addContextMenuListeners();

        setIcons();
        setupShortcuts();

        SecretKey key = loadSecretKey();
        encryptionService = new PasswordEncryptionUtil(key);
    }

    public static UIKeyBox getInstance() {
        if (instance == null) {
            instance = new UIKeyBox();
        }
        return instance;
    }

    private SecretKey loadSecretKey() {
        try {
            return KeyStoreManager.getInstance(KeyBox.masterPassword).loadKey();
        } catch (Exception ex) {
            Constants.LOGGER.error("Error al cargar la clave de cifrado", ex);
            return null;
        }
    }

    private String getDecryptedPassword(PasswordEntry passwordEntry) {
        try {
            String encryptedPassword = passwordEntry.getPassword();
            return encryptionService.decryptPassword(encryptedPassword);
        } catch (Exception ex) {
            UserInterfaceUtil.showErrorDialog("Error al descifrar la contraseña.", true);
            Constants.LOGGER.error("Error al descifrar la contraseña.", ex);
            return null;
        }
    }

    private void createBtn(ActionEvent e) {
        PasswordForm passwordForm = new PasswordForm(this);
        passwordForm.setVisible(true);

        PasswordEntry passwordEntry = passwordForm.getPasswordEntry();
        if (passwordEntry != null) {
            PasswordEntryValidator validator = new PasswordEntryValidator(passwordEntry);
            if (!validator.validate()) {
                UserInterfaceUtil.showErrorDialog(validator.getErrorMessages(), true);
                return;
            }

            try {
                passwordEntry.setPassword(encryptionService.encryptPassword(passwordEntry.getPassword()));
                kbdbAccessor.create(passwordEntry);
            } catch (Exception ex) {
                UserInterfaceUtil.showErrorDialog("Error al cifrar la contraseña.", true);
            }
        }
    }

    private void editBtn(ActionEvent e) {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) return;

        PasswordEntry passwordEntry = tablaModel.getPasswordEntryAt(selectedRow);
        PasswordEntry clonedPasswordEntry = new PasswordEntry(passwordEntry);
        try {
            String encryptedPassword = passwordEntry.getPassword();
            System.out.println("Texto cifrado para mostrar: " + encryptedPassword);
            String decryptedPassword = getDecryptedPassword(passwordEntry);
            System.out.println("Contraseña desencriptada: " + decryptedPassword);
            clonedPasswordEntry.setPassword(decryptedPassword);
        } catch (Exception ex) {
            UserInterfaceUtil.showErrorDialog("Error al descifrar la contraseña.", true);
            return;
        }

        PasswordForm passwordForm = new PasswordForm(this, clonedPasswordEntry);
        passwordForm.setVisible(true);
        PasswordEntry editedPasswordEntry = passwordForm.getPasswordEntry();

        if (editedPasswordEntry != null) {
            PasswordEntryValidator validator = new PasswordEntryValidator(editedPasswordEntry);
            if (!validator.validate()) {
                UserInterfaceUtil.showErrorDialog(validator.getErrorMessages(), true);
                return;
            }
            editedPasswordEntry.setPasswordId(passwordEntry.getPasswordId());
            try {
                if(editedPasswordEntry.equals(clonedPasswordEntry)) {
                    Constants.LOGGER.info("No se realizaron cambios en la entrada.");
                    return;
                }
                String encryptedEditedPassword = encryptionService.encryptPassword(editedPasswordEntry.getPassword());
                editedPasswordEntry.setPassword(encryptedEditedPassword);
                kbdbAccessor.edit(editedPasswordEntry);
            } catch (Exception ex) {
                UserInterfaceUtil.showErrorDialog("Error al cifrar la contraseña.", true);
            }
        }
    }

    private void deleteBtn(ActionEvent e) {
        int[] selectedRows = tabla.getSelectedRows();
        if (selectedRows.length == 0) return;

        List<PasswordEntry> passwordEntries = Arrays.stream(selectedRows)
                .mapToObj(tablaModel::getPasswordEntryAt)
                .toList();

        if (UserInterfaceUtil.showConfirmDialog("¿Estás seguro de que deseas eliminar esta/s entrada/s?")) {
            passwordEntries.forEach(kbdbAccessor::delete);
        }
    }

    private void showPassword() {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) return;

        PasswordEntry passwordEntry = tablaModel.getPasswordEntryAt(selectedRow);
        try {
            String encryptedPassword = passwordEntry.getPassword();
            System.out.println("Texto cifrado para mostrar: " + encryptedPassword);
            String decryptedPassword = getDecryptedPassword(passwordEntry);
            System.out.println("Contraseña desencriptada: " + decryptedPassword);
            UserInterfaceUtil.showInfoDialog(decryptedPassword);
        } catch (Exception ex) {
            UserInterfaceUtil.showErrorDialog("Error al descifrar la contraseña.", true);
            Constants.LOGGER.error("Error al descifrar la contraseña.", ex);
        }
    }

    private void copyPassword() {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) return;

        PasswordEntry passwordEntry = tablaModel.getPasswordEntryAt(selectedRow);
        try {
            String encryptedPassword = passwordEntry.getPassword();
            System.out.println("Texto cifrado para mostrar: " + encryptedPassword);
            String decryptedPassword = getDecryptedPassword(passwordEntry);
            System.out.println("Contraseña desencriptada: " + decryptedPassword);
            StringSelection stringSelection = new StringSelection(decryptedPassword);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        } catch (Exception ex) {
            UserInterfaceUtil.showErrorDialog("Error al descifrar la contraseña.", true);
        }
    }

    private void copyUser() {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) return;

        PasswordEntry passwordEntry = tablaModel.getPasswordEntryAt(selectedRow);
        StringSelection stringSelection = new StringSelection(passwordEntry.getUserName());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
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
                createBtn(e);
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeCreate, "createAction");
        getRootPane().getActionMap().put("createAction", createAction);

        // Shortcut for "Editar" (Ctrl + E)
        KeyStroke keyStrokeEdit = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK);
        Action editAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBtn(e);
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeEdit, "editAction");
        getRootPane().getActionMap().put("editAction", editAction);

        // Shortcut for "Eliminar" (Ctrl + D)
        KeyStroke keyStrokeDelete = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK);
        Action deleteAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBtn(e);
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeDelete, "deleteAction");
        getRootPane().getActionMap().put("deleteAction", deleteAction);

        // Shortcut for "Mostrar contraseña" (Ctrl + S)
        KeyStroke keyStrokeShow = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        Action showAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPassword();  // Simular clic en el botón "Mostrar contraseña"
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeShow, "showAction");
        getRootPane().getActionMap().put("showAction", showAction);  // Faltaba añadir esta línea

        // Shortcut for "Copiar usuario" (Ctrl + U)
        KeyStroke keyStrokeCopyUser = KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK);
        Action copyUserAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyUser();  // Simular clic en el botón "Copiar usuario"
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeCopyUser, "copyUserAction");
        getRootPane().getActionMap().put("copyUserAction", copyUserAction);  // Faltaba añadir esta línea

        // Shortcut for "Copiar contraseña" (Ctrl + C)
        KeyStroke keyStrokeCopyPassword = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK);
        Action copyPasswordAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyPassword();  // Simular clic en el botón "Copiar contraseña"
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeCopyPassword, "copyPasswordAction");
        getRootPane().getActionMap().put("copyPasswordAction", copyPasswordAction);  // Faltaba añadir esta línea
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