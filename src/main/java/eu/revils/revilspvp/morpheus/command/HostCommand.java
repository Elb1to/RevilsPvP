package eu.revils.revilspvp.morpheus.command;

import eu.revils.revilspvp.morpheus.menu.HostMenu;
import eu.revils.revilspvp.kt.command.Command;
import org.bukkit.entity.Player;

public class HostCommand {

    @Command(names = { "host"}, permission = "")
    public static void host(Player sender) {
        new HostMenu().openMenu(sender);
    }

}
