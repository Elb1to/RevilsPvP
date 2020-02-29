package eu.revils.revilspvp.kt.tab

import org.bukkit.entity.Player

interface LayoutProvider {

    fun provide(player: Player): TabLayout

}