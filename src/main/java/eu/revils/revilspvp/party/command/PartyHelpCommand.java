package eu.revils.revilspvp.party.command;

import com.google.common.collect.ImmutableList;

import eu.revils.revilspvp.RevilsPvPLang;
import eu.revils.revilspvp.kt.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public final class PartyHelpCommand {

    private static final List<String> HELP_MESSAGE = ImmutableList.of(
        ChatColor.GRAY + RevilsPvPLang.LONG_LINE,
        "§9Party Commands:",
        " §7▪ §e/party invite §7- Invite a player to join your party",
        " §7▪ §e/party leave §7- Leave your current party",
        " §7▪ §e/party accept [player] §7- Accept party invitation",
        " §7▪ §e/party info [player] §7- View the roster of the party",
        "",
        "§9Leader Commands:",
        " §7▪ §e/party kick <player> §7- Kick a player from your party",
        " §7▪ §e/party leader <player> §7- Transfer party leadership",
        " §7▪ §e/party disband §7 - Disbands party",
        " §7▪ §e/party lock §7 - Lock party from others joining",
        " §7▪ §e/party open §7 - Open party to others joining",
        " §7▪ §e/party password <password> §7 - Sets party password",
        "",
        "§9Other Help:",
        " §eTo use §dparty chat§e, prefix your message with the §7'§d@§7' §esign.",
        ChatColor.GRAY + RevilsPvPLang.LONG_LINE
    );

    @Command(names = {"party", "p", "t", "team", "f", "party help", "p help", "t help", "team help", "f help"}, permission = "")
    public static void party(Player sender) {
        HELP_MESSAGE.forEach(sender::sendMessage);
    }

}