package eu.revils.revilspvp.morpheus.menu;

import com.qrakn.morpheus.game.Game;
import com.qrakn.morpheus.game.GameQueue;
import com.qrakn.morpheus.game.GameState;
import eu.revils.revilspvp.kt.menu.Button;
import eu.revils.revilspvp.kt.menu.Menu;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryView;

import java.util.*;

public class EventsMenu extends Menu {

    public EventsMenu() {
        super(ChatColor.DARK_PURPLE + "Join an event");
        setAutoUpdate(true);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> toReturn = new HashMap<>();

        for (Game game : GameQueue.INSTANCE.getCurrentGames()) {
            toReturn.put(toReturn.size(), new Button() {
                @Override
                public String getName(Player player) {
                    return ChatColor.AQUA.toString() + ChatColor.BOLD + game.getEvent().getName() + " Event";
                }

                @Override
                public List<String> getDescription(Player player) {

                    List<String> lines = new ArrayList<>();

                    for (String line : Arrays.asList(
                            "",
                            " &7▪ &fHoster&7: &a" + game.getHost().getDisplayName(),
                            " &7▪ &fPlayers&7: &a" + game.getPlayers().size() + (game.getMaxPlayers() == -1 ? "" : "/" + game.getMaxPlayers()),
                            " &7▪ &fState&7: &a" + StringUtils.capitalize(game.getState().name().toLowerCase()) + "..",
                            " ",
                            (game.getState() == GameState.STARTING ? "&e&nLeft-Click&e to join the event!" : "&e&nLeft-Click&e to spectate the event!"))) {
                        lines.add(ChatColor.translateAlternateColorCodes('&', line));
                    }

                    return lines;
                }

                @Override
                public Material getMaterial(Player player) {
                    return game.getEvent().getIcon().getType();
                }

                @Override
                public byte getDamageValue(Player player) {
                    return (byte) game.getEvent().getIcon().getDurability();
                }

                @Override
                public void clicked(Player player, int slot, ClickType clickType, InventoryView view) {
                    player.closeInventory();
                    if (game.getState() == GameState.STARTING) {
                        if (game.getMaxPlayers() > 0 && game.getPlayers().size() >= game.getMaxPlayers()) {
                            player.sendMessage(ChatColor.RED + "This event is currently full! Sorry!");
                            return;
                        }
                        game.add(player);
                    } else {
                        game.addSpectator(player);
                    }
                }
            });
        }

        if (toReturn.isEmpty()) {
            player.closeInventory();
        }

        return toReturn;
    }

}
