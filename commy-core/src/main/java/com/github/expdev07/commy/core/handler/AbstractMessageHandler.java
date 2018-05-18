package com.github.expdev07.commy.core.handler;

import com.github.expdev07.commy.core.Connection;
import com.google.gson.Gson;

/**
 * An abstract handler for handling of incoming objects
 *
 * @param <T> Type of source we will communicate with
 * @param <M> Type of message to send
 */
public interface AbstractMessageHandler<T, M> extends StringMessageHandler<T> {

    // No need to make more than one GSON instance
    Gson gson = new Gson();

    @Override
    default void handle(Connection<T> sender, String proxy, String message) {
        this.handle(sender, proxy, gson.fromJson(message, this.getMessageType()));
    }

    /**
     * Handle an incoming abstract message
     *
     * @param sender Sender of message
     * @param proxy proxy
     * @param message Message to handle
     */
    void handle(Connection<T> sender, String proxy, M message);

    /**
     * Gets type of message
     *
     * @return Type of message
     */
    Class<M> getMessageType();
}
