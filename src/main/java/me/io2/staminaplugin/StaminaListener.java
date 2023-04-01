package me.io2.staminaplugin;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class StaminaListener implements Listener {
    public static final HashMap<UUID, StaminaData> playerStaminaData = new HashMap<>();
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        playerStaminaData.put(event.getPlayer().getUniqueId(), new StaminaData(19, 19));

        event.getPlayer().setFoodLevel((int) playerStaminaData.get(event.getPlayer().getUniqueId()).currentStamina);
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent event) {
        playerStaminaData.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        if (event.getFrom().distanceSquared(event.getTo()) == 0 || event.getPlayer().isSwimming()) return;
        StaminaData staminaData = playerStaminaData.get(event.getPlayer().getUniqueId());
        staminaData.timer.reset();
        double v = 1;
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && Bukkit.getPluginManager().getPlugin("TarkovSkills") != null)
            v = 1 - 0.01 * Integer.parseInt(PlaceholderAPI.setPlaceholders(event.getPlayer(), "%tarkovskill_Endurance_currentlevel%"));

        if (event.getPlayer().isSprinting()) {
            staminaData.currentStamina = Math.max(0, staminaData.currentStamina - (StaminaPlugin.sprintStaminaAmount * v));
            // Bukkit.broadcastMessage("Sprinting! decreasing stamina");
        }

        if (event.getTo().getY() > event.getFrom().getY()) {
            if (!staminaData.jumpDetected) {
                staminaData.jumpDetected = true;
                staminaData.currentStamina = Math.max(0, staminaData.currentStamina - (StaminaPlugin.jumpStaminaAmount * v));
                // Bukkit.broadcastMessage("Jump detected! decreasing stamina");
            }
        } else {
            staminaData.jumpDetected = false;
        }

        event.getPlayer().setFoodLevel((int) Math.max(Math.round(staminaData.currentStamina), 1));
    }
}
