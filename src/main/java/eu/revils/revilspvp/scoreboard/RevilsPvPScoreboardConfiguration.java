package eu.revils.revilspvp.scoreboard;


import net.frozenorb.qlib.scoreboard.ScoreboardConfiguration;
import net.frozenorb.qlib.scoreboard.TitleGetter;
import org.apache.commons.lang.StringEscapeUtils;

public final class RevilsPvPScoreboardConfiguration {

    public static ScoreboardConfiguration create() {
        ScoreboardConfiguration configuration = new ScoreboardConfiguration();

        configuration.setTitleGetter(TitleGetter.forStaticString("&6&lRevils &7" + StringEscapeUtils.unescapeJava("\u2758") +" &fPractice 5.0"));
        configuration.setScoreGetter(new MultiplexingScoreGetter(
                new MatchScoreGetter(),
                new LobbyScoreGetter(),
                new GameScoreGetter()
        ));

        return configuration;
    }

}
