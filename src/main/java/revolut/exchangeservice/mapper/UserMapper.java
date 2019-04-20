package revolut.exchangeservice.mapper;

import revolut.exchangeservice.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User map(ResultSet rs) throws SQLException {
        User user = null;
        if(rs.next()) {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            user = new User(id, name);
        }
        return user;
    }
}
