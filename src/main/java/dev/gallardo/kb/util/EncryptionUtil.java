package dev.gallardo.kb.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Utility class for encrypting and decrypting data using AES.
 * <p>
 * This class provides methods for generating a secret key, encrypting data, and decrypting data.
 * The encryption algorithm used is AES (Advanced Encryption Standard).
 * <p>
 * Example usage:
 * <pre>{@code
 * SecretKey key = EncryptionUtil.generateKey();
 * String encryptedData = EncryptionUtil.encrypt("Hello, world!", key);
 * String decryptedData = EncryptionUtil.decrypt(encryptedData, key);
 * System.out.println(decryptedData); // Output: Hello, world!
 * }</pre>
 * @author Gallardo7761
 * @version 1.0
 * @since 1.0
 * @see <a href="https://en.wikipedia.org/wiki/Advanced_Encryption_Standard">Advanced Encryption Standard</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#AppA">Java Cryptography Architecture</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/crypto/package-summary.html">javax.crypto</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html">javax.crypto.Cipher</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/crypto/KeyGenerator.html">javax.crypto.KeyGenerator</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/crypto/SecretKey.html">javax.crypto.SecretKey</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/crypto/spec/SecretKeySpec.html">javax.crypto.spec.SecretKeySpec</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html">java.util.Base64</a>
 */
public class EncryptionUtil {
    private static final String ALGORITHM = "AES";

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(256); // 256-bit AES
        return keyGen.generateKey();
    }

    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
}
