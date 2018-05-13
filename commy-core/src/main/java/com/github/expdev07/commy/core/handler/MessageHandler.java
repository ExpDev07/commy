package com.github.expdev07.commy.core.handler;

import com.github.expdev07.commy.core.Connection;

/**
 * A simple handler interface for handling messages
 *
 * @param <T> Type of source we will communicate with
 */
public interface MessageHandler<T> {

    /**
     * Handles a incoming message
     *
     * @param conn    Connection for message
     * @param proxy   Proxy to route message to
     * @param message Message recieved
     */
    void handle(Connection<T> conn, String proxy, byte[] message);

}
