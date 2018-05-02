package me.expdev.commy.handler;

import com.google.gson.Gson;
import me.expdev.commy.Connection;
import me.expdev.commy.provider.SenderProvider;

/**
 * Project created by ExpDev
 */
public abstract class AbstractMessageHandler<T> implements MessageHandler {

    // No need to make more than one GSON instance
    private final static Gson GSON = new Gson();

    private final Class<T> type;

    public AbstractMessageHandler(Class<T> clazz) {
        this.type = clazz;
    }

    @Override
    public void handle(Connection sender, String tag, String message) {
        this.handle(sender, tag, GSON.fromJson(message, type));
    }

    /**
     * Handle an incoming abstract message
     *
     * @param sender Sender of message
     * @param tag Tag
     * @param message Message to handle
     */
    public abstract void handle(Connection sender, String tag, T message);

}
