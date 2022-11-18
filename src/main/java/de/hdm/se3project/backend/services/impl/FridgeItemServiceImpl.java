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

        return null;
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
    public FridgeItem updateFridgeItem(String id, FridgeItem newItem) throws ResourceNotFoundException {

        FridgeItem fridgeItem = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id : " + id));

        fridgeItem.setName(newItem.getName());
        fridgeItem.setAmount(newItem.getAmount());
        fridgeItem.setExpirationDate(newItem.getExpirationDate());
        fridgeItem.setOwnerAccount(newItem.getOwnerAccount());

        return itemRepository.save(newItem);
    }

    @Override
    public void deleteFridgeItem(String id)  {

        itemRepository.deleteById(id);
    }

}