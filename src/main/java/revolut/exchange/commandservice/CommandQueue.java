package revolut.exchange.commandservice;

import revolut.exchange.command.Command;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Singleton class. Queue for containing and ordering modifying requests
 *
 * @author Evgenii Zagumennov
 */
public enum CommandQueue {
    INSTANCE;

    private static final ConcurrentLinkedQueue<Command> exchangeQueue = new ConcurrentLinkedQueue<>();

    public static CommandQueue getInstance(){
        return INSTANCE;
    }

    public boolean offer(Command command){
        return exchangeQueue.offer(command);
    }


    public Command poll(){
        return exchangeQueue.poll();
    }
}
