package eu.revils.revilspvp.tab;

import com.google.common.collect.Sets;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.elo.EloHandler;
import eu.revils.revilspvp.kt.tab.TabLayout;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;

final class LobbyLayoutProvider implements BiConsumer<Player, TabLayout> {

    @Override
    public void accept(Player player, TabLayout tabLayout) {
        Party party = RevilsPvP.getInstance().getPartyHandler().getParty(player);
        EloHandler eloHandler = RevilsPvP.getInstance().getEloHandler();

        rankings: {
            tabLayout.set(1, 3, RevilsPvP.getInstance().getDominantColor().toString() + ChatColor.BOLD + "Your Rankings");

            int x = 0;
            int y = 4;

            for (KitType kitType : KitType.getAllTypes()) {
                if (kitType.isHidden() || !kitType.isSupportsRanked()) {
                    continue;
                }

                tabLayout.set(x++, y, ChatColor.GRAY + kitType.getDisplayName() + " - " + eloHandler.getElo(player, kitType));

                if (x == 3) {
                    x = 0;
                    y++;
                }
            }
        }

        party: {
            if (party == null) {
                return;
            }

            tabLayout.set(1, 8, ChatColor.GOLD.toString() + ChatColor.BOLD + "Your Party");

            int x = 0;
            int y = 9;

            for (UUID member : getOrderedMembers(player, party)) {
                int ping = RevilsPvPLayoutProvider.getPingOrDefault(member);
                String suffix = member == party.getLeader() ? ChatColor.GRAY + "*" : "";
                String displayName = ChatColor.YELLOW + RevilsPvP.getInstance().getUuidCache().name(member) + suffix;

                tabLayout.set(x++, y, displayName, ping);

                if (x == 3 && y == RevilsPvPLayoutProvider.MAX_TAB_Y) {
                    break;
                }

                if (x == 3) {
                    x = 0;
                    y++;
                }
            }
        }
    }

    // player first, leader next, then all other members
    private Set<UUID> getOrderedMembers(Player viewer, Party party) {
        Set<UUID> orderedMembers = Sets.newSetFromMap(new LinkedHashMap<>());
        UUID leader = party.getLeader();

        orderedMembers.add(viewer.getUniqueId());

        // if they're the leader we don't display them twice
        if (viewer.getUniqueId() != leader) {
            orderedMembers.add(leader);
        }

        for (UUID member : party.getMembers()) {
            // don't display the leader or the viewer again
            if (member == leader || member == viewer.getUniqueId()) {
                continue;
            }

            orderedMembers.add(member);
        }

        return orderedMembers;
    }

}