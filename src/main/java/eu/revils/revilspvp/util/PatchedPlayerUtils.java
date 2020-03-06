package eu.revils.revilspvp.util;

import eu.revils.revilspvp.RevilsPvP;
import net.hylist.knockback.CraftKnockbackProfile;
import net.hylist.knockback.KnockbackProfile;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// we mess with fly mode in RevilsPvP, so we need to reset that with PlayerUtils (in qLib)
// unfortunately, that class doesn't reset fly mode - and plugins like qHub, which use doublejump
// (implemented with fly mode if you're not familiar) have already started using that method.
public class PatchedPlayerUtils {

    public static void resetInventory(Player player) {
        resetInventory(player, null);
    }

    public static void resetInventory(Player player, GameMode gameMode) {
        resetInventory(player, gameMode, false);
    }

    public static void resetInventory(Player player, GameMode gameMode, boolean skipInvReset) {
        player.setHealth(player.getMaxHealth());
        player.setFallDistance(0F);
        player.setFoodLevel(20);
        player.setSaturation(10F);
        player.setLevel(0);
        player.setExp(0F);

        if (!skipInvReset) {
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
        }

        player.setFireTicks(0);

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        if (gameMode != null && player.getGameMode() != gameMode) {
            player.setGameMode(gameMode);
        }

        player.setAllowFlight(false);
        player.setFlying(false);
    }

    public static List<String> mapToNames(Collection<UUID> uuids) {
        return uuids.stream().map(RevilsPvP.getInstance().uuidCache::name).collect(Collectors.toList());
    }

    public static String getFormattedName(UUID uuid) {
        return RevilsPvP.getInstance().getUuidCache().name(uuid);
    }

    /*public static void setKnockbackProfile(Player player, String knockbackProfile) {
        CraftKnockbackProfile profile = new CraftKnockbackProfile(knockbackProfile);
        if (player.isOnline() && new CraftKnockbackProfile(knockbackProfile).getName() != null) {
            ((CraftPlayer)player).getHandle().setKbProfile(profile);
        }
    }*/
}