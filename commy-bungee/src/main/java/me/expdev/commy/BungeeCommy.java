package me.expdev.commy;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
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

    @Override
    public void sendMessage(ServerInfo receiver, String tag, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(tag);
        out.writeUTF(message);
        receiver.sendData(CHANNEL_ID, out.toByteArray());
    }

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent event) {
        // Make sure we are intercepting our own messages
        if (!event.getTag().equals(CHANNEL_ID)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        this.handleMessage(in.readUTF(), in.readUTF());
    }

}
