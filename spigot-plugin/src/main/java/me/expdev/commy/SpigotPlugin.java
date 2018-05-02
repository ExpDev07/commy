package me.expdev.commy;

import me.expdev.commy.handler.MessageHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Project created by ExpDev
 */
public class SpigotPlugin extends JavaPlugin {

    private SpigotCommy commy;

    @Override
    public void onEnable() {
        // Initialize commy, calling setup will
        // start the engines
        this.commy = new SpigotCommy(this).setup();

        // Setup a default handler using lambda
        // Setting this is not obligatory
        SpigotCommy.setDefaultHandler((tag, message) -> getLogger().info("Recieved an unknown message (" + tag + ")"));

        // Assign a handler for incoming messages going through "test" pipe
        commy.addHandler("test", new TestHandler());
    }

    /**
     * Handles a test message
     */
    private static class TestHandler implements MessageHandler {

        @Override
        public void handle(String tag, String message) {
            System.out.println("Recieved a test message!: " + message);
        }
    }

}
