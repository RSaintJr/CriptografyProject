package DAO;

import Crypt.EncryptService;
import Connection.Conn;
import DTO.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

/**
 * UserDAO class provides data access operations related to user information in the database.
 *
 * This class includes methods for inserting, searching, updating, and deleting user data,
 * as well as checking user existence and listing all users.
 */
public class UserDAO {

    // Logger for logging user-related database operations
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    /**
     * Inserts a new user into the database.
     *
     * @param user           the user to be inserted
     * @param encryptionType the encryption type used for storing user information
     * @return true if the user is inserted successfully, false otherwise
     */
    public boolean insert(User user, String encryptionType) {
        logger.info("Inserting user: " + user);
        EncryptService encryptService = new EncryptService(encryptionType, user.getKey());
        String sql = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";
        String encryptedPassword = encryptService.encrypt(user.getPassword());
        try (Connection conn = Conn.getInstance()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, encryptedPassword);
                pstmt.setString(3, user.getRole());
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    logger.info("User inserted successfully: " + user);
                } else {
                    logger.error("Failed to insert user: " + user);
                }
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            logger.error("Exception occurred while inserting user: " + user, e);
            return false;
        }
    }


    /**
     * Searches for a user based on the username.
     *
     * @param username the username of the user to search for
     * @return the user if found, or null if not found
     */
    public User searchForUser(String username) {
        logger.info("Searching for user: " + username);
        User user = null;
        String sql = "SELECT * FROM User WHERE username = ?";
        try (Connection conn = Conn.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                user = new User(rs.getString("username"), storedPassword, rs.getString("role"));
            }
        } catch (SQLException e) {
            logger.error("Exception occurred while searching for user: " + username, e);
        }
        return user;
    }


    /**
     * Lists all users in the database.
     *
     * @return a list of users
     */
    public List<User> listEveryone() {
        logger.info("Listing all users");
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Connection conn = Conn.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Exception occurred while listing all users", e);
        }
        return users;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user           the user to be updated
     * @param encryptionType the encryption type used for storing user information
     * @return true if the user is updated successfully, false otherwise
     */
    public boolean update(User user, String encryptionType) {
        logger.info("Updating user: " + user);
        EncryptService encryptService = new EncryptService(encryptionType, user.getKey());
        user.setPassword(encryptService.encrypt(user.getPassword()));
        String sql = "UPDATE User SET password = ? WHERE username = ?";
        try (Connection conn = Conn.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getUsername());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("User updated successfully: " + user);
            } else {
                logger.error("Failed to update user: " + user);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Exception occurred while updating user: " + user, e);
            return false;
        }
    }

    /**
     * Deletes the user associated with the specified username.
     *
     * @param username the username of the user to be deleted
     * @return true if the user is deleted successfully, false otherwise
     */
    public boolean delete(String username) {
        logger.info("Deleting user: " + username);
        String sql = "DELETE FROM User WHERE username = ?";
        try (Connection conn = Conn.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("User deleted successfully: " + username);
            } else {
                logger.error("Failed to delete user: " + username);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Exception occurred while deleting user: " + username, e);
            return false;
        }
    }

    /**
     * Checks if a user with the specified username exists in the database.
     *
     * @param username the username of the user to check for existence
     * @return true if the user exists, false otherwise
     */
    public boolean exists(String username) {
        logger.info("Checking if user exists: " + username);
        String sql = "SELECT * FROM User WHERE username = ?";
        try (Connection conn = Conn.getInstance()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Exception occurred while checking if user exists: " + username, e);
        }
        return false;
    }
}
