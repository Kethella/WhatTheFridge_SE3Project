package de.hdm.se3project.backend.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.Account;
import de.hdm.se3project.backend.models.enums.SecurityQuestion;
import de.hdm.se3project.backend.repositories.RecipeRepository;
import de.hdm.se3project.backend.services.AccountService;
import de.hdm.se3project.backend.services.IdGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.*;

/* Controller class for "accounts" MongoDB collection
 * author: ag186
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    static Logger logger = Logger.getLogger(AccountController.class.getName());
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {

        this.accountService = accountService;
    }


    //DO NOT DELETE THIS IS FOR FRONTEND LOGIN
    @GetMapping("/accounts/one/")
    public Account getAccountByEmailPassword(@RequestParam(value = "email") String email,
                          @RequestParam(value = "password") String password){

        return accountService.getAccountByEmailPassword(email, password);
    }


    @GetMapping("/accounts")
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}")
    public Account getAccountById(@PathVariable String id) throws ResourceNotFoundException {
        return accountService.getAccountById(id);
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account newAccount){ //whatever data you submit from the client side will be accepted in the post object
        return accountService.createAccount(newAccount);
    }

    @PutMapping("/accounts/{id}")
    public Account replaceAccount(@PathVariable String id, @RequestBody Account newAccount) throws ResourceNotFoundException {
        return accountService.updateAccount(id, newAccount);
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable String id) throws ResourceNotFoundException {
        accountService.deleteAccount(id);
    }

    @GetMapping("/securityQuestions")
    public String getAllSecurityQuestions() {
        return accountService.getAllSecurityQuestions();
    }
}