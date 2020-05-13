package eu.revils.revilspvp.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.RevilsPvPLang;
import eu.revils.revilspvp.elo.EloHandler;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.lobby.menu.StatisticsMenu;
import eu.revils.revilspvp.lobby.menu.statistics.KitButton;
import eu.revils.revilspvp.morpheus.menu.EventsMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class StatsCommand {
    @Command(names = {"stats", "statistics"}, permission = "")
    public static void onCommandExecute(Player sender) {
        EloHandler eloHandler = RevilsPvP.getInstance().getEloHandler();
        sender.sendMessage(ChatColor.GRAY + RevilsPvPLang.LONG_LINE);
        sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.AQUA + sender.getPlayer().getName() + ChatColor.AQUA + "'s Statistics" + ChatColor.GRAY + ":");
        for (KitType kitType : KitType.getAllTypes()) {
            if (kitType.isSupportsRanked()) {
                sender.sendMessage(ChatColor.GRAY + " ▪ " + ChatColor.WHITE + kitType.getDisplayName() + ChatColor.GRAY + " - " + ChatColor.GREEN + eloHandler.getElo(sender, kitType) + " ELO");
            }
        }
        sender.sendMessage(ChatColor.GRAY + RevilsPvPLang.LONG_LINE);
    }

}
