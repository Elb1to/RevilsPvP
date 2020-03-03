package eu.revils.revilspvp.command;

import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.morpheus.menu.EventsMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class EventsCommand {

    @Command(names = {"event", "events", "joinevents"}, permission = "")
    public static void openEventsMenu(Player sender) {
        sender.sendMessage(ChatColor.GREEN + "Opened the Events Menu.");
        new EventsMenu().openMenu(sender);
    }
}
