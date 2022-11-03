package de.hdm.se3project.backend.controller;

import de.hdm.se3project.backend.exception.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.ItemRepository;
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

    private final ItemRepository repository;

    public FridgeItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/fridgeItems")
    List<FridgeItem> getAllFridgeItems() {
        return  repository.findAll();
    }

    @GetMapping("/fridgeItems/{id}")
    FridgeItem getOneFridgeItem(@PathVariable String id) throws ResourceNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));
    }

    @PostMapping("/fridgeItems")
    FridgeItem createItem(@RequestBody FridgeItem newFridgeItem) {
        newFridgeItem.setId(IdGenerationService.generateId(newFridgeItem));
        return repository.save(newFridgeItem);
    }

    @PutMapping("/fridgeItems/{id}")
    FridgeItem updateItem(@PathVariable String id, @RequestBody FridgeItem updatedFridgeItem)
            throws ResourceNotFoundException {

        FridgeItem itemToUpdate = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));

        itemToUpdate.setName(updatedFridgeItem.getName());
        itemToUpdate.setAmount(updatedFridgeItem.getAmount());
        itemToUpdate.setExpirationDate(updatedFridgeItem.getExpirationDate());
        itemToUpdate.setOwnerAccount(updatedFridgeItem.getOwnerAccount());

        return repository.save(itemToUpdate);
    }

    @DeleteMapping("/fridgeItems/{id}")
    void deleteAccount(@PathVariable String id) {
        repository.deleteById(id);
    }

}
