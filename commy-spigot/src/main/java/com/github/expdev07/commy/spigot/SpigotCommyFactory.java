package com.github.expdev07.commy.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotCommyFactory {

    public static SpigotCommy createCommy(JavaPlugin plugin) {
        SpigotCommy commy = new SpigotCommy(plugin);
        commy.setup();
        return commy;
    }
}
