package revolut.exchange.dbservice;

import org.junit.Assert;
import org.junit.Test;
import revolut.exchange.entities.Balance;
import revolut.exchange.entities.User;

import java.math.BigDecimal;
import java.sql.SQLException;

public class DBServiceTest {

    private DBService dbService = DBService.getInstance();

    @Test
    public void CRUDUserTest() throws SQLException {
        User user = dbService.getUser(1L);
        Assert.assertNull(user);

        dbService.createUser(1L, "Test User");
        user = dbService.getUser(1L);
        Assert.assertEquals("Test User", user.getName());

        dbService.updateUser(1L, "Test User Updated");
        user = dbService.getUser(1L);
        Assert.assertEquals("Test User Updated", user.getName());

        dbService.removeUser(1L);
        user = dbService.getUser(1L);
        Assert.assertNull(user);
    }

    @Test
    public void updateBalanceTest() throws SQLException {
        dbService.createUser(1L, "Test User");
        User user = dbService.getUser(1L);
        Assert.assertEquals("Test User", user.getName());

        Balance balance = dbService.getBalance(1L);
        Assert.assertEquals(BigDecimal.ZERO, balance.getAmount());

        BigDecimal amount = new BigDecimal("123.456789");
        dbService.updateBalance(1L,amount);
        balance = dbService.getBalance(1L);
        Assert.assertEquals(amount, balance.getAmount());

        dbService.removeUser(1L);
        user = dbService.getUser(1L);
        Assert.assertNull(user);
    }
}
