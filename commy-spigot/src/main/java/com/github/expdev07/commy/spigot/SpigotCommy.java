package com.github.expdev07.commy.spigot;

import com.github.expdev07.commy.core.Commy;
import com.github.expdev07.commy.core.Connection;
import com.github.expdev07.commy.core.Message;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Spigot implementation of commy
 *
 * <b>Note: </b> uses Object as receiver type as it is unused
 */
public class SpigotCommy extends Commy<Player> implements PluginMessageListener {

    private JavaPlugin plugin;

    public SpigotCommy(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public SpigotCommy setup() {
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, CHANNEL_ID, this);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, CHANNEL_ID);
        return this;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        // Make sure we are intercepting our own messages
        if (!channel.equals(CHANNEL_ID)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        this.handleMessage(this.getConnection(player), in.readUTF(), in.readUTF());
    }

    @Override
    public void sendMessage(Player target, String tag, String message) {
        this.getConnection(target).sendMessage(tag, message);
    }

    @Override
    public void sendMessage(Player target, Message message) {
        this.getConnection(target).sendMessage(message);
    }

    /**
     * Helper method to just quickly send a string message
     * to BungeeCord
     *
     * @param message Message to send
     */
    public void sendMessage(String tag, String message) {
        this.sendMessage(Iterables.getFirst(Bukkit.getOnlinePlayers(), null), tag, message);
    }

    @Override
    public Connection<Player> getConnection(Player target) {
        return new SpigotConnection(plugin, target, CHANNEL_ID);
    }
}
