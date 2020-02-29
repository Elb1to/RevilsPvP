package eu.revils.revilspvp.kt.nametag.impl

import eu.revils.revilspvp.kt.nametag.NametagInfo
import eu.revils.revilspvp.kt.nametag.NametagProvider
import org.bukkit.entity.Player

class StarkNametagProvider : NametagProvider("Stark Provider", 1) {

    override fun fetchNametag(toRefresh: Player, refreshFor: Player): NametagInfo {
        return createNametag("", "")
    }

}