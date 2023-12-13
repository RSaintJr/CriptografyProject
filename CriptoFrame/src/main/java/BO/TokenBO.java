package BO;

import DTO.Token;
import DAO.TokenDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * TokenBO class provides business operations related to authentication tokens.
 *
 * This class encapsulates the business logic for handling authentication tokens,
 * including insertion, listing, updating, deletion, and searching for tokens.
 */
public class TokenBO {

    // Logger for logging token-related operations
    private static final Logger logger = LoggerFactory.getLogger(TokenBO.class);

    // Data Access Object for handling token-related database operations
    private static final TokenDAO tokenDAO = new TokenDAO();

    /**
     * Inserts a new authentication token into the database.
     *
     * @param token          the authentication token to be inserted
     * @param encryptionType the encryption type used for storing the token
     * @return true if the token is inserted successfully, false otherwise
     */
    public boolean insert(Token token, String encryptionType) {
        logger.info("Inserting token: " + token);

        // Check if a token already exists for the user
        if (!tokenDAO.exists(token.getUser().getUsername())) {
            boolean result = tokenDAO.insert(token, encryptionType);
            if (result) {
                logger.info("Token inserted successfully: " + token);
            } else {
                logger.error("Failed to insert token: " + token);
            }
            return result;
        }

        logger.warn("Token already exists for user: " + token.getUser().getUsername());
        return false;
    }

    /**
     * Lists all authentication tokens in the database.
     *
     * @return a list of authentication tokens
     */
    public List<Token> listEveryone() {
        logger.info("Listing all tokens");
        return tokenDAO.listEveryone();
    }

    /**
     * Updates an existing authentication token in the database.
     *
     * @param token          the authentication token to be updated
     * @param encryptionType the encryption type used for storing the token
     * @return true if the token is updated successfully, false otherwise
     */
    public boolean update(Token token, String encryptionType) {
        logger.info("Updating token: " + token);
        return tokenDAO.update(token.getUser().getUsername(),token.getValue(), encryptionType, token.getUser().getKey());
    }

    /**
     * Deletes the authentication token associated with the specified username.
     *
     * @param username the username of the user for whom the token will be deleted
     * @return true if the token is deleted successfully, false otherwise
     */
    public boolean delete(String username) {
        logger.info("Deleting token for user: " + username);
        return tokenDAO.delete(username);
    }

    /**
     * Searches for an authentication token based on its value.
     *
     * @param value the value of the authentication token to search for
     * @return the authentication token if found, or null if not found
     */
    public Token searchForTokenValue(String value) {
        logger.info("Searching for token: " + value);
        return tokenDAO.searchForValue(value);
    }

    /**
     * Checks if an authentication token exists for the specified username.
     *
     * @param username the username of the user for whom to check the token existence
     * @return true if a token exists for the user, false otherwise
     */
    public boolean tokenExists(String username) {
        logger.info("Checking if token exists for user: " + username);
        return tokenDAO.exists(username);
    }
}

