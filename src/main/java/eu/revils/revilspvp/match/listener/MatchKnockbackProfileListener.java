package eu.revils.revilspvp.match.listener;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.MatchHandler;
import net.hylist.knockback.KnockbackProfile;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.SpigotConfig;

public class MatchKnockbackProfileListener extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : RevilsPvP.getInstance().getServer().getOnlinePlayers()) {
            MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
            KnockbackProfile kbprofile = SpigotConfig.globalKbProfile;
            if ((matchHandler.isPlayingMatch(player))) {
                if (matchHandler.getMatchPlaying(player) != null && matchHandler.getMatchPlaying(player).getKitType() != null) {
                    if (SpigotConfig.getKbProfileByName(matchHandler.getMatchPlaying(player).getKitType().getKnockbackProfile().toLowerCase()) != null) {
                        kbprofile = SpigotConfig.getKbProfileByName(matchHandler.getMatchPlaying(player).getKitType().getKnockbackProfile().toLowerCase());
                    }
                    player.setKbProfile(kbprofile);
                }
            }
            player.setKbProfile(kbprofile);
        }
    }

}
