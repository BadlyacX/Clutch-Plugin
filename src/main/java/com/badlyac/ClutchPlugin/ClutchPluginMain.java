package com.badlyac.ClutchPlugin;

import com.badlyac.ClutchPlugin.Player.PlayerGUI.HitsGUI;
import com.badlyac.ClutchPlugin.Player.PlayerGUI.GUI;
import com.badlyac.ClutchPlugin.Player.PlayerGUI.RecordHitsSetting.Listener.GuiListener;
import com.badlyac.ClutchPlugin.Player.PlayerJoin.Join;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ClutchPluginMain extends JavaPlugin {
    private GuiListener guiListener;
    @Override
    public void onEnable() {
        // JoinItem //
        Bukkit.getPluginManager().registerEvents(new Join(),this);
        // JoinItem //

        // GUI //
        Bukkit.getPluginManager().registerEvents(new GUI(), this);
        Bukkit.getPluginManager().registerEvents(new HitsGUI(), this);

        // # Save player 's HitsSetting
        guiListener = new GuiListener(this);
        getServer().getPluginManager().registerEvents(guiListener, this);
        // # Save player 's HitsSetting
        // GUI //
    }
    @Override
    public void onDisable() {
        guiListener.saveRecordsToFile();
    }
}
