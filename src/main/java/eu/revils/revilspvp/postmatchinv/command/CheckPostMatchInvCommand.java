package eu.revils.revilspvp.postmatchinv.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import eu.revils.revilspvp.postmatchinv.PostMatchInvHandler;
import eu.revils.revilspvp.postmatchinv.PostMatchPlayer;
import eu.revils.revilspvp.postmatchinv.menu.PostMatchMenu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public final class CheckPostMatchInvCommand {

    @Command(names = { "checkPostMatchInv", "_" }, permission = "")
    public static void checkPostMatchInv(Player sender, @Param(name = "target") UUID target) {
        PostMatchInvHandler postMatchInvHandler = RevilsPvP.getInstance().getPostMatchInvHandler();
        Map<UUID, PostMatchPlayer> players = postMatchInvHandler.getPostMatchData(sender.getUniqueId());

        if (players.containsKey(target)) {
            new PostMatchMenu(players.get(target)).openMenu(sender);
        } else {
            sender.sendMessage(ChatColor.RED + "Data for " + RevilsPvP.getInstance().getUuidCache().name(target) + " not found.");
        }
    }

}