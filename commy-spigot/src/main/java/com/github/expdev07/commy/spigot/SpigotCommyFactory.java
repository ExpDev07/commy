package com.github.expdev07.commy.spigot;

import com.github.expdev07.commy.core.Commy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotCommyFactory {

    public static Commy<Player> createCommy(JavaPlugin plugin) {
        SpigotCommy commy = new SpigotCommy(plugin);

        commy.setup();

        return commy;
    }
}
