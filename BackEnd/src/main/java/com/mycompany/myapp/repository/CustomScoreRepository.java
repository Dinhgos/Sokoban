package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Score;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data custom repository for the Score entity.
 */
public interface CustomScoreRepository {

    /**
     * Najde score podle id hráče
     * @param id id hráče
     * @return seznam score
     */
    @Query(value = "SELECT * FROM Score score WHERE score.player_id = :id", nativeQuery = true)
    List<Score> findByPlayerId(@Param("id") Integer id);

    /**
     * Najde score podle id mapy
     * @param id id mapy
     * @return seznam score
     */
    @Query(value = "SELECT * FROM Score score WHERE score.map_id = :id", nativeQuery = true)
    List<Score> findByMapId(@Param("id") Integer id);

//    /**
//     * Najde score podle id hráče
//     * @param id id hráče
//     * @return score
//     */
//    @Query(value = "SELECT * FROM Score score WHERE score.player_id = :id", nativeQuery = true)
//    Optional<Score> findByScoreId(@Param("id") Long id);
}
