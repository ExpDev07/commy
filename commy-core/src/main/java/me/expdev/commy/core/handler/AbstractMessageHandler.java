package me.expdev.commy.core.handler;

import com.google.gson.Gson;
import me.expdev.commy.core.Connection;

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
    public void handle(Connection<T> sender, String tag, String message) {
        this.handle(sender, tag, GSON.fromJson(message, this.getMessageType()));
    }

    /**
     * Handle an incoming abstract message
     *
     * @param sender Sender of message
     * @param tag Tag
     * @param message Message to handle
     */
    public abstract void handle(Connection<T> sender, String tag, M message);

    /**
     * Gets type of message
     *
     * @return Type of message
     */
    public abstract Class<M> getMessageType();

}
