package eu.revils.revilspvp.command.lunar;

import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import net.mineaus.lunar.api.LunarClientAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LunarTeamMateAddCommand {

    @Command(names = {"lunar_teammate_add"}, permission = "op")
    public static void lunarTeammateDisplay(Player sender, @Param(name = "target") Player target) {

        try {
            if (sender != null) {
                LunarClientAPI.INSTANCE().sendTeamMate(sender, target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert sender != null;
        sender.sendMessage(ChatColor.GREEN + "You added " + ChatColor.AQUA + target + ChatColor.GREEN + " to your team.");
        sender.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + "(testing)");
    }

}
