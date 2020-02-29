package eu.revils.revilspvp.party.command;

import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.RevilsPvPLang;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.entity.Player;

public final class PartyDisbandCommand {

    @Command(names = {"party disband", "p disband", "t disband", "team disband", "f disband"}, permission = "")
    public static void partyDisband(Player sender) {
        Party party = RevilsPvP.getInstance().getPartyHandler().getParty(sender);

        if (party == null) {
            sender.sendMessage(RevilsPvPLang.NOT_IN_PARTY);
            return;
        }

        if (!party.isLeader(sender.getUniqueId())) {
            sender.sendMessage(RevilsPvPLang.NOT_LEADER_OF_PARTY);
            return;
        }

        party.disband();
    }

}