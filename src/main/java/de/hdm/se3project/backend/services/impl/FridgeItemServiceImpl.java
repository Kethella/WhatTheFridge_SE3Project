package de.hdm.se3project.backend.services.impl;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import de.hdm.se3project.backend.services.FridgeItemService;
import de.hdm.se3project.backend.services.IdGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FridgeItemServiceImpl implements FridgeItemService {

    @Autowired
    private FridgeItemRepository fridgeItemRepository;

    //TODO : having a problem to use it as a list
    @Override
    public List<FridgeItem> getFridgeItems(String ownerAccount) {

        List<FridgeItem> allFridgeItems = fridgeItemRepository.findAll();

        List<FridgeItem> returnFridgeItems = new ArrayList<>();
        for (FridgeItem fridgeItem: allFridgeItems){
            if(fridgeItem.getOwnerAccount().equals(ownerAccount)){
                returnFridgeItems.add(fridgeItem);
            }
        }

        return returnFridgeItems;
    }

    //TODO: you shouldn't be able to access all the fridgeItems (also from other accounts)
    @Override
    public FridgeItem getFridgeItemById(String id) throws ResourceNotFoundException {
        return fridgeItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id : " + id));
    }

    @Override
    public FridgeItem createFridgeItem(FridgeItem item) {
        item.setId(IdGenerationService.generateId(item));
        return fridgeItemRepository.save(item);
    }

    @Override
    public FridgeItem updateFridgeItem(String id, FridgeItem updateItem) throws ResourceNotFoundException {

        FridgeItem fridgeItem = fridgeItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id : " + id));

        fridgeItem.setName(updateItem.getName());
        fridgeItem.setAmount(updateItem.getAmount());
        fridgeItem.setExpirationDate(updateItem.getExpirationDate());
        fridgeItem.setOwnerAccount(updateItem.getOwnerAccount());

        return fridgeItemRepository.save(updateItem);
    }

    @Override
    public void deleteFridgeItem(String id)  {

        fridgeItemRepository.deleteById(id);
    }

}