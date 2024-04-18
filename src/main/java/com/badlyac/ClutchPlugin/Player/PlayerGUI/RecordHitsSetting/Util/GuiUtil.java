package com.badlyac.ClutchPlugin.Player.PlayerGUI.RecordHitsSetting.Util;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiUtil {
    private JavaPlugin plugin;

    public GuiUtil(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadGuiItems(Player player) {
        String uuid = player.getUniqueId().toString();
        FileConfiguration config = plugin.getConfig();
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.AQUA + "hits");

        if (config.contains(uuid + ".gui")) {
            for (int i = 0; i < inventory.getSize(); i++) {
                String path = uuid + ".gui." + i;
                if (config.contains(path)) {
                    ItemStack item = createItemStackFromConfig(config, path);
                    if (item != null) {
                        inventory.setItem(i, item);
                    }
                }
            }
        }

        player.openInventory(inventory);
    }

    private ItemStack createItemStackFromConfig(FileConfiguration config, String path) {
        String itemInfo = config.getString(path);
        if (itemInfo == null || itemInfo.isEmpty()) {
            return null;
        }

        String[] parts = itemInfo.split(", ");
        Material type = Material.valueOf(parts[0].split(": ")[1]);
        int amount = Integer.parseInt(parts[1].split(": ")[1]);
        short durability = Short.parseShort(parts[2].split(": ")[1]);

        ItemStack item = new ItemStack(type, amount);
        item.setDurability(durability);
        return item;
    }
}