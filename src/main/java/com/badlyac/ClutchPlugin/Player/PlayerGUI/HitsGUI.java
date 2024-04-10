package com.badlyac.ClutchPlugin.Player.PlayerGUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HitsGUI implements Listener {

    public static final String GUI_TITLE = ChatColor.AQUA + "hits";

    public static void openClutchGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 9 * 6, GUI_TITLE);
        ItemStack grayGlassPane = createGlassPane((short) 7);
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, grayGlassPane);
        }
        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(GUI_TITLE) && event.getClickedInventory() != null) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() == Material.STAINED_GLASS_PANE) {
                short colorData = clickedItem.getDurability();
                short nextColorData = getNextColorData(colorData);
                if (nextColorData != colorData) {
                    event.getClickedInventory().setItem(event.getSlot(), createGlassPane(nextColorData));
                }
            }
        }
    }

    private static ItemStack createGlassPane(short colorData) {
        ItemStack glassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, colorData);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setDisplayName(" ");
        glassPane.setItemMeta(meta);
        return glassPane;
    }

    private static short getNextColorData(short colorData) {
        switch (colorData) {
            case 7: // Gray
                return 5; // Green
            case 5: // Green
                return 4; // Yellow
            case 4: // Yellow
                return 1; // Orange
            case 1: // Orange
                return 14; // Red
            case 14: // Red
                return 10; // Purple
            case 10: // Purple
                return 7; // Gray (Loop back to the start)
            default:
                return colorData; // No change
        }
    }
}