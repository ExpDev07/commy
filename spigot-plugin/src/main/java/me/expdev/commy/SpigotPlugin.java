package me.expdev.commy;

import me.expdev.commy.handler.AbstractMessageHandler;
import me.expdev.commy.handler.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * A simple Spigot plugin demonstrating the use of Commy
 */
public class SpigotPlugin extends JavaPlugin {

    // Universal logger
    public static final Logger LOGGER = Bukkit.getLogger();

    // Pre-"defining" a commy at class-level
    private SpigotCommy commy;

    @Override
    public void onEnable() {
        // Initialize commy, calling setup will
        // start the engines
        this.commy = new SpigotCommy(this).setup();

        // Setup a default handler using lambda
        // Setting this is not obligatory
        commy.setDefaultHandler((conn, tag, message) -> LOGGER.info(
                String.format("[%s] Recieved an unknown message from %s: %s", tag, conn.getSender().getName(), message)
        ));

        // Adding handlers, you can add as many as you want
        commy.addHandler("test", new TestHandler());
        commy.addHandler("test_msg", new AbstractTestHandler());
    }

    public SpigotCommy getCommy() {
        return commy;
    }

    /**
     * Handles a test message
     */
    private static class TestHandler implements MessageHandler<Player> {

        @Override
        public void handle(Connection<Player> conn, String tag, String message) {
            // We know tag == test, otherwise it would have been intercepted through the default handler
            LOGGER.info("Recieved a message through test from " + conn.getSender().getName() + ": " + message);

            // Respond!
            conn.sendMessage("test", "I heard your test message and is sending this back through the \"test\" pipe");
        }
    }

    /**
     * A simple handler to test out how to use the abstract handler to
     * send objects over the pipes
     */
    private static class AbstractTestHandler extends AbstractMessageHandler<Player, TestObject> {

        @Override
        public void handle(Connection<Player> conn, String tag, TestObject message) {
            // We recieved a "TestObject" object, manipulate it as you want
            LOGGER.info(String.format(
                    "Recieved a %s through %s from %s", message.getClass().getSimpleName(), tag, conn.getSender().getName())
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