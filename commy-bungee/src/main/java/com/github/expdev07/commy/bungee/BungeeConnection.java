package com.github.expdev07.commy.bungee;

import com.github.expdev07.commy.core.Connection;
import com.github.expdev07.commy.core.Message;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import net.md_5.bungee.api.config.ServerInfo;

/**
 * A spigotplugin implementation of commy's connection
 */
public class BungeeConnection implements Connection<ServerInfo> {

    private String channel;
    private ServerInfo server;

    public BungeeConnection(String channel, ServerInfo server) {
        this.channel = channel;
        this.server = server;
    }

    @Override
    public void sendMessage(String tag, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(tag);
        out.writeUTF(message);
        server.sendData(channel, out.toByteArray());
    }

    @Override
    public void sendMessage(Message message) {
        this.sendMessage(message.getTag(), new Gson().toJson(message));
    }

    @Override
    public ServerInfo getSender() {
        return server;
    }


}
