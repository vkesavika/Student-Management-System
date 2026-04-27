package com.student.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a JDBC connection to the student_db MySQL database.
 *
 * Update the URL, USER, and PASSWORD constants to match your environment.
 */
public class DatabaseConnection {

    // ── Configure these to match your MySQL installation ──────────────────
    private static final String URL      = "jdbc:mysql://localhost:3306/student_db"
                                         + "?useSSL=false&serverTimezone=UTC";
    private static final String USER     = "root";
    private static final String PASSWORD = "your_password";
    // ─────────────────────────────────────────────────────────────────────

    private static Connection connection = null;

    /** Returns (or lazily creates) the single shared connection. */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[DB] Connected to student_db successfully.");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found. "
                    + "Add mysql-connector-java.jar to your classpath.", e);
            }
        }
        return connection;
    }

    /** Closes the shared connection if it is open. */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Error closing connection: " + e.getMessage());
        }
    }
}
