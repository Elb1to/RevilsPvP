package com.qrakn.morpheus.game.task

import com.qrakn.morpheus.game.Game
import com.qrakn.morpheus.game.GameState
import com.qrakn.morpheus.game.bukkit.event.GameStateChangeEvent
import eu.revils.revilspvp.RevilsPvP
import eu.revils.revilspvp.arena.Arena
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.concurrent.TimeUnit

class GameStartTask(private val plugin: JavaPlugin, game: Game) {

    private val startsAt = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(45) - 5
    private val interval = 15

    init {
        val arena: Optional<Arena> = RevilsPvP.getInstance().arenaHandler.allocateUnusedArena {it.event == game.event && it.isEnabled}

        if (arena.isPresent) {
            Bukkit.getPluginManager().callEvent(GameStateChangeEvent(game, GameState.STARTING))
            game.startingAt = startsAt
            game.arena = arena.get()
            Task(game).runTaskTimer(plugin, 0, interval * 20L)
        } else {
            Bukkit.getPluginManager().callEvent(GameStateChangeEvent(game, GameState.ENDED))
            for (player in Bukkit.getOnlinePlayers()) {
                if (player.isOp) {
                    player.sendMessage(ChatColor.RED.toString() + "Failed to start " + game.event.getName() + " due to lack of an arena.")
                }
            }
        }
    }

    inner class Task(private val game: Game) : BukkitRunnable() {

        override fun run() {

            if (startsAt <= System.currentTimeMillis() || game.players.size == game.getMaxPlayers()) {
                object: BukkitRunnable() {
                    override fun run() {
                        game.start()
                    }
                }.runTask(plugin)
                cancel()
                return
            }

            for (player in Bukkit.getOnlinePlayers()) {
                player.sendMessage(arrayOf("",
                        ChatColor.WHITE.toString() + " ███████",
                        ChatColor.WHITE.toString() + " █" + ChatColor.DARK_AQUA + "█████" + ChatColor.WHITE + "█" + ChatColor.AQUA + " Event" + ChatColor.GRAY + ": " + ChatColor.WHITE + "${game.event.getName()}",
                        ChatColor.WHITE.toString() + " █" + ChatColor.DARK_AQUA + "█" + ChatColor.WHITE + "█████" + ChatColor.AQUA + " Hoster" + ChatColor.GRAY + ": " + ChatColor.WHITE + game.host.displayName,
                        ChatColor.WHITE.toString() + " █" + ChatColor.DARK_AQUA + "████" + ChatColor.WHITE + "██" + ChatColor.AQUA + " Starting in" + ChatColor.GRAY + ": " + ChatColor.WHITE + (formatIntoDetailedString(((startsAt + 500 - System.currentTimeMillis()) / 1000).toInt())),
                        ChatColor.WHITE.toString() + " █" + ChatColor.DARK_AQUA + "█" + ChatColor.WHITE + "█████",
                        ChatColor.WHITE.toString() + " █" + ChatColor.DARK_AQUA + "█████" + ChatColor.WHITE + "█" + ChatColor.YELLOW + " Join with the" + ChatColor.DARK_GREEN + " Emerald " + ChatColor.YELLOW + "in you hotbar.",
                        ChatColor.WHITE.toString() + " ███████",
                        "")
                )
            }
        }

        private fun formatIntoDetailedString(secs: Int): String {
            return if (secs == 0) {
                "0 seconds"
            } else {
                val remainder = secs % 86400
                val days = secs / 86400
                val hours = remainder / 3600
                val minutes = remainder / 60 - hours * 60
                val seconds = remainder % 3600 - minutes * 60
                val fDays = if (days > 0) " " + days + " day" + (if (days > 1) "s" else "") else ""
                val fHours = if (hours > 0) " " + hours + " hour" + (if (hours > 1) "s" else "") else ""
                val fMinutes = if (minutes > 0) " " + minutes + " minute" + (if (minutes > 1) "s" else "") else ""
                val fSeconds = if (seconds > 0) " " + seconds + " second" + (if (seconds > 1) "s" else "") else ""
                (fDays + fHours + fMinutes + fSeconds).trim { it <= ' ' }
            }
        }
    }

}