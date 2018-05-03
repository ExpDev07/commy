package me.expdev.commy.handler;

import me.expdev.commy.Connection;

/**
 * A simple handler interface for handling messages
 */
public interface MessageHandler<T> {

    /**
     * Handles a incoming message
     *
     * @param conn    Connection for message
     * @param tag     Tag of message
     * @param message Message recieved
     */
    void handle(Connection<T> conn, String tag, String message);

}
