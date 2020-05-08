package eu.revils.revilspvp.setting.command;

import eu.revils.revilspvp.setting.menu.SettingsMenu;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.entity.Player;

/**
 * /settings, accessible by all users, opens a {@link SettingsMenu}
 */
public final class SettingsCommand {

    @Command(names = {"preferences", "prefs", "options"}, permission = "")
    public static void settings(Player sender) {
        new SettingsMenu().openMenu(sender);
    }

}