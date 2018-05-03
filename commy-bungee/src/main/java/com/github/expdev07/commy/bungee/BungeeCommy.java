package com.github.expdev07.commy.bungee;

import com.github.expdev07.commy.core.Commy;
import com.github.expdev07.commy.core.Connection;
import com.github.expdev07.commy.core.Message;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

/**
 * Bungee's implementation of commy
 */
public class BungeeCommy extends Commy<ServerInfo> implements Listener {

    private Plugin plugin;

    public BungeeCommy(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public BungeeCommy setup() {
        plugin.getProxy().registerChannel(CHANNEL_ID);
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
        return this;
    }

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent event) {
        // Make sure we are intercepting our own messages
        if (!event.getTag().equals(CHANNEL_ID)) return;

        // Finding the server which matches our connection
        ServerInfo server = findServerWirhPort(plugin.getProxy(), event.getSender().getAddress().getPort());
        if (server == null) {
            plugin.getLogger().warning("Could not identify source of message. Proceeding anyways.");
        }

        // Receive the message1
        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        this.handleMessage(this.getConnection(server), in.readUTF(), in.readUTF());
    }


    @Override
    public void sendMessage(ServerInfo target, String tag, String message) {
        this.getConnection(target).sendMessage(tag, message);
    }

    @Override
    public void sendMessage(ServerInfo target, Message message) {
        this.getConnection(target).sendMessage(message);
    }

    @Override
    public Connection<ServerInfo> getConnection(ServerInfo target) {
        return new BungeeConnection(CHANNEL_ID, target);
    }

    /**
     * Finds a server on a proxy with the specified port, or null
     * if not found
     *
     * @param proxy Proxy to search
     * @param port  Port to search for
     * @return Server with matching port, null if not found
     */
    private static ServerInfo findServerWirhPort(ProxyServer proxy, int port) {
        return proxy.getServers().values().stream().filter(
                info -> info.getAddress().getPort() == port
        ).findFirst().orElse(null);
    }

}
