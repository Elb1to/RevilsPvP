package eu.revils.revilspvp.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public final class BuildCommand {

    @Command(names = {"build"}, permission = "op")
    public static void silent(Player sender) {
        if (sender.hasMetadata("Build")) {
            sender.removeMetadata("Build", RevilsPvP.getInstance());
            sender.sendMessage(ChatColor.RED + "Build mode has been disabled.");
        } else {
            sender.setMetadata("Build", new FixedMetadataValue(RevilsPvP.getInstance(), true));
            sender.sendMessage(ChatColor.GREEN + "Build mode has been enabled.");
        }
    }

}