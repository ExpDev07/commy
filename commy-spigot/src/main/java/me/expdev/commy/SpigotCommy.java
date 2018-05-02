package me.expdev.commy;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.expdev.commy.provider.ReceiverProvider;
import me.expdev.commy.provider.SenderProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Spigot implementation of commy
 *
 * <b>Note: </b> uses Object as reciever type as it is unused
 */
public class SpigotCommy extends Commy implements PluginMessageListener {

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
    }

    @Override
    public void receiveMessage(SenderProvider sender, String tag, String message) {

    }

    @Override
    public void sendMessage(ReceiverProvider receiver, String tag, String message) {

    }
}
