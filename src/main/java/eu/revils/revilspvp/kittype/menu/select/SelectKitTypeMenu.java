package eu.revils.revilspvp.kittype.menu.select;

import com.google.common.base.Preconditions;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kt.util.Callback;
import eu.revils.revilspvp.util.InventoryUtils;
import eu.revils.revilspvp.kt.menu.Button;
import eu.revils.revilspvp.kt.menu.Menu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class SelectKitTypeMenu extends Menu {

    private final boolean reset;
    private final Callback<KitType> callback;

    public SelectKitTypeMenu(Callback<KitType> callback, String title) {
        this(callback, true, title);
    }

    public SelectKitTypeMenu(Callback<KitType> callback, boolean reset, String title) {
        super(ChatColor.BLUE.toString() + ChatColor.BOLD + title);

        this.callback = Preconditions.checkNotNull(callback, "callback");
        this.reset = reset;
    }

    @Override
    public void onClose(Player player, boolean manualClose) {
        if (reset) {
            InventoryUtils.resetInventoryDelayed(player);
        }
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        int index = 0;

        for (KitType kitType : KitType.getAllTypes()) {
            if (!player.isOp() && kitType.isHidden()) {
                continue;
            }

            buttons.put(index++, new KitTypeButton(kitType, callback));
        }

        Party party = RevilsPvP.getInstance().getPartyHandler().getParty(player);
        if (party != null) {
            buttons.put(8, new KitTypeButton(KitType.teamFight, callback));
        }

        return buttons;
    }

}