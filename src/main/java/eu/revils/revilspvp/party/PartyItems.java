package eu.revils.revilspvp.party;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kt.util.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;

import static org.bukkit.ChatColor.*;

@UtilityClass
public final class PartyItems {

    public static final Material ICON_TYPE = Material.NETHER_STAR;

    public static final ItemStack LEAVE_PARTY_ITEM = new ItemStack(Material.INK_SACK, 1, DyeColor.RED.getDyeData());
    public static final ItemStack ASSIGN_CLASSES = new ItemStack(Material.BLAZE_POWDER);
    public static final ItemStack START_TEAM_SPLIT_ITEM = new ItemStack(Material.DIAMOND_SWORD);
    public static final ItemStack START_FFA_ITEM = new ItemStack(Material.GOLD_SWORD);
    public static final ItemStack OTHER_PARTIES_ITEM = new ItemStack(Material.SKULL_ITEM);

    static {
        ItemUtils.setDisplayName(LEAVE_PARTY_ITEM, RED + "Leave Party" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(ASSIGN_CLASSES, DARK_GREEN + "HCF Kits" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(START_TEAM_SPLIT_ITEM, YELLOW + "Start Split Match" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(START_FFA_ITEM, BLUE + "Start FFA Match" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(OTHER_PARTIES_ITEM, LIGHT_PURPLE + "Other Parties" + GRAY + " (Right-Click)");
    }

    public static ItemStack icon(Party party) {
        ItemStack item = new ItemStack(ICON_TYPE);

        String leaderName = RevilsPvP.getInstance().getUuidCache().name(party.getLeader());
        String displayName = YELLOW.toString() + BOLD + leaderName + YELLOW + "'s Party" + GRAY + " (Right-Click)";

        ItemUtils.setDisplayName(item, displayName);
        return item;
    }

}
