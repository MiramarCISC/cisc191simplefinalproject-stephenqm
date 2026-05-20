package edu.sdccd.cisc191.game.service;

import edu.sdccd.cisc191.game.dto.CreateMatchRequest;
import edu.sdccd.cisc191.game.dto.GameMatchResponse;
import edu.sdccd.cisc191.game.dto.LeaderboardEntry;
import edu.sdccd.cisc191.game.model.GameMatch;
import edu.sdccd.cisc191.game.repository.GameMatchRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final GameMatchRepository repository;
    private final Random random = new Random();

    public GameService(GameMatchRepository repository) {
        this.repository = repository;
    }

    public GameMatchResponse createMatch(CreateMatchRequest request) {
        String playerOne = cleanName(request.playerOneName(), "Player One");
        String playerTwo = cleanName(request.playerTwoName(), "Player Two");

        int playerOneScore = random.nextInt(101);
        int playerTwoScore = random.nextInt(101);

        while (playerOneScore == playerTwoScore) {
            playerTwoScore = random.nextInt(101);
        }

        String winner = playerOneScore > playerTwoScore ? playerOne : playerTwo;

        GameMatch saved = repository.save(new GameMatch(
                playerOne,
                playerTwo,
                playerOneScore,
                playerTwoScore,
                winner,
                request.ranked()
        ));

        return GameMatchResponse.from(saved);
    }

    public List<GameMatchResponse> listMatches() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(GameMatch::getCreatedAt).reversed())
                .map(GameMatchResponse::from)
                .toList();
    }

    public GameMatchResponse getMatch(long id) {
        return repository.findById(id)
                .map(GameMatchResponse::from)
                .orElseThrow(() -> new NoSuchElementException("No match found with id " + id));
    }

    public void deleteMatch(long id) {
        repository.deleteById(id);
    }

    public List<LeaderboardEntry> getRankedLeaderboard() {
        Map<String, Long> winsByPlayer = repository.findByRankedTrue().stream()
                .collect(Collectors.groupingBy(GameMatch::getWinnerName, Collectors.counting()));

        return winsByPlayer.entrySet().stream()
                .map(entry -> new LeaderboardEntry(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(LeaderboardEntry::wins).reversed()
                        .thenComparing(LeaderboardEntry::playerName))
                .toList();
    }

    private String cleanName(String name, String defaultName) {
        if (name == null || name.isBlank()) {
            return defaultName;
        }
        return name.trim();
    }
}
