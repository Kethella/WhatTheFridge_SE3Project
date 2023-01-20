package de.hdm.se3project.backend.services;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.FridgeItem;

import java.util.List;

public interface FridgeItemService {

    List<FridgeItem> getFridgeItems(String ownerAccount);
    FridgeItem getFridgeItemById(String id) throws ResourceNotFoundException;
    FridgeItem createFridgeItem(FridgeItem item);
    FridgeItem updateFridgeItem(String id, FridgeItem itemToBeUpdated) throws ResourceNotFoundException;
    void deleteFridgeItem(String id) throws ResourceNotFoundException;
}