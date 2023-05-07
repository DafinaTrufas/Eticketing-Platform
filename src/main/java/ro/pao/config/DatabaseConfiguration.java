package ro.pao.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521";
    private static final String USER = "system";
    private static final String PASSWORD = "martaoracle";
    private static Connection databaseConnection;

    private DatabaseConfiguration() {
    }

    public static Connection getDatabaseConnection() {

        try {

            if (databaseConnection == null || databaseConnection.isClosed()) {

                databaseConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            }
        } catch (SQLException e) {

            e.printStackTrace();

        }

        return databaseConnection;

    }

    public static void closeDatabaseConnection() {

        try {

            if (databaseConnection != null && !databaseConnection.isClosed()) {

                databaseConnection.close();

            }
        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}
