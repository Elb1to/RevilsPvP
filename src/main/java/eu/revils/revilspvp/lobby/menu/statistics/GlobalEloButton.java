package eu.revils.revilspvp.lobby.menu.statistics;

import java.util.List;
import java.util.Map.Entry;

import eu.revils.revilspvp.RevilsPvP;
import net.frozenorb.qlib.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import eu.revils.revilspvp.elo.EloHandler;

public class GlobalEloButton extends Button {

    private EloHandler eloHandler = RevilsPvP.getInstance().getEloHandler();

    @Override
    public String getName(Player player) {
        return ChatColor.AQUA.toString() + ChatColor.BOLD + "Global ELO" + ChatColor.GRAY + " \u2758 " + ChatColor.WHITE + "Top 10";
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = Lists.newArrayList();

        description.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------");

        int counter = 1;

        for (Entry<String, Integer> entry : eloHandler.topElo(null).entrySet()) {
            String color = (counter <= 3 ? ChatColor.YELLOW : ChatColor.YELLOW).toString();
            description.add(color + " #" + counter + " " + ChatColor.WHITE + entry.getKey() + ChatColor.GRAY + " - " + ChatColor.GREEN + entry.getValue() + " ELO");

            counter++;
        }

        description.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------");

        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.COMPASS;
    }
}
