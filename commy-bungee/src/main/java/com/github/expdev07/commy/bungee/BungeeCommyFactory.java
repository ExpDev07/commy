package com.github.expdev07.commy.bungee;

import com.github.expdev07.commy.core.Commy;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCommyFactory {

    public static Commy<ServerInfo> createCommy(Plugin plugin) {
        BungeeCommy commy = new BungeeCommy(plugin);

        commy.setup();

        return commy;
    }
}
