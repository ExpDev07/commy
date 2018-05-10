package com.github.expdev07.commy.spigot;

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
    public void sendMessage(String tag, String message) {
        if (player == null) {
            plugin.getLogger().warning("Attempted to send a plugin-message, but no player could be found. Aborting.");
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(tag);
        out.writeUTF(message);
        player.sendPluginMessage(plugin, channel, out.toByteArray());
    }

    @Override
    public void sendMessage(String tag, Object message) {
        this.sendMessage(tag, new Gson().toJson(message));
    }

    @Override
    public Player getSender() {
        return player;
    }
}
