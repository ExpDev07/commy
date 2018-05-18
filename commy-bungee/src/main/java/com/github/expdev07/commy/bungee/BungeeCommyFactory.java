package com.github.expdev07.commy.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCommyFactory {

    public static BungeeCommy createCommy(Plugin plugin) {
        BungeeCommy commy = new BungeeCommy(plugin);
        commy.setup();
        return commy;
    }
}
