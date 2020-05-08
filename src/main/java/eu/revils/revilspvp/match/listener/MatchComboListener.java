package eu.revils.revilspvp.match.listener;

import java.util.Objects;

import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.event.MatchStartEvent;
import net.hylist.knockback.KnockbackProfile;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.SpigotConfig;

public class MatchComboListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStart(MatchStartEvent event) {
        Match match = event.getMatch();

        KnockbackProfile knockbackProfile = match.getKitType().getId().contains("COMBO") ? SpigotConfig.getKbProfileByName("Combo") : match.getKitType().getId().contains("SUMO") ? SpigotConfig.getKbProfileByName("Sumo") : match.getKitType().getId().contains("BUILDUHC") ? SpigotConfig.getKbProfileByName("BuildUHC") : SpigotConfig.getKbProfileByName("Default");
        match.getTeams().forEach(team -> team.getAliveMembers().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(p -> p.setKbProfile(knockbackProfile)));

        int noDamageTicks = match.getKitType().getId().contains("COMBO") ? 3 : 20;
        match.getTeams().forEach(team -> team.getAliveMembers().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(p -> p.setMaximumNoDamageTicks(noDamageTicks)));
    }
}