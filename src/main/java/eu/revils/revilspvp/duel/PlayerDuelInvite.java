package eu.revils.revilspvp.duel;

import java.util.UUID;

import eu.revils.revilspvp.kittype.KitType;
import org.bukkit.entity.Player;

public final class PlayerDuelInvite extends DuelInvite<UUID> {

    public PlayerDuelInvite(Player sender, Player target, KitType kitType) {
        super(sender.getUniqueId(), target.getUniqueId(), kitType);
    }

}