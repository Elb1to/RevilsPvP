package eu.revils.revilspvp.match.listener;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public final class MatchEnderPearlDamageListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && event.getEntity() instanceof Player && event.getDamager() instanceof EnderPearl) {
            Player player = (Player) event.getEntity();
            MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
            Match match = matchHandler.getMatchPlaying(player);

            if (match != null && !match.getKitType().isPearlDamage()) {
                event.setCancelled(true);
            }
        }
    }

}