package org.example.handler;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

/**
 * @author Michael Gerasimov
 */
public interface Handler {
    Observable<String> handle(HttpServerRequest<ByteBuf> req);
}
