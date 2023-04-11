package com.live.worldcup.scoreboard;

import com.live.worldcup.scoreboard.exception.ScoreBoardException;
import com.live.worldcup.scoreboard.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ScoreboardTest {
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

    @BeforeEach
    public void setUp() {
        Scoreboard.reset();
    }

    @Test
    void test_startGame_Teams_successCase() throws ScoreBoardException {
        Scoreboard.startGame(createTeam(MEXICO), createTeam(CANADA));

        assertThat(Scoreboard.getGames()).anySatisfy(game -> {
            assertThat(game.getHomeTeam().getName()).isEqualTo(MEXICO);
            assertThat(game.getHomeTeam().getScore()).isZero();
            assertThat(game.getAwayTeam().getName()).isEqualTo(CANADA);
            assertThat(game.getAwayTeam().getScore()).isZero();
        });
    }

    @Test
    void test_startGame_TeamNames_successCase() throws ScoreBoardException {
        Scoreboard.startGame(MEXICO, CANADA);

        assertThat(Scoreboard.getGames()).anySatisfy(game -> {
            assertThat(game.getHomeTeam().getName()).isEqualTo(MEXICO);
            assertThat(game.getHomeTeam().getScore()).isZero();
            assertThat(game.getAwayTeam().getName()).isEqualTo(CANADA);
            assertThat(game.getAwayTeam().getScore()).isZero();
        });
    }

    @Test
    void test_startGame_TeamIsNull() {
        assertThatThrownBy(() -> Scoreboard.startGame(createTeam(MEXICO), null))
                .isInstanceOf(ScoreBoardException.class);
    }

    @Test
    void test_startGame_TeamNameIsEmpty() {
        assertThatThrownBy(() -> Scoreboard.startGame(MEXICO, ""))
                .isInstanceOf(ScoreBoardException.class);
    }

    @Test
    void test_startGame_OneOfTheTeamsAlreadyPlaying() throws ScoreBoardException {
        Scoreboard.startGame(SPAIN, MEXICO);
        assertThatThrownBy(() -> Scoreboard.startGame(MEXICO, CANADA))
                .isInstanceOf(ScoreBoardException.class);
    }

    @Test
    void test_startGame_NewGameForTheExistingTeamAfterPreviousGameFinished() throws ScoreBoardException {
        Scoreboard.startGame(SPAIN, MEXICO);
        Scoreboard.finishGame(Scoreboard.getGames().first());
        Scoreboard.startGame(MEXICO, CANADA);

        assertThat(Scoreboard.getGames()).anySatisfy(game -> {
            assertThat(game.getHomeTeam().getName()).isEqualTo(MEXICO);
            assertThat(game.getHomeTeam().getScore()).isZero();
            assertThat(game.getAwayTeam().getName()).isEqualTo(CANADA);
            assertThat(game.getAwayTeam().getScore()).isZero();
        });
    }

    @Test
    void test_updateScore_successCase() throws ScoreBoardException {
        Scoreboard.startGame(MEXICO, CANADA);
        Scoreboard.updateScore(Scoreboard.getGames().first(), 1, 1);

        assertThat(Scoreboard.getGames()).anySatisfy(game -> {
            assertThat(game.getHomeTeam().getName()).isEqualTo(MEXICO);
            assertThat(game.getHomeTeam().getScore()).isOne();
            assertThat(game.getAwayTeam().getName()).isEqualTo(CANADA);
            assertThat(game.getAwayTeam().getScore()).isOne();
        });
    }

    @Test
    void test_updateScore_NonExistentOrFinishedGame() throws ScoreBoardException {
        Scoreboard.startGame(MEXICO, CANADA);
        var game = Scoreboard.getGames().first();
        Scoreboard.finishGame(game);

        assertThatThrownBy(() -> Scoreboard.updateScore(game, 1, 1))
                .isInstanceOf(ScoreBoardException.class);
    }

    @Test
    void test_updateScore_scoreGoesLower() throws ScoreBoardException {
        Scoreboard.startGame(MEXICO, CANADA);
        var game = Scoreboard.getGames().first();
        Scoreboard.updateScore(game, 2, 2);

        assertThatThrownBy(() -> Scoreboard.updateScore(game, 1, 1))
                .isInstanceOf(ScoreBoardException.class);
    }

    @Test
    void test_finishGame_successCase() throws ScoreBoardException {
        Scoreboard.startGame(MEXICO, CANADA);
        Scoreboard.finishGame(Scoreboard.getGames().first());

        assertThat(Scoreboard.getGames()).isEmpty();
    }

    @Test
    void test_finishGame_nonExistentOrFinishedGame() throws ScoreBoardException {
        Scoreboard.startGame(MEXICO, CANADA);
        var game = Scoreboard.getGames().first();
        Scoreboard.finishGame(game);

        assertThatThrownBy(() -> Scoreboard.finishGame(game))
                .isInstanceOf(ScoreBoardException.class);
    }

    @Test
    void test_getSummary() throws ScoreBoardException {
        Scoreboard.startGame(MEXICO, CANADA);
        var game1 = Scoreboard.getGames().first();
        Scoreboard.startGame(SPAIN, BRAZIL);
        var game2 = Scoreboard.getGames().first();
        Scoreboard.startGame(GERMANY, FRANCE);
        var game3 = Scoreboard.getGames().first();
        Scoreboard.startGame(URUGUAY, ITALY);
        var game4 = Scoreboard.getGames().first();
        Scoreboard.startGame(ARGENTINA, AUSTRALIA);
        var game5 = Scoreboard.getGames().first();

        Scoreboard.updateScore(game1, 0, 5);
        Scoreboard.updateScore(game2, 10, 2);
        Scoreboard.updateScore(game3, 2, 2);
        Scoreboard.updateScore(game4, 6, 6);
        Scoreboard.updateScore(game5, 3, 1);

        assertThat(Scoreboard.getSummary()).isNotEmpty().containsExactly("Uruguay 6 - Italy 6",
                "Spain 10 - Brazil 2", "Mexico 0 - Canada 5", "Argentina 3 - Australia 1", "Germany 2 - France 2");
    }

    private Team createTeam(String name) {
        return Team.builder().name(name).build();
    }
}
