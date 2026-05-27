package edu.sdccd.cisc191.game.dto;

import edu.sdccd.cisc191.game.model.GameMatch;

import java.time.LocalDateTime;

public record GameMatchResponse(
        Long id,
        String playerOneName,
        String playerTwoName,
        int playerOneScore,
        int playerTwoScore,
        String winnerName,
        boolean ranked,
        LocalDateTime createdAt
) {
    public static GameMatchResponse from(GameMatch match) {
        return new GameMatchResponse(
                match.getId(),
                match.getPlayerOneName(),
                match.getPlayerTwoName(),
                match.getPlayerOneScore(),
                match.getPlayerTwoScore(),
                match.getWinnerName(),
                match.isRanked(),
                match.getCreatedAt()
        );
    }
}