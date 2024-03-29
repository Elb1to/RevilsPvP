package eu.revils.revilspvp.match;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.follow.FollowHandler;
import eu.revils.revilspvp.lobby.LobbyItems;
import eu.revils.revilspvp.party.PartyHandler;
import eu.revils.revilspvp.setting.Setting;
import eu.revils.revilspvp.setting.SettingHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MatchUtils {

    public static void resetInventory(Player player) {
        SettingHandler settingHandler = RevilsPvP.getInstance().getSettingHandler();
        FollowHandler followHandler = RevilsPvP.getInstance().getFollowHandler();
        PartyHandler partyHandler = RevilsPvP.getInstance().getPartyHandler();
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

        Match match = matchHandler.getMatchSpectating(player);

        // because we lookup their match with getMatchSpectating this will also
        // return for players fighting in matches
        if (match == null) {
            return;
        }

        PlayerInventory inventory = player.getInventory();

        inventory.clear();
        inventory.setArmorContents(null);

        // don't give players who die (and cause the match to end)
        // a fire item, they'll be sent back to the lobby in a few seconds anyway
        if (match.getState() != MatchState.ENDING) {
            // if they've been on any team or are staff they'll be able to
            // use this item on at least 1 player. if they can't use it all
            // we just don't give it to them (UX purposes)
            boolean canViewInventories = player.hasPermission("revilspvp.inventory.all");

            if (!canViewInventories) {
                for (MatchTeam team : match.getTeams()) {
                    if (team.getAllMembers().contains(player.getUniqueId())) {
                        canViewInventories = true;
                        break;
                    }
                }
            }

            // fill inventory with spectator items
            if (settingHandler.getSetting(player, Setting.VIEW_OTHER_SPECTATORS)) {
                inventory.setItem(7, SpectatorItems.HIDE_SPECTATORS_ITEM);
            } else {
                inventory.setItem(7, SpectatorItems.SHOW_SPECTATORS_ITEM);
            }

            // this bit is correct; see SpectatorItems file for more
            if (partyHandler.hasParty(player)) {
                inventory.setItem(8, SpectatorItems.LEAVE_PARTY_ITEM);
            } else {
                inventory.setItem(8, SpectatorItems.RETURN_TO_LOBBY_ITEM);

                if (!followHandler.getFollowing(player).isPresent()) {
                    inventory.setItem(0, LobbyItems.SPECTATE_RANDOM_ITEM);
                    inventory.setItem(1, LobbyItems.SPECTATE_MENU_ITEM);
                }
            }
        }

        Bukkit.getScheduler().runTaskLater(RevilsPvP.getInstance(), player::updateInventory, 1L);
    }

}