package revolut.exchangeservice.mapper;

import revolut.exchangeservice.entities.Balance;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceMapper {
    public static Balance map(ResultSet rs) throws SQLException {
        Balance balance = null;
        if(rs.next()) {
            Long userId = rs.getLong("userid");
            BigDecimal amount = rs.getBigDecimal("amount");
            balance = new Balance(userId, amount);
        }
        return balance;
    }
}
