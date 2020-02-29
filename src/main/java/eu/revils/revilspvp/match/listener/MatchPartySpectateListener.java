package eu.revils.revilspvp.match.listener;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.party.event.PartyMemberJoinEvent;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * When a player joins a party, attempt to have them spectate their
 * party's active match, if there is one.
 * https://github.com/FrozenOrb/RevilsPvP-SI/issues/32
 */
public final class MatchPartySpectateListener implements Listener {

    @EventHandler
    public void onPartyMemberJoin(PartyMemberJoinEvent event) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
        Match leaderMatch = matchHandler.getMatchPlayingOrSpectating(Bukkit.getPlayer(event.getParty().getLeader()));

        if (leaderMatch != null) {
            leaderMatch.addSpectator(event.getMember(), null);
        }
    }

}