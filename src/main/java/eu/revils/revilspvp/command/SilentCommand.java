package eu.revils.revilspvp.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.util.VisibilityUtils;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public final class SilentCommand {

    @Command(names = {"silent"}, permission = "revilspvp.silent")
    public static void silent(Player sender) {
        if (sender.hasMetadata("ModMode")) {
            sender.removeMetadata("ModMode", RevilsPvP.getInstance());
            sender.removeMetadata("invisible", RevilsPvP.getInstance());

            sender.sendMessage(ChatColor.RED + "Silent mode has been disabled.");
        } else {
            sender.setMetadata("ModMode", new FixedMetadataValue(RevilsPvP.getInstance(), true));
            sender.setMetadata("invisible", new FixedMetadataValue(RevilsPvP.getInstance(), true));
            
            sender.sendMessage(ChatColor.GREEN + "Silent mode has been enabled.");
        }

        VisibilityUtils.updateVisibility(sender);
    }

}