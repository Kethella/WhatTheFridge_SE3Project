package de.hdm.se3project.backend.repositories;

import de.hdm.se3project.backend.models.FridgeItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FridgeItemRepository extends MongoRepository<FridgeItem, String> { //Collection obj = FridgeItem
    // & key = String type that is the id
}