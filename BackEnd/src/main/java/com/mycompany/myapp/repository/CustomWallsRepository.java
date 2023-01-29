package com.mycompany.myapp.repository;


import com.mycompany.myapp.domain.Walls;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data custom repository for the Walls entity.
 */
public interface CustomWallsRepository {

    /**
     * Najde zdi podle id mapy
     * @param id id mapy
     * @return seznam zdí
     */
    @Query(value = "SELECT * FROM Walls walls WHERE walls.map_id = :id", nativeQuery = true)
    List<Walls> findByMapId(@Param("id") Long id);
}
