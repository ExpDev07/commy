package com.github.expdev07.commy.spigotplugin;

import com.github.expdev07.commy.core.BytesOutput;
import com.github.expdev07.commy.core.Connection;
import com.github.expdev07.commy.core.handler.AbstractMessageHandler;
import com.github.expdev07.commy.core.handler.StringMessageHandler;
import com.github.expdev07.commy.spigot.SpigotCommy;
import com.github.expdev07.commy.spigot.SpigotCommyFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple Spigot plugin demonstrating the use of Commy
 */
public class SpigotPlugin extends JavaPlugin {

    // Universal logger
    static Logger logger;

    // Pre-"defining" a commy at class-level
    private SpigotCommy commy;

    @Override
    public void onEnable() {
        logger = Bukkit.getLogger();

        // Initialize commy, will self setup
        this.commy = SpigotCommyFactory.createCommy(this);

        // Adding handlers, you can add as many as you want
        // The first parameter here is the "pipe" the handler will handle messages for
        commy.addHandler("test", new TestHandler());
        commy.addHandler("test_msg", new AbstractTestHandler());
    }

    /**
     * A method demonstrating some usage
     */
    private void sendMessage() {
        // Get a connection with a player
        Connection<Player> connection = commy.getConnection(Bukkit.getPlayer("ExpDev"));

        // Now, there are many ways you can send a message
        //   * You can just send a simple string
        connection.sendMessage("test", "This is a message");
        //   * You can send a custom object!
        connection.sendMessage("test", new Object());
        //   * You can send bytes like you normally would
        //     Use our helper class "BytesOutput" to quickly write to an array
        byte[] bytes = new BytesOutput()
                .write("a string", "another string")
                .getBytes();
        connection.sendMessage("test_proxy", bytes);

        // You can also "quick send" a message
        commy.sendMessage("test_proxy", "Message to send");
    }

    /**
     * Handles a test message. The parameter of MessageHandler is the type
     * of source we will communicate with, which for Spigot's case is
     * always Player
     */
    private static class TestHandler implements StringMessageHandler<Player> {

        @Override
        public void handle(Connection<Player> conn, String tag, String message) {
            // We know tag == test, otherwise it would have been intercepted through the default handler
            logger.log(Level.INFO, "Received a message through test from " + conn.getSender().getName() + ": " + message);

            // Respond! Here, the source we're communicating with will need to have a handler for the "test"
            // pipe, otherwise it will be rerouted to their default handler
            conn.sendMessage("test", "I heard your test message and is sending this back through the \"test\" pipe");
        }

    }

    /**
     * A simple handler to test out how to use the abstract handler to
     * send objects over the pipes
     */
    private static class AbstractTestHandler implements AbstractMessageHandler<Player, TestObject> {

        @Override
        public void handle(Connection<Player> conn, String tag, TestObject message) {
            // We received a "TestObject" object, manipulate it as you want
            logger.log(Level.INFO, String.format(
                    "Received a %s through %s from %s", message.getClass().getSimpleName(), tag, conn.getSender().getName())
            );
        }

        @Override
        public Class<TestObject> getMessageType() {
            // This is important as generics is not available in run-time,
            // which means this will have to manually be specified
            return TestObject.class;
        }

    }

}
