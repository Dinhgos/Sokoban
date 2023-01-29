package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GoalPosition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GoalPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoalPositionRepository extends JpaRepository<GoalPosition, Long>, CustomGoalPositionRepository {
}
