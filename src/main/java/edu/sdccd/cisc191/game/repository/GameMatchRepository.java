package edu.sdccd.cisc191.game.repository;

import edu.sdccd.cisc191.game.model.GameMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameMatchRepository extends JpaRepository<GameMatch, Long> {
    List<GameMatch> findByRankedTrue();
}