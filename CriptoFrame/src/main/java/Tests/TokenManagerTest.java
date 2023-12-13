/**
 * This class contains unit tests for the TokenManager class.
 */
package Tests;

import DTO.User;
import Management.TokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenManagerTest {

    private TokenManager tokenManager;
    private User testUser;

    /**
     * Sets up the necessary objects before each test.
     */
    @BeforeEach
    void setUp() {
        tokenManager = new TokenManager();
        testUser = new User("testUser", "password", "admin");
    }

    /**
     * Tests the creation of a token by the TokenManager.
     * <p>
     * This test creates a token using the TokenManager for a test user
     * and ensures that the token is not null and can be successfully validated.
     * </p>
     */
    @Test
    void createToken_ShouldCreateToken() {
        String token = tokenManager.createToken(testUser);
        assertNotNull(token);
        assertTrue(tokenManager.validateToken(token));
    }

    /**
     * Tests the validation of a token with valid credentials.
     * <p>
     * This test creates a token using the TokenManager for a test user
     * and validates that the token is valid.
     * </p>
     */
    @Test
    void validateToken_WithValidToken_ShouldReturnTrue() {
        String token = tokenManager.createToken(testUser);
        assertTrue(tokenManager.validateToken(token));
    }

    /**
     * Tests the validation of an invalid token.
     * <p>
     * This test validates that an invalid token returns false.
     * </p>
     */
    @Test
    void validateToken_WithInvalidToken_ShouldReturnFalse() {
        assertFalse(tokenManager.validateToken("invalidToken"));
    }

    /**
     * Tests the invalidation of a valid token.
     * <p>
     * This test creates a token using the TokenManager for a test user,
     * invalidates the token, and ensures that the token is no longer valid.
     * </p>
     */
    @Test
    void invalidateToken_WithValidToken_ShouldInvalidateToken() {
        String token = tokenManager.createToken(testUser);
        tokenManager.invalidateToken(token);
        assertFalse(tokenManager.validateToken(token));
    }

    /**
     * Tests the invalidation of an invalid token without throwing an exception.
     * <p>
     * This test ensures that invalidating an invalid token does not throw an exception.
     * </p>
     */
    @Test
    void invalidateToken_WithInvalidToken_ShouldNotThrowException() {
        assertDoesNotThrow(() -> tokenManager.invalidateToken("invalidToken"));
    }
}
