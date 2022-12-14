package de.hdm.se3project.backend.controller;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.services.FridgeItemService;
import de.hdm.se3project.backend.services.IdGenerationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class FridgeItemController {

    private final FridgeItemService service;

    public FridgeItemController(FridgeItemService service) {
        this.service = service;
    }

    @GetMapping("/fridgeItems")
    List<FridgeItem> getAllFridgeItems() {
        return  service.getFridgeItems();
    }

    @GetMapping("/fridgeItems/{id}")
    FridgeItem getOneFridgeItem(@PathVariable String id) throws ResourceNotFoundException {
        return service.getFridgeItemById(id);
    }

    @PostMapping("/fridgeItems")
    FridgeItem createItem(@RequestBody FridgeItem newFridgeItem) {
        newFridgeItem.setId(IdGenerationService.generateId(newFridgeItem));
        return service.createFridgeItem(newFridgeItem);
    }

    @PutMapping("/fridgeItems/{id}")
    FridgeItem updateItem(@PathVariable String id, @RequestBody FridgeItem updatedFridgeItem)
            throws ResourceNotFoundException {

        return service.updateFridgeItem(id, updatedFridgeItem);
    }

    @DeleteMapping("/fridgeItems/{id}")
    void deleteFridgeItem(@PathVariable String id) {
        service.deleteFridgeItem(id);
    }

}
