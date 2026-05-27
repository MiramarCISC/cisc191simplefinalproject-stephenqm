package edu.sdccd.cisc191.game.dto;

public record CreateMatchRequest(
        String playerOneName,
        String playerTwoName,
        boolean ranked
) {
}