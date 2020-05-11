package eu.revils.revilspvp.setting.menu;

import eu.revils.revilspvp.kt.menu.Button;
import eu.revils.revilspvp.kt.menu.Menu;
import eu.revils.revilspvp.setting.Setting;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Menu used by /settings to let players toggle settings
 */
public final class SettingsMenu extends Menu {

    public SettingsMenu() {
        super("Edit settings");

        setAutoUpdate(true);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        int index = 0;

        for (Setting setting : Setting.values()) {
            if (setting.canUpdate(player)) {
                buttons.put(index++, new SettingButton(setting));
            }
        }

        return buttons;
    }

}