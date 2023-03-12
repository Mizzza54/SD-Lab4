package org.example.handler;

import com.mongodb.rx.client.MongoDatabase;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.example.model.Product;
import org.example.model.User;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import rx.Observable;

import java.util.List;
import java.util.Map;

/**
 * @author Michael Gerasimov
 */
public class ProductsHandler implements Handler {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public ProductsHandler(MongoDatabase mongoDatabase) {
        this.productRepository = new ProductRepository(mongoDatabase);
        this.userRepository = new UserRepository(mongoDatabase);
    }

    @Override
    public Observable<String> handle(HttpServerRequest<ByteBuf> req) {
        Map<String, List<String>> params = req.getQueryParameters();
        int userId = Integer.parseInt(params.get("userId").get(0));

        Observable<User> user = userRepository.getUser(userId);
        Observable<Product> products = productRepository.getAll();

        return user.isEmpty().flatMap(x ->
                {
                    if (x) {
                        return Observable.just(userId + "not found");
                    } else {
                        return user.flatMap(u -> products.map(g -> g.toString(u.getCurrency())));
                    }
                }
        );
    }
}
