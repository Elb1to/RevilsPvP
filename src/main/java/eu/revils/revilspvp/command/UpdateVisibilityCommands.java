package eu.revils.revilspvp.command;

import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.util.VisibilityUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class UpdateVisibilityCommands {

    @Command(names = {"updatevisibility", "updatevis", "upvis", "uv"}, permission = "")
    public static void updateVisibility(Player sender) {
        VisibilityUtils.updateVisibility(sender);
        sender.sendMessage(ChatColor.GREEN + "Updated your visibility.");
    }

    @Command(names = {"updatevisibilityFlicker", "updatevisFlicker", "upvisFlicker", "uvf"}, permission = "")
    public static void updateVisibilityFlicker(Player sender) {
        VisibilityUtils.updateVisibilityFlicker(sender);
        sender.sendMessage(ChatColor.GREEN + "Updated your visibility (flicker mode).");
    }

}