package com.live.worldcup.scoreboard;

import com.live.worldcup.scoreboard.exception.ScoreBoardException;
import com.live.worldcup.scoreboard.model.Game;
import com.live.worldcup.scoreboard.model.Side;
import com.live.worldcup.scoreboard.model.Team;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.*;

@UtilityClass
public class Scoreboard {
    @Getter
    private static final Set<Team> teams = new HashSet<>();

    @Getter
    private static final SortedSet<Game> games = createGameSet();

    public static void startGame(String homeTeamName, String awayTeamName) throws ScoreBoardException {
        if (StringUtils.isAnyEmpty(homeTeamName, awayTeamName)) {
            throw new ScoreBoardException("Team names are not supposed to be null or empty");
        }

        startGame(createHomeTeam(homeTeamName), createAwayTeam(awayTeamName));
    }

    public static void startGame(Team homeTeam, Team awayTeam) throws ScoreBoardException {
        if (Objects.isNull(homeTeam) || Objects.isNull(awayTeam)) {
            throw new ScoreBoardException("Teams are not supposed to be null");
        } else if (homeTeam.isPlaying() || awayTeam.isPlaying()) {
            throw new ScoreBoardException("At least one of the teams is already playing");
        }

        teams.add(homeTeam);
        teams.add(awayTeam);
        homeTeam.setPlaying(true);
        awayTeam.setPlaying(true);

        games.add(Game.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .startTime(Instant.now())
                .build());
    }

    public static void updateScore(Game game, int homeScore, int awayScore) throws ScoreBoardException {
        if (!games.contains(game)) {
            throw new ScoreBoardException("Game is either not existent or finished");
        } else if (homeScore < game.getHomeTeam().getScore() || awayScore < game.getAwayTeam().getScore()) {
            throw new ScoreBoardException("New scores can't be lower than the old scores");
        }

        games.remove(game);
        game.getHomeTeam().setScore(homeScore);
        game.getAwayTeam().setScore(awayScore);
        games.add(game);
    }

    public static void finishGame(Game game) throws ScoreBoardException {}

    public List<String> getSummary() {
        return Collections.emptyList();
    }

    public void reset() {
        games.clear();
        teams.clear();
    }

    private static SortedSet<Game> createGameSet() {
        var comparator = Comparator.comparingInt(Game::getTotalScore)
                .thenComparing(Game::getStartTime).reversed();

        return new TreeSet<>(comparator);
    }


    private static Team createHomeTeam(String name) {
        return createTeam(name, Side.HOME);
    }

    private static Team createAwayTeam(String name) {
        return createTeam(name, Side.AWAY);
    }
    private static Team createTeam(String name, Side side) {
        return teams.stream().filter(team -> team.getName().equals(name)).findAny()
                .orElse(Team.builder().name(name).side(side).build());
    }
}
