package com.qrakn.morpheus.game.event.impl.lms

import com.qrakn.morpheus.game.Game
import com.qrakn.morpheus.game.GameState
import com.qrakn.morpheus.game.event.GameEvent
import com.qrakn.morpheus.game.event.GameEventLogic
import com.qrakn.morpheus.game.util.team.GameTeamSizeParameter
import com.qrakn.morpheus.game.parameter.GameParameter
import eu.revils.revilspvp.RevilsPvPLang
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

object LastManStandingGameEvent : GameEvent {

    private const val NAME = "LMS"
    private const val PERMISSION = "com.qrakn.morpheus.host.lms"
    private const val DESCRIPTION = "Compete against other players to be the last man standing."

    override fun getName(): String {
        return NAME
    }

    override fun getPermission(): String {
        return PERMISSION
    }

    override fun getDescription(): String {
        return DESCRIPTION
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.TNT)
    }

    override fun canStart(game: Game): Boolean {
        if (game.getParameter(GameTeamSizeParameter.Duos.javaClass) != null) {
            return game.players.size >= 4
        }

        return game.players.size >= 2
    }

    override fun getLogic(game: Game): GameEventLogic {
        return LastManStandingGameEventLogic(game)
    }

    override fun getScoreboardScores(player: Player, game: Game): List<String> {
        val toReturn = ArrayList<String>()
        val logic = game.logic as LastManStandingGameEventLogic
        var name = NAME

        if (game.getParameter(GameTeamSizeParameter.Duos.javaClass) != null) {
            name = "2v2 $name"
        }

        toReturn.add("&fEvent&7: &a$name")
        toReturn.add("&fPlayers&7: &a${logic.getPlayersLeft()}/${game.players.size}")

        if (game.state == GameState.RUNNING) {
            toReturn.add("")
            toReturn.add("&fKills&7: &a${player.getStatistic(Statistic.PLAYER_KILLS)}")
        }
        return toReturn
    }

    override fun getNameTag(game: Game, player: Player, viewer: Player): String {
        return ChatColor.RED.toString()
    }

    override fun getListeners(): List<Listener> {
        return arrayListOf(LastManStandingGameEventListeners())
    }

    override fun getParameters(): List<GameParameter> {
        return listOf(GameTeamSizeParameter, LastManStandingGameKitParameter)
    }

    override fun getMaxInstances(): Int {
        return 5
    }

    override fun getLobbyItems(): List<ItemStack> {
        return listOf()
    }

}