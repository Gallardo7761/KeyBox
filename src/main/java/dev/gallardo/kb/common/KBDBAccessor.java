package dev.gallardo.kb.common;

import dev.gallardo.kb.util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KBDBAccessor {

    private KBDBAccessor() {}

    private static KBDBAccessor instance = new KBDBAccessor();

    public static KBDBAccessor getInstance() {
        if(instance == null) {
            instance = new KBDBAccessor();
        }
        return instance;
    }

    private List<DBChangeListener> listeners = new ArrayList<>();

    private void notifyDBChange() {
        for(DBChangeListener listener : listeners) {
            listener.onDBChanged();
        }
    }

    public void addDBChangeListener(DBChangeListener listener) {
        listeners.add(listener);
    }

    public PasswordEntry getById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PasswordEntry passwordEntry = null;

        try {
            connection = DriverManager.getConnection(Constants.DB_URL);
            preparedStatement = connection.prepareStatement("SELECT passwordId, title, userName, url, password FROM Passwords WHERE passwordId = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                passwordEntry = new PasswordEntry(
                        resultSet.getInt("passwordId"),
                        resultSet.getString("title"),
                        resultSet.getString("userName"),
                        resultSet.getString("url"),
                        resultSet.getString("password")
                );
            }

        } catch (SQLException e) {
            Constants.LOGGER.error("Error al obtener datos de la base de datos.", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Constants.LOGGER.error("Error al cerrar la conexión a la base de datos.", e);
            }
        }

        return passwordEntry;
    }

    public PasswordEntry[] getAll() {
        List<PasswordEntry> passwordEntries = fetchDataFromDB();
        return passwordEntries.toArray(new PasswordEntry[0]);
    }

    public void create(PasswordEntry passwordEntry) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(Constants.DB_URL);
            preparedStatement = connection.prepareStatement("INSERT INTO Passwords (title, userName, url, password) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, passwordEntry.getTitle());
            preparedStatement.setString(2, passwordEntry.getUserName());
            preparedStatement.setString(3, passwordEntry.getUrl());
            preparedStatement.setString(4, passwordEntry.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Constants.LOGGER.error("Error al insertar datos en la base de datos.", e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Constants.LOGGER.error("Error al cerrar la conexión a la base de datos.", e);
            }
        }

        notifyDBChange();
    }

    public void edit(PasswordEntry passwordEntry) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(Constants.DB_URL);
            preparedStatement = connection.prepareStatement("UPDATE Passwords SET title = ?, userName = ?, url = ?, password = ? WHERE passwordId = ?");
            preparedStatement.setString(1, passwordEntry.getTitle());
            preparedStatement.setString(2, passwordEntry.getUserName());
            preparedStatement.setString(3, passwordEntry.getUrl());
            preparedStatement.setString(4, passwordEntry.getPassword());
            preparedStatement.setInt(5, passwordEntry.getPasswordId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Constants.LOGGER.error("Error al actualizar datos en la base de datos.", e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Constants.LOGGER.error("Error al cerrar la conexión a la base de datos.", e);
            }
        }

        notifyDBChange();
    }

    public void delete(PasswordEntry passwordEntry) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(Constants.DB_URL);
            preparedStatement = connection.prepareStatement("DELETE FROM Passwords WHERE passwordId = ?");
            preparedStatement.setInt(1, passwordEntry.getPasswordId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Constants.LOGGER.error("Error al eliminar datos en la base de datos.", e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                Constants.LOGGER.error("Error al cerrar la conexión a la base de datos.", e);
            }
        }

        notifyDBChange();
    }

    public List<PasswordEntry> fetchDataFromDB() {
        List<PasswordEntry> passwordEntries = new ArrayList<>();

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

        return passwordEntries;
    }
}
