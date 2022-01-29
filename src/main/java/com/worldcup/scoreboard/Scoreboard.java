package com.worldcup.scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.worldcup.scoreboard.exception.ScoreboardException;
import com.worldcup.scoreboard.model.Game;
import com.worldcup.scoreboard.model.Score;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Scoreboard {

    private final Map<Game, Score> games = new HashMap<>();

    public void startGame(String homeTeam, String awayTeam) throws ScoreboardException {
    }

    public void finishGame(String homeTeam, String awayTeam) throws ScoreboardException {
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
}
