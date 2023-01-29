package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Save entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaveRepository extends JpaRepository<Save, Long>, CustomSaveRepository {

}
