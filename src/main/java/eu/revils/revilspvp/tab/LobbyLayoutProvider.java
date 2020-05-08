package eu.revils.revilspvp.tab;


import me.activated.core.plugin.AquaCoreAPI;
import net.frozenorb.qlib.tab.TabLayout;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

final class LobbyLayoutProvider implements BiConsumer<Player, TabLayout> {

    @Override
    public void accept(Player player, TabLayout tabLayout) {
        int x = 0;
        int y = 0;

        for (final Player online : Bukkit.getServer().getOnlinePlayers()) {
            ChatColor profile = AquaCoreAPI.INSTANCE.getPlayerData(online.getUniqueId()).getHighestRank().getColor();
            if (profile != null) {
                tabLayout.set(x, y, (profile + online.getName()), ((CraftPlayer) online).getHandle().ping);
                if (x++ < 2) {
                    continue;
                }
                x = 0;
                ++y;
            }
        }
    }
}