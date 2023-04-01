package me.io2.staminaplugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class StaminaDataExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getAuthor() {
        return "io2";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "stamina";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) return null;
        if (params.equalsIgnoreCase("current")) {
            return String.valueOf(StaminaListener.playerStaminaData.get(player.getUniqueId()).currentStamina);
        }

        if (params.equalsIgnoreCase("max")) {
            return String.valueOf(StaminaListener.playerStaminaData.get(player.getUniqueId()).maxStamina);
        }


        return null; // Placeholder is unknown by the Expansion
    }
}