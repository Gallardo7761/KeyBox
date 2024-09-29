package dev.gallardo.kb.util;

import javax.crypto.SecretKey;

/**
 * Service to encrypt and decrypt passwords.
 * <p>
 * This service uses the {@link EncryptionUtil} to encrypt and decrypt passwords.
 * The encryption key is provided in the constructor.
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 * @see EncryptionUtil
 * @see SecretKey
 * @see javax.crypto.SecretKey
 */
public class PasswordEncryptionService {

    private final SecretKey key;

    public PasswordEncryptionService(SecretKey key) {
        this.key = key;
    }

    public String encryptPassword(String password) throws Exception {
        return EncryptionUtil.encrypt(password, key);
    }

    public String decryptPassword(String encryptedPassword) throws Exception {
        return EncryptionUtil.decrypt(encryptedPassword, key);
    }
}
