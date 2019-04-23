package revolut.exchange.command;

/**
 * Interface for commands
 *
 * @author Evgenii Zagumennov
 */
public interface Command {
    void run() throws Exception;
}
