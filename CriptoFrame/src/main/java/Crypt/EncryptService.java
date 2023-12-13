package Crypt;

/**
 * EncryptService class provides a service for encrypting and decrypting data using different encryption algorithms.
 *
 * This class implements the Encrypt interface, allowing clients to use the encrypt and decrypt methods
 * without being aware of the specific encryption algorithm being used. The available encryption algorithms
 * are AES and RSA.
 */
public class EncryptService implements Encrypt {

    // Instance of the selected encryption algorithm
    private final Encrypt crypto;

    /**
     * Private constructor for EncryptService.
     * Initializes the EncryptService with the specified encryption algorithm.
     *
     * @param encryptionType the type of encryption algorithm to use (AES)
     * @param key            the key to be used for encryption and decryption
     * @throws IllegalArgumentException if an invalid encryption type is provided
     */
    public EncryptService(String encryptionType, byte[] key) {
        if ("AES".equalsIgnoreCase(encryptionType)) {
            this.crypto = new AES_Crypt(key);
        } else {
            throw new IllegalArgumentException("Invalid encryption type: " + encryptionType);
        }
    }

    /**
     * Encrypts the given data using the selected encryption algorithm.
     *
     * @param data the data to be encrypted
     * @return the encrypted data as a string
     */
    @Override
    public String encrypt(String data) {
        return crypto.encrypt(data);
    }

    /**
     * Decrypts the given data using the selected encryption algorithm.
     *
     * @param data the data to be decrypted
     * @return the decrypted data as a string
     */
    @Override
    public String decrypt(String data) {
        return crypto.decrypt(data);
    }

    /**
     * Gets the name of the selected encryption algorithm.
     *
     * @return the name of the encryption algorithm (AES)
     */
    public String getEncryptionType() {
        return crypto.getClass().getSimpleName();
    }

    /**
     * Gets the key used for encryption and decryption.
     *
     * @return the key as a byte array
     */
    public byte[] getKey() {
        if (crypto instanceof AES_Crypt) {
            return ((AES_Crypt) crypto).getKey();
        } else {
            throw new IllegalStateException("Invalid encryption algorithm");
        }
    }
}
