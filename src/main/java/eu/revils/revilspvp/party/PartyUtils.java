package eu.revils.revilspvp.party;

import com.google.common.collect.ImmutableList;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kittype.menu.select.SelectKitTypeMenu;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchTeam;
import eu.revils.revilspvp.party.menu.oddmanout.OddManOutMenu;
import eu.revils.revilspvp.validation.RevilsPvPValidation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class PartyUtils {

    public static void startTeamSplit(Party party, Player initiator) {
        // will be called again but we fail fast if possible
        if (!RevilsPvPValidation.canStartTeamSplit(party, initiator)) {
            return;
        }

        new SelectKitTypeMenu(kitType -> {
            initiator.closeInventory();

            if (party.getMembers().size() % 2 == 0) {
                startTeamSplit(party, initiator, kitType, false);
            } else {
                new OddManOutMenu(oddManOut -> {
                    initiator.closeInventory();
                    startTeamSplit(party, initiator, kitType, oddManOut);
                }).openMenu(initiator);
            }
        }, "Start a Team Split...").openMenu(initiator);
    }

    public static void startTeamSplit(Party party, Player initiator, KitType kitType, boolean oddManOut) {
        if (!RevilsPvPValidation.canStartTeamSplit(party, initiator)) {
            return;
        }

        List<UUID> members = new ArrayList<>(party.getMembers());
        Collections.shuffle(members);

        Set<UUID> team1 = new HashSet<>();
        Set<UUID> team2 = new HashSet<>();
        Player spectator = null; // only can be one

        while (members.size() >= 2) {
            team1.add(members.remove(0));
            team2.add(members.remove(0));
        }

        if (!members.isEmpty()) {
            if (oddManOut) {
                spectator = Bukkit.getPlayer(members.remove(0));
                party.message(ChatColor.YELLOW + spectator.getName() + " was selected as the odd-man out.");
            } else {
                team1.add(members.remove(0));
            }
        }

        Match match = RevilsPvP.getInstance().getMatchHandler().startMatch(
            ImmutableList.of(
                new MatchTeam(team1),
                new MatchTeam(team2)
            ),
            kitType,
            false,
            false
        );

        if (match == null) {
            initiator.sendMessage(ChatColor.RED + "Failed to start team split.");
            return;
        }

        if (spectator != null) {
            match.addSpectator(spectator, null);
        }
    }

}