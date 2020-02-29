package eu.revils.revilspvp.party.command;

import eu.revils.revilspvp.RevilsPvPLang;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PartyLeaderCommand {

    @Command(names = {"party leader", "p leader", "t leader", "team leader", "leader", "f leader"}, permission = "")
    public static void partyLeader(Player sender, @Param(name = "player") Player target) {
        Party party = RevilsPvP.getInstance().getPartyHandler().getParty(sender);

        if (party == null) {
            sender.sendMessage(RevilsPvPLang.NOT_IN_PARTY);
        } else if (!party.isLeader(sender.getUniqueId())) {
            sender.sendMessage(RevilsPvPLang.NOT_LEADER_OF_PARTY);
        } else if (!party.isMember(target.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + target.getName() + " isn't in your party.");
        } else if (sender == target) {
            sender.sendMessage(ChatColor.RED + "You cannot promote yourself to the leader of your own party.");
        } else {
            party.setLeader(target);
        }
    }

}