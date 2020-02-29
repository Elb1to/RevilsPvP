package eu.revils.revilspvp.listener;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.setting.Setting;
import eu.revils.revilspvp.setting.SettingHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class ChatToggleListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        // players always see messages sent by ops
        if (event.getPlayer().isOp()) {
            return;
        }

        SettingHandler settingHandler = RevilsPvP.getInstance().getSettingHandler();
        event.getRecipients().removeIf(p -> !settingHandler.getSetting(p, Setting.ENABLE_GLOBAL_CHAT));
    }

}