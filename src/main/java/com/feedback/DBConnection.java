
package com.feedback;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://127.0.0.1:3306/feedbackflow_db";

    private static final String USER = "feedback_app";

    private static final String PASSWORD =
        System.getenv("FEEDBACK_DB_PASSWORD");

    public static Connection getConnection()
            throws SQLException {

        System.out.println("=== JDBC DRIVER DIAGNOSTIC ===");

        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException(
                "FEEDBACK_DB_PASSWORD is not configured."
            );
        }

        try {
            Class<?> driverClass =
                Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println(
                "MYSQL DRIVER LOADED SUCCESSFULLY: "
                + driverClass.getName()
            );

            System.out.println(
                "Driver classloader: "
                + driverClass.getClassLoader()
            );

        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "Tomcat cannot load the MySQL driver from WEB-INF/lib.",
                e
            );
        }

        Enumeration<Driver> drivers =
            DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();

            System.out.println(
                "Registered driver: "
                + driver.getClass().getName()
            );

            try {
                System.out.println(
                    "Accepts MySQL URL: "
                    + driver.acceptsURL(URL)
                );
            } catch (SQLException e) {
                System.out.println(
                    "Driver URL check failed: " + e.getMessage()
                );
            }
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
