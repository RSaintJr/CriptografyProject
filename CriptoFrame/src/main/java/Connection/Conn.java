package Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Conn class provides a static method for establishing a connection to a MySQL database.
 *
 * This class uses JDBC to connect to a MySQL database with the specified parameters.
 * It also includes logging of connection-related errors using SLF4J.
 */
public class Conn {

    // Database name to connect to
    private static final String DATABASE_NAME = "cripto";

    // Logger for logging connection-related operations
    private static final Logger logger = LoggerFactory.getLogger(Conn.class);

    // Singleton instance
    private static Connection instance;

    // Private constructor to prevent instantiation
    private Conn() {
    }

    /**
     * Returns the singleton instance of the Connection.
     *
     * @return a Connection object representing the database connection if successful,
     *         or null if an error occurs
     */
    public static Connection getInstance() {
        if (instance == null) {
            synchronized (Conn.class) {
                if (instance == null) {
                    instance = createConnection();
                }
            }
        }
        return instance;
    }

    private static Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/" + DATABASE_NAME;
            Connection conn = DriverManager.getConnection(url, "root", "");
            if (conn != null) {
                return conn;
            } else {
                logger.error("Connection is null. Check database connection.");
                return null;
            }
        } catch (Exception e) {
            logger.error("Error: " + e);
            return null;
        }
    }
}
