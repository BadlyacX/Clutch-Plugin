package com.badlyac.ClutchPlugin.Player.PlayerGUI.RecordHitsSetting.Listener;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiListener implements Listener {
    final JavaPlugin plugin;

    public GuiListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();

        if (inventory.getTitle().equals(ChatColor.AQUA + "hits")) {
            String uuid = player.getUniqueId().toString();

            FileConfiguration config = plugin.getConfig();

            boolean hasChanged = false;
            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack currentItem = inventory.getItem(i);
                String path = uuid + ".gui." + i;
                if (currentItem != null) {
                    String currentItemInfo = getItemInfo(currentItem, i);
                    String savedItemInfo = config.getString(path);
                    if (savedItemInfo == null || !savedItemInfo.equals(currentItemInfo)) {
                        hasChanged = true;
                        break;
                    }
                } else if (config.contains(path)) {
                    hasChanged = true;
                    break;
                }
            }

            if (hasChanged) {
                for (int i = 0; i < inventory.getSize(); i++) {
                    ItemStack item = inventory.getItem(i);
                    String path = uuid + ".gui." + i;
                    if (item != null) {
                        String itemInfo = getItemInfo(item, i);
                        config.set(path, itemInfo);
                    } else {
                        config.set(path, null);
                    }
                }
                plugin.saveConfig();
            }
        }
    }
    private String getItemInfo(ItemStack item, int slotIndex) {
        return "type: " + item.getType().toString() +
                ", amount: " + item.getAmount() +
                ", durability: " + item.getDurability() +
                ", position: " + slotIndex;
    }
}