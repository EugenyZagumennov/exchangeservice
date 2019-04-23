package revolut.exchange.dbservice;

import revolut.exchange.h2.H2Server;
import revolut.exchange.entities.Balance;
import revolut.exchange.entities.User;
import revolut.exchange.mapper.BalanceMapper;
import revolut.exchange.mapper.UserMapper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton class. Service for working with database
 *
 * @author Evgenii Zagumennov
 */
public enum DBService {
    INSTANCE;

    private static final String CREATE_USER     = "INSERT INTO user VALUES (%d, '%s')";
    private static final String CREATE_BALANCE  = "INSERT INTO balance VALUES (%d, %d)";
    private static final String GET_BALANCE     = "SELECT userid, amount FROM balance WHERE userId = %d";
    private static final String GET_USER        = "SELECT id, name FROM user WHERE id = %d";
    private static final String UPDATE_BALANCE  = "UPDATE balance SET amount=%f WHERE userId=%d";
    private static final String UPDATE_USER     = "UPDATE user SET name='%s' WHERE id=%d";
    private static final String REMOVE_USER     = "DELETE FROM user WHERE id=%d";
    private static final String REMOVE_BALANCE  = "DELETE FROM balance WHERE userid=%d";

    private H2Server h2Server;

    DBService() {
        this.h2Server = H2Server.getInstance();
    }

    public static DBService getInstance(){
        return INSTANCE;
    }

    public void createUser(Long userId, String name) throws SQLException {
        Connection connection = h2Server.retrieve();
        try(Statement statement = connection.createStatement()) {
            statement.addBatch(String.format(CREATE_USER, userId, name));
            statement.addBatch(String.format(CREATE_BALANCE, userId, 0));
            statement.executeBatch();
        }finally {
            h2Server.putback(connection);
        }
    }

    public void removeUser(Long userId) throws SQLException {
        Connection connection = h2Server.retrieve();
        try(Statement statement = connection.createStatement()) {
            statement.addBatch(String.format(REMOVE_BALANCE, userId));
            statement.addBatch(String.format(REMOVE_USER, userId));
            statement.executeBatch();
        }finally {
            h2Server.putback(connection);
        }
    }

    public User getUser(Long id) throws SQLException {
        User user = null;
        Connection connection = h2Server.retrieve();
        try(Statement st = connection.createStatement()) {
            user = UserMapper.map(st.executeQuery(String.format(GET_USER, id)));
        }finally {
            h2Server.putback(connection);
        }
        return user;
    }

    public Balance getBalance(Long userId) throws SQLException {
        Balance balance = null;
        Connection connection = h2Server.retrieve();
        try(Statement st = connection.createStatement()) {
            balance = BalanceMapper.map(st.executeQuery(String.format(GET_BALANCE, userId)));
        }finally {
            h2Server.putback(connection);
        }
        return balance;
    }

    public boolean updateBalance(Long userId, BigDecimal amount) throws SQLException {
        return updateQuery(String.format(UPDATE_BALANCE, amount, userId));
    }

    public boolean updateUser(Long id, String name) throws SQLException {
        return updateQuery(String.format(UPDATE_USER, name, id));
    }

    private boolean updateQuery(String query) throws SQLException {
        boolean result = false;
        Connection connection = h2Server.retrieve();
        try(Statement st = connection.createStatement()) {
            result = st.executeUpdate(query) == 1;
        }finally {
            h2Server.putback(connection);
        }
        return result;
    }

}
