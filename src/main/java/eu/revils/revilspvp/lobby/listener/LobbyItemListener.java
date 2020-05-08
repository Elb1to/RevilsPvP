package eu.revils.revilspvp.lobby.listener;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.command.ManageCommand;
import eu.revils.revilspvp.follow.command.UnfollowCommand;
import eu.revils.revilspvp.lobby.LobbyHandler;
import eu.revils.revilspvp.lobby.LobbyItems;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.match.MatchState;
import eu.revils.revilspvp.party.command.PartyCreateCommand;
import eu.revils.revilspvp.setting.command.SettingsCommand;
import eu.revils.revilspvp.setting.menu.SettingsMenu;
import eu.revils.revilspvp.util.ItemListener;
import eu.revils.revilspvp.validation.RevilsPvPValidation;
import eu.revils.revilspvp.lobby.menu.SpectateMenu;
import eu.revils.revilspvp.lobby.menu.StatisticsMenu;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class LobbyItemListener extends ItemListener {

    private final Map<UUID, Long> canUseRandomSpecItem = new HashMap<>();

    public LobbyItemListener(LobbyHandler lobbyHandler) {
        addHandler(LobbyItems.MANAGE_ITEM, p -> {
            PartyCreateCommand.partyCreate(p);
        });

        addHandler(LobbyItems.PLAYER_SETTINGS, p -> {
            SettingsCommand.settings(p);
        });

        addHandler(LobbyItems.DISABLE_SPEC_MODE_ITEM, player -> {
            if (lobbyHandler.isInLobby(player)) {
                lobbyHandler.setSpectatorMode(player, false);
            }
        });

        addHandler(LobbyItems.ENABLE_SPEC_MODE_ITEM, player -> {
            if (lobbyHandler.isInLobby(player) && RevilsPvPValidation.canUseSpectateItem(player)) {
                lobbyHandler.setSpectatorMode(player, true);
            }
        });

        addHandler(LobbyItems.SPECTATE_MENU_ITEM, player -> {
            if (RevilsPvPValidation.canUseSpectateItemIgnoreMatchSpectating(player)) {
                new SpectateMenu().openMenu(player);
            }
        });

        addHandler(LobbyItems.SPECTATE_RANDOM_ITEM, player -> {
            MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();

            if (!RevilsPvPValidation.canUseSpectateItemIgnoreMatchSpectating(player)) {
                return;
            }

            if (canUseRandomSpecItem.getOrDefault(player.getUniqueId(), 0L) > System.currentTimeMillis()) {
                player.sendMessage(ChatColor.RED + "Please wait before doing this again!");
                return;
            }

            List<Match> matches = new ArrayList<>(matchHandler.getHostedMatches());
            matches.removeIf(m -> m.isSpectator(player.getUniqueId()) || m.getState() == MatchState.ENDING);

            if (matches.isEmpty()) {
                player.sendMessage(ChatColor.RED + "There are no matches available to spectate.");
            } else {
                Match currentlySpectating = matchHandler.getMatchSpectating(player);
                Match newSpectating = matches.get(ThreadLocalRandom.current().nextInt(matches.size()));

                if (currentlySpectating != null) {
                    currentlySpectating.removeSpectator(player, false);
                }

                newSpectating.addSpectator(player, null);
                canUseRandomSpecItem.put(player.getUniqueId(), System.currentTimeMillis() + 3_000L);
            }
        });
        addHandler(LobbyItems.PLAYER_STATISTICS, player -> {
            new StatisticsMenu().openMenu(player);
        });

        addHandler(LobbyItems.PLAYER_SETTINGS, player -> {
            new SettingsMenu().openMenu(player);
        });

        addHandler(LobbyItems.UNFOLLOW_ITEM, UnfollowCommand::unfollow);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        canUseRandomSpecItem.remove(event.getPlayer().getUniqueId());
    }

}