package eu.revils.revilspvp.arena.event;

import eu.revils.revilspvp.arena.Arena;
import eu.revils.revilspvp.match.Match;

import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * Called when an {@link Arena} is done being used by a
 * {@link Match}
 */
public final class ArenaReleasedEvent extends ArenaEvent {

    @Getter private static HandlerList handlerList = new HandlerList();

    public ArenaReleasedEvent(Arena arena) {
        super(arena);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}