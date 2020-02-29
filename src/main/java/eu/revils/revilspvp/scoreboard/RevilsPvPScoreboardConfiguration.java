package eu.revils.revilspvp.scoreboard;

import eu.revils.revilspvp.kt.scoreboard.ScoreboardConfiguration;
import eu.revils.revilspvp.kt.scoreboard.TitleGetter;
import org.apache.commons.lang.StringEscapeUtils;

public final class RevilsPvPScoreboardConfiguration {

    public static ScoreboardConfiguration create() {
        return new ScoreboardConfiguration(
                TitleGetter.forStaticString("&6&lMineHQ &7" + StringEscapeUtils.unescapeJava("\u2758") +" &fPractice"),
                new MultiplexingScoreGetter(
                        new MatchScoreGetter(),
                        new LobbyScoreGetter(),
                        new GameScoreGetter()
                )
        );
    }

}
