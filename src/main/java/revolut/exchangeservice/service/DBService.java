package revolut.exchangeservice.service;

import revolut.exchangeservice.db.H2Server;
import revolut.exchangeservice.entities.Balance;
import revolut.exchangeservice.entities.User;
import revolut.exchangeservice.mapper.BalanceMapper;
import revolut.exchangeservice.mapper.UserMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {
    private static final String CREATE_USER     = "INSERT INTO user VALUES (%d, '%s')";
    private static final String CREATE_BALANCE  = "INSERT INTO balance VALUES (%d, %d)";
    private static final String GET_BALANCE     = "SELECT userid, amount FROM balance WHERE userId = %d";
    private static final String GET_USER        = "SELECT id, name FROM user WHERE id = %d";
    private static final String UPDATE_BALANCE  = "UPDATE balance SET amount=%f WHERE userId=%d";
    private static final String UPDATE_USER     = "UPDATE user SET name='%s' WHERE id=%d";
    private static final String REMOVE_USER     = "DELETE FROM user WHERE id=%d";
    private static final String REMOVE_BALANCE  = "DELETE FROM balance WHERE userid=%d";

    private H2Server h2Server;

    public DBService() {
        this.h2Server = H2Server.getInstance();
    }

    public void createUser(Long userId, String name) throws SQLException {
        Statement statement = h2Server.getStatement();
        statement.addBatch(String.format(CREATE_USER, userId, name));
        statement.addBatch(String.format(CREATE_BALANCE, userId, 0));
        statement.executeBatch();
    }

    public void removeUser(Long userId) throws SQLException {
        Statement statement = h2Server.getStatement();
        statement.addBatch(String.format(REMOVE_BALANCE, userId));
        statement.addBatch(String.format(REMOVE_USER, userId));
        statement.executeBatch();
    }

    public User getUser(Long id) throws SQLException {
        ResultSet rs = h2Server.getStatement().executeQuery(String.format(GET_USER, id));
        return UserMapper.map(rs);
    }

    public Balance getBalance(Long userId) throws SQLException {
        ResultSet rs = h2Server.getStatement().executeQuery(String.format(GET_BALANCE, userId));
        return BalanceMapper.map(rs);
    }

    public boolean updateBalance(Long userId, BigDecimal amount) throws SQLException {
        return h2Server.getStatement().execute(String.format(UPDATE_BALANCE, amount, userId));
    }

    public boolean updateUser(Long id, String name) throws SQLException {
        return h2Server.getStatement().execute(String.format(UPDATE_USER, name, id));
    }
}
