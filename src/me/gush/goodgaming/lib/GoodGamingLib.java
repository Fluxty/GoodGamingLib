package me.gush.goodgaming.lib;

import me.gush.goodgaming.lib.DataStore;
import me.gush.goodgaming.lib.listener.GGListener;
import me.gush.goodgaming.lib.prisonmines.AsyncInstantReset;
import me.gush.goodgaming.lib.prisonmines.PrisonMinesHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class GoodGamingLib
extends JavaPlugin {
    private static GoodGamingLib instance;
    private final DataStore prisonBlockDataStore = new DataStore();

    public static GoodGamingLib getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents((Listener)new GGListener(this), (Plugin)this);
        PrisonMinesHandler.IMP.setMinesResetType(new AsyncInstantReset());
    }

    public DataStore getPrisonBlockDataStore() {
        return this.prisonBlockDataStore;
    }
}

