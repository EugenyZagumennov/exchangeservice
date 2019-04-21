package revolut.exchange;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import revolut.exchange.commandservice.CommandThread;
import revolut.exchange.processrequest.RequestProcessor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ExchangeServer implements HttpHandler {
    private static final int PORT = 80;
    private static final CommandThread COMMAND_THREAD = new CommandThread();
    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", new ExchangeServer());
        server.start();
        COMMAND_THREAD.start();
        System.out.println("ExchangeServer start, port:" + PORT);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean isCloseRequest = false;
        try{
            String url = httpExchange.getRequestURI().toASCIIString();
            if("/stop".equals(url)){
                isCloseRequest = true;
            } else if("GET".equals(httpExchange.getRequestMethod())){
                RequestProcessor.processGetRequest(httpExchange);
            } else {
                RequestProcessor.processRequest(httpExchange);
            }
        }catch(Exception e){
            byte[] outputStream = e.getMessage().getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, outputStream.length);
            httpExchange.getResponseBody().write(outputStream);
            e.printStackTrace();
        }
        finally {
            if(isCloseRequest){
                closeServer(httpExchange);
            }else {
                httpExchange.close();
            }
        }
    }

    private void closeServer(HttpExchange httpExchange) {
        String closeMessage  = "ExchangeServer stop!";
        try {
            byte[] outputStream = closeMessage.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, outputStream.length);
            httpExchange.getResponseBody().write(outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            httpExchange.close();
        }

        COMMAND_THREAD.interrupt();
        server.stop(0);
        System.out.println(closeMessage);
    }
}
