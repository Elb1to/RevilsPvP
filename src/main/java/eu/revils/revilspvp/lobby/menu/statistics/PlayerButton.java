package eu.revils.revilspvp.lobby.menu.statistics;

import com.google.common.collect.Lists;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.elo.EloHandler;
import eu.revils.revilspvp.kittype.KitType;
import net.frozenorb.qlib.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerButton extends Button {

    private EloHandler eloHandler = RevilsPvP.getInstance().getEloHandler();

    @Override
    public String getName(Player player) {
        return ChatColor.AQUA + getColoredName(player) + ChatColor.BOLD + "'s Statistics";
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = Lists.newArrayList();

        description.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------");
        description.add(ChatColor.YELLOW + " ▪ " + ChatColor.WHITE + "Global" + ChatColor.GRAY + " - " + ChatColor.GREEN + eloHandler.getGlobalElo(player.getUniqueId()) + " ELO");

        for (KitType kitType : KitType.getAllTypes()) {
            if (kitType.isSupportsRanked()) {
                description.add(ChatColor.YELLOW + " ▪ " + ChatColor.WHITE + kitType.getDisplayName() + ChatColor.GRAY + " - " + ChatColor.GREEN + eloHandler.getElo(player, kitType) + " ELO");
            }
        }

        description.add(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "---------------------------");

        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.SKULL_ITEM;
    }

    @Override
    public byte getDamageValue(Player player) {
        return (byte) 3;
    }

    private String getColoredName(Player player) {
        return player.getName();
    }
}
