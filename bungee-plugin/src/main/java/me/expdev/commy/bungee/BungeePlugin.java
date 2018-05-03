package me.expdev.commy.bungee;

import me.expdev.commy.bungee.handler.MessageHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

/**
 * A simple Bungee plugin demonstrating the use of Commy
 */
public class BungeePlugin extends Plugin {

    // Universal logger
    public static final Logger LOGGER = ProxyServer.getInstance().getLogger();

    // Pre-"defining" a commy at class-level
    private BungeeCommy commy;

    @Override
    public void onEnable() {
        // Initialize commy, calling setup will
        // start the engines
        this.commy = new BungeeCommy(this).setup();

        // Setup a default handler using lambda
        // Setting this is not obligatory
        commy.setDefaultHandler((conn, tag, message) -> LOGGER.info(
                String.format("[%s] Recieved an unknown message from %s: %s", tag, conn.getSender().getName(), message)
        ));

        // Adding handlers, you can add as many as you want
        commy.addHandler("test", new TestHandler());
        commy.addHandler("test_msg", new TestHandler());
    }

    public BungeeCommy getCommy() {
        return commy;
    }

    /**
     * Handles a test message
     */
    private static class TestHandler implements MessageHandler<ServerInfo> {

        @Override
        public void handle(Connection<ServerInfo> conn, String tag, String message) {
            // We know tag == test, otherwise it would have been intercepted through the default handler
            LOGGER.info("Recieved a message through test from " + conn.getSender().getName() + ": " + message);

            // Let's respond by sending them a TestObject
            conn.sendMessage(new TestObject("ExpDev", 2));
        }
    }

}
