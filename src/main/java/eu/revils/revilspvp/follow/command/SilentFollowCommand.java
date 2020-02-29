package eu.revils.revilspvp.follow.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.command.LeaveCommand;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class SilentFollowCommand {

    @Command(names = "silentfollow", permission = "potpvp.silent")
    public static void silentfollow(Player sender, @Param(name = "target") Player target) {
        sender.setMetadata("ModMode", new FixedMetadataValue(RevilsPvP.getInstance(), true));
        sender.setMetadata("invisible", new FixedMetadataValue(RevilsPvP.getInstance(), true));

        if (RevilsPvP.getInstance().getPartyHandler().hasParty(sender)) {
            LeaveCommand.leave(sender);
        }

        FollowCommand.follow(sender, target);
    }

}
