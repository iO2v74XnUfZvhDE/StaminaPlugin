package me.io2.staminaplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public final class StaminaPlugin extends JavaPlugin {
    public static double jumpStaminaAmount = 0.0;
    public static double sprintStaminaAmount = 0.0;
    private static double staminaRegenAmount = 0.0;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Hi!");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new StaminaDataExpansion().register();
        } else {
            getLogger().info("PlaceholderAPI is not installed, This does not affect the functionality, but may cause problems with other plugins.");
        }

        Bukkit.getServer().getPluginManager().registerEvents(new StaminaListener(), this);

        saveDefaultConfig();
        jumpStaminaAmount = getConfig().getDouble("jumpStaminaDecreaseAmount", 0.2);
        sprintStaminaAmount = getConfig().getDouble("sprintStaminaDecreaseAmount", 0.01);
        staminaRegenAmount = getConfig().getDouble("staminaRegenAmount", 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<UUID, StaminaData> uuidStaminaDataHashMap = new HashMap<>(StaminaListener.playerStaminaData);
                uuidStaminaDataHashMap.forEach((uuid, staminaData) -> {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player == null) return;

                    // Bukkit.broadcastMessage("Current stamina: " + staminaData.currentStamina);

                    if (staminaData.timer.hasTimePassed(1000) || player.isSwimming()) {
                        staminaData.currentStamina = Math.min(staminaData.currentStamina + staminaRegenAmount, staminaData.maxStamina);
                        player.setFoodLevel((int) Math.round(staminaData.currentStamina));
                        // Bukkit.broadcastMessage("Not moving or swimming. Regenerating stamina!");
                    }
                });
            }
        }.runTaskTimer(this, 0, 10);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
    }
}
