package revolut.exchange.commandservice;

import revolut.exchange.command.Command;

/**
 * Thread for processing of modifying requests fom queue.
 *
 * @author Evgenii Zagumennov
 */
public class CommandThread extends Thread {

    private CommandQueue exchangeQueue;

    public CommandThread() {
        this.exchangeQueue = CommandQueue.getInstance();
    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("Command thread started");
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            Command command = exchangeQueue.poll();
            if(command != null){
                try {
                    command.run();
                    System.out.println(String.format("[SUCCESS] %s is completed.", command.toString()));
                } catch (Exception e) {
                    System.err.println(String.format("[FAIL] %s is failed! Error message=%s", command.toString(), e.getMessage()));
                }
            }
        }
    }
}
