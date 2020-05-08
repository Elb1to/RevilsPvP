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
        tabLayout.set(1, 0, "&6&lRevils Network");

        tabLayout.set(0, 1, "&7&m----------------");
        tabLayout.set(1, 1, "&7&m----------------");
        tabLayout.set(2, 1, "&7&m----------------");

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

        tabLayout.set(0, 18, "&7&m----------------");
        tabLayout.set(1, 18, "&7&m----------------");
        tabLayout.set(2, 18, "&7&m----------------");

        tabLayout.set(0, 19, "&7www.revils.eu");
        tabLayout.set(1, 19, "&7ts.revils.eu");
        tabLayout.set(2, 19, "&7store.revils.eu");
    }
}