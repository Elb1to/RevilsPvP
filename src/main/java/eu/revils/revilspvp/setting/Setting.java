package eu.revils.revilspvp.setting;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

@AllArgsConstructor
public enum Setting {

    SHOW_SCOREBOARD(
            ChatColor.GOLD + "" + ChatColor.BOLD + "Match Scoreboard",
            ImmutableList.of(
                    ChatColor.GRAY + " Toggles side scoreboard in-match"
            ),
            Material.ITEM_FRAME,
            ChatColor.YELLOW + "Show match scoreboard",
            ChatColor.YELLOW + "Hide match scoreboard",
            true,
            null // no permission required
    ),
    SHOW_SPECTATOR_JOIN_MESSAGES(
            ChatColor.GOLD + "" + ChatColor.BOLD + "Spectator Join Messages",
            ImmutableList.of(
                    ChatColor.GRAY + " Enable this to display",
                    ChatColor.GRAY + " messages as spectators join."
            ),
            Material.BONE,
            ChatColor.YELLOW + "Show spectator join messages",
            ChatColor.YELLOW + "Hide spectator join messages",
            true,
            null // no permission required
    ),
    VIEW_OTHER_SPECTATORS(
            ChatColor.GOLD.toString() + ChatColor.BOLD + "Other Spectators",
            ImmutableList.of(
                    ChatColor.GRAY + " If enabled, you can see spectators",
                    ChatColor.GRAY + " in the same match as you.",
                    "",
                    ChatColor.GRAY + " Disable to only see alive players in match."
            ),
            Material.GLASS_BOTTLE,
            ChatColor.YELLOW + "Show other spectators",
            ChatColor.YELLOW + "Hide other spectators",
            true,
            null // no permission required
    ),
    ALLOW_SPECTATORS(
            ChatColor.GOLD.toString() + ChatColor.BOLD + "Allow Spectators",
            ImmutableList.of(
                    ChatColor.GRAY + " If enabled, players can spectate your",
                    ChatColor.GRAY + " matches with /spectate.",
                    "",
                    ChatColor.GRAY + " Disable to disallow match spectators."
            ),
            Material.REDSTONE_TORCH_ON,
            ChatColor.YELLOW + "Let players spectate your matches",
            ChatColor.YELLOW + "Don't let players spectate your matches",
            true,
            null // no permission required
    ),
    RECEIVE_DUELS(
            ChatColor.GOLD.toString() + ChatColor.BOLD + "Duel Invites",
            ImmutableList.of(
                    ChatColor.GRAY + " If enabled, you will be able to receive",
                    ChatColor.GRAY + " duels from other players or parties.",
                    "",
                    ChatColor.GRAY + " Disable to not receive, but still send duels."
            ),
            Material.FIRE,
            ChatColor.YELLOW + "Allow duel invites",
            ChatColor.YELLOW + "Disallow duel invites",
            true,
            null
    ),
    VIEW_OTHERS_LIGHTNING(
            ChatColor.GOLD.toString() + ChatColor.BOLD + "Death Lightning",
            ImmutableList.of(
                    ChatColor.GRAY + " If enabled, lightning will be visible",
                    ChatColor.GRAY + " when other players die.",
                    "",
                    ChatColor.GRAY + " Disable to hide others lightning."
            ),
            Material.TORCH,
            ChatColor.YELLOW + "Show other lightning",
            ChatColor.YELLOW + "Hide other lightning",
            true,
            null // no permission required
    ),
    NIGHT_MODE(
            ChatColor.GOLD.toString() + ChatColor.BOLD + "Night Mode",
            ImmutableList.of(
                    ChatColor.GRAY + " If enabled, your player time will be",
                    ChatColor.GRAY + " changed to night time.",
                    "",
                    ChatColor.GRAY + " Disable to play in day time."
            ),
            Material.GLOWSTONE,
            ChatColor.YELLOW + "Time is set to night",
            ChatColor.YELLOW + "Time is set to day",
            false,
            null // no permission required
    ),
    SEE_TOURNAMENT_JOIN_MESSAGE(
            ChatColor.GOLD.toString() + ChatColor.BOLD + "Tournament Join Messages",
            ImmutableList.of(
                    ChatColor.GRAY + " If enabled, you will see messages",
                    ChatColor.GRAY + " when people join the tournament",
                    "",
                    ChatColor.GRAY + " Disable to only see your own party join messages."
            ),
            Material.IRON_DOOR,
            ChatColor.YELLOW + "Tournament join messages are shown",
            ChatColor.YELLOW + "Tournament join messages are hidden",
            true,
            null // no permission required
    ),
    SEE_TOURNAMENT_ELIMINATION_MESSAGES(
            ChatColor.GOLD.toString() + ChatColor.BOLD + "Tournament Elimination Messages",
            ImmutableList.of(
                    ChatColor.GRAY + " If enabled, you will see messages when",
                    ChatColor.GRAY + " people are eliminated the tournament",
                    "",
                    ChatColor.GRAY + " Disable to only see your own party elimination messages."
            ),
            Material.SKULL_ITEM,
            ChatColor.YELLOW + "Tournament elimination messages are shown",
            ChatColor.YELLOW + "Tournament elimination messages are hidden",
            true,
            null // no permission required
    );

    /**
     * Friendly (colored) display name for this setting
     */
    @Getter
    private final String name;

    /**
     * Friendly (colored) description for this setting
     */
    @Getter
    private final List<String> description;

    /**
     * Material to be used when rendering an icon for this setting
     *
     * @see eu.revils.revilspvp.setting.menu.SettingButton
     */
    @Getter
    private final Material icon;

    /**
     * Text to be shown when rendering an icon for this setting, while enabled
     *
     * @see eu.revils.revilspvp.setting.menu.SettingButton
     */
    @Getter
    private final String enabledText;

    /**
     * Text to be shown when rendering an icon for this setting, while enabled
     *
     * @see eu.revils.revilspvp.setting.menu.SettingButton
     */
    @Getter
    private final String disabledText;

    /**
     * Default value for this setting, will be used for players who haven't
     * updated the setting and if a player's settings fail to load.
     */
    private final boolean defaultValue;

    /**
     * The permission required to be able to see and update this setting,
     * null means no permission is required to update/see.
     */
    private final String permission;

    // Using @Getter means the method would be 'isDefaultValue',
    // which doesn't correctly represent this variable.
    public boolean getDefaultValue() {
        return defaultValue;
    }

    public boolean canUpdate(Player player) {
        return permission == null || player.hasPermission(permission);
    }

}