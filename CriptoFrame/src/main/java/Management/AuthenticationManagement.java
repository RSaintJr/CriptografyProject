package Management;

import BO.*;
import Crypt.*;
import DTO.*;

/**
 * AuthenticationManagement class provides methods for user authentication.
 *
 * This class is responsible for authenticating users by interacting with the
 * EncryptService for password encryption, the UserBO for retrieving user information,
 * and the TokenManager for generating authentication tokens.
 */
public class AuthenticationManagement {

    // Service for encryption and decryption operations
    private final EncryptService encryptService;

    // Manager for handling authentication tokens
    private final TokenManager tokenManager;

    /**
     * Constructs an AuthenticationManagement object with the specified EncryptService
     * and TokenManager.
     *
     * @param encryptService the service for encrypting and decrypting data
     * @param tokenManager   the manager for handling authentication tokens
     */
    public AuthenticationManagement(EncryptService encryptService, TokenManager tokenManager) {
        this.encryptService = encryptService;
        this.tokenManager = tokenManager;
    }

    /**
     * Authenticates a user by verifying the provided username and password.
     *
     * @param username the username of the user to be authenticated
     * @param password the password of the user to be authenticated
     * @return a Token object representing the authentication token if authentication is successful,
     *         or null if authentication fails
     */
    public Token authenticateUser(String username, String password) {
        // Create a new instance of UserBO to interact with user data
        UserBO userBO = new UserBO();

        // Encrypt the provided password for comparison
        String encryptedPassword = encryptService.encrypt(password);

        // Search for the user based on the provided username
        User user = userBO.searchForUser(username);

        // Check if the passwords match
        if (!encryptedPassword.equals(user.getPassword())) {
            return null;
        }

        // Generate an authentication token for the authenticated user
        String tokenValue = tokenManager.createToken(user);

        // Return a Token object with the authentication token and associated user information
        return new Token(tokenValue, user);
    }
}

