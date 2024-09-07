package dev.gallardo.kb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Constants {
    public static final String APP_NAME = "KeyBox";
    public static final String APP_VERSION = "1.0-SNAPSHOT";
    public static final String APP_AUTHOR = "Gallardo7761";
    public static final String APP_DIR = System.getProperty("user.home") + File.separator + "." + APP_NAME.toLowerCase();

    public static final String DB_FILE_NAME = "macaroni.kbdb";
    public static final String DB_FILE_PATH = APP_DIR + File.separator + DB_FILE_NAME;
    public static final String DB_URL = "jdbc:sqlite:" + DB_FILE_PATH;
    public static final String DEFAULT_SQL = "resources/default.sql";

    public static final Logger LOGGER = LoggerFactory.getLogger(Constants.APP_NAME);

    private Constants() {
        throw new AssertionError("Utility class cannot be instantiated.");
    }
}
