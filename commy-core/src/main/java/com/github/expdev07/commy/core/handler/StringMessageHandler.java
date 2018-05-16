package com.github.expdev07.commy.core.handler;

import com.github.expdev07.commy.core.Connection;

/**
 *
 */
public abstract class StringMessageHandler<T> implements MessageHandler<T> {

    @Override
    public void handle(Connection<T> conn, String proxy, byte[] message) {
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
    public abstract void handle(Connection<T> conn, String proxy, String message);

}
