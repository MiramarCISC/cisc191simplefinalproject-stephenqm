package edu.sdccd.cisc191.game.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class GameMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playerOneName;
    private String playerTwoName;
    private int playerOneScore;
    private int playerTwoScore;
    private String winnerName;
    private boolean ranked;
    private LocalDateTime createdAt;

    protected GameMatch() {
        // JPA requires a no-argument constructor.
    }

    public GameMatch(String playerOneName, String playerTwoName, int playerOneScore,
                     int playerTwoScore, String winnerName, boolean ranked) {
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
        this.winnerName = winnerName;
        this.ranked = ranked;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public boolean isRanked() {
        return ranked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
