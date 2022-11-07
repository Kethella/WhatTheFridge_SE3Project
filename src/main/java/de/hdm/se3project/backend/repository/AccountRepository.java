package de.hdm.se3project.backend.repository;

import de.hdm.se3project.backend.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

//The API implementation happens in the repository.
// It acts as a link between the model and the database, and has all the methods for CRUD operations.
public interface AccountRepository extends MongoRepository<Account, String> {

}
