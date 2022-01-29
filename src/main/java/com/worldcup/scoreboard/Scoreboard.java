package com.worldcup.scoreboard;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import com.worldcup.scoreboard.exception.ScoreboardException;
import com.worldcup.scoreboard.model.Game;
import com.worldcup.scoreboard.model.Score;
import lombok.ToString;

@ToString
public class Scoreboard {

    private final Map<Game, Score> games = new HashMap<>();

    public void startGame(String homeTeam, String awayTeam) throws ScoreboardException {
        if (Objects.isNull(homeTeam)) {
            throw new ScoreboardException("Home team is not supposed to be null");
        } else if (Objects.isNull(awayTeam)) {
            throw new ScoreboardException("Away team is not supposed to be null");
        } else if (games.keySet().stream().flatMap(game -> Stream.of(game.getHomeTeam(), game.getAwayTeam()))
                .anyMatch(team -> team.equals(homeTeam) || team.equals(awayTeam))) {
            throw new ScoreboardException("One of the given teams is already playing");
        }

        games.put(Game.builder().homeTeam(homeTeam).awayTeam(awayTeam).build(),
                Score.builder().start(LocalDateTime.now()).build());
    }

    public void finishGame(String homeTeam, String awayTeam) throws ScoreboardException {
        Game game;

        if (Objects.isNull(homeTeam) && Objects.isNull(awayTeam)) {
            throw new ScoreboardException("Either home team or away team is not supposed be null");
        } else if (Objects.nonNull(homeTeam) && Objects.nonNull(awayTeam)) {
            game = Game.builder().homeTeam(homeTeam).awayTeam(awayTeam).build();
        } else {
            game = games.keySet().stream()
                    .filter(g -> g.getHomeTeam().equals(homeTeam) || g.getAwayTeam().equals(awayTeam))
                    .findAny().orElse(null);
        }

        if (Objects.isNull(games.remove(game))) {
            throw new ScoreboardException("Given game is not existent");
        }
    }

    public void finishGameForHomeTeam(String team) throws ScoreboardException {
        finishGame(team, null);
    }

    public void finishGameForAwayTeam(String team) throws ScoreboardException {
        finishGame(null, team);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) throws ScoreboardException {
        updateScoreForHome(homeTeam, homeScore);
        updateScoreForAway(awayTeam, awayScore);
    }

    public void updateScoreForHome(String team, int score) throws ScoreboardException {
    }

    public void updateScoreForAway(String team, int score) throws ScoreboardException {
    }

    public List<String> getSummary() {
        return null;
    }

    public Map<Game, Score> getGames() {
        return Collections.unmodifiableMap(games);
    }
}
