package de.hdm.se3project.backend.repository;

import de.hdm.se3project.backend.model.FridgeItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<FridgeItem, String> {

}