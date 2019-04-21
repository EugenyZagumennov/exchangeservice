package revolut.exchange.command;

import lombok.AllArgsConstructor;
import lombok.ToString;
import revolut.exchange.dbservice.DBService;

@AllArgsConstructor
@ToString
public class CreateUserCommand implements Command {

    private Long id;
    private String name;

    @Override
    public void run() throws Exception {
        DBService.getInstance().createUser(id, name);
    }
}
