package de.hdm.se3project.backend.services.impl;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.repository.FridgeItemRepository;
import de.hdm.se3project.backend.services.FridgeItemService;
import de.hdm.se3project.backend.services.IdGenerationService;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

@Service
public class FridgeItemServiceImpl implements FridgeItemService {

    final static Logger log = LogManager.getLogger(FridgeItemServiceImpl.class); // can't be private

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
    public FridgeItem updateFridgeItem(String id, FridgeItem updateItem) throws ResourceNotFoundException {

        FridgeItem fridgeItem = fridgeItemRepository.findById(updateItem.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id"));

        if (updateItem.getName() != null) {
            fridgeItem.setName(updateItem.getName());
        }

        try {
            if (updateItem.getAmount() != 0) {
                fridgeItem.setAmount(updateItem.getAmount());
            }
        }
        catch (NullPointerException exception) {
            log.info("no amount change");
        }

        if (updateItem.getExpirationDate() != null) {
            fridgeItem.setExpirationDate(updateItem.getExpirationDate());
        }


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
