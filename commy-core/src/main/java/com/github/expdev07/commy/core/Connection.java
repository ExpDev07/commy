package com.github.expdev07.commy.core;

/**
 * A connection with a source
 */
public interface Connection<T> {

    /**
     * Sends a message to the connection
     *
     * @param proxy   Proxy to direct message to
     * @param message Message (bytes) to send
     */
    void sendMessage(String proxy, byte[] message);

    /**
     * Sends a message to the connection
     *
     * @param proxy   Proxy to direct message to
    * @param message Message (string) to send
    */
    void sendMessage(String proxy, String message);

    /**
    * Sends a message to the connection
    *
     * @param proxy   Proxy to direct message to
     * @param message Message to send
    */
    void sendMessage(String proxy, Object message);

    /**
    * Gets the "sender" for the connection
    *
    * @return Sender o
    */
    T getSender();

}
