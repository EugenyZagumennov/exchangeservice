package revolut.exchange.processrequest;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;
import org.json.JSONTokener;
import revolut.exchange.command.Command;
import revolut.exchange.command.CreateUserCommand;
import revolut.exchange.command.ExchangeCommand;
import revolut.exchange.command.RemoveUserCommand;
import revolut.exchange.command.UpdateBalanceCommand;
import revolut.exchange.command.UpdateUserCommand;
import revolut.exchange.dbservice.DBService;
import revolut.exchange.entities.Balance;
import revolut.exchange.entities.User;

import java.math.BigDecimal;


public class HttpExchangeParser {
    public static Command parse(HttpExchange httpExchange) throws Exception {
        String url = httpExchange.getRequestURI().toASCIIString();

        if("/createuser".equals(url) && "POST".equals(httpExchange.getRequestMethod())){

            JSONObject json = new JSONObject(new JSONTokener(httpExchange.getRequestBody()));
            long id = json.getLong("id");
            String name = json.getString("name");
            return new CreateUserCommand(id, name);

        }else if("/removeuser".equals(url) && "DELETE".equals(httpExchange.getRequestMethod())){

            JSONObject json = new JSONObject(new JSONTokener(httpExchange.getRequestBody()));
            long id = json.getLong("id");
            return new RemoveUserCommand(id);

        }else if("/updatebalance".equals(url) && "PUT".equals(httpExchange.getRequestMethod())){

            JSONObject json = new JSONObject(new JSONTokener(httpExchange.getRequestBody()));
            long id = json.getLong("id");
            BigDecimal amount = json.getBigDecimal("amount");
            return new UpdateBalanceCommand(id, amount);

        }else if("/updateuser".equals(url) && "PUT".equals(httpExchange.getRequestMethod())){

            JSONObject json = new JSONObject(new JSONTokener(httpExchange.getRequestBody()));
            long id = json.getLong("id");
            String name = json.getString("name");
            return new UpdateUserCommand(id, name);

        }else if("/exchange".equals(url) && "PUT".equals(httpExchange.getRequestMethod())){

            JSONObject json = new JSONObject(new JSONTokener(httpExchange.getRequestBody()));
            long fromId = json.getLong("fromId");
            long toId = json.getLong("toId");
            BigDecimal amount = json.getBigDecimal("amount");
            return new ExchangeCommand(fromId, toId, amount);

        }else{
           throw new Exception("No such method");
        }
    }

    public static String parseGet(HttpExchange httpExchange) throws Exception {
        String url = httpExchange.getRequestURI().toASCIIString();

        if("/getbalance".equals(url) && "GET".equals(httpExchange.getRequestMethod())){

            JSONObject json = new JSONObject(new JSONTokener(httpExchange.getRequestBody()));
            long id = json.getLong("id");
            Balance balance = DBService.getInstance().getBalance(id);
            return balance.getAmount().toString();

        }else if("/getname".equals(url) && "GET".equals(httpExchange.getRequestMethod())){

            JSONObject json = new JSONObject(new JSONTokener(httpExchange.getRequestBody()));
            long id = json.getLong("id");
            User user = DBService.getInstance().getUser(id);
            return user.getName();

        } else{
            throw new Exception("No such method");
        }
    }
}
