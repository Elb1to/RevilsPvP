package eu.revils.revilspvp.party.event;

import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.party.PartyHandler;
import eu.revils.revilspvp.party.command.PartyCreateCommand;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * Called when a {@link Party} is created.
 * @see PartyCreateCommand
 * @see PartyHandler#getOrCreateParty(Player)
 */
public final class PartyCreateEvent extends PartyEvent {

    @Getter private static HandlerList handlerList = new HandlerList();

    public PartyCreateEvent(Party party) {
        super(party);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}