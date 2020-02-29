package eu.revils.revilspvp.arena.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class ArenaFreeCommand {

    @Command(names = { "arena free" }, permission = "op")
    public static void arenaFree(Player sender) {
        RevilsPvP.getInstance().getArenaHandler().getGrid().free();
        sender.sendMessage(ChatColor.GREEN + "Arena grid has been freed.");
    }

}