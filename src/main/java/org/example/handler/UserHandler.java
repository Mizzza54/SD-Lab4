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
public class UserHandler implements Handler {
    private final UserRepository userRepository;

    public UserHandler(MongoDatabase mongoDatabase) {
        this.userRepository = new UserRepository(mongoDatabase);
    }

    @Override
    public Observable<String> handle(HttpServerRequest<ByteBuf> req) {
        Map<String, List<String>> params = req.getQueryParameters();
        int userId = Integer.parseInt(params.get("userId").get(0));

        return userRepository.getUser(userId).map(User::toString);
    }
}
