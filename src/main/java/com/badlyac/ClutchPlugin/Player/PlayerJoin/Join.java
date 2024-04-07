package com.badlyac.ClutchPlugin.Player.PlayerJoin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class Join implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (int i = 0; i < 2; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.SANDSTONE, 64));
        }
        player.getInventory().setItem(8, new ItemStack(Material.INK_SACK, 1, (short) 1));
    }
}
