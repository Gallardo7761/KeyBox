package dev.gallardo.kb.ui.models;

import dev.gallardo.kb.common.KBDBAccessor;
import dev.gallardo.kb.common.PasswordEntry;
import dev.gallardo.kb.util.Constants;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TablaModel extends AbstractTableModel {
    private List<PasswordEntry> passwordEntries = KBDBAccessor.fetchDataFromDB();
    private final String[] columnNames = {"Título", "Usuario", "Url", "Contraseña"};

    public TablaModel() {
        passwordEntries = new ArrayList<>();
        passwordEntries = KBDBAccessor.fetchDataFromDB();
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

        switch (columnIndex) {
            case 0: return entry.getTitle();
            case 1: return entry.getUserName();
            case 2: return entry.getUrl();
            case 3: return entry.isPasswordVisible() ? entry.getPassword() : "••••••••";
            default: return null;
        }
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
        passwordEntries = KBDBAccessor.fetchDataFromDB();
        fireTableDataChanged();
    }
}