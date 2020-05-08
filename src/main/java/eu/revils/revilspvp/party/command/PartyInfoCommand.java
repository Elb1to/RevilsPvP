package eu.revils.revilspvp.party.command;

import com.google.common.base.Joiner;

import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.RevilsPvPLang;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.util.PatchedPlayerUtils;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.kt.command.data.parameter.Param;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.entity.Player;

public final class PartyInfoCommand {

    @Command(names = {"party info", "p info", "t info", "team info", "f info", "p i", "t i", "f i", "party i", "team i"}, permission = "")
    public static void partyInfo(Player sender, @Param(name = "player", defaultValue = "self") Player target) {
        Party party = RevilsPvP.getInstance().getPartyHandler().getParty(target);

        if (party == null) {
            if (sender == target) {
                sender.sendMessage(RevilsPvPLang.NOT_IN_PARTY);
            } else {
                sender.sendMessage(ChatColor.RED + target.getName() + " isn't in a party.");
            }

            return;
        }

        String leaderName = RevilsPvP.getInstance().getUuidCache().name(party.getLeader());
        int memberCount = party.getMembers().size();
        String members = Joiner.on(", ").join(PatchedPlayerUtils.mapToNames(party.getMembers()));

        sender.sendMessage(ChatColor.GRAY + RevilsPvPLang.LONG_LINE);

        switch (party.getAccessRestriction()) {
            case PUBLIC:
                sender.sendMessage(ChatColor.GREEN + "Your Party " + ChatColor.GOLD + "[" + ChatColor.RED + "Open" + ChatColor.GRAY + " - " + ChatColor.WHITE + party.getMembers().size() + ChatColor.GRAY + "/" + ChatColor.WHITE + "30" + ChatColor.GOLD + "]");
                break;
            case INVITE_ONLY:
                sender.sendMessage(ChatColor.GREEN + "Your Party " + ChatColor.GOLD + "[" + ChatColor.RED + "Invite-Only" + ChatColor.GRAY + " - " + ChatColor.WHITE + party.getMembers().size() + ChatColor.GRAY + "/" + ChatColor.WHITE + "30" + ChatColor.GOLD + "]");
                break;
            case PASSWORD:
                // leader can see password by hovering
                if (party.isLeader(sender.getUniqueId())) {
                    HoverEvent.Action showText = HoverEvent.Action.SHOW_TEXT;
                    BaseComponent[] passwordComponent = { new TextComponent(party.getPassword()) };

                    // Privacy: Password Protected [Hover for password]
                    ComponentBuilder builder = new ComponentBuilder(ChatColor.GREEN + "Your Party " + ChatColor.GOLD + "[").color(ChatColor.YELLOW);
                    builder.append("Password Protected ").color(ChatColor.RED);
                    builder.append("[Hover to Show]" + ChatColor.GRAY + " - " + ChatColor.WHITE + party.getMembers().size() + ChatColor.GRAY + "/" + ChatColor.WHITE + "30" + ChatColor.GOLD + "]").color(ChatColor.GRAY);
                    builder.event(new HoverEvent(showText, passwordComponent));

                    sender.spigot().sendMessage(builder.create());
                } else {
                }

                break;
            default:
                break;
        }

        sender.sendMessage(ChatColor.GRAY + " ▪ " + ChatColor.YELLOW + "Leader" + ChatColor.GRAY + ": " + ChatColor.GOLD + leaderName);
        sender.sendMessage(ChatColor.GRAY + " ▪ " + ChatColor.YELLOW + "Members " + ChatColor.GRAY + "(" + memberCount + ")" + ChatColor.GRAY + ": " + ChatColor.GOLD + members);
        sender.sendMessage(ChatColor.GRAY + RevilsPvPLang.LONG_LINE);
    }

}