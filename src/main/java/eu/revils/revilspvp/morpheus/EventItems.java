package eu.revils.revilspvp.morpheus;

import com.qrakn.morpheus.game.Game;
import com.qrakn.morpheus.game.GameQueue;
import eu.revils.revilspvp.kt.util.ItemBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static org.bukkit.ChatColor.*;

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