package me.expdev.commy.handler;

import me.expdev.commy.Connection;
import me.expdev.commy.provider.MessagingProvider;
import me.expdev.commy.provider.SenderProvider;

/**
 * A simple handler interface for handling messages
 */
public interface MessageHandler {

    void handle(Connection sender, String tag, String message);

}
