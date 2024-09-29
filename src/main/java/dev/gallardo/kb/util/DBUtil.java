package dev.gallardo.kb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static dev.gallardo.kb.util.Constants.*;

/**
 * Utility class for database operations.
 * <p>
 * This class contains methods to create the database file and setup the database.
 * <p>
 * The database file is created in the application directory and the database is created using the default.sql script.
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 * @see Constants
 * @see java.io.File
 * @see java.io.IOException
 * @see java.sql.Connection
 * @see java.sql.DriverManager
 * @see java.sql.SQLException
 * @see java.sql.Statement
 * @see java.util.Arrays
 * @see java.io.InputStream
 * @see java.io.InputStreamReader
 * @see java.io.BufferedReader
 */
@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class DBUtil {


    public static void createFile() {
        File dbDir = new File(APP_DIR);
        File dbFile = new File(DB_FILE_PATH);
        try {
            dbDir.mkdirs();
            dbFile.createNewFile();
            LOGGER.info("Archivo de la base de datos creado en: {}", DB_FILE_PATH);
        } catch (IOException e) {
            LOGGER.error("Error al crear el archivo de la base de datos.", e);
        }
    }

    public static void setup() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            LOGGER.info("Creando la base de datos...");
            defaultSQLScript(conn);
        } catch (Exception e) {
            LOGGER.error("Error al crear la base de datos.", e);
        }
    }

    private static void defaultSQLScript(Connection conn) throws IOException, java.sql.SQLException {
        try (InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("default.sql")) {

            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder sqlBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sqlBuilder.append(line).append(System.lineSeparator());
                }
                String sql = sqlBuilder.toString();

                String[] sqlCommands = sql.split(";");
                try (Statement stmt = conn.createStatement()) {
                    for (String sqlCommand : sqlCommands) {
                        String trim = sqlCommand.trim();
                        if (!trim.isEmpty()) {
                            if (trim.startsWith("CREATE TABLE")) {
                                try {
                                    stmt.execute(trim);
                                } catch (SQLException e) {
                                    LOGGER.error("Error al ejecutar el comando SQL: {}", trim, e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}
