package eu.revils.revilspvp.follow.command;

import com.qrakn.morpheus.game.Game;
import com.qrakn.morpheus.game.GameQueue;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.follow.FollowHandler;
import eu.revils.revilspvp.validation.RevilsPvPValidation;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.setting.Setting;
import eu.revils.revilspvp.setting.SettingHandler;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class FollowCommand {

    @Command(names={"follow"}, permission="")
    public static void follow(Player sender, @Param(name="target") Player target) {
        if (!RevilsPvPValidation.canFollowSomeone(sender)) {
            return;
        }

        FollowHandler followHandler = RevilsPvP.getInstance().getFollowHandler();
        SettingHandler settingHandler = RevilsPvP.getInstance().getSettingHandler();
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

        if (sender == target) {
            sender.sendMessage(ChatColor.RED + "No, you can't follow yourself.");
            return;
        } else if (!settingHandler.getSetting(target, Setting.ALLOW_SPECTATORS)) {
            if (sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "Bypassing " + target.getName() + "'s no spectators preference...");
            } else {
                sender.sendMessage(ChatColor.RED + target.getName() + " doesn't allow spectators at the moment.");
                return;
            }
        }

        Game game = GameQueue.INSTANCE.getCurrentGame(target);
        if (game != null && game.getPlayers().contains(target)) {
            sender.sendMessage(ChatColor.RED + target.getName() + " is playing an event!");
            return;
        }

        followHandler.getFollowing(sender).ifPresent(fo -> UnfollowCommand.unfollow(sender));

        if (matchHandler.isSpectatingMatch(sender)) {
            matchHandler.getMatchSpectating(sender).removeSpectator(sender);
        }

        followHandler.startFollowing(sender, target);
    }

}