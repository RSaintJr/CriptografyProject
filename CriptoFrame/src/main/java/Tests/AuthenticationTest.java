/**
 * This class contains unit tests for user authentication.
 */
package Tests;

import BO.UserBO;
import Crypt.EncryptService;
import DTO.*;
import Management.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {

    private AuthenticationManagement authenticationManagement;

    /**
     * Tests successful authentication of a user.
     * <p>
     * This test creates a test user and inserts it into the database.
     * Then, it authenticates the user using correct credentials and verifies
     * that a token is returned successfully.
     * </p>
     */
    @Test
    void testAuthenticateUser_Successful() {
        // Create a test user and insert it into the database
        UserBO userBO = new UserBO();
        User user = userBO.searchForUser("testUser1334");
        byte[] key = {42, -112, 91, 25, -20, -116, -110, -33, 47, 61, 105, -73, 80, 12, -58, 105, -30, 68, 120, -106, -121, 123, -26, -128, -37, 27, 67, -113, 126, -114, 9, -8};
        user.setKey(key);
        EncryptService encryptService = new EncryptService("AES", user.getKey());

        AuthenticationManagement authenticationManagement = new AuthenticationManagement(encryptService, new TokenManager());

        // Authenticate the user
        Token token = authenticationManagement.authenticateUser(user.getUsername(), "passwor24d3");

        // Assert that the authentication was successful and a token is returned
        assertNotNull(token.getValue());
        assertNotNull(token.getUser());
        assertEquals("testUser1334", token.getUser().getUsername());
    }

    /**
     * Tests authentication of a user with invalid credentials.
     * <p>
     * This test tries to authenticate a user with invalid credentials and verifies
     * that the authentication fails, and no token is returned.
     * </p>
     */
    @Test
    void testAuthenticateUser_InvalidCredentials() {
        // Try to authenticate with invalid credentials
        Token token = authenticationManagement.authenticateUser("nonexistentUser", "wrongPassword");

        // Assert that authentication fails and no token is returned
        assertNull(token);
    }
}
