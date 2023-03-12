package org.example;

import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import io.reactivex.netty.protocol.http.server.HttpServer;
import org.example.handler.*;

import java.util.Map;

/**
 * @author Michael Gerasimov
 */
public class Main {
    private static final MongoDatabase mongoDatabase = MongoClients.create("mongodb://localhost:27017").getDatabase("shop");
    private static final Map<String, Handler> handlers = Map.of(
            "/add_product", new AddProductHandler(mongoDatabase),
            "/get_products", new ProductsHandler(mongoDatabase),
            "/add_user", new AddUserHandler(mongoDatabase),
            "/get_users", new UserHandler(mongoDatabase)
    );

    public static void main(String[] args) {
        HttpServer
                .newServer(25565)
                .start((req, resp) -> {
                    String path = req.getDecodedPath();
                    Handler handler = handlers.get(path);
                    return resp.writeString(handler.handle(req));
                })
                .awaitShutdown();
    }
}