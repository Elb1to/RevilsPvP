package eu.revils.revilspvp.rematch;

import eu.revils.revilspvp.kt.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;

import static org.bukkit.ChatColor.*;

@UtilityClass
public final class RematchItems {

    public static final ItemStack REQUEST_REMATCH_ITEM = new ItemStack(Material.DIAMOND);
    public static final ItemStack SENT_REMATCH_ITEM = new ItemStack(Material.EMERALD);
    public static final ItemStack ACCEPT_REMATCH_ITEM = new ItemStack(Material.EMERALD);

    static {
        ItemUtils.setDisplayName(REQUEST_REMATCH_ITEM, DARK_GREEN + "Request Rematch" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(SENT_REMATCH_ITEM, DARK_GREEN + "Sent Rematch" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(ACCEPT_REMATCH_ITEM, DARK_GREEN + "Accept Rematch" + GRAY + " (Right-Click)");
    }

}