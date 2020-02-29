package eu.revils.revilspvp.match.event;

import eu.revils.revilspvp.match.Match;

import eu.revils.revilspvp.match.MatchState;
import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * Called when a match is terminated (when its {@link MatchState} changes
 * to {@link MatchState#TERMINATED})
 * @see MatchState#TERMINATED
 */

public final class MatchTerminateEvent extends MatchEvent {

    @Getter private static HandlerList handlerList = new HandlerList();

    public MatchTerminateEvent(Match match) {
        super(match);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}