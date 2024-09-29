package dev.gallardo.kb.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import javax.crypto.SecretKey;

/**
 * Utility class for managing a keystore.
 * <p>
 * This class provides methods for storing and loading a secret key in a keystore.
 * The keystore is used to securely store the secret key.
 * <p>
 * Example usage:
 * <pre>{@code
 * KeyStoreManager keyStoreManager = KeyStoreManager.getInstance("password");
 * SecretKey key = EncryptionUtil.generateKey();
 * keyStoreManager.storeKey(key);
 * SecretKey loadedKey = keyStoreManager.loadKey();
 * }</pre>
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 */
public class KeyStoreManager {
    private final String KEYSTORE_PASSWORD;
    private static KeyStoreManager instance;

    public static KeyStoreManager getInstance(String keystorePassword) {
        if (instance == null) {
            instance = new KeyStoreManager(keystorePassword);
        }
        return instance;
    }

    private KeyStoreManager(String keystorePassword) {
        KEYSTORE_PASSWORD = keystorePassword;
    }

    /**
     * Stores a secret key in a keystore.
     * @param key the secret key to store
     * @throws Exception if an error occurs while storing the key
     */
    public void storeKey(SecretKey key) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());
        KeyStore.SecretKeyEntry keyEntry = new KeyStore.SecretKeyEntry(key);
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(KEYSTORE_PASSWORD.toCharArray());
        keyStore.setEntry(Constants.KEYSTORE_KEY_ALIAS, keyEntry, protectionParam);
        try (FileOutputStream fos = new FileOutputStream(Constants.KEYSTORE_FILE)) {
            keyStore.store(fos, KEYSTORE_PASSWORD.toCharArray());
        }
    }

    /**
     * Loads a secret key from a keystore.
     * @return the loaded secret key
     * @throws Exception if an error occurs while loading the key
     */
    public SecretKey loadKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        try (FileInputStream fis = new FileInputStream(Constants.KEYSTORE_FILE)) {
            keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());
        }
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(KEYSTORE_PASSWORD.toCharArray());
        KeyStore.SecretKeyEntry keyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(Constants.KEYSTORE_KEY_ALIAS, protParam);
        return keyEntry.getSecretKey();
    }
}
