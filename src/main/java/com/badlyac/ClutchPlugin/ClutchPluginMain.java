package com.badlyac.ClutchPlugin;

import com.badlyac.ClutchPlugin.Player.PlayerGUI.HitsGUI;
import com.badlyac.ClutchPlugin.Player.PlayerGUI.GUI;
import com.badlyac.ClutchPlugin.Player.PlayerGUI.RecordHitsSetting.Listener.HitSettingGuiListener;
import com.badlyac.ClutchPlugin.Player.PlayerJoin.Join;
import com.badlyac.ClutchPlugin.Player.Test.Test;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ClutchPluginMain extends JavaPlugin {
    private static ClutchPluginMain instance;
    @Override
    public void onEnable() {
        instance = this;
        // get UUID from config.yml //

        // get UUID from config.yml //

        // JoinItem //
        Bukkit.getPluginManager().registerEvents(new Join(),this);
        // JoinItem //

        // GUI //
        Bukkit.getPluginManager().registerEvents(new GUI(), this);
        Bukkit.getPluginManager().registerEvents(new HitsGUI(), this);
        // GUI //
        // # Save player 's HitsSetting
        Bukkit.getPluginManager().registerEvents(new HitSettingGuiListener(this), this);
        // # Save player 's HitsSetting
        // GUI //

        // TEST //
        Bukkit.getPluginManager().registerEvents(new Test(), this);
        // TEST //
    }
    @Override
    public void onDisable() {

    }

    public static ClutchPluginMain getInstance() {
        return instance;
    }
}
