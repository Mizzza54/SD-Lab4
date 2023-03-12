package org.example.repository;

import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;
import org.example.model.Product;
import rx.Observable;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Michael Gerasimov
 */
public class ProductRepository {
    private final MongoDatabase mongoDatabase;

    public ProductRepository(MongoDatabase mongoClient) {
        this.mongoDatabase = mongoClient;
    }

    private MongoCollection<Document> getCollection() {
        return mongoDatabase.getCollection("products");
    }

    public Observable<Product> getAll() {
        return getCollection().find().toObservable().map(Product::new);
    }

    public Observable<Product> get(int id) {
        return getCollection().find(eq("id", id)).toObservable().map(Product::new);
    }

    public Observable<Boolean> add(Product product) {
        return get(product.getId()).isEmpty().flatMap(x -> {
            if (x) {
                return getCollection().insertOne(product.toDocument()).map(y -> true);
            } else {
                return Observable.just(false);
            }
        });
    }
}