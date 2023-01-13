package de.hdm.se3project.backend.controller;

import java.io.Serializable;
import java.util.List;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.repository.AccountRepository;
import de.hdm.se3project.backend.services.IdGenerationService;
import org.springframework.web.bind.annotation.*;

/* Controller class for "accounts" MongoDB collection
 * author: ag186
 */
@RestController
@RequestMapping("/api/v1")
public class AccountController implements Serializable {

    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts(){
        return repository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public Account getOneAccount(@PathVariable String id) throws ResourceNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account newAccount){ //whatever data you submit from the client side will be accepted in the post object
        newAccount.setId(IdGenerationService.generateId(newAccount));
        return repository.save(newAccount);
    }

    @PutMapping("/accounts/{id}")
    public Account replaceAccount(@PathVariable String id, @RequestBody Account newAccount) throws ResourceNotFoundException {

        Account account = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));

        account.setName(newAccount.getName());
        account.setEmail(newAccount.getEmail());
        account.setPassword(newAccount.getPassword());
        account.setSecurityQuestion(newAccount.getSecurityQuestion());
        account.setSecurityAnswer(newAccount.getSecurityAnswer());
        account.setPersonalRecipes(newAccount.getPersonalRecipes());
        account.setFridgeItems(newAccount.getFridgeItems());

        return repository.save(account);
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable String id) {
        repository.deleteById(id);
    }
}

