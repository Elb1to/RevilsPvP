package eu.revils.revilspvp.kt.nametag

import org.bukkit.entity.Player

internal class NametagUpdate(var toRefresh: String, var refreshFor: String?) {
    constructor(toRefresh: Player): this(toRefresh.name, null)
    constructor(toRefresh: Player, refreshFor: Player): this(toRefresh.name, refreshFor.name)
}
