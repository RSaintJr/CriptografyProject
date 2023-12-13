package DAO;

import Crypt.EncryptService;
import Connection.Conn;
import DTO.Token;
import DTO.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * TokenDAO class provides data access operations related to authentication tokens in the database.
 * This class includes methods for inserting, searching, updating, and deleting authentication tokens,
 * as well as checking token existence and listing all tokens.
 */
public class TokenDAO {

    // Logger for logging token-related database operations
    private static final Logger logger = LoggerFactory.getLogger(TokenDAO.class);

    /**
     * Inserts a new authentication token into the database.
     *
     * @param token          the authentication token to be inserted
     * @param encryptionType the encryption type used for storing the token
     * @return true if the token is inserted successfully, false otherwise
     */
    public boolean insert(Token token, String encryptionType) {
        logger.info("Inserting token: " + token);
        EncryptService encryptService = new EncryptService(encryptionType,token.getUser().getKey());
        String sql = "INSERT INTO Token (value, username) VALUES (?, ?)";
        String encryptedValue = encryptService.encrypt(token.getValue());
        try (Connection conn = Conn.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, encryptedValue);
            pstmt.setString(2, token.getUser().getUsername());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Token inserted successfully: " + token);
            } else {
                logger.error("Failed to insert token: " + token);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Exception occurred while inserting token: " + token, e);
            return false;
        }
    }

    /**
     * Searches for an authentication token based on its value.
     *
     * @param value the value of the authentication token to search for
     * @return the authentication token if found, or null if not found
     */
    public Token searchForValue(String value) {
        logger.info("Searching for token: " + value);
        Token token = null;
        String sql = "SELECT * FROM Token WHERE value = ?";
        try (Connection conn = Conn.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, value);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                token = new Token(rs.getString("value"), new User(rs.getString("username")));
                logger.info("Token found: " + token);
            }
        } catch (SQLException e) {
            logger.error("Exception occurred while searching for token: " + value, e);
        }
        return token;
    }

    /**
     * Updates an existing token in the database.
     *
     * @param username        the username associated with the token to be updated
     * @param updatedToken    the updated token value
     * @param encryptionType  the encryption type used for storing token information
     * @return true if the token is updated successfully, false otherwise
     */
    public boolean update(String username, String updatedToken, String encryptionType,byte[] key) {
        logger.info("Updating token for user: " + username);
        EncryptService encryptService = new EncryptService(encryptionType,key);
        String encryptedToken = encryptService.encrypt(updatedToken);
        String sql = "UPDATE Token SET value = ? WHERE username = ?";
        try (Connection conn = Conn.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, encryptedToken);
            pstmt.setString(2, username);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Token updated successfully for user: " + username);
            } else {
                logger.error("Failed to update token for user: " + username);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Exception occurred while updating token for user: " + username, e);
            return false;
        }
    }


    /**
     * Deletes the authentication token associated with the specified username.
     *
     * @param username the username of the user for whom the token will be deleted
     * @return true if the token is deleted successfully, false otherwise
     */
    public boolean delete(String username) {
        logger.info("Deleting token for user: " + username);
        String sql = "DELETE FROM Token WHERE username = ?";
        try (Connection conn = Conn.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Token deleted successfully for user: " + username);
            } else {
                logger.error("Failed to delete token for user: " + username);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Exception occurred while deleting token for user: " + username, e);
            return false;
        }
    }

    /**
     * Checks if an authentication token exists for the specified username.
     *
     * @param username the username of the user to check for token existence
     * @return true if a token exists for the user, false otherwise
     */
    public boolean exists(String username) {
        logger.info("Checking if token exists for user: " + username);
        String sql = "SELECT * FROM Token WHERE username = ?";
        try (Connection conn = Conn.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Exception occurred while checking if token exists for user: " + username, e);
        }
        return false;
    }

    /**
     * Lists all authentication tokens in the database.
     *
     * @return a list of authentication tokens
     */
    public List<Token> listEveryone() {
        logger.info("Listing all tokens");
        List<Token> tokens = new ArrayList<>();
        String sql = "SELECT * FROM Token";
        try (Connection conn = Conn.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Token token = new Token(rs.getString("value"),
                        new User(rs.getString("username"), rs.getString("role")));
                tokens.add(token);
            }
        } catch (SQLException e) {
            logger.error("Exception occurred while listing all tokens", e);
        }
        return tokens;
    }
}
