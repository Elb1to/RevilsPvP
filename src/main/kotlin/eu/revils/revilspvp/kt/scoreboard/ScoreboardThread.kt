package eu.revils.revilspvp.kt.scoreboard

import eu.revils.revilspvp.RevilsPvP

class ScoreboardThread : Thread("stark - Scoreboard Thread") {

    init {
        this.isDaemon = true
    }

    override fun run() {
        while (true) {
            for (online in RevilsPvP.getInstance().server.onlinePlayers) {
                try {
                    RevilsPvP.getInstance().scoreboardEngine.updateScoreboard(online)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            try {
                sleep(RevilsPvP.getInstance().scoreboardEngine.updateInterval * 50L)
            } catch (e2: InterruptedException) {
                e2.printStackTrace()
            }

        }
    }

}