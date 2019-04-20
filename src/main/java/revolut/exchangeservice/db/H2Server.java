package revolut.exchangeservice.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public enum H2Server {
    INSTANCE;

    private static final String URL = "jdbc:h2:mem:";
    private static final String CREATE_USER = "CREATE TABLE user(id BIGINT PRIMARY KEY, name VARCHAR(255))";
    private static final String CREATE_BALANCE = "CREATE TABLE balance(userid BIGINT, amount DECIMAL, FOREIGN KEY (userid) REFERENCES user(id))";

    private Connection connection;

    H2Server() {
        try {
            connection = DriverManager.getConnection(URL);
            createDbSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static H2Server getInstance() {
        return INSTANCE;
    }

    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

    private void createDbSchema() throws SQLException {
        getStatement().execute(CREATE_USER);
        getStatement().execute(CREATE_BALANCE);
    }
}
