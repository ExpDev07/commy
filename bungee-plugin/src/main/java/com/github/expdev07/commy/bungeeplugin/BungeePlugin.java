package com.github.expdev07.commy.bungeeplugin;

import com.github.expdev07.commy.bungee.BungeeCommy;
import com.github.expdev07.commy.bungee.BungeeCommyFactory;
import com.github.expdev07.commy.core.Connection;
import com.github.expdev07.commy.core.handler.MessageHandler;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple Bungee plugin demonstrating the use of Commy
 */
public class BungeePlugin extends Plugin {

    // Universal logger
    private static Logger logger;

    // Pre-"defining" a commy at class-level
    private BungeeCommy commy;

    @Override
    public void onEnable() {
        logger = ProxyServer.getInstance().getLogger();

        // Initialize commy, calling setup will
        // start the engines
        this.commy = BungeeCommyFactory.createCommy(this);

        // Adding handlers, you can add as many as you want
        commy.addHandler("test", new TestHandler());
    }

    /**
     * Handles a test message
     */
    private static class TestHandler implements MessageHandler<ServerInfo> {

        @Override
        public void handle(Connection<ServerInfo> conn, String tag, byte[] message) {
            // We know tag == test, otherwise it would have been intercepted through the default handler
            logger.log(Level.INFO, ("Received a message through test from " + conn.getSender().getName() + ": " + new String(message)));

            // Or... if you sent bytes, you can manipulate it like you normally would
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            logger.log(Level.INFO, in.readUTF());
            logger.log(Level.INFO, in.readUTF());

            // Let's respond by sending them a TestObject
            conn.sendMessage("test_msg", new TestObject("ExpDev", 2));
        }
    }

}
