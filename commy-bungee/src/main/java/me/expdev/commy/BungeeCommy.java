package me.expdev.commy;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.expdev.commy.provider.ReceiverProvider;
import me.expdev.commy.provider.SenderProvider;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

/**
 * Bungee's implementation of commy
 */
public class BungeeCommy extends Commy implements Listener {

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

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        // this.receiveMessage(new BungeeConnection(event.));
    }


    @Override
    public void receiveMessage(Connection sender, String tag, String message) {

    }

    @Override
    public void sendMessage(Connection receiver, String tag, String message) {

    }
}
