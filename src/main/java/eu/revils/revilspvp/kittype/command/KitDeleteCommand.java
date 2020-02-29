package eu.revils.revilspvp.kittype.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class KitDeleteCommand {

	@Command(names = { "kittype delete" }, permission = "op", description = "Deletes an existing kit-type")
	public static void execute(Player player, @Param(name = "kittype") KitType kitType) {
		kitType.deleteAsync();
		KitType.getAllTypes().remove(kitType);
		RevilsPvP.getInstance().getQueueHandler().removeQueues(kitType);

		player.sendMessage(ChatColor.GREEN + "You've deleted the kit-type by the ID \"" + kitType.getId() + "\".");
	}

}
