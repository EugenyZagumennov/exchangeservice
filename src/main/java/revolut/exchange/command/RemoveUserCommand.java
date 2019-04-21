package revolut.exchange.command;

import lombok.AllArgsConstructor;
import lombok.ToString;
import revolut.exchange.dbservice.DBService;

@AllArgsConstructor
@ToString
public class RemoveUserCommand implements Command {

    private Long id;

    @Override
    public void run() throws Exception {
        DBService.getInstance().removeUser(id);
    }
}
