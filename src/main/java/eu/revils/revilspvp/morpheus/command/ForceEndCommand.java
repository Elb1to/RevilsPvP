package eu.revils.revilspvp.morpheus.command;

import com.qrakn.morpheus.game.Game;
import com.qrakn.morpheus.game.GameQueue;
import eu.revils.revilspvp.kt.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ForceEndCommand {

    @Command(names = {"forceend", "event cancel","stopevent", "cancelevent"}, permission = "op")
    public static void host(Player sender) {
        Game game = GameQueue.INSTANCE.getCurrentGame(sender);

        if (game == null) {
            sender.sendMessage("You're not in a game");
            return;
        }
        game.end();
        sender.sendMessage(ChatColor.GREEN + "Successfully stopped the ongoing event.");
    }

}
