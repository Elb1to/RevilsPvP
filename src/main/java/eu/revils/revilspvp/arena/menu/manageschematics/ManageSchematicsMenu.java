package eu.revils.revilspvp.arena.menu.manageschematics;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.arena.ArenaHandler;
import eu.revils.revilspvp.arena.ArenaSchematic;
import eu.revils.revilspvp.command.ManageCommand;
import eu.revils.revilspvp.util.menu.MenuBackButton;
import eu.revils.revilspvp.kt.menu.Button;
import eu.revils.revilspvp.kt.menu.Menu;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class ManageSchematicsMenu extends Menu {

    public ManageSchematicsMenu() {
        super("Manage schematics");
        setAutoUpdate(true);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        ArenaHandler arenaHandler = RevilsPvP.getInstance().getArenaHandler();
        Map<Integer, Button> buttons = new HashMap<>();
        int index = 0;

        buttons.put(index++, new MenuBackButton(p -> new ManageCommand.ManageMenu().openMenu(p)));

        for (ArenaSchematic schematic : arenaHandler.getSchematics()) {
            buttons.put(index++, new ManageSchematicButton(schematic));
        }

        return buttons;
    }

}