package com.badlyac.ClutchPlugin.Player.PlayerGUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.block.Action;

public class GUI implements Listener {

    String gui_title = ChatColor.AQUA + "Clutch";

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack itemInHand = player.getInventory().getItemInHand();
            if (itemInHand.getType() == Material.INK_SACK && itemInHand.getDurability() == 1) {
                Inventory gui = Bukkit.createInventory(null, 9, gui_title);

                ItemStack stick = new ItemStack(Material.STICK);
                ItemMeta stickMeta = stick.getItemMeta();
                stickMeta.setDisplayName(ChatColor.AQUA + "hits");
                stick.setItemMeta(stickMeta);
                gui.setItem(1, stick);

                ItemStack clock = new ItemStack(Material.WATCH);
                ItemMeta clockMeta = clock.getItemMeta();
                clockMeta.setDisplayName(ChatColor.AQUA + "Ticks");
                clock.setItemMeta(clockMeta);
                gui.setItem(3, clock);

                player.openInventory(gui);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(gui_title)) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.hasItemMeta()) {
                String displayName = clickedItem.getItemMeta().getDisplayName();
                Player player = (Player) event.getWhoClicked();

                if (clickedItem.getType() == Material.STICK && displayName.equals(ChatColor.AQUA + "hits")) {
                    HitsGUI.openClutchGUI(player);
                } else if (clickedItem.getType() == Material.WATCH && displayName.equals(ChatColor.AQUA + "Ticks")) {
                    // Handle clock item click
                }
            }
        }
    }
}