package revolut.exchange.mapper;

import revolut.exchange.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for User entity
 *
 * @author Evgenii Zagumennov
 */
public class UserMapper {
    public static User map(ResultSet rs) throws SQLException {
        User user = null;
        if(rs != null && rs.next()) {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            user = new User(id, name);
        }
        return user;
    }
}
