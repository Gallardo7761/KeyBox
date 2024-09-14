package dev.gallardo.kb.common;

import dev.gallardo.kb.util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KBDBAccessor {
    public static List<PasswordEntry> fetchDataFromDB() {
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
