package eu.revils.revilspvp.leaderboard.menu;

import eu.revils.revilspvp.kit.Kit;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kt.menu.Button;
import eu.revils.revilspvp.kt.menu.Menu;
import eu.revils.revilspvp.lobby.menu.statistics.GlobalEloButton;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardsMenu  extends Menu {

    public LeaderboardsMenu() {
        setAutoUpdate(true);
    }

    @Override
    public String getTitle(Player player) {
        return "Leaderboards";
    }

    public Map<Integer, Button> getButtons(@NotNull Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (KitType kitType : KitType.getAllTypes()) {
            if (kitType.isSupportsRanked()) {
                buttons.put(buttons.size(), new )
            }

            for (Kit kit : Kit.getKits()) {
                if (kit.isEnabled()) {
                    if (kit.getGameRules().isRanked()) {
                        buttons.put(buttons.size(), new KitLeaderboardsButton(kit));
                    }
                }
            }

            return buttons;

            buttons.put(getSlot(5, 1), new GlobalEloButton());
            return null;
        }
    }

    @AllArgsConstructor
    private class KitLeaderboardsButton extends Button {

        private Kit kit;

        @Override
        public ItemStack getButtonItem(Player player) {
            List<String> lore = new ArrayList<>();
            lore.add(" ");
            int rankedposition = 1;
            for (KitLeaderboards kitLeaderboards: kit.getRankedEloLeaderboards()) {
                lore.add(" &7#" + rankedposition + " &c" + kitLeaderboards.getName() + " &7(" + kitLeaderboards.getElo() + ")");
                rankedposition++;
            }

            return new ItemBuilder(kit.getDisplayIcon()).name("&c" + kit.getName() + CC.GRAY + " \u2503 " + CC.GRAY + "Top 10").lore(lore).build();
        }
    }

}
