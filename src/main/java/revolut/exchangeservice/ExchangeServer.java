package revolut.exchangeservice;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.InetSocketAddress;

@NoArgsConstructor
public class ExchangeServer implements HttpHandler {
    private static final int PORT = 80;

    public static void main(String[] args) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", new ExchangeServer());
        server.start();
        System.out.println(" ExchangeServer start, port:" + PORT);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println(httpExchange.getRequestMethod());
    }
}
