package eu.revils.revilspvp.command.lunar;

import eu.revils.revilspvp.kt.command.Command;
import net.mineaus.lunar.api.LunarClientAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LunarTeammateResetCommand {

    @Command(names = {"lunar_teammate_reset"}, permission = "op")
    public static void lunarTeammateReset(Player sender) {

        try {
            if (sender != null) {
                LunarClientAPI.INSTANCE().resetTeamMates(sender);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert sender != null;
        sender.sendMessage(ChatColor.RED + "You resetted your teams!");
        sender.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + "(testing)");
    }
}
