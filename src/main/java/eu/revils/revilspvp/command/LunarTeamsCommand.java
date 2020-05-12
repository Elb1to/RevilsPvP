package eu.revils.revilspvp.command;

import eu.revils.revilspvp.kt.command.Command;
import net.mineaus.lunar.api.LunarClientAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class LunarTeamsCommand {

    @Command(names = {"lunarteams", "lcteams", "lcteam"}, permission = "revils.lunarteams")
    public static void displayLunarTeamTag(Player sender, Player viewer) {
        try {
            if (sender != null) {
                LunarClientAPI.INSTANCE().updateNameTag(sender, viewer, "TestString Eksde");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sender.sendMessage(ChatColor.GREEN + "Now displaying LunarClientAPI's TeamNT");
    }

}
