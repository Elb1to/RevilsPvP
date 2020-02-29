package eu.revils.revilspvp.duel.listener;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.event.MatchCountdownStartEvent;
import eu.revils.revilspvp.match.event.MatchSpectatorJoinEvent;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.party.event.PartyDisbandEvent;
import eu.revils.revilspvp.duel.DuelHandler;
import eu.revils.revilspvp.match.MatchTeam;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public final class DuelListener implements Listener {

    @EventHandler
    public void onMatchSpectatorJoin(MatchSpectatorJoinEvent event) {
        DuelHandler duelHandler = RevilsPvP.getInstance().getDuelHandler();
        Player player = event.getSpectator();

        duelHandler.removeInvitesTo(player);
        duelHandler.removeInvitesFrom(player);
    }

    @EventHandler
    public void onPartyDisband(PartyDisbandEvent event) {
        DuelHandler duelHandler = RevilsPvP.getInstance().getDuelHandler();
        Party party = event.getParty();

        duelHandler.removeInvitesTo(party);
        duelHandler.removeInvitesFrom(party);
    }

    @EventHandler
    public void onMatchCountdownStart(MatchCountdownStartEvent event) {
        DuelHandler duelHandler = RevilsPvP.getInstance().getDuelHandler();

        for (MatchTeam team : event.getMatch().getTeams()) {
            for (UUID member : team.getAllMembers()) {
                Player memberPlayer = Bukkit.getPlayer(member);

                duelHandler.removeInvitesTo(memberPlayer);
                duelHandler.removeInvitesFrom(memberPlayer);
            }
        }
    }

}