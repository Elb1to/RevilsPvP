package eu.revils.revilspvp.scoreboard;

import java.util.function.BiConsumer;

import com.qrakn.morpheus.game.Game;
import com.qrakn.morpheus.game.GameQueue;
import com.qrakn.morpheus.game.GameState;
import eu.revils.revilspvp.RevilsPvP;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.setting.Setting;
import eu.revils.revilspvp.setting.SettingHandler;
import org.bukkit.entity.Player;


import net.frozenorb.qlib.scoreboard.ScoreGetter;
import net.frozenorb.qlib.util.LinkedList;

final class MultiplexingScoreGetter implements ScoreGetter {

    private final BiConsumer<Player, LinkedList<String>> matchScoreGetter;
    private final BiConsumer<Player, LinkedList<String>> lobbyScoreGetter;
    private final BiConsumer<Player, LinkedList<String>> gameScoreGetter;

    MultiplexingScoreGetter(
            BiConsumer<Player, LinkedList<String>> matchScoreGetter,
            BiConsumer<Player, LinkedList<String>> lobbyScoreGetter,
            BiConsumer<Player, LinkedList<String>> gameScoreGetter
    ) {
        this.matchScoreGetter = matchScoreGetter;
        this.lobbyScoreGetter = lobbyScoreGetter;
        this.gameScoreGetter = gameScoreGetter;
    }

    @Override
    public void getScores(LinkedList<String> scores, Player player) {
        if (RevilsPvP.getInstance() == null) return;
        MatchHandler matchHandler = RevilsPvP.getInstance().getMatchHandler();
        SettingHandler settingHandler = RevilsPvP.getInstance().getSettingHandler();

        if (settingHandler.getSetting(player, Setting.SHOW_SCOREBOARD)) {
            if (matchHandler.isPlayingOrSpectatingMatch(player)) {
                matchScoreGetter.accept(player, scores);
            } else {
                Game game = GameQueue.INSTANCE.getCurrentGame(player);

                if (game != null && game.getPlayers().contains(player) && game.getState() != GameState.ENDED) {
                    gameScoreGetter.accept(player, scores);
                } else {
                    lobbyScoreGetter.accept(player, scores);
                }
            }
        }

        if (!scores.isEmpty()) {
            scores.addFirst("&a&7&m----------------------");
            scores.add("");
            scores.add("&7https://revils.eu/");
            scores.add("&f&7&m----------------------");
        }
    }

}