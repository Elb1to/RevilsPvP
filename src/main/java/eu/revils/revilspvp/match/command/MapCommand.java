package eu.revils.revilspvp.match.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.arena.Arena;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class MapCommand {

    @Command(names = { "map" }, permission = "")
    public static void map(Player sender) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
        Match match = matchHandler.getMatchPlayingOrSpectating(sender);

        if (match == null) {
            sender.sendMessage(ChatColor.RED + "You are not in a match.");
            return;
        }

        Arena arena = match.getArena();
        sender.sendMessage(ChatColor.YELLOW + "Playing on copy " + ChatColor.GOLD + arena.getCopy() + ChatColor.YELLOW + " of " + ChatColor.GOLD + arena.getSchematic() + ChatColor.YELLOW + ".");
    }

}