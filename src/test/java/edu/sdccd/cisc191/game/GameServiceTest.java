package edu.sdccd.cisc191.game;

import edu.sdccd.cisc191.game.dto.CreateMatchRequest;
import edu.sdccd.cisc191.game.dto.GameMatchResponse;
import edu.sdccd.cisc191.game.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:service-test-db;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@ActiveProfiles("test")
class GameServiceTest {
    @Autowired
    private GameService service;

    @Test
    void createMatchSavesWinnerAndScores() {
        GameMatchResponse match = service.createMatch(new CreateMatchRequest("Ada", "Grace", true));

        assertThat(match.id()).isNotNull();
        assertThat(match.playerOneName()).isEqualTo("Ada");
        assertThat(match.playerTwoName()).isEqualTo("Grace");
        assertThat(match.winnerName()).isIn("Ada", "Grace");
        assertThat(match.playerOneScore()).isNotEqualTo(match.playerTwoScore());
    }

    @Test
    void listMatchesReturnsSavedMatches() {
        service.createMatch(new CreateMatchRequest("Linus", "Margaret", false));

        assertThat(service.listMatches())
                .extracting(GameMatchResponse::playerOneName)
                .contains("Linus");
    }
}
