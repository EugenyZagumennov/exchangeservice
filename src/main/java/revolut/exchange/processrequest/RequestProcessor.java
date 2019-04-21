package revolut.exchange.processrequest;

import com.sun.net.httpserver.HttpExchange;
import revolut.exchange.command.Command;
import revolut.exchange.commandservice.CommandQueue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class RequestProcessor {

    public static void processRequest(HttpExchange httpExchange) throws Exception {
        Command command = HttpExchangeParser.parse(httpExchange);
        offer(command);
        writeResponse("Request accepted", HttpURLConnection.HTTP_OK, httpExchange);
    }

    public static void processGetRequest(HttpExchange httpExchange) throws Exception {
        String message = HttpExchangeParser.parseGet(httpExchange);
        writeResponse(message, HttpURLConnection.HTTP_OK, httpExchange);
    }

    private static void writeResponse(String data, int code, HttpExchange httpExchange) throws IOException {
        byte[] outputStream = data.getBytes(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(code, outputStream.length);
        httpExchange.getResponseBody().write(outputStream);
    }

    private static void offer(Command command){
        boolean result = false;
        while(!result) {
            result = CommandQueue.getInstance().offer(command);
        }
    }
}
