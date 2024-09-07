package dev.gallardo.kb.util;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static dev.gallardo.kb.util.Constants.*;


public class DBUtil {

    @SuppressWarnings("ResultOfMethodCallIgnored")
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
            executeSqlScript(conn);
        } catch (Exception e) {
            LOGGER.error("Error al crear la base de datos.", e);
        }
    }

    private static void executeSqlScript(Connection conn) throws IOException, java.sql.SQLException {
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
                    Arrays.stream(sqlCommands)
                        .map(String::trim)
                        .filter(trim -> !trim.isEmpty())
                        .filter(command -> command.startsWith("CREATE TABLE"))
                        .forEach(command -> {
                            try{
                                stmt.execute(command);
                            } catch (SQLException e) {
                                LOGGER.error("Error al ejecutar el comando SQL: {}", command, e);
                            }
                    });
                }
            }
        }
    }


}
