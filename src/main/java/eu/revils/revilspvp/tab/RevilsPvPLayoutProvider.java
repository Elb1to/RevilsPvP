package eu.revils.revilspvp.tab;

import eu.revils.revilspvp.RevilsPvP;
import net.frozenorb.qlib.tab.LayoutProvider;
import net.frozenorb.qlib.tab.TabLayout;
import org.bukkit.entity.Player;

public final class RevilsPvPLayoutProvider implements LayoutProvider {

    @Override
    public TabLayout provide(Player player) {
        if (RevilsPvP.getInstance() == null) return TabLayout.create(player);
        TabLayout tabLayout = TabLayout.create(player);

        new LobbyLayoutProvider().accept(player, tabLayout);

        return tabLayout;
    }

}
