package eu.revils.revilspvp.match;

import eu.revils.revilspvp.kt.util.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;

import static org.bukkit.ChatColor.*;

@UtilityClass
public final class SpectatorItems {

    public static final ItemStack SHOW_SPECTATORS_ITEM = new ItemStack(Material.INK_SACK, 1, DyeColor.GRAY.getDyeData());
    public static final ItemStack HIDE_SPECTATORS_ITEM = new ItemStack(Material.INK_SACK, 1, DyeColor.LIME.getDyeData());

    public static final ItemStack VIEW_INVENTORY_ITEM = new ItemStack(Material.BOOK);

    // these items both do the same thing but we change the name if
    // clicking the item will reuslt in the player being removed
    // from their party. both serve the function of returning a player
    // to the lobby.
    // https://github.com/FrozenOrb/RevilsPvP-SI/issues/37
    public static final ItemStack RETURN_TO_LOBBY_ITEM = new ItemStack(Material.INK_SACK, 1, DyeColor.RED.getDyeData());
    public static final ItemStack LEAVE_PARTY_ITEM = new ItemStack(Material.INK_SACK, 1, DyeColor.RED.getDyeData());

    static {
        ItemUtils.setDisplayName(SHOW_SPECTATORS_ITEM, GRAY + "Show Spectators" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(HIDE_SPECTATORS_ITEM, GREEN + "Hide Spectators" + GRAY + " (Right-Click)");

        ItemUtils.setDisplayName(VIEW_INVENTORY_ITEM, YELLOW + "View Inventory" + GRAY + " (Right-Click)");

        ItemUtils.setDisplayName(RETURN_TO_LOBBY_ITEM, RED + "Return to Lobby" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(LEAVE_PARTY_ITEM, RED + "Leave Party" + GRAY + " (Right-Click)");
    }

}