package eu.revils.revilspvp.party.command;

import eu.revils.revilspvp.RevilsPvPLang;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.entity.Player;

public final class PartyLeaveCommand {

    @Command(names = {"party leave", "p leave", "t leave", "team leave", "leave", "f leave"}, permission = "")
    public static void partyLeave(Player sender) {
        Party party = RevilsPvP.getInstance().getPartyHandler().getParty(sender);

        if (party == null) {
            sender.sendMessage(RevilsPvPLang.NOT_IN_PARTY);
        } else {
            party.leave(sender);
        }
    }

}