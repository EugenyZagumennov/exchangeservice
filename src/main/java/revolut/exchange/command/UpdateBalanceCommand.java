package revolut.exchange.command;

import lombok.AllArgsConstructor;
import lombok.ToString;
import revolut.exchange.dbservice.DBService;

import java.math.BigDecimal;

@AllArgsConstructor
@ToString
public class UpdateBalanceCommand implements Command {

    private Long id;
    private BigDecimal amount;

    @Override
    public void run() throws Exception {
        DBService.getInstance().updateBalance(id, amount);
    }
}
