package com.worldcup.scoreboard.model;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class Score {

    private int homeScore;
    private int awayScore;

    @Setter(value = AccessLevel.PRIVATE)
    private Instant start;

    public int getTotalScore() {
        return homeScore + awayScore;
    }
}
