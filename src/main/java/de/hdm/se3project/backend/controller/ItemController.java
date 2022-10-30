package de.hdm.se3project.backend.controller;

import de.hdm.se3project.backend.exception.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.ItemRepository;
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

public class ItemController {

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/fridgeItems")
    List<FridgeItem> getAllFridgeItems() {
        return  repository.findAll();
    }

    @GetMapping("/fridgeItems/{idItem}")
    FridgeItem getOneFridgeItem(@PathVariable String idItem) throws ResourceNotFoundException {
        return repository.findById(idItem)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + idItem));
    }

    @PostMapping("/fridgeItems")
    FridgeItem createItem(@RequestBody FridgeItem newFridgeItem) {
        return repository.save(newFridgeItem);
    }

    @PutMapping("/fridgeItems/{idItem}")
    FridgeItem updateItem(@PathVariable String idItem, @RequestBody FridgeItem updatedFridgeItem)
            throws ResourceNotFoundException {

        FridgeItem itemToUpdate = this.getOneFridgeItem(idItem);

        itemToUpdate.setAmountItem(updatedFridgeItem.getAmountItem());
        itemToUpdate.setExpirationDate(updatedFridgeItem.getExpirationDate());

        return repository.save(itemToUpdate);
    }

    /**
    //@PutMapping("/fridgeItems/{idItem}")
    FridgeItem replaceItemData(@PathVariable String idItem, @RequestBody FridgeItem newFridgeItem)
            throws ResourceNotFoundException {
                FridgeItem item = repository.findById(idItem)
                        .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + idItem));

                //maybe just the AmountItem and ExpirationDate is necessary?
                item.setIdItem(newFridgeItem.getIdItem());
                item.setNameItem(newFridgeItem.getNameItem());
                item.setAmountItem(newFridgeItem.getAmountItem());
                item.setExpirationDate(newFridgeItem.getExpirationDate());

                return repository.save(item);
            }
     **/

    @DeleteMapping("/fridgeItems/{idItem}")
    void deleteAccount(@PathVariable String idItem) {
        repository.deleteById(idItem);
    }

}
