package revolut.exchange.command;

import lombok.AllArgsConstructor;
import lombok.ToString;
import revolut.exchange.dbservice.DBService;

/**
 * Remove user command
 *
 * @author Evgenii Zagumennov
 */
@AllArgsConstructor
@ToString
public class RemoveUserCommand implements Command {

    private Long id;

    @Override
    public void run() throws Exception {
        DBService.getInstance().removeUser(id);
    }
}
