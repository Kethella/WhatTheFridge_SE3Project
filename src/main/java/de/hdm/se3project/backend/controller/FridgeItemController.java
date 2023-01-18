package de.hdm.se3project.backend.controller;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.services.FridgeItemService;
import de.hdm.se3project.backend.services.IdGenerationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class FridgeItemController {

    private final FridgeItemService service;

    public FridgeItemController(FridgeItemService service) {
        this.service = service;
    }

    @GetMapping("/fridgeItems/oa={ownerAccount}")
    List<FridgeItem> getAllFridgeItems(@PathVariable String ownerAccount) {
        return  service.getFridgeItems(ownerAccount);
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
