package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GoalPosition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data custom repository for the GoalPosition entity.
 */
public interface CustomGoalPositionRepository {

    /**
     * Najde cílovou pozici podle id mapy
     * @param id id mapy
     * @return seznam cílových pozic
     */
    @Query(value = "SELECT * FROM GOAL_POSITION gp WHERE gp.map_id = :id", nativeQuery = true)
    List<GoalPosition> findByMapId(@Param("id") Long id);

}
