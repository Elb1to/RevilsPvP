package eu.revils.revilspvp.tab;

import me.activated.core.api.player.PlayerData;
import me.activated.core.api.rank.RankData;
import me.activated.core.plugin.AquaCore;
import net.frozenorb.qlib.tab.TabLayout;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;

final class LobbyLayoutProvider implements BiConsumer<Player, TabLayout> {

    @Override
    public void accept(Player player, TabLayout tabLayout) {
        tabLayout.set(1, 0, "&6&lRevils Network");

        tabLayout.set(0, 1, "&7&m----------------");
        tabLayout.set(1, 1, "&7&m----------------");
        tabLayout.set(2, 1, "&7&m----------------");

        int x = 0;
        int y = 2;

        for (RankData rank : getSortedRanks()) {
            for (final Player online : Bukkit.getServer().getOnlinePlayers()) {
                PlayerData profile = AquaCore.INSTANCE.getPlayerManagement().getPlayerData(online.getUniqueId());
                if (profile != null) {
                    if (profile.getHighestRank() == rank) {
                        tabLayout.set(x, y, (rank.getDisplayColor() + online.getName()), ((CraftPlayer) online).getHandle().ping);
                        if (x++ < 2) {
                            continue;
                        }
                        x = 0;
                        ++y;
                    }
                }
            }
        }

        tabLayout.set(0, 18, "&7&m----------------");
        tabLayout.set(1, 18, "&7&m----------------");
        tabLayout.set(2, 18, "&7&m----------------");

        tabLayout.set(0, 19, "&7www.revils.eu");
        tabLayout.set(1, 19, "&7ts.revils.eu");
        tabLayout.set(2, 19, "&7store.revils.eu");
    }

    public List<RankData> getSortedRanks() {
        List<RankData> ranks = new ArrayList<>(AquaCore.INSTANCE.getRankManagement().getRanks());
        ranks.sort(Comparator.comparing(RankData::getWeight).reversed());
        return ranks;
    }

}