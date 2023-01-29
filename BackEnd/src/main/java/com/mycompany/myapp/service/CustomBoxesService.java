package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Boxes;
import com.mycompany.myapp.repository.BoxesRepository;
import com.mycompany.myapp.service.dto.CustomBoxesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing Boxes.
 */
@Service
public class CustomBoxesService {

    @Autowired
    private BoxesRepository boxesRepository;

    /**
     * Najde všechny krabice podle id mapy
     * @param id id mapy
     * @return seznam CustomBoxesDTO
     */
    public List<CustomBoxesDTO> getAllMapsBoxes(Long id) {
        return boxesRepository
            .findByMapId(id)
            .stream()
            .map(this::convertToBoxesDTO)
            .collect(Collectors.toList());
    }

    /**
     * Převede CustomBoxesDTO na Boxes
     * @param cb CustomBoxesDTO
     * @return Boxes
     */
    public Boxes convertToBoxes(CustomBoxesDTO cb) {
        Boxes tmp = new Boxes();

        tmp.setPositionX(cb.getPositionX());
        tmp.setPositionY(cb.getPositionY());
        tmp.setPositionZ(cb.getPositionZ());

        return tmp;
    }

    /**
     * Převede Boxes na CustomBoxesDTO
     * @param boxes Boxes
     * @return CustomBoxesDTO
     */
    private CustomBoxesDTO convertToBoxesDTO(Boxes boxes) {
        CustomBoxesDTO tmp = new CustomBoxesDTO();

        tmp.setPositionX(boxes.getPositionX());
        tmp.setPositionY(boxes.getPositionY());
        tmp.setPositionZ(boxes.getPositionZ());

        return tmp;
    }
}
