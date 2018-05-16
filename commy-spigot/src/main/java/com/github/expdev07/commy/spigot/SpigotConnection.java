package com.github.expdev07.commy.spigot;

import com.github.expdev07.commy.core.BytesOutput;
import com.github.expdev07.commy.core.Connection;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A spigot implementation of commy's connection
 */
public class SpigotConnection implements Connection<Player> {

    private JavaPlugin plugin;
    private Player player;
    private String channel;

    public SpigotConnection(JavaPlugin plugin, Player player, String channel) {
        this.plugin = plugin;
        this.player = player;
        this.channel = channel;
    }

    @Override
    public void sendMessage(String proxy, byte[] message) {
        if (player == null) {
            plugin.getLogger().warning("Attempted to send a plugin-message, but no player could be found. Aborting.");
            return;
        }

        byte[] bytes = new BytesOutput()
                .write(proxy, new String(message))
                .getBytes();

        player.sendPluginMessage(plugin, channel, bytes);
    }

    @Override
    public void sendMessage(String proxy, String message) {
        this.sendMessage(proxy, message.getBytes());
    }

    @Override
    public void sendMessage(String proxy, Object message) {
        this.sendMessage(proxy, new Gson().toJson(message));
    }

    @Override
    public Player getSender() {
        return player;
    }
}
