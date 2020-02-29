package eu.revils.revilspvp.postmatchinv.listener;

import eu.revils.revilspvp.match.event.MatchCountdownStartEvent;
import eu.revils.revilspvp.match.event.MatchTerminateEvent;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.MatchTeam;
import eu.revils.revilspvp.postmatchinv.PostMatchInvHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public final class PostMatchInvGeneralListener implements Listener {

    @EventHandler
    public void onMatchTerminate(MatchTerminateEvent event) {
        PostMatchInvHandler postMatchInvHandler = RevilsPvP.getInstance().getPostMatchInvHandler();
        postMatchInvHandler.recordMatch(event.getMatch());
    }

    // remove 'old' post match data when their match starts
    @EventHandler
    public void onMatchCountdownStart(MatchCountdownStartEvent event) {
        PostMatchInvHandler postMatchInvHandler = RevilsPvP.getInstance().getPostMatchInvHandler();

        for (MatchTeam team : event.getMatch().getTeams()) {
            for (UUID member : team.getAllMembers()) {
                postMatchInvHandler.removePostMatchData(member);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PostMatchInvHandler postMatchInvHandler = RevilsPvP.getInstance().getPostMatchInvHandler();
        UUID playerUuid = event.getPlayer().getUniqueId();

        postMatchInvHandler.removePostMatchData(playerUuid);
    }

}