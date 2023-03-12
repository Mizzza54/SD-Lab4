package org.example.handler;

import com.mongodb.rx.client.MongoDatabase;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.example.model.User;
import org.example.repository.UserRepository;
import rx.Observable;

import java.util.List;
import java.util.Map;

/**
 * @author Michael Gerasimov
 */
public class AddUserHandler implements Handler {
    private final UserRepository userRepository;

    public AddUserHandler(MongoDatabase mongoDatabase) {
        this.userRepository = new UserRepository(mongoDatabase);
    }

    @Override
    public Observable<String> handle(HttpServerRequest<ByteBuf> req) {
        Map<String, List<String>> params = req.getQueryParameters();
        int id = Integer.parseInt(params.get("id").get(0));
        String login = params.get("login").get(0);
        String currency = params.get("currency").get(0);

        return userRepository.insertUser(new User(id, login, currency)).flatMap(result ->
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
