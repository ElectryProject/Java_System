package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

class DatabaseConnectionImpl implements DatabaseConnection {

    private static DatabaseConnectionImpl instance;

    private static Connection connection;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private DatabaseConnectionImpl() throws SQLException {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(
                    DatabaseConfig.getUrl(),
                    DatabaseConfig.getUser(),
                    DatabaseConfig.getPassword()
            );

        } catch (ClassNotFoundException e) {
            this.logger.severe("Driver Oracle não localizado");
        }
    }

    static  synchronized DatabaseConnectionImpl getInstance() throws SQLException {
        if (instance == null || connection.isClosed()) {
            instance = new DatabaseConnectionImpl();
        }
        return instance;
    }


    @Override
    public Connection getConnection() throws SQLException {
        connection.setAutoCommit(false);
        return connection;
    }
}
