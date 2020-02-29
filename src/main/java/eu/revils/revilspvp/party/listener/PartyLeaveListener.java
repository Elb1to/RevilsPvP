package eu.revils.revilspvp.party.listener;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.event.MatchSpectatorLeaveEvent;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.party.PartyHandler;
import eu.revils.revilspvp.party.PartyInvite;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public final class PartyLeaveListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUuid = player.getUniqueId();

        for (Party party : RevilsPvP.getInstance().getPartyHandler().getParties()) {
            if (party.isMember(playerUuid)) {
                party.leave(player);
            }

            PartyInvite invite = party.getInvite(playerUuid);

            if (invite != null) {
                party.revokeInvite(invite);
            }
        }
    }

    // players who leave a match while in a party won't be able to
    // do anything (most validation checks disallow players in a party)
    // so as a convenience we remove them from their party.
    // see @jhalt for any further explanation
    @EventHandler
    public void onMatchSpectatorLeave(MatchSpectatorLeaveEvent event) {
        PartyHandler partyHandler = RevilsPvP.getInstance().getPartyHandler();
        Party party = partyHandler.getParty(event.getSpectator());

        if (party != null) {
            party.leave(event.getSpectator());
        }
    }

}