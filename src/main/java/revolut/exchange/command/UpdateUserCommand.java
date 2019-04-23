package revolut.exchange.command;

import lombok.AllArgsConstructor;
import lombok.ToString;
import revolut.exchange.dbservice.DBService;

/**
 * Update user command
 *
 * @author Evgenii Zagumennov
 */
@AllArgsConstructor
@ToString
public class UpdateUserCommand implements Command {

    private Long id;
    private String name;

    @Override
    public void run() throws Exception {
        DBService.getInstance().updateUser(id, name);
    }
}
