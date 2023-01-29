package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.GoalPosition;
import com.mycompany.myapp.repository.GoalPositionRepository;
import com.mycompany.myapp.service.dto.CustomGoalPositionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing GoalPosition.
 */
@Service
public class CustomGoalPositionService {

    @Autowired
    private GoalPositionRepository goalPositionRepository;

    /**
     * Najde všechny cílové pozice podle id mapy
     * @param id id mapy
     * @return seznam CustomGoalPositionDTO
     */
    public List<CustomGoalPositionDTO> getAllMapsGoalPositions(Long id) {
        return goalPositionRepository
            .findByMapId(id)
            .stream()
            .map(this::convertToGoalPosition)
            .collect(Collectors.toList());
    }

    /**
     * Převede CustomGoalPositionDTO na GoalPosition
     * @param gp CustomGoalPositionDTO
     * @return GoalPosition
     */
    public GoalPosition convertToGoalPosition(CustomGoalPositionDTO gp) {
        GoalPosition tmp = new GoalPosition();

        tmp.setPositionX(gp.getPositionX());
        tmp.setPositionY(gp.getPositionY());
        tmp.setPositionZ(gp.getPositionZ());

        return tmp;
    }

    /**
     * Převede GoalPosition na CustomGoalPositionDTO
     * @param gp GoalPosition
     * @return CustomGoalPositionDTO
     */
    private CustomGoalPositionDTO convertToGoalPosition(GoalPosition gp) {
        CustomGoalPositionDTO tmp = new CustomGoalPositionDTO();

        tmp.setPositionX(gp.getPositionX());
        tmp.setPositionY(gp.getPositionY());
        tmp.setPositionZ(gp.getPositionZ());

        return tmp;
    }
}
