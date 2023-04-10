package com.live.worldcup.scoreboard;

import com.live.worldcup.scoreboard.exception.ScoreBoardException;
import com.live.worldcup.scoreboard.model.Game;
import com.live.worldcup.scoreboard.model.Team;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class Scoreboard {
    @Getter
    private static final List<Team> teams = new ArrayList<>();

    @Getter
    private static final SortedSet<Game> games = createGameSet();

    public static void startGame(String homeTeam, String awayTeam) throws ScoreBoardException {}

    public static void startGame(Team homeTeam, Team awayTeam) throws ScoreBoardException {}

    public static void updateScore(Game game, int homeScore, int awayScore) throws ScoreBoardException {}

    public static void finishGame(Game game) throws ScoreBoardException {}

    public List<String> getSummary() {
        return Collections.emptyList();
    }

    public void resetGames() {
        games.clear();
    }

    private static SortedSet<Game> createGameSet() {
        var comparator = Comparator.comparingInt(Game::getTotalScore).reversed()
                .thenComparing(Game::getStartTime).reversed();

        return new TreeSet<>(comparator);
    }
}
