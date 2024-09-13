package dev.gallardo.kb.ui.models;

import dev.gallardo.kb.common.PasswordEntry;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class TablaModel extends AbstractTableModel {

    private final String[] columnNames = {"Servicio", "Usuario", "Contraseña"};
    private final List<PasswordEntry> data;

    public TablaModel() {
        this.data = new ArrayList<>();  // Lista de entradas de contraseñas
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PasswordEntry entry = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return entry.getService();
            case 1:
                return entry.getUsername();
            case 2:
                return entry.isPasswordVisible() ? entry.getPassword() : "********";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Solo la columna de la contraseña es editable (para poder revelarla/ocultarla)
        return columnIndex == 2;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        PasswordEntry entry = data.get(rowIndex);
        if (columnIndex == 2) {
            entry.setPasswordVisible(!entry.isPasswordVisible()); // Cambia la visibilidad de la contraseña
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    public void addPasswordEntry(PasswordEntry entry) {
        data.add(entry);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void removePasswordEntry(int rowIndex) {
        data.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
