package de.hdm.se3project.backend.controller;

import java.io.Serializable;
import java.util.List;

import de.hdm.se3project.backend.exception.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.repository.AccountRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;

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
    List<Account> getAllAccounts(){
        return repository.findAll();
    }

    @GetMapping("/accounts/{id}")
    Account getOneAccount(@PathVariable String id) throws ResourceNotFoundException {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));
    }


    //temporary for testing
    @GetMapping("/accounts/seqQuestion/{id}")
    String getAccountSecurityQuestionText(@PathVariable String id) throws ResourceNotFoundException {
        Account account = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));

        return account.getSecurityQuestion().getText();
    }


    @PostMapping("/accounts")
    Account createAccount(@RequestBody Account newAccount){ //whatever data you submit prom the client side will be accepted in the post object

        return repository.save(newAccount);
    }

    @PutMapping("/accounts/{id}")
    Account replaceAccount(@PathVariable String id, @RequestBody Account newAccount) throws ResourceNotFoundException {

        Account account = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));

        account.setName(newAccount.getName());
        account.setEmail(newAccount.getEmail());
        account.setPassword(newAccount.getPassword());
        account.setSecurityQuestion(newAccount.getSecurityQuestion());
        account.setSecurityAnswer(newAccount.getSecurityAnswer());

        return repository.save(account);
    }

    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable String id) {
        repository.deleteById(id);
    }
}
