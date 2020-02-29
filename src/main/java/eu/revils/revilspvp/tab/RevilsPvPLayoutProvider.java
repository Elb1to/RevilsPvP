package eu.revils.revilspvp.tab;

import java.util.UUID;
import java.util.function.BiConsumer;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.kt.tab.LayoutProvider;
import eu.revils.revilspvp.kt.tab.TabLayout;
import eu.revils.revilspvp.kt.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class RevilsPvPLayoutProvider implements LayoutProvider {

    static final int MAX_TAB_Y = 20;
    private static boolean testing = true;

    private final BiConsumer<Player, TabLayout> headerLayoutProvider = new HeaderLayoutProvider();
    private final BiConsumer<Player, TabLayout> lobbyLayoutProvider = new LobbyLayoutProvider();
    private final BiConsumer<Player, TabLayout> matchSpectatorLayoutProvider = new MatchSpectatorLayoutProvider();
    private final BiConsumer<Player, TabLayout> matchParticipantLayoutProvider = new MatchParticipantLayoutProvider();

    @Override
    public TabLayout provide(Player player) {
        if (RevilsPvP.getInstance() == null) return TabLayout.create(player);
        TabLayout tabLayout = TabLayout.create(player);

        Match match = RevilsPvP.getInstance().getMatchHandler().getMatchPlayingOrSpectating(player);
        headerLayoutProvider.accept(player, tabLayout);

        if (match != null) {
            if (match.isSpectator(player.getUniqueId())) {
                matchSpectatorLayoutProvider.accept(player, tabLayout);
            } else {
                matchParticipantLayoutProvider.accept(player, tabLayout);
            }
        } else {
            lobbyLayoutProvider.accept(player, tabLayout);
        }

        return tabLayout;
    }

    static int getPingOrDefault(UUID check) {
        Player player = Bukkit.getPlayer(check);
        return player != null ? PlayerUtils.getPing(player) : 0;
    }

}