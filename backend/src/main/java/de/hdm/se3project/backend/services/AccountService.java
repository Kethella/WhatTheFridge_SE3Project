package de.hdm.se3project.backend.services;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.Account;

import java.util.List;

public interface AccountService {

    Account createAccount(Account account);
    Account updateAccount(String id, Account account) throws ResourceNotFoundException;

    Account getAccountById(String id) throws ResourceNotFoundException;

    void deleteAccount(String id) throws ResourceNotFoundException;
    List<Account> getAllAccounts();

    String getAllSecurityQuestions();

    Account getAccountByEmailPassword(String email, String password);
}
