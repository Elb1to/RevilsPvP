package eu.revils.revilspvp.kt.nametag

import eu.revils.revilspvp.RevilsPvP
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.MetadataValue
import org.bukkit.event.player.PlayerJoinEvent

internal class NametagListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.player.setMetadata("starkNametag-LoggedIn", FixedMetadataValue(RevilsPvP.getInstance(), true) as MetadataValue)
        RevilsPvP.getInstance().nametagEngine.initiatePlayer(event.player)
        RevilsPvP.getInstance().nametagEngine.reloadPlayer(event.player)
        RevilsPvP.getInstance().nametagEngine.reloadOthersFor(event.player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.player.removeMetadata("starkNametag-LoggedIn", RevilsPvP.getInstance())
        RevilsPvP.getInstance().nametagEngine.teamMap.remove(event.player.name)
    }
}