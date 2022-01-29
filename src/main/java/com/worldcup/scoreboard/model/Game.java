package com.worldcup.scoreboard.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Game {

    private String homeTeam;
    private String awayTeam;
}
