package eu.revils.revilspvp.kt.tab

import eu.revils.revilspvp.RevilsPvP
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.EventHandler
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.event.player.PlayerJoinEvent

class TabListeners : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        object : BukkitRunnable() {
            override fun run() {
                RevilsPvP.getInstance().tabEngine.addPlayer(event.player)
            }
        }.runTaskLater(RevilsPvP.getInstance(), 10L)
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {
        RevilsPvP.getInstance().tabEngine.removePlayer(event.player)
        TabLayout.remove(event.player)
    }

}