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
public class BungeeCommy extends Commy<ServerInfo> {

    private Plugin plugin;

    public BungeeCommy(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public BungeeCommy setup() {
        plugin.getProxy().registerChannel(CHANNEL_ID);
        plugin.getProxy().getPluginManager().registerListener(plugin, new MessageListener(plugin, this));
        return this;
    }

    @Override
    public void sendMessage(ServerInfo target, String tag, String message) {
        this.getConnection(target).sendMessage(tag, message);
    }

    @Override
    public void sendMessage(ServerInfo target, String tag, Message message) {
        this.getConnection(target).sendMessage(tag, message);
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
    private static ServerInfo findServerWithPort(ProxyServer proxy, int port) {
        return proxy.getServers().values().stream().filter(
                info -> info.getAddress().getPort() == port
        ).findFirst().orElse(null);
    }

    /**
     * Own class to isolate the #onPluginMessageReceived(...) method
     */
    private static class MessageListener implements Listener {

        private Plugin plugin;
        private BungeeCommy commy;

        public MessageListener(Plugin plugin, BungeeCommy commy) {
            this.plugin = plugin;
            this.commy = commy;
        }

        @EventHandler
        public void onPluginMessageReceived(PluginMessageEvent event) {
            // Make sure we are intercepting our own messages
            if (!event.getTag().equals(CHANNEL_ID)) return;

            // Finding the server which matches our connection
            ServerInfo server = findServerWithPort(plugin.getProxy(), event.getSender().getAddress().getPort());
            if (server == null) {
                plugin.getLogger().warning("Could not identify source of message. Proceeding anyways.");
            }

            // Receive the message1
            ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
            commy.handleMessage(commy.getConnection(server), in.readUTF(), in.readUTF());
            event.setCancelled(true); // Prevent message leaks
        }
    }

}
