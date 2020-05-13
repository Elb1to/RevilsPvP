package eu.revils.revilspvp.kittype.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class KitCreateCommand {

	@Command(names = { "kittype create" }, permission = "op", description = "Creates a new kit-type")
	public static void execute(Player player, @Param(name = "name") String id) {
		if (KitType.byId(id) != null) {
			player.sendMessage(ChatColor.RED + "A kit-type by that name already exists.");
			return;
		}

		KitType kitType = new KitType(id);
		kitType.setDisplayName(id);
		kitType.setDisplayColor(ChatColor.AQUA);
		kitType.setIcon(new MaterialData(Material.DIAMOND_SWORD));
		kitType.setSort(50);
		kitType.saveAsync();

		KitType.getAllTypes().add(kitType);
		RevilsPvP.getInstance().getQueueHandler().addQueues(kitType);

		player.sendMessage(ChatColor.GREEN + "You've created a new kit-type by the ID \"" + kitType.getId() + "\".");
	}

}
