package com.github.expdev07.commy.core.handler;

import com.github.expdev07.commy.core.Connection;

/**
 * Handles an incoming string
 */
public interface StringMessageHandler<T> extends MessageHandler<T> {

    @Override
    default void handle(Connection<T> conn, String proxy, byte[] message) {
        // Just transfer bytes to string
        this.handle(conn, proxy, new String(message));
    }

    /**
     * Handles a incoming message (string)
     *
     * @param conn    Connection for message
     * @param proxy   Proxy to route message to
     * @param message Message received
     */
    void handle(Connection<T> conn, String proxy, String message);

}
