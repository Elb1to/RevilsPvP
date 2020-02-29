package eu.revils.revilspvp.rematch.listener;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.event.MatchTerminateEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class RematchGeneralListener implements Listener {

    @EventHandler
    public void onMatchTerminate(MatchTerminateEvent event) {
        RevilsPvP.getInstance().getRematchHandler().registerRematches(event.getMatch());
    }

}