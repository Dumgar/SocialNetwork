package stuff;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides connections from TomCat connection pool
 *
 * @author Roman Dmitriev
 */
public class DBConnectionManager {

    /**
     * Factory for connections for the physical source
     * {@code DataSource} object
     */
    private static DataSource dataSource;

    static {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/socialnetwork");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets connection from {@code DataSource} object
     * @return connection object
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
