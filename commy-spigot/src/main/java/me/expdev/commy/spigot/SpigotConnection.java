package me.expdev.commy.spigot;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import me.expdev.commy.core.Connection;
import me.expdev.commy.core.Message;
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
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(tag);
        out.writeUTF(message);
        player.sendPluginMessage(plugin, channel, out.toByteArray());
    }

    @Override
    public void sendMessage(Message message) {
        this.sendMessage(message.getTag(), new Gson().toJson(message));
    }

    @Override
    public Player getSender() {
        return player;
    }
}
