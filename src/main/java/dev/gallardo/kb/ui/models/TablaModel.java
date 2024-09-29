package dev.gallardo.kb.ui.models;

import dev.gallardo.kb.common.KBDBAccessor;
import dev.gallardo.kb.common.PasswordEntry;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Table model for the password entries table.
 * Contains the data for the table and the column names.
 * <p>
 * <strong>Attributes:</strong><br>
 * - {@code kbdbAccessor}: Instance of the {@code KBDBAccessor} class to access the database.<br>
 * - {@code passwordEntries}: List of {@code PasswordEntry} objects to display in the table.<br>
 * - {@code columnNames}: Array of strings containing the names of the columns in the table.
 * <p>
 * <strong>Methods:</strong><br>
 * - {@code getRowCount()}: Returns the number of rows in the table.<br>
 * - {@code getColumnCount()}: Returns the number of columns in the table.<br>
 * - {@code getValueAt()}: Returns the value of a cell in the table.<br>
 * - {@code setValueAt()}: Sets the value of a cell in the table.<br>
 * - {@code getColumnName()}: Returns the name of a column in the table.<br>
 * - {@code getPasswordEntryAt()}: Returns the {@code PasswordEntry} object at a given row index.<br>
 * - {@code refreshData()}: Refreshes the data in the table.
 * <p>
 * <strong>Extends:</strong><br>
 * - {@code AbstractTableModel}
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 * @see KBDBAccessor
 * @see PasswordEntry
 * @see AbstractTableModel
 */

public class TablaModel extends AbstractTableModel {
    private final KBDBAccessor kbdbAccessor = KBDBAccessor.getInstance();
    private List<PasswordEntry> passwordEntries;
    private final String[] columnNames = {"", "Título", "Usuario", "Url"};

    public TablaModel() {
        passwordEntries = new ArrayList<>();
        passwordEntries = kbdbAccessor.getAll();
    }

    @Override
    public int getRowCount() {
        return passwordEntries.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PasswordEntry entry = passwordEntries.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> entry.getIcon();
            case 1 -> entry.getTitle();
            case 2 -> entry.getUserName();
            case 3 -> entry.getUrl();
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 4) {
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    public PasswordEntry getPasswordEntryAt(int rowIndex) {
        return passwordEntries.get(rowIndex);
    }

    public void refreshData() {
        passwordEntries.clear();
        passwordEntries = kbdbAccessor.getAll();
        fireTableDataChanged();
    }
}