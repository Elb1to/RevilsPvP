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
        ChatColor.DARK_PURPLE + RevilsPvPLang.LONG_LINE,
        "§5§lPractice Help",
        ChatColor.DARK_PURPLE + RevilsPvPLang.LONG_LINE,
        "§7§lRemember: §eMost things are clickable!",
        ""
    );

    private static final List<String> HELP_MESSAGE_LOBBY = ImmutableList.of(
        "§5Common Commands:",
        "§e/duel <player> §7- Challenge a player to a duel",
        "§e/party invite <player> §7- Invite a player to a party",
        "",
        "§5Other Commands:",
        "§e/party help §7- Information on party commands",
        "§e/report <player> <reason> §7- Report a player for violating the rules",
        "§e/request <message> §7- Request assistance from a staff member"
    );

    private static final List<String> HELP_MESSAGE_MATCH = ImmutableList.of(
        "§5Common Commands:",
        "§e/spectate <player> §7- Spectate a player in a match",
        "§e/report <player> <reason> §7- Report a player for violating the rules",
        "§e/request <message> §7- Request assistance from a staff member"
    );

    private static final List<String> HELP_MESSAGE_FOOTER = ImmutableList.of(
        "",
        "§5Server Information:",
        RevilsPvP.getInstance().getDominantColor() == ChatColor.LIGHT_PURPLE ? "§eOfficial Teamspeak §7- §dts.MineHQ.com" : "§eOfficial Teamspeak §7- §dts.MineHQ.com",
        RevilsPvP.getInstance().getDominantColor() == ChatColor.LIGHT_PURPLE ? "§eOfficial Rules §7- §dwww.MineHQ.com/rules" : "§eOfficial Rules §7- §dwww.MineHQ.com/rules",
        RevilsPvP.getInstance().getDominantColor() == ChatColor.LIGHT_PURPLE ? "§eStore §7- §dwww.MineHQ.com/store" : "§eStore §7- §dwww.MineHQ.com/store",
     // "§ePractice Leaderboards §7- §dwww.MineHQ.com/stats/potpvp",
        ChatColor.DARK_PURPLE + RevilsPvPLang.LONG_LINE
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
