package de.hdm.se3project.backend.repositories;

import de.hdm.se3project.backend.models.Account;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//The API implementation happens in the repository.
// It acts as a link between the model and the database, and has all the methods for CRUD operations.
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {


}
