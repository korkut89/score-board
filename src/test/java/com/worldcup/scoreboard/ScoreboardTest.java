package com.worldcup.scoreboard;

import java.util.List;

import com.worldcup.scoreboard.exception.ScoreboardException;
import com.worldcup.scoreboard.model.Game;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

public class ScoreboardTest {

    private static final String MEXICO = "Mexico";
    private static final String CANADA = "Canada";
    private static final String SPAIN = "Spain";
    private static final String BRAZIL = "Brazil";
    private static final String GERMANY = "Germany";
    private static final String FRANCE = "France";
    private static final String URUGUAY = "Uruguay";
    private static final String ITALY = "Italy";
    private static final String ARGENTINA = "Argentina";
    private static final String AUSTRALIA = "Australia";

    @Test
    public void test_startGame_successCase() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);

        Assertions.assertThat(scoreboard.getGames())
                .containsKey(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build());
        Assertions.assertThat(scoreboard.getGames())
                .hasEntrySatisfying(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build(), score ->
                {
                    Assertions.assertThat(score.getHomeScore()).isZero();
                    Assertions.assertThat(score.getAwayScore()).isZero();
                });
    }

    @Test
    public void test_startGame_homeTeamIsNull() {
        Scoreboard scoreboard = new Scoreboard();

        Assertions.assertThatExceptionOfType(ScoreboardException.class)
                .isThrownBy(() -> scoreboard.startGame(null, CANADA))
                .withMessage("Home team is not supposed to be null");
    }

    @Test
    public void test_startGame_awayTeamIsNull() {
        Scoreboard scoreboard = new Scoreboard();

        Assertions.assertThatExceptionOfType(ScoreboardException.class)
                .isThrownBy(() -> scoreboard.startGame(MEXICO, null))
                .withMessage("Away team is not supposed to be null");
    }

    @Test
    public void test_startGame_OneOfTheTeamsAlreadyPlaying() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);

        Assertions.assertThatExceptionOfType(ScoreboardException.class)
                .isThrownBy(() -> scoreboard.startGame(SPAIN, MEXICO))
                .withMessage("One of the given teams is already playing");
    }

    @Test
    public void test_finishGame_successCase() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);
        scoreboard.finishGame(MEXICO, CANADA);

        Assertions.assertThat(scoreboard.getGames())
                .doesNotContainKey(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build());
    }

    @Test
    public void test_finishGame_successCase_onlyHomeTeam() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);
        scoreboard.finishGameForHomeTeam(MEXICO);

        Assertions.assertThat(scoreboard.getGames())
                .doesNotContainKey(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build());
    }

    @Test
    public void test_finishGame_successCase_onlyAwayTeam() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);
        scoreboard.finishGameForAwayTeam(CANADA);

        Assertions.assertThat(scoreboard.getGames())
                .doesNotContainKey(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build());
    }

    @Test
    public void test_finishGame_bothTeamsNull() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);

        Assertions.assertThatExceptionOfType(ScoreboardException.class)
                .isThrownBy(() -> scoreboard.finishGame(null, null))
                .withMessage("Either home team or away team is not supposed be null");
    }

    @Test
    public void test_finishGame_nonExistingGame() {
        Scoreboard scoreboard = new Scoreboard();

        Assertions.assertThatExceptionOfType(ScoreboardException.class)
                .isThrownBy(() -> scoreboard.finishGame(MEXICO, CANADA))
                .withMessage("Given game is not existent");
    }

    @Test
    public void test_updateScore_successCase() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);
        scoreboard.updateScore(MEXICO, CANADA, 1, 1);

        Assertions.assertThat(scoreboard.getGames())
                .containsKey(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build());
        Assertions.assertThat(scoreboard.getGames())
                .hasEntrySatisfying(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build(), score ->
                {
                    Assertions.assertThat(score.getHomeScore()).isOne();
                    Assertions.assertThat(score.getAwayScore()).isOne();
                });
    }

    @Test
    public void test_updateScore_successCase_homeScore() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);
        scoreboard.updateScoreForHome(MEXICO, 1);

        Assertions.assertThat(scoreboard.getGames())
                .containsKey(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build());
        Assertions.assertThat(scoreboard.getGames())
                .hasEntrySatisfying(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build(), score ->
                {
                    Assertions.assertThat(score.getHomeScore()).isOne();
                    Assertions.assertThat(score.getAwayScore()).isZero();
                });
    }

    @Test
    public void test_updateScore_successCase_awayScore() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);
        scoreboard.updateScoreForAway(CANADA, 1);

        Assertions.assertThat(scoreboard.getGames())
                .containsKey(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build());
        Assertions.assertThat(scoreboard.getGames())
                .hasEntrySatisfying(Game.builder().homeTeam(MEXICO).awayTeam(CANADA).build(), score ->
                {
                    Assertions.assertThat(score.getHomeScore()).isZero();
                    Assertions.assertThat(score.getAwayScore()).isOne();
                });
    }

    @Test
    public void test_updateScore_homeAndAwayTeamsNull() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);

        Assertions.assertThatExceptionOfType(ScoreboardException.class)
                .isThrownBy(() -> scoreboard.updateScore(null, null, 1, 1))
                .withMessage("Team names are not supposed be null");
    }

    @Test
    public void test_updateScore_nonExistentGame() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);

        Assertions.assertThatExceptionOfType(ScoreboardException.class)
                .isThrownBy(() -> scoreboard.updateScore(SPAIN, BRAZIL, 1, 1))
                .withMessage("Given game is not existent");
    }

    @Test
    public void test_updateScore_homeScore_nonExistentGame() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);

        Assertions.assertThatExceptionOfType(ScoreboardException.class)
                .isThrownBy(() -> scoreboard.updateScoreForHome(SPAIN, 1))
                .withMessage("Given home team is not existent");
    }

    @Test
    public void test_updateScore_awayScore_nonExistentGame() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);

        Assertions.assertThatExceptionOfType(ScoreboardException.class)
                .isThrownBy(() -> scoreboard.updateScoreForAway(BRAZIL, 1))
                .withMessage("Given away team is not existent");
    }

    @Test
    public void test_updateScore_scoreGoesLower() throws ScoreboardException {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);
        scoreboard.updateScore(MEXICO, CANADA, 1, 1);

        Assertions.assertThatExceptionOfType(ScoreboardException.class)
                .isThrownBy(() -> scoreboard.updateScore(MEXICO, CANADA, 0, 0))
                .withMessage("New score can't be lower than the old score");
    }

    @Ignore
    @Test
    public void test_getSummary() throws ScoreboardException{
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);
        scoreboard.startGame(SPAIN, BRAZIL);
        scoreboard.startGame(GERMANY, FRANCE);
        scoreboard.startGame(URUGUAY, ITALY);
        scoreboard.startGame(ARGENTINA, AUSTRALIA);

        scoreboard.updateScore(MEXICO, CANADA, 0, 5);
        scoreboard.updateScore(SPAIN, BRAZIL, 10, 2);
        scoreboard.updateScore(GERMANY, FRANCE, 2, 2);
        scoreboard.updateScore(URUGUAY, ITALY, 6, 6);
        scoreboard.updateScore(ARGENTINA, AUSTRALIA, 3, 1);

        List<String> summary = scoreboard.getSummary();
        Assertions.assertThat(summary).isNotEmpty();
        Assertions.assertThat(summary).containsExactly("Uruguay 6 - Italy 6", "Spain 10 - Brazil 2",
                "Mexico 0 - CANADA 5", "Argentina 3 - Australia 1", "Germany 2 - France 2");
    }
}
