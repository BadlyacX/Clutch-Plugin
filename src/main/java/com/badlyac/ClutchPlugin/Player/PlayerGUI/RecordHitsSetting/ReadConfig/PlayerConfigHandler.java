package com.badlyac.ClutchPlugin.Player.PlayerGUI.RecordHitsSetting.ReadConfig;

import com.badlyac.ClutchPlugin.ClutchPluginMain;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerConfigHandler {
    private FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/ClutchPlugin/config.yml"));;
    private int ticks;
    private int knockbackCount = 0;
    public void applyKnockback(Player player, UUID playerUUID, int ticks) {
        Map<Integer, Integer> durabilityMap = getPlayerDurabilityValues(playerUUID);
        this.ticks = ticks;
        knockbackCount = 0;
        for (int durability : durabilityMap.values()) {
            if (durability != 7) {
                knockbackCount++;
            }
        }

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(ClutchPluginMain.getInstance(), new Runnable() {
            int count = knockbackCount;

            @Override
            public void run() {
                if (count > 0) {
                    giveKnockbackEffect(player);
                    count--;
                } else {
                    scheduler.cancelTasks(ClutchPluginMain.getInstance());
                }
            }
        }, 0L, ticks);
    }

    private void giveKnockbackEffect(Player player) {
        Vector knockbackDirection = new Vector(0, 1, 0);
        player.setVelocity(player.getVelocity().add(knockbackDirection));
    }

    public Map<Integer, Integer> getPlayerDurabilityValues(UUID playerUUID) {
        Map<Integer, Integer> durabilityMap = new HashMap<>();
        String uuidString = playerUUID.toString();
        if (config.contains(uuidString + ".gui")) {
            Map<String, Object> guiItems = config.getConfigurationSection(uuidString + ".gui").getValues(false);

            for (Map.Entry<String, Object> entry : guiItems.entrySet()) {
                String itemData = (String) entry.getValue();
                String[] parts = itemData.split(", ");
                for (String part : parts) {
                    if (part.startsWith("durability: ")) {
                        try {
                            int position = Integer.parseInt(entry.getKey());
                            int durability = Integer.parseInt(part.substring("durability: ".length()));
                            durabilityMap.put(position, durability);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        } else {
            System.out.println("GUI section not found for player with UUID: " + uuidString);
        }

        return durabilityMap;
    }
}