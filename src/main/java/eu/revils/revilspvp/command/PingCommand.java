package eu.revils.revilspvp.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import eu.revils.revilspvp.kt.util.PlayerUtils;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PingCommand {

    @Command(names = "ping", permission = "")
    public static void ping(Player sender, @Param(name = "target", defaultValue = "self") Player target) {
        int ping = PlayerUtils.getPing(target);

        sender.sendMessage(target.getDisplayName() + ChatColor.YELLOW + "'s Ping" + ChatColor.GRAY + ": " + ChatColor.GREEN + ping + "ms");

        if (sender.getName().equalsIgnoreCase(target.getName())) {
            Match match = RevilsPvP.getInstance().getMatchHandler().getMatchPlaying(sender);
            if (match != null) {
                for (MatchTeam team : match.getTeams()) {
                    for (UUID other : team.getAllMembers()) {
                        Player otherPlayer = Bukkit.getPlayer(other);
                        if (otherPlayer != null && !otherPlayer.equals(sender)) {
                            int otherPing = PlayerUtils.getPing(otherPlayer);
                            sender.sendMessage(otherPlayer.getDisplayName() + ChatColor.YELLOW + "'s Ping" + ChatColor.GRAY + ": " + ChatColor.GREEN + otherPing + "ms");
                        }
                    }
                }
            }
        }
    }

}
