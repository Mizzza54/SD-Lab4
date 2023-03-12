package org.example.handler;

import com.mongodb.rx.client.MongoDatabase;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.example.model.Product;
import org.example.repository.ProductRepository;
import rx.Observable;

import java.util.List;
import java.util.Map;

/**
 * @author Michael Gerasimov
 */
public class AddProductHandler implements Handler {
    private final ProductRepository productRepository;

    public AddProductHandler(MongoDatabase mongoDatabase) {
        this.productRepository = new ProductRepository(mongoDatabase);
    }

    @Override
    public Observable<String> handle(HttpServerRequest<ByteBuf> req) {
        Map<String, List<String>> params = req.getQueryParameters();
        int id = Integer.parseInt(params.get("id").get(0));
        String name = params.get("name").get(0);
        double price = Double.parseDouble(params.get("price").get(0));

        return productRepository.add(new Product(id, name, price)).flatMap(result ->
                {
                    if (result) {
                        return Observable.just("added");
                    } else {
                        return Observable.just("not added");
                    }
                }
        );
    }

}
