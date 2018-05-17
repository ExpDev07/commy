package com.github.expdev07.commy.spigot;

import com.github.expdev07.commy.core.Commy;
import com.github.expdev07.commy.core.Connection;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Spigot implementation of commy
 */
public class SpigotCommy extends Commy<Player> {

    private JavaPlugin plugin;

    protected SpigotCommy(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, CHANNEL_ID, new MessageListener(this));
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, CHANNEL_ID);
    }

    /**
     * Helper method to just quickly send a string message
     * to BungeeCord
     *
     * @param proxy   Proxy to route message to
     * @param message Message to send
     */
    public void sendMessage(String proxy, String message) {
        this.getConnection(Iterables.getFirst(Bukkit.getOnlinePlayers(), null)).sendMessage(proxy, message);
    }

    @Override
    public Connection<Player> getConnection(Player target) {
        return new SpigotConnection(plugin, target, CHANNEL_ID);
    }

    /**
     * Own class to isolate the #onPluginMessageReceived(...) method
     */
    public class MessageListener implements PluginMessageListener {

        private SpigotCommy commy;

        MessageListener(SpigotCommy commy) {
            this.commy = commy;
        }

        @Override
        public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
            // Make sure we are intercepting our own messages
            if (!channel.equals(CHANNEL_ID)) return;

            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            commy.handleMessage(commy.getConnection(player), in.readUTF(), in.readUTF().getBytes());
        }
    }

}
