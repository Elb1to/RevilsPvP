package eu.revils.revilspvp.command;

import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.lobby.menu.StatisticsMenu;
import org.bukkit.entity.Player;

public final class LeaderboardCommand {

    @Command(names = {"leaderboard", "leaderboards", "topelo", "tops", "lb"})
    public static void openLeaderboardsMenu(Player sender) {
        new StatisticsMenu().openMenu(sender);
    }

}
