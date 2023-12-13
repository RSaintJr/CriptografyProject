package DTO;

import java.io.Serializable;
/**
 * Token class represents an authentication token associated with a user.
 * This class implements the Serializable interface to allow objects of this class
 * to be converted into a byte stream for various purposes, such as serialization.
 */
public class Token implements Serializable {

    // The value of the authentication token
    private String value;

    // The user associated with the authentication token
    private User user;

    /**
     * Constructs a Token object with the specified value and associated user.
     *
     * @param value the value of the authentication token
     * @param user  the user associated with the authentication token
     */
    public Token(String value, User user) {
        this.value = value;
        this.user = user;
    }

    /**
     * Gets the value of the authentication token.
     *
     * @return the value of the authentication token
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the authentication token.
     *
     * @param value the new value for the authentication token
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the user associated with the authentication token.
     *
     * @return the user associated with the authentication token
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the authentication token.
     *
     * @param user the new user associated with the authentication token
     */
    public void setUser(User user) {
        this.user = user;
    }
}

