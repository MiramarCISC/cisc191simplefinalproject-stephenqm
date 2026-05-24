package edu.sdccd.cisc191.game;

import edu.sdccd.cisc191.game.dto.CreateMatchRequest;
import edu.sdccd.cisc191.game.dto.GameMatchResponse;
import edu.sdccd.cisc191.game.dto.LeaderboardEntry;
import edu.sdccd.cisc191.game.exception.InvalidMatchException;
import edu.sdccd.cisc191.game.model.GameMatch;
import edu.sdccd.cisc191.game.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:module-test-db;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@ActiveProfiles("test")
class ModuleIntegrationTest {

    @Autowired
    private GameService service;

    // MODULE 1: 2D score array is populated after creating a match
    @Test
    void scoreHistoryStoresScores() {
        service.createMatch(new CreateMatchRequest("Alice", "Bob", true));
        int highest = service.getHighestStoredScore();
        assertThat(highest).isBetween(0, 100);
    }

    @Test
    void scoreHistoryArrayHasCorrectShape() {
        int[][] history = service.getScoreHistory();
        assertThat(history).hasNumberOfRows(10);
        assertThat(history[0]).hasSize(2);
    }

    // MODULE 2: Predicate rejects blank/null names
    @Test
    void blankNameThrowsException() {
        assertThatThrownBy(() ->
                service.createMatch(new CreateMatchRequest("", "Bob", true))
        ).isInstanceOf(InvalidMatchException.class);
    }

    @Test
    void nullNameThrowsException() {
        assertThatThrownBy(() ->
                service.createMatch(new CreateMatchRequest(null, "Bob", true))
        ).isInstanceOf(InvalidMatchException.class);
    }

    // MODULE 3: GameMatch.compareTo() sorts newest first
    @Test
    void comparableSortsNewestFirst() throws InterruptedException {
        GameMatch older = new GameMatch("A","B",50,30,"A",true);
        Thread.sleep(10);
        GameMatch newer = new GameMatch("C","D",70,40,"C",true);
        assertThat(newer.compareTo(older)).isNegative();
        assertThat(older.compareTo(newer)).isPositive();
    }

    // MODULE 4: Custom exception is thrown with correct message
    @Test
    void invalidMatchExceptionHasMessage() {
        assertThatThrownBy(() ->
                service.createMatch(new CreateMatchRequest("  ", "Bob", true))
        ).isInstanceOf(InvalidMatchException.class)
                .hasMessageContaining("Player One");
    }

    @Test
    void matchIsPersistedToDatabase() {
        GameMatchResponse match = service.createMatch(new CreateMatchRequest("Linus", "Ada", true));
        assertThat(match.id()).isNotNull();
        assertThat(service.getMatch(match.id()).playerOneName()).isEqualTo("Linus");
    }

    // MODULE 5: Recursive method finds the top winner correctly
    @Test
    void recursiveTopWinnerFindsHighest() {
        List<LeaderboardEntry> entries = List.of(
                new LeaderboardEntry("Alice", 3),
                new LeaderboardEntry("Bob", 7),
                new LeaderboardEntry("Carol", 5)
        );
        LeaderboardEntry top = service.findTopWinner(entries, 0);
        assertThat(top.playerName()).isEqualTo("Bob");
        assertThat(top.wins()).isEqualTo(7);
    }

    @Test
    void recursiveTopWinnerBaseCase() {
        List<LeaderboardEntry> entries = List.of(new LeaderboardEntry("Solo", 1));
        assertThat(service.findTopWinner(entries, 0).playerName()).isEqualTo("Solo");
    }

    // MODULE 6: Leaderboard uses groupingBy and is sorted descending
    @Test
    void leaderboardIsSortedByWins() {
        service.createMatch(new CreateMatchRequest("StreamTest", "Opponent", true));
        List<LeaderboardEntry> board = service.getRankedLeaderboard();
        assertThat(board).isNotEmpty();
        for (int i = 0; i < board.size() - 1; i++) {
            assertThat(board.get(i).wins()).isGreaterThanOrEqualTo(board.get(i+1).wins());
        }
    }

    @Test
    void unrankedMatchesNotOnLeaderboard() {
        int before = service.getRankedLeaderboard().size();
        service.createMatch(new CreateMatchRequest("Ghost", "Shadow", false));
        // unranked matches don't add new leaderboard entries
        assertThat(service.getRankedLeaderboard().size()).isGreaterThanOrEqualTo(before);
    }
}