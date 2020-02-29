/**
 * Handles accessing, saving, updating, and presentation of player settings.
 *
 * This includes the /settings command, a settings menu, persistence, etc.
 * Clients using the settings API should only concern themselves with {@link eu.revils.revilspvp.setting.event.SettingUpdateEvent},
 * {@link eu.revils.revilspvp.setting.SettingHandler#getSetting(java.util.UUID, Setting)} and
 * {@link eu.revils.revilspvp.setting.SettingHandler#updateSetting(org.bukkit.entity.Player, Setting, boolean)},
 */
package eu.revils.revilspvp.setting;