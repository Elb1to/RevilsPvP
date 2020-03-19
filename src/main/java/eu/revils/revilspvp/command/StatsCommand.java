package eu.revils.revilspvp.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.elo.EloHandler;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.lobby.menu.StatisticsMenu;
import eu.revils.revilspvp.lobby.menu.statistics.KitButton;
import eu.revils.revilspvp.morpheus.menu.EventsMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class StatsCommand {
    @Command(names = {"stats"}, permission = "")
    public static void onCommandExecute(Player sender) {
        EloHandler eloHandler = RevilsPvP.getInstance().getEloHandler();
        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "----------------------");
        sender.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Your Stats" + ChatColor.GRAY + " - " + ChatColor.YELLOW + sender.getPlayer().getName());
        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "----------------------");
        for (KitType kitType : KitType.getAllTypes()) {
            if (kitType.isSupportsRanked()) {
                sender.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + kitType.getDisplayName() + ChatColor.GRAY + " - " + ChatColor.WHITE + eloHandler.getElo(sender, kitType) + ChatColor.ITALIC + " ELO");
            }
        }
        sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "----------------------");
    }

}
