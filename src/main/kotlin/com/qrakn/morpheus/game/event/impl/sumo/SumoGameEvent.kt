package com.qrakn.morpheus.game.event.impl.sumo

import com.qrakn.morpheus.game.Game
import com.qrakn.morpheus.game.GameState
import com.qrakn.morpheus.game.event.GameEvent
import com.qrakn.morpheus.game.event.GameEventLogic
import com.qrakn.morpheus.game.event.impl.brackets.BracketsGameEvent
import com.qrakn.morpheus.game.parameter.GameParameter
import com.qrakn.morpheus.game.util.team.GameTeamSizeParameter
import eu.revils.revilspvp.RevilsPvPLang
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta

object SumoGameEvent : GameEvent {

    private const val NAME = "Sumo"
    private const val PERMISSION = "com.qrakn.morpheus.host.sumo"
    private const val DESCRIPTION = "Knock people off the sumo platform."

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
        return ItemStack(Material.LEASH)
    }

    override fun canStart(game: Game): Boolean {
        if (game.getParameter(GameTeamSizeParameter.Duos.javaClass) != null) {
            return game.players.size >= 4
        }

        return game.players.size >= 2
    }

    override fun getLogic(game: Game): GameEventLogic {
        return SumoGameEventLogic(game)
    }

    override fun getScoreboardScores(player: Player, game: Game): List<String> {
        val toReturn = ArrayList<String>()
        val logic = game.logic as SumoGameEventLogic
        var name = NAME

        if (game.getParameter(GameTeamSizeParameter.Duos.javaClass) != null) {
            name = "2v2 $name"
        }

        toReturn.add("&fEvent&7: &a$name")
        toReturn.add("&fPlayers&7: &a${logic.getPlayersLeft()}/${game.players.size}")

        if (game.state == GameState.RUNNING) {
            toReturn.add("&fRound&7: &a#${logic.getRound()}")
            if (game.getParameter(GameTeamSizeParameter.Duos.javaClass) == null) {
                val fighter = logic.getNextParticipant(null)
                val opponent = logic.getNextParticipant(fighter)

                if (opponent != null && fighter != null) {
                    toReturn.add("&7")
                    toReturn.add("&bCurrent Match&7:")
                    toReturn.add("&f" + fighter.getName() + "&7 vs. &f" + opponent.getName())
                }
            }
        }
        return toReturn
    }

    override fun getNameTag(game: Game, player: Player, viewer: Player): String {
        return BracketsGameEvent.getNameTag(game, player, viewer)
    }

    override fun getListeners(): List<Listener> {
        return arrayListOf(SumoGameEventListeners())
    }

    override fun getParameters(): List<GameParameter> {
        return listOf(GameTeamSizeParameter)
    }

    override fun getLobbyItems(): List<ItemStack> {
        return listOf()
    }

}