/**
 * This class contains unit tests for the TokenBO class, which manages tokens.
 */
package Tests;

import BO.TokenBO;
import Crypt.EncryptService;
import DTO.KeyGenerator;
import DTO.Token;
import DTO.User;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TokenTests {

    private TokenBO tokenBO = new TokenBO();
    private User testUser = new User("testUser23", "password", "admin");

    /**
     * Tests the insertion of a token into the database.
     * <p>
     * This test generates a test token, inserts it into the database, encrypts its value,
     * and verifies that the token can be retrieved from the database.
     * </p>
     */
    @Test
    void insertToken_ShouldInsertToken() {
        testUser.setKey(KeyGenerator.generateAesKey(32));
        Token token = new Token("testToken23", testUser);
        assertTrue(tokenBO.insert(token, "AES"));
        EncryptService encryptService = new EncryptService("AES", testUser.getKey());
        token.setValue(encryptService.encrypt("testToken2"));
        assertNotNull(tokenBO.searchForTokenValue(token.getValue()));
    }

    /**
     * Tests listing all tokens in the database.
     * <p>
     * This test inserts a test token into the database and retrieves the list of all tokens.
     * It then asserts that the list is not null, has a size greater than 0, and contains the test token.
     * </p>
     */
    @Test
    void listEveryone_ShouldReturnAllTokens() {
        // Insert a test token
        testUser.setKey(KeyGenerator.generateAesKey(32));
        Token testToken = new Token("testToken", testUser);
        tokenBO.insert(testToken, "AES");

        // Retrieve the list of tokens
        List<Token> tokens = tokenBO.listEveryone();

        // Assert that the list is not null and contains the test token
        assertNotNull(tokens);
        assertTrue(tokens.size() > 0);
        assertTrue(tokens.contains(testToken));
    }

    /**
     * Tests updating the value of a token in the database.
     * <p>
     * This test inserts a test token, updates its value, and verifies that the updated value is stored in the database.
     * </p>
     */
    @Test
    void updateToken_ShouldUpdateToken() {
        // Insert a test token
        testUser.setKey(KeyGenerator.generateAesKey(32));
        Token testToken = new Token("testToken", testUser);
        tokenBO.insert(testToken, "AES");

        // Update the test token
        testToken.setValue("updatedToken");
        assertTrue(tokenBO.update(testToken, "AES"));

        // Retrieve the updated token from the database
        Token updatedToken = tokenBO.searchForTokenValue(testToken.getValue());

        // Assert that the retrieved token is not null and has the updated value
        assertNotNull(updatedToken);
        assertEquals("updatedToken", updatedToken.getValue());
    }

    /**
     * Tests deleting a token from the database.
     * <p>
     * This test inserts a test token, deletes it, and verifies that the token no longer exists in the database.
     * </p>
     */
    @Test
    void deleteToken_ShouldDeleteToken() {
        testUser.setKey(KeyGenerator.generateAesKey(32));
        // Insert a test token
        Token testToken = new Token("testToken", testUser);
        tokenBO.insert(testToken, "AES");

        // Delete the test token
        assertTrue(tokenBO.delete(testUser.getUsername()));

        // Assert that the token no longer exists in the database
        assertNull(tokenBO.searchForTokenValue((testToken.getValue())));
    }

    /**
     * Tests searching for a token by its value in the database.
     * <p>
     * This test inserts a test token, searches for it by its value, and verifies that the found token has the correct value.
     * </p>
     */
    @Test
    void searchForTokenValue_ShouldReturnToken() {
        testUser.setKey(KeyGenerator.generateAesKey(32));
        // Insert a test token
        Token testToken = new Token("testToken", testUser);
        tokenBO.insert(testToken, "AES");

        // Search for the test token
        Token foundToken = tokenBO.searchForTokenValue(testToken.getValue());

        // Assert that the found token is not null and has the correct value
        assertNotNull(foundToken);
        assertEquals(testToken.getValue(), foundToken.getValue());
    }

    /**
     * Tests checking if a token exists in the database.
     * <p>
     * This test inserts a test token and verifies that the existence of the token can be checked.
     * </p>
     */
    @Test
    void tokenExists_WhenTokenExists_ShouldReturnTrue() {
        testUser.setKey(KeyGenerator.generateAesKey(32));
        // Insert a test token
        Token testToken = new Token("testToken", testUser);
        tokenBO.insert(testToken, "AES");

        // Check if the token exists
        assertTrue(tokenBO.tokenExists(testUser.getUsername()));
    }

    /**
     * Tests checking if a non-existing token exists in the database.
     * <p>
     * This test checks if a token that does not exist returns false when checking for its existence.
     * </p>
     */
    @Test
    void tokenExists_WhenTokenDoesNotExist_ShouldReturnFalse() {
        // Check if a non-existing token exists
        assertFalse(tokenBO.tokenExists("nonExistingUser"));
    }
}
