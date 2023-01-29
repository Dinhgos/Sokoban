package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data custom repository for the Map entity.
 */
public interface CustomMapRepository {

    /**
     * Najde mapu podle id mapy
     * @param id id mapy
     * @return map
     */
    @Query(value = "SELECT * FROM Map map WHERE map.id = :id", nativeQuery = true)
    Optional<Map> findByMapId(@Param("id") Long id);
}
