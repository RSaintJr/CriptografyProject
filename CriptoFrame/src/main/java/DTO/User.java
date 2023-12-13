package DTO;

import java.util.Arrays;

/**
 * User class represents a user in the system with associated information such as username, password, and role.
 */
public class User {

    // The username of the user
    private String username;

    // The password of the user
    private String password;

    // The role of the user
    private String role;

    // The key of the user
    private byte[] key;

    /**
     * Constructs a User object with the specified username, password, and role.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param role     the role of the user
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructs a User object with the specified username and role.
     *
     * @param username the username of the user
     * @param role     the role of the user
     */
    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public User(String username) {
        this.username = username;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the key of the user.
     *
     * @return the key of the user
     */
    public byte[] getKey() {
        return key;
    }

    /**
     * Sets the key of the user.
     *
     * @param key the new key for the user
     */

    public void setKey(byte[] key) {
        this.key = key;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the new role for the user
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return a string representation of the User object
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append(", key=").append(Arrays.toString(key));
        sb.append('}');
        return sb.toString();
    }
}

