package eu.revils.revilspvp.command;

import com.google.common.collect.ImmutableList;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.RevilsPvPLang;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.match.MatchHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Generic /help command, changes message sent based on if sender is playing in
 * or spectating a match.
 */
public final class HelpCommand {

    private static final List<String> HELP_MESSAGE_HEADER = ImmutableList.of(
            ChatColor.GRAY + RevilsPvPLang.LONG_LINE
    );

    private static final List<String> HELP_MESSAGE_LOBBY = ImmutableList.of(
            "§6Common Commands:",
            " §7▪ §e/duel <player> §7- Challenge a player to a duel",
            " §7▪ §e/party invite <player> §7- Invite a player to a party",
            "",
            "§6Other Commands:",
            " §7▪ §e/party help §7- Information on party commands",
            " §7▪ §e/report <player> <reason> §7- Report a player for violating the rules",
            " §7▪ §e/request <message> §7- Request assistance from a staff member"
    );

    private static final List<String> HELP_MESSAGE_MATCH = ImmutableList.of(
            "§6Common Commands:",
            " §7▪ §e/spectate <player> §7- Spectate a player in a match",
            " §7▪ §e/report <player> <reason> §7- Report a player for violating the rules",
            " §7▪ §e/request <message> §7- Request assistance from a staff member"
    );

    private static final List<String> HELP_MESSAGE_FOOTER = ImmutableList.of(
            "",
            "§6Server Information:",
            RevilsPvP.getInstance().getDominantColor() == ChatColor.YELLOW ? " §7▪ §eOfficial Discord §7- §6Revils.eu/discord" : "§eOfficial Discord §7- §6revils.eu/discord",
            RevilsPvP.getInstance().getDominantColor() == ChatColor.YELLOW ? " §7▪ §eStore §7- §6Revils.eu/store" : "§eStore §7- §6revils.eu/store",
            ChatColor.GRAY + RevilsPvPLang.LONG_LINE
    );

    @Command(names = {"help", "?", "halp", "helpme"}, permission = "")
    public static void help(Player sender) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

        HELP_MESSAGE_HEADER.forEach(sender::sendMessage);

        if (matchHandler.isPlayingOrSpectatingMatch(sender)) {
            HELP_MESSAGE_MATCH.forEach(sender::sendMessage);
        } else {
            HELP_MESSAGE_LOBBY.forEach(sender::sendMessage);
        }

        HELP_MESSAGE_FOOTER.forEach(sender::sendMessage);
    }

}
