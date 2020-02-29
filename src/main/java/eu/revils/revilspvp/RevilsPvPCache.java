package eu.revils.revilspvp;

import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class RevilsPvPCache implements Runnable {

    private int onlineCount = 0;
    private int fightsCount = 0;
    private int queuesCount = 0;

    @Override
    public void run() {
        onlineCount = Bukkit.getOnlinePlayers().size();
        fightsCount = RevilsPvP.getInstance().getMatchHandler().countPlayersPlayingInProgressMatches();
        queuesCount = RevilsPvP.getInstance().getQueueHandler().getQueuedCount();
    }

}
