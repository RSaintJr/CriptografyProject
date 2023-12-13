package Crypt;

/**
 * The Encrypt interface defines methods for encrypting and decrypting data.
 * Classes implementing this interface should provide implementations for both
 * encryption and decryption operations using a specific encryption algorithm.
 */
public interface Encrypt {

    /**
     * Encrypts the given data.
     *
     * @param data the data to be encrypted
     * @return the encrypted data
     */
    String encrypt(String data);

    /**
     * Decrypts the given data.
     *
     * @param data the data to be decrypted
     * @return the decrypted data
     */
    String decrypt(String data);
}
