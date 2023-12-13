package Management;

import DTO.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TokenManager class manages authentication tokens for users.
 * This class provides methods for creating, validating, and invalidating authentication tokens.
 */
public class TokenManager {

    // Map to store active tokens with their corresponding users
    private final Map<String, User> activeTokens = new HashMap<>();

    /**
     * Creates a new authentication token for the specified user.
     * @param user the user for whom the authentication token is created
     * @return a string representing the authentication token
     */
    public String createToken(User user) {
        String tokenValue = UUID.randomUUID().toString();
        activeTokens.put(tokenValue, user);
        return tokenValue;
    }

    /**
     * Validates the provided authentication token.
     * @param tokenValue the authentication token to be validated
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String tokenValue) {
        return activeTokens.containsKey(tokenValue);
    }

    /**
     * Invalidates the provided authentication token, removing it from the active tokens.
     * @param tokenValue the authentication token to be invalidated
     */
    public void invalidateToken(String tokenValue) {
        activeTokens.remove(tokenValue);
    }
}

