package eu.revils.revilspvp.arena.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.arena.Arena;
import eu.revils.revilspvp.arena.ArenaHandler;
import eu.revils.revilspvp.arena.ArenaSchematic;
import eu.revils.revilspvp.util.LocationUtils;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class ArenaListArenasCommand {

    @Command(names = { "arena listArenas" }, permission = "op")
    public static void arenaListArenas(Player sender, @Param(name="schematic") String schematicName) {
        ArenaHandler arenaHandler = RevilsPvP.getInstance().getArenaHandler();
        ArenaSchematic schematic = arenaHandler.getSchematic(schematicName);

        if (schematic == null) {
            sender.sendMessage(ChatColor.RED + "Schematic " + schematicName + " not found.");
            sender.sendMessage(ChatColor.RED + "List all schematics with /arena listSchematics");
            return;
        }

        sender.sendMessage(ChatColor.RED + "------ " + ChatColor.WHITE + schematic.getName() + " Arenas" + ChatColor.RED + " ------");

        for (Arena arena : arenaHandler.getArenas(schematic)) {
            String locationStr = LocationUtils.locToStr(arena.getSpectatorSpawn());
            String occupiedStr = arena.isInUse() ? ChatColor.RED + "In Use" : ChatColor.GREEN + "Open";

            sender.sendMessage(arena.getCopy() + ": " + ChatColor.GREEN + locationStr + ChatColor.GRAY + " - " + occupiedStr);
        }
    }

}