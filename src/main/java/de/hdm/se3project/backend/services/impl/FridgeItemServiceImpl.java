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

    private FridgeItemRepository fridgeItemRepository;

    public FridgeItemServiceImpl(FridgeItemRepository fridgeItemRepository) {
        this.fridgeItemRepository = fridgeItemRepository;
    }

    public List<FridgeItem> getFridgeItems() {
        return fridgeItemRepository.findAll();
    }

    @Override
    public FridgeItem createFridgeItem(FridgeItem item) {
        item.setId(IdGenerationService.generateId(item));
        return fridgeItemRepository.save(item);
    }

    @Override
    public FridgeItem getFridgeItemById(String id) throws ResourceNotFoundException {
        return fridgeItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));
    }

    @Override
    public FridgeItem updateFridgeItem(FridgeItem updateItem) throws ResourceNotFoundException {

        //Check if it is not null and if the account exists or not
        if (updateItem == null) {new ResourceNotFoundException("Item not found");}
        FridgeItem fridgeItem = fridgeItemRepository.findById(updateItem.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id"));

        //set the new values to update. The not null condition is on FridgeItem model class
        fridgeItem.setId(updateItem.getId());
        fridgeItem.setName(updateItem.getName());
        fridgeItem.setAmount(updateItem.getAmount());
        fridgeItem.setExpirationDate(updateItem.getExpirationDate());
        fridgeItem.setOwnerAccount(updateItem.getOwnerAccount());

        return fridgeItemRepository.save(fridgeItem);
    }

    @Override
    public void deleteFridgeItem(String id) throws ResourceNotFoundException {
        if (fridgeItemRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Item not found for this id :: " + id);
        } else {
            fridgeItemRepository.deleteById(id);
        }
    }

    @Override
    public List<FridgeItem> getFridgeItems(String ownerAccount) {
        List<FridgeItem> fridgeItems = getFridgeItems();

        fridgeItems = getFridgeItemsByOwnerAccount(ownerAccount, fridgeItems);

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
