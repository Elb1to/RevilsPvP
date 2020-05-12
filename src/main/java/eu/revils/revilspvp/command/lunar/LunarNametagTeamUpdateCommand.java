package eu.revils.revilspvp.command.lunar;

import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import net.mineaus.lunar.api.LunarClientAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class LunarNametagTeamUpdateCommand {

    @Command(names = {"lunar_update_nametag"}, permission = "op")
    public static void displayLunarNametag(Player sender, @Param(name = "target") Player target) {

        try {
            if (sender != null) {
                LunarClientAPI.INSTANCE().updateNameTag(sender, target, ChatColor.GOLD + "TEST");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert sender != null;
        sender.sendMessage(ChatColor.GREEN + "Now displaying LunarClientAPI TeamNT");
    }

}
