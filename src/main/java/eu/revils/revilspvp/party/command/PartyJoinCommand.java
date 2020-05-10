package eu.revils.revilspvp.party.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.party.PartyHandler;
import eu.revils.revilspvp.party.PartyInvite;
import eu.revils.revilspvp.tournament.TournamentHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PartyJoinCommand {

    // default value for password parameter used to detect that password
    // wasn't provided. No Optional<String> :(
    private static final String NO_PASSWORD_PROVIDED = "revils_best_eu";

    @Command(names = {"party join", "p join", "t join", "team join", "f join"}, permission = "")
    public static void partyJoin(Player sender, @Param(name = "player") Player target, @Param(name = "password", defaultValue = NO_PASSWORD_PROVIDED) String providedPassword) {
        TournamentHandler tournamentHandler = RevilsPvP.getInstance().getTournamentHandler();
        PartyHandler partyHandler = RevilsPvP.getInstance().getPartyHandler();
        Party targetParty = partyHandler.getParty(target);

        if (partyHandler.hasParty(sender)) {
            sender.sendMessage(ChatColor.RED + "You are already in a party. You must leave your current party first.");
            return;
        }

        /*if (!partyHandler.hasParty(sender)) {
            if (targetParty.getMembers().size() != tournamentHandler.getTournament().getRequiredPartySize()) {
                sender.sendMessage(ChatColor.RED + "The party you are trying to join exceeds the required members to join the tournament.");
                return;
            }
        }*/

        if (targetParty == null) {
            sender.sendMessage(ChatColor.RED + target.getName() + " is not in a party.");
            return;
        }

        PartyInvite invite = targetParty.getInvite(sender.getUniqueId());

        switch (targetParty.getAccessRestriction()) {
            case PUBLIC:
                targetParty.join(sender);
                break;
            case INVITE_ONLY:
                if (invite != null) {
                    targetParty.join(sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have an invitation to this party.");
                }
                break;
            case PASSWORD:
                if (providedPassword.equals(NO_PASSWORD_PROVIDED) && invite == null) {
                    sender.sendMessage(ChatColor.RED + "You need the password or an invitation to join this party.");
                    sender.sendMessage(ChatColor.GRAY + "To join with a password, use " + ChatColor.YELLOW + "/party join " + target.getName() + " <password>");
                    return;
                }
                String correctPassword = targetParty.getPassword();
                if (invite == null && !correctPassword.equals(providedPassword)) {
                    sender.sendMessage(ChatColor.RED + "Invalid password.");
                } else {
                    targetParty.join(sender);
                }
                break;
            default:
                break;
        }
    }

}