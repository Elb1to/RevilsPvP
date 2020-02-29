package eu.revils.revilspvp.lobby.listener;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.party.event.PartyCreateEvent;
import eu.revils.revilspvp.party.event.PartyMemberJoinEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class LobbySpecModeListener implements Listener {

    @EventHandler
    public void onPartyMemberJoin(PartyMemberJoinEvent event) {
        RevilsPvP.getInstance().getLobbyHandler().setSpectatorMode(event.getMember(), false);
    }

    @EventHandler
    public void onPartyCreate(PartyCreateEvent event) {
        Player leader = Bukkit.getPlayer(event.getParty().getLeader());
        RevilsPvP.getInstance().getLobbyHandler().setSpectatorMode(leader, false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        RevilsPvP.getInstance().getLobbyHandler().setSpectatorMode(event.getPlayer(), false);
    }

}