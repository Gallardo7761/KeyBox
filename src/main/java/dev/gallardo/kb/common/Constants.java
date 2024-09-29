package dev.gallardo.kb.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Class that contains constants used throughout the application.
 * <p>
 * <strong>Attributes:</strong><br>
 * - {@code APP_NAME}: Name of the application.<br>
 * - {@code APP_VERSION}: Version of the application.<br>
 * - {@code APP_AUTHOR}: Author of the application.<br>
 * - {@code APP_DIR}: Directory where the application data is stored.<br>
 * - {@code DB_FILE_NAME}: Name of the database file.<br>
 * - {@code DB_FILE_PATH}: Path to the database file.<br>
 * - {@code DB_URL}: URL to connect to the database.<br>
 * - {@code DEFAULT_SQL}: Path to the default SQL file.<br>
 * - {@code KEYSTORE_KEY_ALIAS}: Alias for the keystore key.<br>
 * - {@code KEYSTORE_FILE_NAME}: Name of the keystore file.<br>
 * - {@code KEYSTORE_FILE}: Path to the keystore file.<br>
 * - {@code LOGGER}: Logger instance for the application.
 * <p>
 * <strong>Methods:</strong><br>
 * - {@code Constants()}: Private constructor to prevent instantiation.
 * @author Gallardo7761
 * @since 1.0
 * @version 1.0
 * @see Logger
 * @see LoggerFactory
 * @see File
 * @see System
 */
@SuppressWarnings("all")
public class Constants {
    public static final String APP_NAME = "KeyBox";
    public static final String APP_VERSION = "1.0-SNAPSHOT";
    public static final String APP_AUTHOR = "Gallardo7761";
    public static final String APP_DIR = System.getProperty("user.home") + File.separator + "." + APP_NAME.toLowerCase();

    public static final String DB_FILE_NAME = "macaroni.kbdb";
    public static final String DB_FILE_PATH = APP_DIR + File.separator + DB_FILE_NAME;
    public static final String DB_URL = "jdbc:sqlite:" + DB_FILE_PATH;
    public static final String DEFAULT_SQL = "resources/default.sql";

    public static final String KEYSTORE_KEY_ALIAS = "KBKey";
    public static final String KEYSTORE_FILE_NAME = "kbkeys.jks";
    public static final String KEYSTORE_FILE = APP_DIR + File.separator + KEYSTORE_FILE_NAME;

    public static final Logger LOGGER = LoggerFactory.getLogger(Constants.APP_NAME);

    private Constants() {
        throw new AssertionError("Utility class cannot be instantiated.");
    }
}
