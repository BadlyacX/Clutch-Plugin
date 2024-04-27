package com.badlyac.ClutchPlugin.Player.Test;

import com.badlyac.ClutchPlugin.Player.PlayerGUI.RecordHitsSetting.ReadConfig.PlayerConfigHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Test implements Listener {
    PlayerConfigHandler playerConfigHandler = new PlayerConfigHandler();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInHand().getType() == Material.DIAMOND) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                playerConfigHandler.applyKnockback(event.getPlayer(), event.getPlayer().getUniqueId(), 10);
            }
        }
    }
}
