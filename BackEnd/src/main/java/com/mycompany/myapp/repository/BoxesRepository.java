package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Boxes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Boxes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoxesRepository extends JpaRepository<Boxes, Long>, CustomBoxesRepository {

}
