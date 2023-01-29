package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Walls;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Walls entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WallsRepository extends JpaRepository<Walls, Long>, CustomWallsRepository {
}
