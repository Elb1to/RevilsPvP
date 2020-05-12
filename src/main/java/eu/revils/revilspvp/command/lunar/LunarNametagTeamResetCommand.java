package eu.revils.revilspvp.command.lunar;

import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import net.mineaus.lunar.api.LunarClientAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LunarNametagTeamResetCommand {

    @Command(names = {"lunar_reset_nametag"}, permission = "op")
    public static void resetLunarNametag(Player sender, @Param(name = "target") Player target) {

        try {
            if (sender != null) {
                LunarClientAPI.INSTANCE().resetNameTag(sender, target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert sender != null;
        sender.sendMessage(ChatColor.RED + "You resetted the player nametags you added for yourself!");
    }
}
