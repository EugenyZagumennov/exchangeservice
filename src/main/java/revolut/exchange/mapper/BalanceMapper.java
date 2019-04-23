package revolut.exchange.mapper;

import revolut.exchange.entities.Balance;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for Balance entity
 *
 * @author Evgenii Zagumennov
 */
public class BalanceMapper {
    public static Balance map(ResultSet rs) throws SQLException {
        Balance balance = null;
        if(rs != null && rs.next()) {
            Long userId = rs.getLong("userid");
            BigDecimal amount = rs.getBigDecimal("amount");
            balance = new Balance(userId, amount);
        }
        return balance;
    }
}
