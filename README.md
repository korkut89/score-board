# score-board

**Description:** A score-board library of the Football World Cup.

# Example usage

## To start game
```
Scoreboard.startGame("Home team name", "Away team name");
Scoreboard.startGame(Team homeTem, Team awayTeam);
```
## To update score
```
scoreboard.updateScore(Game game, int homeScore, int awayScore);
```
## To finish game
```
scoreboard.finishGame(Game game);
```
## To get summary
```
scoreboard.getSummary(); // returns a string list of the unfinished games' scores
```