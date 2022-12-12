package de.hdm.se3project.backend.controller;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.services.FridgeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//controller handle all the APIs of the project
//after this we can make changes in our objects

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class FridgeItemController {

    @Autowired
    private final FridgeItemService fridgeItemService;

    public FridgeItemController(FridgeItemService fridgeItemService) {
        this.fridgeItemService = fridgeItemService;
    }

    @GetMapping("/fridgeItems/oa={ownerAccount}/")
    List<FridgeItem> getFridgeItems(@PathVariable String ownerAccount) {
        return  fridgeItemService.getFridgeItems(ownerAccount);
    }

    @GetMapping("/fridgeItems/{id}")
    FridgeItem getOneFridgeItem(@PathVariable String id) throws ResourceNotFoundException {
        return fridgeItemService.getFridgeItemById(id);
    }

    @PostMapping("/fridgeItems")
    FridgeItem createFridgeItem(@RequestBody FridgeItem newFridgeItem) {
        return fridgeItemService.createFridgeItem(newFridgeItem);
    }

    @PutMapping("/fridgeItems/{id}")
    FridgeItem updateFridgeItem(@PathVariable String id, @RequestBody FridgeItem updatedFridgeItem)
            throws ResourceNotFoundException {
        return fridgeItemService.updateFridgeItem(id, updatedFridgeItem);
    }

    @DeleteMapping("/fridgeItems/{id}")
    void deleteFridgeItem(@PathVariable String id) throws ResourceNotFoundException {
        fridgeItemService.deleteFridgeItem(id);
    }

}

