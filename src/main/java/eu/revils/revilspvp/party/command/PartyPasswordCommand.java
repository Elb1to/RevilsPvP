package eu.revils.revilspvp.party.command;

import eu.revils.revilspvp.RevilsPvPLang;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.party.PartyAccessRestriction;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PartyPasswordCommand {

    @Command(names = {"party password", "p password", "t password", "team password", "party pass", "p pass", "t pass", "team pass", "f password", "f pass"}, permission = "")
    public static void partyPassword(Player sender, @Param(name = "password") String password) {
        Party party = RevilsPvP.getInstance().getPartyHandler().getParty(sender);

        if (party == null) {
            sender.sendMessage(RevilsPvPLang.NOT_IN_PARTY);
        } else if (!party.isLeader(sender.getUniqueId())) {
            sender.sendMessage(RevilsPvPLang.NOT_LEADER_OF_PARTY);
        } else {
            party.setAccessRestriction(PartyAccessRestriction.PASSWORD);
            party.setPassword(password);

            sender.sendMessage(ChatColor.YELLOW + "Your party's password is now " + ChatColor.AQUA + password + ChatColor.YELLOW + ".");
        }
    }

}
