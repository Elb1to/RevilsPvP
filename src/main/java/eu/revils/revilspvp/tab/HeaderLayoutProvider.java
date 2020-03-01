package eu.revils.revilspvp.tab;

import java.util.function.BiConsumer;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kt.tab.TabLayout;
import eu.revils.revilspvp.kt.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

final class HeaderLayoutProvider implements BiConsumer<Player, TabLayout> {

    @Override
    public void accept(Player player, TabLayout tabLayout) {
        header: {
            tabLayout.set(0, 1, ChatColor.GRAY + "Online: " + Bukkit.getOnlinePlayers().size());
            tabLayout.set(1, 0, "&6&lRevils Network");
            tabLayout.set(2, 1, ChatColor.GRAY + "In Fights: " + RevilsPvP.getInstance().getCache().getFightsCount());
        }

        status: {
            tabLayout.set(1, 1, ChatColor.GRAY + "Your Connection: " + getPing(player) + "ms");
        }
    }

    public int getPing(Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }

}
