package eu.revils.revilspvp.kittype.command;

import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import net.hylist.knockback.CraftKnockbackProfile;
import net.hylist.knockback.KnockbackProfile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class KitSetKnockbackCommand {

    @Command(names = { "kittype setKnockback" }, permission = "op", description = "Sets a kit-type's knockback profile")
    public void execute(Player player, @Param(name = "kittype") KitType kitType, @Param(name = "knockbackprofile") KnockbackProfile profile) {
        /*KitType target = KitType.byId(kitType.getId());
        if (target == null) {
            player.sendMessage(ChatColor.RED + "This kit doesn't exist.");
            return;
        }

        if (new CraftKnockbackProfile(knockbackProfile).getName() == null) {
            player.sendMessage(ChatColor.RED + "This KB Profile doesn't exist.");
            return;
        }*/

        kitType.setKnockbackProfile(profile);
        kitType.saveAsync();

        player.sendMessage(ChatColor.GREEN + "You've updated" + kitType + "'s knockback profile.");
    }
}