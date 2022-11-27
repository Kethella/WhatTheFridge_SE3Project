package de.hdm.se3project.backend.services;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.model.Recipe;

import java.util.List;

public interface AccountService {

    public abstract Account createAccount(Account account);
    public abstract Account updateAccount(String id, Account account) throws ResourceNotFoundException;

    Account getAccountById(String id) throws ResourceNotFoundException;

    public abstract void deleteAccount(String id) throws ResourceNotFoundException;
    public abstract List<Account> getAllAccounts();
}
