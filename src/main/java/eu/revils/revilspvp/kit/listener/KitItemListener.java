package eu.revils.revilspvp.kit.listener;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kit.KitItems;
import eu.revils.revilspvp.kit.menu.kits.KitsMenu;
import eu.revils.revilspvp.kittype.menu.select.SelectKitTypeMenu;
import eu.revils.revilspvp.lobby.LobbyHandler;
import eu.revils.revilspvp.util.ItemListener;

public final class KitItemListener extends ItemListener {

    public KitItemListener() {
        addHandler(KitItems.OPEN_EDITOR_ITEM, player -> {
            LobbyHandler lobbyHandler = RevilsPvP.getInstance().getLobbyHandler();

            if (lobbyHandler.isInLobby(player)) {
                new SelectKitTypeMenu(kitType -> {
                    new KitsMenu(kitType).openMenu(player);
                }, "Select a kit to edit...").openMenu(player);
            }
        });
    }

}