package de.hdm.se3project.backend.repository;

import de.hdm.se3project.backend.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

}
