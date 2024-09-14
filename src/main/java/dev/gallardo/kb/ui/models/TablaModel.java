package dev.gallardo.kb.ui.models;

import dev.gallardo.kb.common.PasswordEntry;
import dev.gallardo.kb.util.Constants;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TablaModel extends AbstractTableModel {
    private List<PasswordEntry> passwordEntries;
    private String[] columnNames = {"Título", "Usuario", "Url", "Contraseña", ""};

    public TablaModel() {
        passwordEntries = new ArrayList<>();
        fetchDataFromDB();
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

    private void fetchDataFromDB() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(Constants.DB_URL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT passwordId, title, userName, url, password FROM Passwords");

            while (resultSet.next()) {
                PasswordEntry entry = new PasswordEntry(
                        resultSet.getInt("passwordId"),
                        resultSet.getString("title"),
                        resultSet.getString("userName"),
                        resultSet.getString("url"),
                        resultSet.getString("password")
                );
                passwordEntries.add(entry);
            }

        } catch (SQLException e) {
            Constants.LOGGER.error("Error al obtener datos de la base de datos.", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Constants.LOGGER.error("Error al cerrar la conexión a la base de datos.", e);
            }
        }
    }

    public void refreshData() {
        passwordEntries.clear();
        fetchDataFromDB();
        fireTableDataChanged();
    }
}