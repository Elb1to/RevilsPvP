package eu.revils.revilspvp.morpheus;

import com.qrakn.morpheus.game.Game;
import com.qrakn.morpheus.game.GameQueue;
import eu.revils.revilspvp.kt.util.ItemUtils;
import lombok.experimental.UtilityClass;
import eu.revils.revilspvp.kt.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static net.md_5.bungee.api.ChatColor.LIGHT_PURPLE;
import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.BOLD;

@UtilityClass
public final class EventItems {

    public static ItemStack getEventItem() {
        List<Game> game = GameQueue.INSTANCE.getCurrentGames();

        if (game.size() > 0) {
            return ItemBuilder.of(Material.EMERALD).name(BLUE.toString() + BOLD + "» " + GOLD + BOLD + "Join an Event" + BLUE.toString() + BOLD + " «").build();
        }

        return null;
    }

}