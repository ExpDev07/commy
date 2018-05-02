package me.expdev.commy;

/**
 * Provider for sending messages
 *
 * @param <T> Type of reciever for message
 */
public interface SenderProvider<T> {

    void sendMessage(T receiver, String tag, String message);

}
