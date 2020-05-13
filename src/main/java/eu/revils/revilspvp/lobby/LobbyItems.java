package eu.revils.revilspvp.lobby;

import eu.revils.revilspvp.kt.util.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;

import static eu.revils.revilspvp.RevilsPvPLang.LEFT_ARROW;
import static eu.revils.revilspvp.RevilsPvPLang.RIGHT_ARROW;
import static org.bukkit.ChatColor.*;

import org.bukkit.ChatColor;

@UtilityClass
public final class LobbyItems {

    public static final ItemStack SPECTATE_RANDOM_ITEM = new ItemStack(Material.COMPASS);
    public static final ItemStack SPECTATE_MENU_ITEM = new ItemStack(Material.PAPER);
    public static final ItemStack ENABLE_SPEC_MODE_ITEM = new ItemStack(Material.REDSTONE_TORCH_ON);
    public static final ItemStack DISABLE_SPEC_MODE_ITEM = new ItemStack(Material.LEVER);
    public static final ItemStack MANAGE_ITEM = new ItemStack(Material.NAME_TAG);
    public static final ItemStack PLAYER_SETTINGS = new ItemStack(Material.REDSTONE_COMPARATOR);
    public static final ItemStack UNFOLLOW_ITEM = new ItemStack(Material.INK_SACK, 1, DyeColor.RED.getDyeData());
    public static final ItemStack PLAYER_STATISTICS = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);



    static {
        ItemUtils.setDisplayName(SPECTATE_RANDOM_ITEM, GREEN + "Spectate Random Match" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(SPECTATE_MENU_ITEM, LIGHT_PURPLE + "Spectate Menu" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(ENABLE_SPEC_MODE_ITEM, GREEN + "Enable Spectator Mode" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(DISABLE_SPEC_MODE_ITEM, RED + "Disable Spectator Mode" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(MANAGE_ITEM, LIGHT_PURPLE + "Create a Party" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(PLAYER_SETTINGS, YELLOW + "Settings" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(UNFOLLOW_ITEM, RED + "Stop Following" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(PLAYER_STATISTICS, DARK_GREEN + "Leaderboards" + GRAY + " (Right-Click)");

    }

}