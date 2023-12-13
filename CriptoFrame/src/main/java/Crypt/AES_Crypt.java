package Crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES_Crypt class provides methods for encrypting and decrypting data using AES algorithm.
 *
 * This class implements the Encrypt interface, defining the encrypt and decrypt methods.
 * The encryption is performed using the AES algorithm with a secret key specified by the
 * ALGORITHM constant.
 */
public class AES_Crypt implements Encrypt {

    // Algorithm used for AES encryption and decryption
    private static final String ALGORITHM = "AES";

    // Secret key used for AES encryption and decryption
    private final byte[] key;

    /**
     * Constructs an AES_Crypt object with the specified key for encryption and decryption.
     *
     * @param key the key used for encryption and decryption
     */
    public AES_Crypt(byte[] key) {
        this.key = key;
    }

    /**
     * Encrypts the given data using the AES algorithm and the provided key.
     *
     * @param data the data to be encrypted
     * @return the encrypted data as a Base64-encoded string
     * @throws RuntimeException if an error occurs during encryption
     */
    @Override
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    /**
     * Decrypts the given data using the AES algorithm and the provided key.
     *
     * @param data the data to be decrypted
     * @return the decrypted data as a string
     * @throws RuntimeException if an error occurs during decryption
     */
    @Override
    public String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }

    public byte[] getKey() {
        return key;
    }
}
