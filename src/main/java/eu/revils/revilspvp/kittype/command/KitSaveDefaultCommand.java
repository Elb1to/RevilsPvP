package eu.revils.revilspvp.kittype.command;

import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class KitSaveDefaultCommand {

    @Command(names = "kit saveDefault", permission = "op")
    public static void kitSaveDefault(Player sender, @Param(name="kit type") KitType kitType) {
        kitType.setDefaultArmor(sender.getInventory().getArmorContents());
        kitType.setDefaultInventory(sender.getInventory().getContents());
        kitType.saveAsync();

        sender.sendMessage(ChatColor.YELLOW + "Saved default armor/inventory for " + kitType + ".");
    }

}