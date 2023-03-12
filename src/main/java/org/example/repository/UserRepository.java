package org.example.repository;

import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;
import org.example.model.User;
import rx.Observable;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Michael Gerasimov
 */
public class UserRepository {
    private final MongoDatabase mongoDatabase;

    public UserRepository(MongoDatabase mongoClient) {
        this.mongoDatabase = mongoClient;
    }

    private MongoCollection<Document> getCollection() {
        return mongoDatabase.getCollection("users");
    }

    public Observable<User> getUser(int id) {
        return getCollection()
                .find(eq("id", id))
                .toObservable()
                .map(User::new);
    }

    public Observable<Boolean> insertUser(User user) {
        return getUser(user.getId()).isEmpty().flatMap(x -> {
            if (x) {
                return getCollection().insertOne(user.toDocument()).map(g -> true);
            } else {
                return Observable.just(false);
            }
        });
    }
}