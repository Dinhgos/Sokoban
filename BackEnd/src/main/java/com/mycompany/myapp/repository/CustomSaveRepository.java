package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Save;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data custom repository for the Save entity.
 */
public interface CustomSaveRepository {

    /**
     * Najde save podle id hráče
     * @param id id hráče
     * @return seznam savu
     */
    @Query(value = "SELECT * FROM SAVE save WHERE save.player_id = :id", nativeQuery = true)
    List<Save> findByPlayerId(@Param("id") Long id);

    /**
     * Najde save podle id mapy
     * @param id id mapy
     * @return seznam savu
     */
    @Query(value = "SELECT * FROM SAVE save where save.map_id = :id", nativeQuery = true)
    List<Save> findByMapId(@Param("id") Long id);

}
