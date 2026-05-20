package edu.sdccd.cisc191.game.controller;

import edu.sdccd.cisc191.game.dto.CreateMatchRequest;
import edu.sdccd.cisc191.game.dto.GameMatchResponse;
import edu.sdccd.cisc191.game.dto.LeaderboardEntry;
import edu.sdccd.cisc191.game.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {
    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("/matches")
    @ResponseStatus(HttpStatus.CREATED)
    public GameMatchResponse createMatch(@RequestBody CreateMatchRequest request) {
        return service.createMatch(request);
    }

    @GetMapping("/matches")
    public List<GameMatchResponse> listMatches() {
        return service.listMatches();
    }

    @GetMapping("/matches/{id}")
    public GameMatchResponse getMatch(@PathVariable long id) {
        return service.getMatch(id);
    }

    @DeleteMapping("/matches/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMatch(@PathVariable long id) {
        service.deleteMatch(id);
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardEntry> getLeaderboard() {
        return service.getRankedLeaderboard();
    }
}
