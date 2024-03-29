package eu.revils.revilspvp.morpheus.menu;

import com.qrakn.morpheus.game.event.GameEvent;
import eu.revils.revilspvp.kt.menu.Button;
import eu.revils.revilspvp.kt.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class HostMenu extends Menu {

    public HostMenu() {
        super(ChatColor.DARK_PURPLE + "Host an event");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> toReturn = new HashMap<>();

        for (GameEvent event : GameEvent.getEvents()) {
            toReturn.put(toReturn.size(), new HostEventButton(event));
        }

        return toReturn;
    }

}
