package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data custom repository for the Map entity.
 */
public interface CustomPlayerRepository {

    /**
     * Najde hráče podle jména
     * @param name jméno hráče
     * @return Player
     */
    @Query(value = "SELECT * FROM PLAYER player where player.name = :name", nativeQuery = true)
    Optional<Player> findByName(@Param("name") String name);

    /**
     * Najde hráče podle id
     * @param id id hráče
     * @return Player
     */
    @Query(value = "SELECT * FROM PLAYER player where player.id = :id", nativeQuery = true)
    Player findByID(@Param("id") Long id);
}
