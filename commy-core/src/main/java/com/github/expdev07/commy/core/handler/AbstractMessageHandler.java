package com.github.expdev07.commy.core.handler;

import com.github.expdev07.commy.core.Connection;
import com.google.gson.Gson;

/**
 * An abstract handler for handling of incoming objects
 *
 * @param <T> Type of source we will communicate with
 * @param <M> Type of message to send
 */
public abstract class AbstractMessageHandler<T, M> implements MessageHandler<T> {

    // No need to make more than one GSON instance
    private final static Gson GSON = new Gson();

    @Override
    public void handle(Connection<T> sender, String proxy, byte[] message) {
        this.handle(sender, proxy, GSON.fromJson(new String(message), this.getMessageType()));
    }

    /**
     * Handle an incoming abstract message
     *
     * @param sender Sender of message
     * @param proxy proxy
     * @param message Message to handle
     */
    public abstract void handle(Connection<T> sender, String proxy, M message);

    /**
     * Gets type of message
     *
     * @return Type of message
     */
    public abstract Class<M> getMessageType();

}
