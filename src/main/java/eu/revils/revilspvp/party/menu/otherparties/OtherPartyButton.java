package eu.revils.revilspvp.party.menu.otherparties;

import com.google.common.base.Preconditions;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.duel.command.DuelCommand;
import eu.revils.revilspvp.party.Party;
import eu.revils.revilspvp.kt.menu.Button;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final class OtherPartyButton extends Button {

    private final Party party;

    OtherPartyButton(Party party) {
        this.party = Preconditions.checkNotNull(party, "party");
    }

    @Override
    public String getName(Player player) {
        return ChatColor.AQUA + RevilsPvP.getInstance().getUuidCache().name(party.getLeader()) + "'s Team";
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = new ArrayList<>();

        description.add("");
        description.add("&7Members:");

        for (UUID member : party.getMembers()) {
            ChatColor color = party.isLeader(member) ? ChatColor.GREEN : ChatColor.GREEN;
            description.add(ChatColor.GRAY + " - " + color + RevilsPvP.getInstance().getUuidCache().name(member));
        }

        description.add("");
        description.add(ChatColor.YELLOW.toString() + ChatColor.UNDERLINE + "Left-Click" + ChatColor.YELLOW + " to duel " + ChatColor.AQUA + RevilsPvP.getInstance().getUuidCache().name(party.getLeader()) + ChatColor.YELLOW + "'s team.");

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

    @Override
    public int getAmount(Player player) {
        return party.getMembers().size();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, InventoryView view) {
        Party senderParty = RevilsPvP.getInstance().getPartyHandler().getParty(player);

        if (senderParty == null) {
            return;
        }

        if (senderParty.isLeader(player.getUniqueId())) {
            DuelCommand.duel(player, Bukkit.getPlayer(party.getLeader()));
        } else {
            player.sendMessage(ChatColor.RED + "Only the leader can duel other parties!");
        }
    }

}