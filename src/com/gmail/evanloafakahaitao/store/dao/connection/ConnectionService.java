package com.gmail.evanloafakahaitao.store.dao.connection;

import com.gmail.evanloafakahaitao.store.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.store.config.properties.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

    private Connection connection;

    private ConnectionService() {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        try {
            Class.forName(configurationManager.getProperty(DatabaseProperties.DATABASE_DRIVER_NAME));
            System.out.println("MySQL JDBC driver was successfully loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver loading failed");
            e.printStackTrace();
        }
    }

    private static class Holder {
        private static final ConnectionService connectionService = new ConnectionService();
    }

    public static ConnectionService getInstance() {
        return Holder.connectionService;
    }

    public Connection getConnection() {
        System.out.println("Establishing DB connection...");
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        try {
            connection = DriverManager.getConnection(
                configurationManager.getProperty(DatabaseProperties.DATABASE_URL),
                configurationManager.getProperty(DatabaseProperties.DATABASE_USERNAME),
                configurationManager.getProperty(DatabaseProperties.DATABASE_PASSWORD)
            );
            System.out.println("DB Connection established!");
            return connection;
        } catch (SQLException e) {
            System.out.println("DB Connection failure. Printing trace.");
            e.printStackTrace();
            throw new RuntimeException("Connection failure");
        }
    }
}
