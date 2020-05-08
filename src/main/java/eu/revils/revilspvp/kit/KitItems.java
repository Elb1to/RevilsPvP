package eu.revils.revilspvp.kit;

import eu.revils.revilspvp.kt.util.ItemUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;

import static org.bukkit.ChatColor.*;

@UtilityClass
public final class KitItems {

    public static final ItemStack OPEN_EDITOR_ITEM = new ItemStack(Material.BOOK);

    static {
        ItemUtils.setDisplayName(OPEN_EDITOR_ITEM, GOLD + "Edit Kits" + GRAY + " (Right-Click)");
    }

}