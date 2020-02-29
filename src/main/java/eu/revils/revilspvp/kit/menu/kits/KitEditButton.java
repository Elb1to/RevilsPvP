package eu.revils.revilspvp.kit.menu.kits;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import eu.revils.revilspvp.kit.Kit;
import eu.revils.revilspvp.kit.KitHandler;
import eu.revils.revilspvp.kit.menu.editkit.EditKitMenu;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kt.menu.Button;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryView;

import java.util.List;
import java.util.Optional;

final class KitEditButton extends Button {

    private final Optional<Kit> kitOpt;
    private final KitType kitType;
    private final int slot;

    KitEditButton(Optional<Kit> kitOpt, KitType kitType, int slot) {
        this.kitOpt = Preconditions.checkNotNull(kitOpt, "kitOpt");
        this.kitType = Preconditions.checkNotNull(kitType, "kitType");
        this.slot = slot;
    }

    @Override
    public String getName(Player player) {
        return ChatColor.GREEN.toString() + ChatColor.BOLD + "Load/Edit";
    }

    @Override
    public List<String> getDescription(Player player) {
        return ImmutableList.of(
            "",
            ChatColor.YELLOW + "Click to edit this kit."
        );
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.BOOK;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, InventoryView view) {
        Kit resolvedKit = kitOpt.orElseGet(() -> {
            KitHandler kitHandler = RevilsPvP.getInstance().getKitHandler();
            return kitHandler.saveDefaultKit(player, kitType, this.slot);
        });

        new EditKitMenu(resolvedKit).openMenu(player);
    }

}