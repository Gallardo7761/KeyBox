package dev.gallardo.kb.common;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for accessing the database and performing CRUD operations on the Passwords table.
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 * @see PasswordEntry
 * @see DBChangeListener
 * @see DriverManager
 * @see Connection
 * @see PreparedStatement
 * @see Statement
 * @see ResultSet
 * @see SQLException
 * @see List
 */
@SuppressWarnings({"unused"})
public class KBDBAccessor {

    private KBDBAccessor() {}

    private static KBDBAccessor instance = new KBDBAccessor();

    /**
     * Returns the singleton instance of KBDBAccessor.
     * @return The singleton instance of KBDBAccessor.
     */
    public static KBDBAccessor getInstance() {
        if (instance == null) {
            instance = new KBDBAccessor();
        }
        return instance;
    }

    private final List<DBChangeListener> listeners = new ArrayList<>();

    /**
     * Notifies all listeners that the database has changed.
     */
    private void notifyDBChange() {
        for (DBChangeListener listener : listeners) {
            listener.onDBChanged();
        }
    }

    /**
     * Adds a listener to the list of listeners.
     * @param listener The listener to be added.
     */
    public void addDBChangeListener(DBChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Gets a PasswordEntry object from the database by its ID.
     * @param id The ID of the entry to retrieve.
     * @return A PasswordEntry object with the details of the entry.
     */
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

    /**
     * Creates a new password entry in the database.
     * @param passwordEntry The PasswordEntry object containing the details of the entry to create.
     */
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

    /**
     * Edits an existing password entry in the database.
     * @param passwordEntry The PasswordEntry object containing the details of the entry to edit.
     */
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

    /**
     * Deletes an entry from the database.
     * @param passwordEntry The PasswordEntry object containing the details of the entry to delete.
     */
    public void delete(PasswordEntry passwordEntry) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(Constants.DB_URL);
            preparedStatement = connection.prepareStatement("DELETE FROM Passwords WHERE passwordId = ?");
            preparedStatement.setInt(1, passwordEntry.getPasswordId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Constants.LOGGER.error("Error al eliminar datos de la base de datos.", e);
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

    /**
     * Gets all entries from the database.
     * @return A list of PasswordEntry objects containing the details of all entries in the database.
     */
    public List<PasswordEntry> getAll() {
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
