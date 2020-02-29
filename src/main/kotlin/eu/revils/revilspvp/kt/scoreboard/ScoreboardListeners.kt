package eu.revils.revilspvp.kt.scoreboard

import eu.revils.revilspvp.RevilsPvP
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerJoinEvent

class ScoreboardListeners : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        RevilsPvP.getInstance().scoreboardEngine.create(event.player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        RevilsPvP.getInstance().scoreboardEngine.remove(event.player)
    }

}