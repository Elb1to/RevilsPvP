package eu.revils.revilspvp.command;

import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.kt.command.Command;
import eu.revils.revilspvp.lobby.LobbyHandler;
import org.bukkit.entity.Player;

public final class FlyCommand {
    @Command(names = {"fly", "flymode", "flight"}, permission = "donator.fly")
    public static void onCommandExecute(Player sender) {
        LobbyHandler lobbyHandler = RevilsPvP.getInstance().getLobbyHandler();
        if (!lobbyHandler.isInLobby(sender)) {
            return;
        }
        sender.setFlying(true);
    }
}
