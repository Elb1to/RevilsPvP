package eu.revils.revilspvp.kt.tab

import eu.revils.revilspvp.RevilsPvP
import org.bukkit.Bukkit

class TabThread : Thread("stark - Tab Thread") {

    private val protocolLib = Bukkit.getServer().pluginManager.getPlugin("ProtocolLib")

    init {
        this.isDaemon = true
    }

    override fun run() {
        while (RevilsPvP.getInstance().isEnabled && protocolLib != null && protocolLib.isEnabled) {
            for (online in RevilsPvP.getInstance().server.onlinePlayers) {
                try {
                    RevilsPvP.getInstance().tabEngine.updatePlayer(online)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            try {
                sleep(250L)
            } catch (e2: InterruptedException) {
                e2.printStackTrace()
            }

        }
    }

}