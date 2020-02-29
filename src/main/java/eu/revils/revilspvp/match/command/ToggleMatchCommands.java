package eu.revils.revilspvp.match.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class ToggleMatchCommands {

    @Command(names = { "toggleMatches unranked" }, permission = "op")
    public static void toggleMatchesUnranked(Player sender) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

        boolean newState = !matchHandler.isUnrankedMatchesDisabled();
        matchHandler.setUnrankedMatchesDisabled(newState);

        sender.sendMessage(ChatColor.YELLOW + "Unranked matches are now " + ChatColor.UNDERLINE + (newState ? "disabled" : "enabled") + ChatColor.YELLOW + ".");
    }

    @Command(names = { "toggleMatches ranked" }, permission = "op")
    public static void toggleMatchesRanked(Player sender) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

        boolean newState = !matchHandler.isRankedMatchesDisabled();
        matchHandler.setRankedMatchesDisabled(newState);

        sender.sendMessage(ChatColor.YELLOW + "Ranked matches are now " + ChatColor.UNDERLINE + (newState ? "disabled" : "enabled") + ChatColor.YELLOW + ".");
    }

}