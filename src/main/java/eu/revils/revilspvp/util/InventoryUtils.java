package eu.revils.revilspvp.util;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.lobby.LobbyUtils;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.match.MatchUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class InventoryUtils {

    public static final long RESET_DELAY_TICKS = 2L;

    public static void resetInventoryDelayed(Player player) {
        Runnable task = () -> resetInventoryNow(player);
        Bukkit.getScheduler().runTaskLater(RevilsPvP.getInstance(), task, RESET_DELAY_TICKS);
    }

    public static void resetInventoryNow(Player player) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

        if (matchHandler.isPlayingOrSpectatingMatch(player)) {
            MatchUtils.resetInventory(player);
        } else {
            LobbyUtils.resetInventory(player);
        }
    }

}