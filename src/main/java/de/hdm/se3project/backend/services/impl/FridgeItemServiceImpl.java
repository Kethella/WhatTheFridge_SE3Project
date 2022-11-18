package de.hdm.se3project.backend.services.impl;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.ItemRepository;
import de.hdm.se3project.backend.services.FridgeItemService;
import de.hdm.se3project.backend.services.IdGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FridgeItemServiceImpl implements FridgeItemService {

    @Autowired
    private ItemRepository itemRepository;

    //TODO : having a problem to use it as a list
    @Override
    public List<FridgeItem> getFridgeItems() {

        return itemRepository.findAll();
    }

    @Override
    public FridgeItem getFridgeItemById(String id) throws ResourceNotFoundException {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id : " + id));
    }

    @Override
    public FridgeItem createFridgeItem(FridgeItem item) {
        item.setId(IdGenerationService.generateId(item));
        return itemRepository.save(item);
    }

    @Override
    public FridgeItem updateFridgeItem(String id, FridgeItem updateItem) throws ResourceNotFoundException {

        FridgeItem fridgeItem = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id : " + id));

        fridgeItem.setName(updateItem.getName()); //maybe this one we could delete
        fridgeItem.setAmount(updateItem.getAmount());
        fridgeItem.setExpirationDate(updateItem.getExpirationDate());
        fridgeItem.setOwnerAccount(updateItem.getOwnerAccount()); //this one I think we could also delete

        return itemRepository.save(updateItem);
    }

    @Override
    public void deleteFridgeItem(String id)  {

        itemRepository.deleteById(id);
    }

}