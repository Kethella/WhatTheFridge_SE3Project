package de.hdm.se3project.backend.services;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.FridgeItem;

import java.util.List;

public interface FridgeItemService {

    FridgeItem createFridgeItem(FridgeItem item);
    FridgeItem updateFridgeItem(String id, FridgeItem newItem) throws ResourceNotFoundException;
    void deleteFridgeItem(String id);
    FridgeItem getFridgeItemById(String id) throws ResourceNotFoundException;
    List<FridgeItem> getFridgeItems(String ownerAccount, String defaultFridgeItems);
}
