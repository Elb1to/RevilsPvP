package eu.revils.revilspvp.duel.command;

import com.google.common.collect.ImmutableList;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.RevilsPvPLang;
import eu.revils.revilspvp.duel.PlayerDuelInvite;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.match.MatchTeam;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.party.PartyHandler;
import eu.revils.revilspvp.validation.RevilsPvPValidation;
import eu.revils.revilspvp.duel.DuelHandler;
import eu.revils.revilspvp.duel.DuelInvite;
import eu.revils.revilspvp.duel.PartyDuelInvite;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class AcceptCommand {

    @Command(names = {"accept"}, permission = "")
    public static void accept(Player sender, @Param(name = "player") Player target) {
        if (sender == target) {
            sender.sendMessage(ChatColor.RED + "You can't accept a duel from yourself!");
            return;
        }

        PartyHandler partyHandler = RevilsPvP.getInstance().getPartyHandler();
        DuelHandler duelHandler = RevilsPvP.getInstance().getDuelHandler();

        Party senderParty = partyHandler.getParty(sender);
        Party targetParty = partyHandler.getParty(target);

        if (senderParty != null && targetParty != null) {
            // party accepting from party (legal)
            PartyDuelInvite invite = duelHandler.findInvite(targetParty, senderParty);

            if (invite != null) {
                acceptParty(sender, senderParty, targetParty, invite);
            } else {
                // we grab the leader's name as the member targeted might not be the leader
                String leaderName = RevilsPvP.getInstance().getUuidCache().name(targetParty.getLeader());
                sender.sendMessage(ChatColor.RED + "Your party doesn't have a duel invite from " + leaderName + "'s party.");
            }
        } else if (senderParty == null && targetParty == null) {
            // player accepting from player (legal)
            PlayerDuelInvite invite = duelHandler.findInvite(target, sender);

            if (invite != null) {
                acceptPlayer(sender, target, invite);
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have a duel invite from " + target.getName() + ".");
            }
        } else if (senderParty == null) {
            // player accepting from party (illegal)
            sender.sendMessage(ChatColor.RED + "You don't have a duel invite from " + target.getName() + ".");
        } else {
            // party accepting from player (illegal)
            sender.sendMessage(ChatColor.RED + "Your party doesn't have a duel invite from " + target.getName() + "'s party.");
        }
    }

    private static void acceptParty(Player sender, Party senderParty, Party targetParty, DuelInvite invite) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
        DuelHandler duelHandler = RevilsPvP.getInstance().getDuelHandler();

        if (!senderParty.isLeader(sender.getUniqueId())) {
            sender.sendMessage(RevilsPvPLang.NOT_LEADER_OF_PARTY);
            return;
        }

        if (!RevilsPvPValidation.canAcceptDuel(senderParty, targetParty, sender)) {
            return;
        }

        Match match = matchHandler.startMatch(
                ImmutableList.of(new MatchTeam(senderParty.getMembers()), new MatchTeam(targetParty.getMembers())),
                invite.getKitType(),
                false,
                true // see Match#allowRematches
        );

        if (match != null) {
            // only remove invite if successful
            duelHandler.removeInvite(invite);
        } else {
            senderParty.message(RevilsPvPLang.ERROR_WHILE_STARTING_MATCH);
            targetParty.message(RevilsPvPLang.ERROR_WHILE_STARTING_MATCH);
        }
    }

    private static void acceptPlayer(Player sender, Player target, DuelInvite invite) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
        DuelHandler duelHandler = RevilsPvP.getInstance().getDuelHandler();

        if (!RevilsPvPValidation.canAcceptDuel(sender, target)) {
            return;
        }

        Match match = matchHandler.startMatch(
                ImmutableList.of(new MatchTeam(sender.getUniqueId()), new MatchTeam(target.getUniqueId())),
                invite.getKitType(),
                false,
                true // see Match#allowRematches
        );

        if (match != null) {
            // only remove invite if successful
            duelHandler.removeInvite(invite);
        } else {
            sender.sendMessage(RevilsPvPLang.ERROR_WHILE_STARTING_MATCH);
            target.sendMessage(RevilsPvPLang.ERROR_WHILE_STARTING_MATCH);
        }
    }

}