package eu.revils.revilspvp.elo.listener;

import com.google.common.collect.ImmutableSet;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.party.event.PartyCreateEvent;
import eu.revils.revilspvp.party.event.PartyMemberJoinEvent;
import eu.revils.revilspvp.party.event.PartyMemberKickEvent;
import eu.revils.revilspvp.party.event.PartyMemberLeaveEvent;
import eu.revils.revilspvp.elo.EloHandler;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;
import java.util.UUID;

// TODO: Remove old data!
public final class EloLoadListener implements Listener {

    private final EloHandler eloHandler;

    public EloLoadListener(EloHandler eloHandler) {
        this.eloHandler = eloHandler;
    }

    @EventHandler(priority = EventPriority.LOWEST) // LOWEST runs first
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        Set<UUID> playerSet = ImmutableSet.of(event.getUniqueId());
        eloHandler.loadElo(playerSet);
    }

    @EventHandler(priority = EventPriority.MONITOR) // MONITOR runs last
    public void onPlayerQuit(PlayerQuitEvent event) {
        Set<UUID> playerSet = ImmutableSet.of(event.getPlayer().getUniqueId());
        eloHandler.unloadElo(playerSet);
    }

    @EventHandler
    public void onPartyCreate(PartyCreateEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(RevilsPvP.getInstance(), () -> {
            eloHandler.loadElo(event.getParty().getMembers());
        });
    }

    @EventHandler
    public void onPartyMemberJoin(PartyMemberJoinEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(RevilsPvP.getInstance(), () -> {
            eloHandler.loadElo(event.getParty().getMembers());
        });
    }

    @EventHandler
    public void onPartyMemberKick(PartyMemberKickEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(RevilsPvP.getInstance(), () -> {
            eloHandler.loadElo(event.getParty().getMembers());
        });
    }

    @EventHandler
    public void onPartyMemberLeave(PartyMemberLeaveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(RevilsPvP.getInstance(), () -> {
            // calling load will overwrite the existing data for the party (which we want)
            eloHandler.loadElo(event.getParty().getMembers());
        });
    }

}