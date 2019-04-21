package revolut.exchange.command;

import lombok.AllArgsConstructor;
import lombok.ToString;
import revolut.exchange.dbservice.DBService;
import revolut.exchange.entities.Balance;

import java.math.BigDecimal;

@AllArgsConstructor
@ToString
public class ExchangeCommand implements Command {

    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;

    @Override
    public void run() throws Exception {
        DBService dbService = DBService.getInstance();

        System.out.println(String.format("Transaction between user (id=%d) and user (id=%d) (amount=%f) is started",
                fromUserId, toUserId, amount));

        Balance fromBalance = dbService.getBalance(fromUserId);
        Balance toBalance = dbService.getBalance(toUserId);

        if(fromBalance == null){
            throw new Exception(String.format("User with id=%d is not found!", fromUserId));
        }
        if(toBalance == null){
            throw new Exception(String.format("User with id=%d is not found!", toUserId));
        }
        if(fromBalance.getAmount().compareTo(amount) < 0){
            throw new Exception(String.format("User with id=%d has no enough money!", fromBalance.getUserId()));
        }

        BigDecimal newFromAmount = fromBalance.getAmount().add(amount.negate());
        BigDecimal newToAmount = toBalance.getAmount().add(amount);

        boolean fromUpdateResult = dbService.updateBalance(fromUserId, newFromAmount);
        if(fromUpdateResult) {
            boolean toUpdateResult = dbService.updateBalance(toUserId, newToAmount);
            if(!toUpdateResult){
                boolean rollbackResult = false;
                while (!rollbackResult){
                    rollbackResult = dbService.updateBalance(fromUserId, fromBalance.getAmount());
                }
                System.out.println(
                        String.format("Transaction between user (id=%d) and user (id=%d) is rolled back (amount=%f)",
                                fromUserId, toUserId, amount));
            }else{
                System.out.println(
                        String.format("Balances of user (id=%d) and user (id=%d) are updated (amount=%f) successfully",
                                fromUserId, toUserId, amount));
            }
        }else{
            System.out.println(
                    String.format("Balance of user (id=%d) is updated (amount=%f) unsuccessfully", fromUserId, amount));
        }

        System.out.println(String.format("Transaction between user (id=%d) and user (id=%d) (amount=%f) is stopped",
                fromUserId, toUserId, amount));
    }
}
