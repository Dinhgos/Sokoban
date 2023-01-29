package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Boxes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data custom repository for the Boxes entity.
 */
public interface CustomBoxesRepository {

    /**
     * Najde krabice podle id mapy
     * @param id id mapy
     * @return seznam krabic
     */
    @Query(value = "SELECT * FROM Boxes boxes WHERE boxes.map_id = :id", nativeQuery = true)
    List<Boxes> findByMapId(@Param("id") Long id);

    /**
     * Najde krabice podle id save
     * @param id id save
     * @return seznam krabic
     */
    @Query(value = "SELECT * FROM Boxes boxes WHERE boxes.save_id = :id", nativeQuery = true)
    List<Boxes> findBySaveId(@Param("id") Long id);
}
