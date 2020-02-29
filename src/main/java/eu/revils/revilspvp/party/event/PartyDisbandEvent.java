package eu.revils.revilspvp.party.event;

import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.party.command.PartyDisbandCommand;

import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * Called when a {@link Party} is disbanded.
 * @see PartyDisbandCommand
 * @see Party#disband()
 */
public final class PartyDisbandEvent extends PartyEvent {

    @Getter private static HandlerList handlerList = new HandlerList();

    public PartyDisbandEvent(Party party) {
        super(party);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}