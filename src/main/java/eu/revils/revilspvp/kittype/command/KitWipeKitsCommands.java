package eu.revils.revilspvp.kittype.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class KitWipeKitsCommands {

    @Command(names = "kit wipeKits Type", permission = "op")
    public static void kitWipeKitsType(Player sender, @Param(name="kit type") KitType kitType) {
        int modified = RevilsPvP.getInstance().getKitHandler().wipeKitsWithType(kitType);
        sender.sendMessage(ChatColor.YELLOW + "Wiped " + modified + " " + kitType.getDisplayName() + " kits.");
        sender.sendMessage(ChatColor.GRAY + "^ We would have a proper count here if we ran recent versions of MongoDB");
    }

    @Command(names = "kit wipeKits Player", permission = "op")
    public static void kitWipeKitsPlayer(Player sender, @Param(name="target") UUID target) {
        RevilsPvP.getInstance().getKitHandler().wipeKitsForPlayer(target);
        sender.sendMessage(ChatColor.YELLOW + "Wiped " + RevilsPvP.getInstance().getUuidCache().name(target) + "'s kits.");
    }

}