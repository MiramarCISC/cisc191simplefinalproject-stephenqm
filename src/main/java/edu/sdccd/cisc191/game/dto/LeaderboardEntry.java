package edu.sdccd.cisc191.game.dto;

public record LeaderboardEntry(
        String playerName,
        long wins
) {
}