package revolut.exchange.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public enum H2Server {
    INSTANCE;

    private static final String URL = "jdbc:h2:mem:";
    private static final String CREATE_USER = "CREATE TABLE user(id BIGINT PRIMARY KEY, name VARCHAR(255))";
    private static final String CREATE_BALANCE = "CREATE TABLE balance(userid BIGINT, amount DECIMAL, FOREIGN KEY (userid) REFERENCES user(id))";

    private static final int INIT_CONNECTIONS_COUNT = 10;

    private Vector<Connection> availableConnections = new Vector<>();
    private Vector<Connection> usedConnections = new Vector<>();

    H2Server() {
        for (int i = 0; i < INIT_CONNECTIONS_COUNT; i++) {
            availableConnections.addElement(getConnection());
        }
        createDbSchema();
    }

    public static H2Server getInstance() {
        return INSTANCE;
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public synchronized Connection retrieve() {
        Connection newConnection = null;
        if (availableConnections.size() == 0) {
            newConnection = getConnection();
        } else {
            newConnection = availableConnections.lastElement();
            availableConnections.removeElement(newConnection);
        }
        usedConnections.addElement(newConnection);
        return newConnection;
    }

    public synchronized void putback(Connection c) throws NullPointerException {
        if (c == null) return;

        if (usedConnections.removeElement(c)) {
            availableConnections.addElement(c);
        } else {
            throw new NullPointerException("Connection not in the usedConnections array");
        }
    }

    public int getAvailableConnectionssCount() {
        return availableConnections.size();
    }

    private void createDbSchema(){
        Connection connection = retrieve();

        try(Statement st = connection.createStatement()) {
            st.execute(CREATE_USER);
            st.execute(CREATE_BALANCE);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            putback(connection);
        }
    }
}
