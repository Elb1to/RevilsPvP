package eu.revils.revilspvp.duel;

import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.party.Party;

public final class PartyDuelInvite extends DuelInvite<Party> {

    public PartyDuelInvite(Party sender, Party target, KitType kitTypes) {
        super(sender, target, kitTypes);
    }

}