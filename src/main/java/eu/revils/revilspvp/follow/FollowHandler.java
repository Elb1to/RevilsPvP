package eu.revils.revilspvp.follow;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.follow.listener.FollowGeneralListener;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.match.MatchState;
import eu.revils.revilspvp.util.InventoryUtils;
import eu.revils.revilspvp.util.VisibilityUtils;

import net.frozenorb.qlib.nametag.FrozenNametagHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class FollowHandler {

    // (follower -> target)
    private final Map<UUID, UUID> followingData = new ConcurrentHashMap<>();

    public FollowHandler() {
        Bukkit.getPluginManager().registerEvents(new FollowGeneralListener(this), RevilsPvP.getInstance());
    }

    public Optional<UUID> getFollowing(Player player) {
        return Optional.ofNullable(followingData.get(player.getUniqueId()));
    }

    public void startFollowing(Player player, Player target) {
        followingData.put(player.getUniqueId(), target.getUniqueId());
        player.sendMessage(ChatColor.YELLOW + "Now following " + ChatColor.LIGHT_PURPLE + target.getName() + ChatColor.YELLOW + ", exit with /unfollow.");

        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
        Match targetMatch = matchHandler.getMatchPlayingOrSpectating(target);

        if (targetMatch != null && targetMatch.getState() != MatchState.ENDING) {
            targetMatch.addSpectator(player, target);
        } else {
            InventoryUtils.resetInventoryDelayed(player);
            VisibilityUtils.updateVisibility(player);
            FrozenNametagHandler.reloadOthersFor(player);

            player.teleport(target);
        }
    }

    public void stopFollowing(Player player) {
        UUID prevTarget = followingData.remove(player.getUniqueId());

        if (prevTarget != null) {
            player.sendMessage(ChatColor.YELLOW + "Stopped following " + ChatColor.LIGHT_PURPLE + RevilsPvP.getInstance().getUuidCache().name(prevTarget) + ChatColor.YELLOW + ".");
            InventoryUtils.resetInventoryDelayed(player);
            VisibilityUtils.updateVisibility(player);
            FrozenNametagHandler.reloadOthersFor(player);
        }
    }

    public Set<UUID> getFollowers(Player player) {
        Set<UUID> followers = new HashSet<>();

        followingData.forEach((follower, followed) -> {
            if (followed == player.getUniqueId()) {
                followers.add(follower);
            }
        });

        return followers;
    }

}