package me.expdev.commy.provider;

import me.expdev.commy.Connection;

/**
 * Project created by ExpDev
 */


public interface SenderProvider {

    void sendMessage(Connection receiver, String tag, String message);

}
