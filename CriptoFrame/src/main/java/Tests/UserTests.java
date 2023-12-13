/**
 * This class contains unit tests for the UserBO class, which manages user operations.
 */
package Tests;

import BO.UserBO;
import DTO.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    /**
     * Tests the insertion of a user into the database.
     * <p>
     * This test creates a test user, generates an AES key for the user,
     * inserts the user into the database, and verifies the user's existence.
     * </p>
     */
    @Test
    void testInsertUser() {
        UserBO userBO = new UserBO();
        User user = new User("testUser1334", "passwor24d3", "32role33");
        user.setKey(KeyGenerator.generateAesKey(32));
        assertTrue(userBO.insert(user, "aes"));
        assertTrue(userBO.exists("testUser1334"));
    }

    /**
     * Tests searching for a user by username in the database.
     * <p>
     * This test searches for a test user by username and verifies that the found user is not null
     * and has the correct username.
     * </p>
     */
    @Test
    void testSearchForUsername() {
        UserBO userBO = new UserBO();
        User foundUser = userBO.searchForUser("testUser2");
        assertNotNull(foundUser);
        assertEquals("testUser2", foundUser.getUsername());
    }

    /**
     * Tests listing all users in the database.
     * <p>
     * This test retrieves the list of all users from the database and logs each retrieved user.
     * It then asserts that the list is not empty.
     * </p>
     */
    @Test
    void testListEveryone() {
        UserBO userBO = new UserBO();
        List<User> users = userBO.listEveryone();

        // Log the retrieved users
        users.forEach(user -> System.out.println("Retrieved user: " + user));

        assertFalse(users.isEmpty());
    }

    /**
     * Tests the deletion of a user from the database.
     * <p>
     * This test verifies the existence of a test user, deletes the user, and verifies that the user
     * no longer exists in the database.
     * </p>
     */
    @Test
    void testDeleteUser() {
        UserBO userBO = new UserBO();
        assertTrue(userBO.exists("testUser133"));
        assertTrue(userBO.delete("testUser133"));
        assertFalse(userBO.exists("testUser133"));
    }

    /**
     * Tests updating a user in the database.
     * <p>
     * This test creates a test user, updates the user in the database, and verifies the user's existence.
     * </p>
     */
    @Test
    void testUpdateUser() {
        UserBO userBO = new UserBO();
        User user = new User("testUser", "passwor2d3", "32role3");
        assertTrue(userBO.update(user, "aes"));
        assertTrue(userBO.exists("testUser"));
    }

    /**
     * Tests searching for a user by username in the database.
     * <p>
     * This test searches for a test user by username and verifies that the found user is not null
     * and has the correct username.
     * </p>
     */
    @Test
    void testSearchForUser() {
        UserBO userBO = new UserBO();
        User foundUser = userBO.searchForUser("testUser");
        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
    }

    /**
     * Tests checking if a user exists in the database.
     * <p>
     * This test checks if a test user exists in the database and asserts that the result is true.
     * </p>
     */
    @Test
    void testExists() {
        UserBO userBO = new UserBO();
        assertTrue(userBO.exists("testUser"));
    }
}
