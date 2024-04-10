package com.badlyac.ClutchPlugin.Player.PlayerGUI.RecordHitsSetting.Listener;

import com.badlyac.ClutchPlugin.Player.PlayerGUI.HitsGUI;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class GuiListener implements Listener {
    final JavaPlugin plugin;
    final List<PlayerInventoryRecord> records;
    private final Map<UUID, List<PlayerInventoryRecord>> playerRecords = new HashMap<>();

    public GuiListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.records = new ArrayList<>();
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equals(HitsGUI.GUI_TITLE)) {
            Player player = (Player) event.getPlayer();
            UUID playerUUID = player.getUniqueId();
            ItemStack[] contents = event.getInventory().getContents();
            List<PlayerInventoryRecord> records = new ArrayList<>();

            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];
                if (item != null && item.getType() == Material.STAINED_GLASS_PANE) {
                    // 獲取玻璃片的顏色
                    Material color = item.getType();
                    records.add(new PlayerInventoryRecord(color.name(), i));
                }
            }

            // 更新該玩家的最新記錄
            playerRecords.put(playerUUID, records);
        }
    }

    public void saveRecordsToFile() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        config.set("PlayerData", null);

        for (PlayerInventoryRecord record : records) {
            String path = "PlayerData." + record.getPlayerUUID() + ".items";
            List<String> itemList = config.getStringList(path);
            itemList.add("Slot: " + record.getSlot() + ", Color: " + record.getColor());
            config.set(path, itemList);
        }

        try {
            config.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static class PlayerInventoryRecord {
        private final String color;
        private final int slot;

        public PlayerInventoryRecord(String color, int slot) {
            this.color = color;
            this.slot = slot;
        }

        public String getColor() {
            return color;
        }

        public int getSlot() {
            return slot;
        }
    }
}