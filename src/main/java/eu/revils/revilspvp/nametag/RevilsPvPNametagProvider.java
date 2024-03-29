package eu.revils.revilspvp.nametag;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.follow.FollowHandler;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.match.MatchTeam;
import eu.revils.revilspvp.pvpclasses.pvpclasses.ArcherClass;
import net.frozenorb.qlib.nametag.NametagInfo;
import net.frozenorb.qlib.nametag.NametagProvider;
import net.mineaus.lunar.api.LunarClientAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class RevilsPvPNametagProvider extends NametagProvider {

    public RevilsPvPNametagProvider() {
        super("RevilsPvP Provider", 5);
    }

    @Override
    public NametagInfo fetchNametag(Player toRefresh, Player refreshFor) {
        ChatColor prefixColor = getNameColor(toRefresh, refreshFor);
        return createNametag(prefixColor.toString(), "");
    }

    /*private static String getPartyTeammate(Player lcAPIteam) {
        LunarClientAPI.INSTANCE().getUserManager().getPlayerData(lcAPIteam.getUniqueId()).isLunarClient();
    }*/

    public static ChatColor getNameColor(Player toRefresh, Player refreshFor) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

        if (matchHandler.isPlayingOrSpectatingMatch(toRefresh)) {
            return getNameColorMatch(toRefresh, refreshFor);
        } else {
            return getNameColorLobby(toRefresh, refreshFor);
        }
    }

    private static ChatColor getNameColorMatch(Player toRefresh, Player refreshFor) {
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

        Match toRefreshMatch = matchHandler.getMatchPlayingOrSpectating(toRefresh);
        MatchTeam toRefreshTeam = toRefreshMatch.getTeam(toRefresh.getUniqueId());

        // they're a spectator, so we see them as gray
        if (toRefreshTeam == null) {
            return ChatColor.GRAY;
        }

        MatchTeam refreshForTeam = toRefreshMatch.getTeam(refreshFor.getUniqueId());

        // if we can't find a current team, check if they have any
        // previously teams we can use for this
        if (refreshForTeam == null) {
            refreshForTeam = toRefreshMatch.getPreviousTeam(refreshFor.getUniqueId());
        }

        // if we were/are both on teams display a friendly/enemy color
        if (refreshForTeam != null) {
            if (toRefreshTeam == refreshForTeam) {
                return ChatColor.GREEN;
            } else {
                if (ArcherClass.getMarkedPlayers().containsKey(toRefresh.getName()) && System.currentTimeMillis() < ArcherClass.getMarkedPlayers().get(toRefresh.getName())) {
                    return ChatColor.YELLOW;
                }
                return ChatColor.RED;
            }
        }

        // if we're a spectator just display standard colors
        List<MatchTeam> teams = toRefreshMatch.getTeams();

        // we have predefined colors for 'normal' matches
        if (teams.size() == 2) {
            // team 1 = LIGHT_PURPLE, team 2 = AQUA
            if (toRefreshTeam == teams.get(0)) {
                return ChatColor.LIGHT_PURPLE;
            } else {
                return ChatColor.AQUA;
            }
        } else {
            // we don't have colors defined for larger matches
            // everyone is just red for spectators
            return ChatColor.RED;
        }
    }

    private static ChatColor getNameColorLobby(Player toRefresh, Player refreshFor) {
        FollowHandler followHandler = RevilsPvP.getInstance().getFollowHandler();

        Optional<UUID> following = followHandler.getFollowing(refreshFor);
        boolean refreshForFollowingTarget = following.isPresent() && following.get().equals(toRefresh.getUniqueId());

        if (refreshForFollowingTarget) {
            return ChatColor.AQUA;
        } else {
            return ChatColor.GREEN;
        }
    }

}