# score-board

**Description:** A score-board library of the Football World Cup.

# Example usage

## To start game
```
Scoreboard scoreboard = new Scoreboard();
scoreboard.startGame("Home team name", "Away team name");
```
## To finish game
```
scoreboard.finishGame("Home team name", "Away team name");
scoreboard.finishGameForHomeTeam("Home team name");
scoreboard.finishGameForAwayTeam("Away team name");
```
## To update score
```
scoreboard.updateScore("Home team name", "Away team name", homeScore, awayScore);
scoreboard.updateScoreForHome("Home team name", homeScore);
scoreboard.updateScoreForAway("Away team name", awayScore);
```
## To get summary
```
scoreboard.getSummary(); // returns a string list of the unfinished games' scores
```