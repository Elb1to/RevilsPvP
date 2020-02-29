
package eu.revils.revilspvp.scoreboard;

import java.util.LinkedList;
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

final class LobbyScoreGetter implements BiConsumer<Player, LinkedList<String>> {

    @Override
    public void accept(Player player, LinkedList<String> scores) {
        Optional<UUID> followingOpt = RevilsPvP.getInstance().getFollowHandler().getFollowing(player);
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
        PartyHandler partyHandler = RevilsPvP.getInstance().getPartyHandler();
        QueueHandler queueHandler = RevilsPvP.getInstance().getQueueHandler();
        EloHandler eloHandler = RevilsPvP.getInstance().getEloHandler();

        Party playerParty = partyHandler.getParty(player);
        if (playerParty != null) {
            int size = playerParty.getMembers().size();
            scores.add("&9Your Party: &f" + size);
        }

        scores.add("&eOnline: &f" + RevilsPvP.getInstance().getCache().getOnlineCount());
        scores.add("&dIn Fights: &f" + RevilsPvP.getInstance().getCache().getFightsCount());
        scores.add("&bIn Queues: &f" + RevilsPvP.getInstance().getCache().getQueuesCount());

        // this definitely can be a .ifPresent, however creating the new lambda that often
        // was causing some performance issues, so we do this less pretty (but more efficent)
        // check (we can't define the lambda up top and reference because we reference the
        // scores variable)
        if (followingOpt.isPresent()) {
            Player following = Bukkit.getPlayer(followingOpt.get());
            scores.add("&6Following: *&7" + following.getName());

            if (player.hasPermission("basic.staff")) {
                MatchQueueEntry targetEntry = getQueueEntry(following);

                if (targetEntry != null) {
                    MatchQueue queue = targetEntry.getQueue();

                    scores.add("&6Target queue:");
                    scores.add("&7" + (queue.isRanked() ? "Ranked" : "Unranked") + " " + queue.getKitType().getDisplayName());
                }
            }
        }

        MatchQueueEntry entry = getQueueEntry(player);

        if (entry != null) {
            String waitTimeFormatted = TimeUtils.formatIntoMMSS(entry.getWaitSeconds());
            MatchQueue queue = entry.getQueue();

            scores.add("&b&7&m--------------------");
            scores.add(queue.getKitType().getDisplayColor() + (queue.isRanked() ? "Ranked" : "Unranked") + " " + queue.getKitType().getDisplayName());
            scores.add("&6Time: *&f" + waitTimeFormatted);

            if (queue.isRanked()) {
                int elo = eloHandler.getElo(entry.getMembers(), queue.getKitType());
                int window = entry.getWaitSeconds() * QueueHandler.RANKED_WINDOW_GROWTH_PER_SECOND;

                scores.add("&6Search range: *&f" + Math.max(0, elo - window) + " - " + (elo + window));
            }
        }

        if (player.hasMetadata("ModMode")) {
            scores.add(ChatColor.GRAY.toString() + ChatColor.BOLD + "In Silent Mode");
        }

        Tournament tournament = RevilsPvP.getInstance().getTournamentHandler().getTournament();
        if (tournament != null) {
            scores.add("&7&m--------------------");
            scores.add("&6&lTournament");

            if (tournament.getStage() == Tournament.TournamentStage.WAITING_FOR_TEAMS) {
                int teamSize = tournament.getRequiredPartySize();
                scores.add("&cKit&7: " + tournament.getType().getDisplayName());
                scores.add("&cTeam Size&7: " + teamSize + "v" + teamSize);
                int multiplier = teamSize < 3 ? teamSize : 1;
                scores.add("&c" + (teamSize < 3 ? "Players"  : "Teams") + "&7: " + (tournament.getActiveParties().size() * multiplier + "/" + tournament.getRequiredPartiesToStart() * multiplier));
            } else if (tournament.getStage() == Tournament.TournamentStage.COUNTDOWN) {
                if (tournament.getCurrentRound() == 0) {
                    scores.add("&9");
                    scores.add("&7Begins in &c" + tournament.getBeginNextRoundIn() + "&7 second" + (tournament.getBeginNextRoundIn() == 1 ? "." : "s."));
                } else {
                    scores.add("&9");
                    scores.add("&c&lRound " + (tournament.getCurrentRound() + 1));
                    scores.add("&7Begins in &c" + tournament.getBeginNextRoundIn() + "&7 second" + (tournament.getBeginNextRoundIn() == 1 ? "." : "s."));
                }
            } else if (tournament.getStage() == Tournament.TournamentStage.IN_PROGRESS) {
                scores.add("&cRound&7: " + tournament.getCurrentRound());

                int teamSize = tournament.getRequiredPartySize();
                int multiplier = teamSize < 3 ? teamSize : 1;

                scores.add("&c" + (teamSize < 3 ? "Players" : "Teams") + "&7: " + tournament.getActiveParties().size() * multiplier + "/" + tournament.getRequiredPartiesToStart() * multiplier);
                scores.add("&6Duration&7: " + TimeUtils.formatIntoMMSS((int) (System.currentTimeMillis() - tournament.getRoundStartedAt()) / 1000));
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