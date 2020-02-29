package eu.revils.revilspvp.rematch.listener;

import eu.revils.revilspvp.RevilsPvP;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class RematchUnloadListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        RevilsPvP.getInstance().getRematchHandler().unloadRematchData(event.getPlayer());
    }

}