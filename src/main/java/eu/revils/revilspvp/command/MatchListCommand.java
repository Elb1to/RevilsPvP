package eu.revils.revilspvp.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class MatchListCommand {

    @Command(names = { "match list" }, permission = "op")
    public static void matchList(Player sender) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

        for (Match match : matchHandler.getHostedMatches()) {
            sender.sendMessage(ChatColor.RED + match.getSimpleDescription(true));
        }
    }

}