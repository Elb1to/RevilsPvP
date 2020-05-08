package eu.revils.revilspvp.party.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.party.PartyHandler;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PartyCreateCommand {

    @Command(names = {"party create", "p create", "t create", "team create", "f create"}, permission = "")
    public static void partyCreate(Player sender) {
        PartyHandler partyHandler = RevilsPvP.getInstance().getPartyHandler();

        if (partyHandler.hasParty(sender)) {
            sender.sendMessage(ChatColor.BLUE + "[Party] " + ChatColor.RED + "You are already in a party.");
            return;
        }

        partyHandler.getOrCreateParty(sender);
        sender.sendMessage(ChatColor.BLUE + "[Party] " + ChatColor.GREEN + "You have created a new party.");
    }

}