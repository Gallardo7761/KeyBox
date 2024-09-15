package dev.gallardo.kb.ui.models;

import dev.gallardo.kb.common.KBDBAccessor;
import dev.gallardo.kb.common.PasswordEntry;
import dev.gallardo.kb.util.Constants;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TablaModel extends AbstractTableModel {
    private final KBDBAccessor kbdbAccessor = KBDBAccessor.getInstance();
    private List<PasswordEntry> passwordEntries = kbdbAccessor.fetchDataFromDB();
    private final String[] columnNames = {"Título", "Usuario", "Url", "Contraseña"};

    public TablaModel() {
        passwordEntries = new ArrayList<>();
        passwordEntries = kbdbAccessor.fetchDataFromDB();
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
            case 0 -> entry.getTitle();
            case 1 -> entry.getUserName();
            case 2 -> entry.getUrl();
            case 3 -> entry.isPasswordVisible() ? entry.getPassword() : "••••••••";
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        PasswordEntry entry = passwordEntries.get(rowIndex);
        if (columnIndex == 4) {
            entry.setPasswordVisible(!entry.isPasswordVisible());
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
        passwordEntries = kbdbAccessor.fetchDataFromDB();
        fireTableDataChanged();
    }
}