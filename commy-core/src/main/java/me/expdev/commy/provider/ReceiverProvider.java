package me.expdev.commy.provider;

import me.expdev.commy.Connection;

/**
 * Project created by ExpDev
 */


public interface ReceiverProvider {

    void receiveMessage(Connection sender, String tag, String message);

}
