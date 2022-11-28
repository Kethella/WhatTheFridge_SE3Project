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

    @Override
    public FridgeItem createFridgeItem(FridgeItem item) {
        item.setId(IdGenerationService.generateId(item));
        return fridgeItemRepository.save(item);
    }

    @Override
    public FridgeItem getFridgeItemById(String id) throws ResourceNotFoundException {
        return fridgeItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id : " + id));
    }

    @Override
    public FridgeItem updateFridgeItem(String id, FridgeItem updateItem) throws ResourceNotFoundException {

        FridgeItem fridgeItem = fridgeItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id : " + id));

        if (updateItem.getName() != null) {
            fridgeItem.setName(updateItem.getName());
        }
        if (updateItem.getAmount() != 0) {
            fridgeItem.setAmount(updateItem.getAmount());
        }
        if (updateItem.getExpirationDate() != null) {
            fridgeItem.setExpirationDate(updateItem.getExpirationDate());
        }
        if (updateItem.getOwnerAccount() != null) {
            fridgeItem.setOwnerAccount(updateItem.getOwnerAccount());
        }

        return fridgeItemRepository.save(updateItem);
    }

    @Override
    public void deleteFridgeItem(String id)  {
        fridgeItemRepository.deleteById(id);
    }

    public List<FridgeItem> getAllFridgeItems() {
        return fridgeItemRepository.findAll();
    }

    @Override
    public List<FridgeItem> getFridgeItems(String ownerAccount, String defaultFridgeItems) {
        List<FridgeItem> fridgeItems = getAllFridgeItems();

        fridgeItems = getFridgeItemsByOwnerAccount(ownerAccount, fridgeItems);

        if (defaultFridgeItems.equals("yes")){
            List<FridgeItem> externalFridgeItems = getFridgeItemsByOwnerAccount(null, getAllFridgeItems());
            fridgeItems.addAll(externalFridgeItems);
        }

        return fridgeItems;

    }

    public List<FridgeItem> getFridgeItemsByOwnerAccount(String ownerAccount, List<FridgeItem> inputedFridgeItem) {

        List<FridgeItem> result = new ArrayList<>();

        for (FridgeItem fridgeItem: inputedFridgeItem) {
            if (fridgeItem.getOwnerAccount() == null) {
                if (ownerAccount == null) {
                    result.add(fridgeItem);
                }
            }
            else if (fridgeItem.getOwnerAccount().equals(ownerAccount)) {
                result.add(fridgeItem);
            }
        }

        if (result.isEmpty()){
            return null;
        }

        return result;
    }
}
