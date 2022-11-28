package de.hdm.se3project.backend.controller;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.services.FridgeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class FridgeItemController {

    @Autowired
    private final FridgeItemService fridgeItemService;

    public FridgeItemController(FridgeItemService fridgeItemService) {
        this.fridgeItemService = fridgeItemService;
    }

    @PostMapping("/fridgeItems")
    FridgeItem createFridgeItem(@RequestBody FridgeItem newFridgeItem) {
        //newFridgeItem.setId(IdGenerationService.generateId(newFridgeItem));
        return fridgeItemService.createFridgeItem(newFridgeItem);
    }

    @GetMapping("/fridgeItems/{id}")
    FridgeItem getOneFridgeItem(@PathVariable String id) throws ResourceNotFoundException {
        return fridgeItemService.getFridgeItemById(id);
    }

    @PutMapping("/fridgeItems/{id}")
    FridgeItem updateFridgeItem(@PathVariable String id, @RequestBody FridgeItem updatedFridgeItem)
            throws ResourceNotFoundException {
        return fridgeItemService.updateFridgeItem(id, updatedFridgeItem);
    }

    @DeleteMapping("/fridgeItems/{id}")
    void deleteFridgeItem(@PathVariable String id) {
        fridgeItemService.deleteFridgeItem(id);
    }

    @GetMapping("/fridgeItems/oa={ownerAccount}/")
    List<FridgeItem> getFridgeItems(@PathVariable String ownerAccount) {
        return  fridgeItemService.getFridgeItems(ownerAccount);
    }
}

