package util;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_PATH = "/database/leters.db";

    public static Connection getConnection() throws SQLException {
        try {
            // Resolve the database resource
            URL resource = DBConnection.class.getResource(DB_PATH);
            if (resource == null) {
                throw new RuntimeException("Database file not found: " + DB_PATH);
            }

            
            // Use the absolute path of the resolved resource
            String dbUrl = "jdbc:sqlite:" + new File(resource.toURI()).getAbsolutePath();
            Connection conn = DriverManager.getConnection(dbUrl);
            System.out.println("Connecting to database at: " + dbUrl); // Debugging
            System.out.println("Auto-commit mode; " + conn.getAutoCommit());
            return DriverManager.getConnection(dbUrl);
        } catch (Exception e) {
            if (e instanceof SQLException) {
                throw (SQLException) e; // Re-throw SQLException
            }
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.", e);
        }
    }
}