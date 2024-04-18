package com.badlyac.ClutchPlugin.Player.PlayerGUI.RecordHitsSetting.Detection;

import com.badlyac.ClutchPlugin.ClutchPluginMain;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DetectUuidOfPlayer {
    public static boolean hasPlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        FileConfiguration config = ClutchPluginMain.getInstance().getConfig();
        return config.getConfigurationSection(uuid) != null;
    }
}
