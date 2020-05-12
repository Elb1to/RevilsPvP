package eu.revils.revilspvp.command.lunar;

import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import net.mineaus.lunar.api.LunarClientAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class LunarTeamMateAddCommand {

    @Command(names = {"lunar_teammate_add"}, permission = "op")
    public static void lunarTeammateDisplay(Player sender, @Param(name="player1") Player p1, @Param(name="player2") Player p2) {
        ArrayList<Player> players = new ArrayList<>();
        try {
            if (sender != null) {
                LunarClientAPI.INSTANCE().sendTeamMate(sender, p1, p2);
                //LunarClientAPI.INSTANCE().sendTeamMate(sender, players.toArray(new Player[players.size()]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert sender != null;
        sender.sendMessage(ChatColor.GREEN + "You added " + ChatColor.AQUA + players.size() + ChatColor.GREEN + " to your team.");
    }

}
