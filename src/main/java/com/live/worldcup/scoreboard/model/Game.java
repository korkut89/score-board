package com.live.worldcup.scoreboard.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;

@Data
@Builder
public class Game {
    private Team homeTeam;
    private Team awayTeam;

    @Setter(value = AccessLevel.PRIVATE)
    private Instant startTime;

    public int getTotalScore() {
        return homeTeam.getScore() + awayTeam.getScore();
    }

    @Override
    public String toString() {
        return homeTeam.toString() + " - " + awayTeam.toString();
    }
}
