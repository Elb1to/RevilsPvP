package eu.revils.revilspvp.rematch.listener;

import eu.revils.revilspvp.util.InventoryUtils;
import eu.revils.revilspvp.util.ItemListener;
import eu.revils.revilspvp.duel.command.AcceptCommand;
import eu.revils.revilspvp.duel.command.DuelCommand;
import eu.revils.revilspvp.rematch.RematchData;
import eu.revils.revilspvp.rematch.RematchHandler;
import eu.revils.revilspvp.rematch.RematchItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class RematchItemListener extends ItemListener {

    public RematchItemListener(RematchHandler rematchHandler) {
        addHandler(RematchItems.REQUEST_REMATCH_ITEM, player -> {
            RematchData rematchData = rematchHandler.getRematchData(player);

            if (rematchData != null) {
                Player target = Bukkit.getPlayer(rematchData.getTarget());
                DuelCommand.duel(player, target, rematchData.getKitType());

                InventoryUtils.resetInventoryDelayed(player);
                InventoryUtils.resetInventoryDelayed(target);
            }
        });

        addHandler(RematchItems.SENT_REMATCH_ITEM, p -> p.sendMessage(ChatColor.RED + "You have already sent a rematch request."));

        addHandler(RematchItems.ACCEPT_REMATCH_ITEM, player -> {
            RematchData rematchData = rematchHandler.getRematchData(player);

            if (rematchData != null) {
                Player target = Bukkit.getPlayer(rematchData.getTarget());
                AcceptCommand.accept(player, target);
            }
        });
    }

}