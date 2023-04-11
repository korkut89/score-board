package com.live.worldcup.scoreboard.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
public class Team {
    private String name;

    @EqualsAndHashCode.Exclude
    private boolean playing;

    @EqualsAndHashCode.Exclude
    private int score;

    public void finishGame() {
        setScore(0);
        setPlaying(false);
    }
}
