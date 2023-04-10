package com.live.worldcup.scoreboard.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Team {
    private String name;
    private boolean playing;
    private int score;
    private Side side;
}
