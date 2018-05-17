package com.github.expdev07.commy.core;

import com.github.expdev07.commy.core.handler.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Base commy implementation
 *
 * @param <T> Type of source that we will send messages to
 */
public abstract class Commy<T> {

    /**
     * Global channel id, doesn't have to be used by each implementation,
     * but is preferred
     */
    public static final String CHANNEL_ID = "Commy";

    private MessageHandler<T> defaultHandler = null;
    private Map<String, MessageHandler<T>> handlers = new HashMap<>();

    /**
     * Constructs a commy
     */
    public Commy() {
    }

    /**
     * Sets a default handler. Will be used when unknown messages are
     * intercepted through "Commy"
     *
     * @param handler Handler
     */
    public void setDefaultHandler(MessageHandler<T> handler) {
        this.defaultHandler = handler;
    }

    /**
     * Adds a handler for handling of incoming messages
     *
     * @param tag Tag
     * @param handler Handler
     */
    public void addHandler(String tag, MessageHandler<T> handler) {
        handlers.put(tag.toLowerCase(), handler);
    }

    /**
     * Handles a message
     *
     * @param proxy Routing proxy/id of message
     * @param message Message to handle
     */
    protected void handleMessage(Connection<T> sender, String proxy, byte[] message) {
        // Attempt to route to appropriate handler
        MessageHandler<T> handler = handlers.get(proxy);
        if (handler == null) {
            // Have message handled by default handler if set
            if (defaultHandler != null) defaultHandler.handle(sender, proxy, message);
            return;
        }
        // Handle message appropriately
        handler.handle(sender, proxy, message);
    }

    /**
     * Setup appropriate stuff
     */
    public abstract void setup();

    /**
     * Gets a connection with the target
     *
     * @param target Target to get connection with
     * @return Connection with target
     */
    public abstract Connection<T> getConnection(T target);

}
