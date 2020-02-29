package eu.revils.revilspvp.party.command;

import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.party.PartyAccessRestriction;
import eu.revils.revilspvp.RevilsPvPLang;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PartyOpenCommand {

    @Command(names = {"party open", "p open", "t open", "team open", "f open", "party unlock", "p unlock", "t unlock", "team unlock", "f unlock"}, permission = "")
    public static void partyOpen(Player sender) {
        Party party = RevilsPvP.getInstance().getPartyHandler().getParty(sender);

        if (party == null) {
            sender.sendMessage(RevilsPvPLang.NOT_IN_PARTY);
        } else if (!party.isLeader(sender.getUniqueId())) {
            sender.sendMessage(RevilsPvPLang.NOT_LEADER_OF_PARTY);
        } else if (party.getAccessRestriction() == PartyAccessRestriction.PUBLIC) {
            sender.sendMessage(ChatColor.RED + "Your party is already open.");
        } else {
            party.setAccessRestriction(PartyAccessRestriction.PUBLIC);
            sender.sendMessage(ChatColor.YELLOW + "Your party is now " + ChatColor.GREEN + "open" + ChatColor.YELLOW + ".");
        }
    }

}
