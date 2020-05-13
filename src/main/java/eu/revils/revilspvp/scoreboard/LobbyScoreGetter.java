package eu.revils.revilspvp.scoreboard;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;


import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.party.PartyHandler;
import eu.revils.revilspvp.kt.util.TimeUtils;
import eu.revils.revilspvp.tournament.Tournament;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.revils.revilspvp.elo.EloHandler;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.queue.MatchQueue;
import eu.revils.revilspvp.queue.MatchQueueEntry;
import eu.revils.revilspvp.queue.QueueHandler;
import net.frozenorb.qlib.util.LinkedList;

final class LobbyScoreGetter implements BiConsumer<Player, LinkedList<String>> {

    @Override
    public void accept(Player player, LinkedList<String> scores) {
        Optional<UUID> followingOpt = RevilsPvP.getInstance().getFollowHandler().getFollowing(player);
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
        PartyHandler partyHandler = RevilsPvP.getInstance().getPartyHandler();
        QueueHandler queueHandler = RevilsPvP.getInstance().getQueueHandler();
        EloHandler eloHandler = RevilsPvP.getInstance().getEloHandler();

        scores.add("&fOnline&7: &a" + RevilsPvP.getInstance().getCache().getOnlineCount());
        scores.add("&fPlaying&7: &a" + RevilsPvP.getInstance().getCache().getFightsCount());

        Party playerParty = partyHandler.getParty(player);
        if (playerParty != null) {
            int size = playerParty.getMembers().size();
            scores.add(ChatColor.GRAY + RevilsPvP.getInstance().getUuidCache().name(playerParty.getLeader()) + ChatColor.WHITE + "'s Party&7: &a" + size);
        }

        // this definitely can be a .ifPresent, however creating the new lambda that often
        // was causing some performance issues, so we do this less pretty (but more efficent)
        // check (we can't define the lambda up top and reference because we reference the
        // scores variable)
        if (followingOpt.isPresent()) {
            Player following = Bukkit.getPlayer(followingOpt.get());
            scores.add("&fFollowing&7: *&a" + following.getName());

            if (player.hasPermission("basic.staff")) {
                MatchQueueEntry targetEntry = getQueueEntry(following);

                if (targetEntry != null) {
                    MatchQueue queue = targetEntry.getQueue();

                    scores.add("&bTarget Queue&6:");
                    scores.add(" &7▪ &e" + (queue.isRanked() ? "Ranked" : "Unranked") + " " + queue.getKitType().getDisplayName());
                }
            }
        }

        MatchQueueEntry entry = getQueueEntry(player);

        if (entry != null) {
            String waitTimeFormatted = TimeUtils.formatIntoMMSS(entry.getWaitSeconds());
            MatchQueue queue = entry.getQueue();

            scores.add("");
            scores.add("&fQueuing for:");
            scores.add(ChatColor.AQUA.toString() + (queue.isRanked() ? "Ranked" : "Unranked") + " " + queue.getKitType().getDisplayName());

            if (queue.isRanked()) {
                int elo = eloHandler.getElo(entry.getMembers(), queue.getKitType());
                int window = entry.getWaitSeconds() * QueueHandler.RANKED_WINDOW_GROWTH_PER_SECOND;

                scores.add("&fSearching range:");
                scores.add(ChatColor.AQUA.toString() + Math.max(0, elo - window) + " - " + (elo + window));
            }
        }

        if (player.hasMetadata("ModMode")) {
            scores.add(ChatColor.GRAY.toString() + ChatColor.BOLD + "Silent Mode Active!");
        }

        Tournament tournament = RevilsPvP.getInstance().getTournamentHandler().getTournament();
        if (tournament != null) {
            scores.add("");
            scores.add("&b&lTournament&7:");

            if (tournament.getStage() == Tournament.TournamentStage.WAITING_FOR_TEAMS) {
                int teamSize = tournament.getRequiredPartySize();
                scores.add(" &7▪ &fKit&7: &a" + tournament.getType().getDisplayName());
                scores.add(" &7▪ &fTeam Size&7: &a" + teamSize + "v" + teamSize);
                int multiplier = teamSize < 3 ? teamSize : 1;
                scores.add(" &7▪ &f" + (teamSize < 3 ? "Players"  : "Teams") + "&7: &a" + (tournament.getActiveParties().size() * multiplier + "/" + tournament.getRequiredPartiesToStart() * multiplier));
            } else if (tournament.getStage() == Tournament.TournamentStage.COUNTDOWN) {
                if (tournament.getCurrentRound() == 0) {
                    scores.add(" &7▪ &eBegins in &b" + tournament.getBeginNextRoundIn() + "s&e!");
                } else {
                    scores.add(" &7▪ &fRound &d#" + (tournament.getCurrentRound() + 1));
                    scores.add(" &7▪ &eBegins in &b" + tournament.getBeginNextRoundIn() + "s&e!");
                }
            } else if (tournament.getStage() == Tournament.TournamentStage.IN_PROGRESS) {
                scores.add(" &7▪ &fRound&7: &a#" + tournament.getCurrentRound());

                int teamSize = tournament.getRequiredPartySize();
                int multiplier = teamSize < 3 ? teamSize : 1;

                scores.add(" &7▪ &f" + (teamSize < 3 ? "Players" : "Teams") + "&7: &a" + tournament.getActiveParties().size() * multiplier + "/" + tournament.getRequiredPartiesToStart() * multiplier);
                scores.add(" &7▪ &fDuration&7: &a" + TimeUtils.formatIntoMMSS((int) (System.currentTimeMillis() - tournament.getRoundStartedAt()) / 1000));
            }
        }
    }

    private MatchQueueEntry getQueueEntry(Player player) {
        PartyHandler partyHandler = RevilsPvP.getInstance().getPartyHandler();
        QueueHandler queueHandler = RevilsPvP.getInstance().getQueueHandler();

        Party playerParty = partyHandler.getParty(player);
        if (playerParty != null) {
            return queueHandler.getQueueEntry(playerParty);
        } else {
            return queueHandler.getQueueEntry(player.getUniqueId());
        }
    }

}