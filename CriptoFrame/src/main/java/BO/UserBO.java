package BO;

import DTO.User;
import DAO.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * UserBO class provides business operations related to user management.
 *
 * This class encapsulates the business logic for handling user operations,
 * including insertion, listing, updating, deletion, and searching for users.
 */
public class UserBO {

    // Logger for logging user-related operations
    private static final Logger logger = LoggerFactory.getLogger(UserBO.class);

    // Data Access Object for handling user-related database operations
    private static final UserDAO userDAO = new UserDAO();

    /**
     * Inserts a new user into the database.
     *
     * @param user       the user to be inserted
     * @param encryptType the encryption type used for storing user information
     * @return true if the user is inserted successfully, false otherwise
     */
    public boolean insert(User user, String encryptType) {
        logger.info("Inserting user: " + user);

        // Check if a user with the same username already exists
        if (!exists(user.getUsername())) {
            boolean result = userDAO.insert(user, encryptType);
            if (result) {
                logger.info("User inserted successfully: " + user);
            } else {
                logger.error("Failed to insert user: " + user);
            }
            return result;
        }

        logger.warn("User already exists: " + user);
        return false;
    }

    /**
     * Lists all users in the database.
     *
     * @return a list of users
     */
    public List<User> listEveryone() {
        logger.info("Listing all users");
        return userDAO.listEveryone();
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user       the user to be updated
     * @param encryptType the encryption type used for storing user information
     * @return true if the user is updated successfully, false otherwise
     */
    public boolean update(User user, String encryptType) {
        logger.info("Updating user: " + user);
        return userDAO.update(user, encryptType);
    }

    /**
     * Deletes the user associated with the specified username.
     *
     * @param username the username of the user to be deleted
     * @return true if the user is deleted successfully, false otherwise
     */
    public boolean delete(String username) {
        logger.info("Deleting user: " + username);
        return userDAO.delete(username);
    }

    /**
     * Searches for a user based on the username.
     *
     * @param username the username of the user to search for
     * @return the user if found, or null if not found
     */
    public User searchForUser(String username) {
        logger.info("Searching for user: " + username);
        return userDAO.searchForUser(username);
    }

    /**
     * Checks if a user with the specified username exists in the database.
     *
     * @param username the username of the user to check for existence
     * @return true if the user exists, false otherwise
     */
    public boolean exists(String username) {
        logger.info("Checking if user exists: " + username);
        return userDAO.exists(username);
    }
}

