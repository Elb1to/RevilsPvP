package eu.revils.revilspvp.queue;

import eu.revils.revilspvp.kt.util.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import lombok.experimental.UtilityClass;
import static org.bukkit.ChatColor.*;

@UtilityClass
public final class QueueItems {

    public static final ItemStack JOIN_SOLO_UNRANKED_QUEUE_ITEM = new ItemStack(Material.IRON_SWORD);
    public static final ItemStack LEAVE_SOLO_UNRANKED_QUEUE_ITEM = new ItemStack(Material.INK_SACK, 1, (byte) DyeColor.RED.getDyeData());

    public static final ItemStack JOIN_SOLO_RANKED_QUEUE_ITEM = new ItemStack(Material.DIAMOND_SWORD);
    public static final ItemStack LEAVE_SOLO_RANKED_QUEUE_ITEM = new ItemStack(Material.INK_SACK, 1, (byte) DyeColor.RED.getDyeData());

    public static final ItemStack JOIN_PARTY_UNRANKED_QUEUE_ITEM = new ItemStack(Material.IRON_SWORD);
    public static final ItemStack LEAVE_PARTY_UNRANKED_QUEUE_ITEM = new ItemStack(Material.INK_SACK, 1, (byte) DyeColor.RED.getDyeData());

    public static final ItemStack JOIN_PARTY_RANKED_QUEUE_ITEM = new ItemStack(Material.DIAMOND_SWORD);
    public static final ItemStack LEAVE_PARTY_RANKED_QUEUE_ITEM = new ItemStack(Material.INK_SACK, 1, (byte) DyeColor.RED.getDyeData());

    static {
        ItemUtils.setDisplayName(JOIN_SOLO_UNRANKED_QUEUE_ITEM, GREEN  + "Unranked Matches" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(LEAVE_SOLO_UNRANKED_QUEUE_ITEM, RED + "Leave Queue" + GRAY + " (Right-Click)");

        ItemUtils.setDisplayName(JOIN_SOLO_RANKED_QUEUE_ITEM, AQUA  + "Ranked Matches" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(LEAVE_SOLO_RANKED_QUEUE_ITEM, RED + "Leave Ranked Queue" + GRAY + " (Right-Click)");

        ItemUtils.setDisplayName(JOIN_PARTY_UNRANKED_QUEUE_ITEM, GREEN  + "Unranked 2v2 Matches" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(LEAVE_PARTY_UNRANKED_QUEUE_ITEM, RED + "Leave Party Queue" + GRAY + " (Right-Click)");

        ItemUtils.setDisplayName(JOIN_PARTY_RANKED_QUEUE_ITEM, AQUA  + "Ranked 2v2 Matches" + GRAY + " (Right-Click)");
        ItemUtils.setDisplayName(LEAVE_PARTY_RANKED_QUEUE_ITEM, RED + "Leave Party Queue" + GRAY + " (Right-Click)");
    }
}