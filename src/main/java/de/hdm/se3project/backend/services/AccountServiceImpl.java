package de.hdm.se3project.backend.services;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        account.setId(IdGenerationService.generateId(account));
        return accountRepository.save(account);
    }
    @Override
    public Account getAccountById(String id) throws ResourceNotFoundException {

        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));
    }

    @Override
    public Account updateAccount(String id, Account newAccount) throws ResourceNotFoundException {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));

        account.setName(newAccount.getName());
        account.setEmail(newAccount.getEmail());
        account.setPassword(newAccount.getPassword());
        account.setSecurityQuestion(newAccount.getSecurityQuestion());
        account.setSecurityAnswer(newAccount.getSecurityAnswer());


        return accountRepository.save(account);
    }
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }
}
