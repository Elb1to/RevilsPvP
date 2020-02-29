package eu.revils.revilspvp.follow.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.follow.FollowHandler;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class UnfollowCommand {

    @Command(names={"unfollow"}, permission="")
    public static void unfollow(Player sender) {
        FollowHandler followHandler = RevilsPvP.getInstance().getFollowHandler();
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

        if (!followHandler.getFollowing(sender).isPresent()) {
            sender.sendMessage(ChatColor.RED + "You're not following anybody.");
            return;
        }

        Match spectating = matchHandler.getMatchSpectating(sender);

        if (spectating != null) {
            spectating.removeSpectator(sender);
        }

        followHandler.stopFollowing(sender);
    }

}