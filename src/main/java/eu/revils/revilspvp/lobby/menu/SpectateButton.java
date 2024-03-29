package eu.revils.revilspvp.lobby.menu;

import com.google.common.base.Preconditions;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchTeam;
import eu.revils.revilspvp.validation.RevilsPvPValidation;
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

final class SpectateButton extends Button {

    private final Match match;

    SpectateButton(Match match) {
        this.match = Preconditions.checkNotNull(match, "match");
    }

    @Override
    public String getName(Player player) {
        return ChatColor.YELLOW.toString() + ChatColor.BOLD + match.getSimpleDescription(false);
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = new ArrayList<>();
        MatchTeam teamA = match.getTeams().get(0);
        MatchTeam teamB = match.getTeams().get(1);

        if (match.isRanked()) {
            description.add(ChatColor.GRAY + " ▪ " + ChatColor.WHITE + "Mode" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Ranked");
        } else {
            description.add(ChatColor.GRAY + " ▪ " + ChatColor.WHITE + "Mode" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Unranked");
        }

        description.add("");
        description.add(ChatColor.GRAY + " ▪ " + ChatColor.WHITE + "Kit" + ChatColor.GRAY + ": " + ChatColor.GREEN + match.getKitType().getDisplayName());
        description.add(ChatColor.GRAY + " ▪ " + ChatColor.WHITE + "Arena" + ChatColor.GRAY + ": " + ChatColor.GREEN + match.getArena().getSchematic());

        List<UUID> spectators = new ArrayList<>(match.getSpectators());
        // don't count actual players and players in silent mode.
        spectators.removeIf(uuid -> Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).hasMetadata("ModMode") || match.getPreviousTeam(uuid) != null);

        description.add(ChatColor.GRAY + " ▪ " + ChatColor.WHITE + "Spectators" + ChatColor.GRAY + ": " + ChatColor.GREEN + spectators.size());

        if (teamA.getAliveMembers().size() != 1 || teamB.getAliveMembers().size() != 1) {
            description.add("");

            for (UUID member : teamA.getAliveMembers()) {
                description.add(ChatColor.AQUA + RevilsPvP.getInstance().getUuidCache().name(member));
            }

            description.add(ChatColor.YELLOW + " vs ");

            for (UUID member : teamB.getAliveMembers()) {
                description.add(ChatColor.AQUA + RevilsPvP.getInstance().getUuidCache().name(member));
            }
        }

        description.add("");
        description.add(ChatColor.YELLOW.toString() + ChatColor.UNDERLINE + "Left-Click" + ChatColor.YELLOW + " to spectate the match!");

        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.PAPER;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, InventoryView view) {
        if (!RevilsPvPValidation.canUseSpectateItemIgnoreMatchSpectating(player)) {
            return;
        }

        Match currentlySpectating = RevilsPvP.getInstance().getMatchHandler().getMatchSpectating(player);

        if (currentlySpectating != null) {
            currentlySpectating.removeSpectator(player, false);
        }

        match.addSpectator(player, null);
    }

}