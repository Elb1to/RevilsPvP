package eu.revils.revilspvp.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class MatchStatusCommand {

    @Command(names = { "match status" }, permission = "")
    public static void matchStatus(CommandSender sender, @Param(name = "target") Player target) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
        Match match = matchHandler.getMatchPlayingOrSpectating(target);

        if (match == null) {
            sender.sendMessage(ChatColor.RED + target.getName() + " is not playing in or spectating a match.");
            return;
        }

        for (String line : RevilsPvP.getGson().toJson(match).split("\n")) {
            sender.sendMessage("  " + ChatColor.GRAY + line);
        }
    }

}