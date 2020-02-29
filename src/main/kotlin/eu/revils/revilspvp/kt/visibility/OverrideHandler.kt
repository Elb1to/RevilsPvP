package eu.revils.revilspvp.kt.visibility

import org.bukkit.entity.Player

interface OverrideHandler {
    fun getAction(toRefresh: Player, refreshFor: Player): OverrideAction
}