package eu.revils.revilspvp.match.replay;

import org.bukkit.entity.Player;

public interface ReplayableAction {

    public void replay(Player replayFor);
}
