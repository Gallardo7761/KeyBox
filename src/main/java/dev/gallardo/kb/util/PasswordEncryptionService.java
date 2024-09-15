package dev.gallardo.kb.util;

import javax.crypto.SecretKey;

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
