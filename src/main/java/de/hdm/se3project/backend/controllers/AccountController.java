package de.hdm.se3project.backend.controllers;

import java.io.Serializable;
import java.util.List;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.Account;
import de.hdm.se3project.backend.services.AccountService;
import de.hdm.se3project.backend.services.FridgeItemService;
import de.hdm.se3project.backend.services.RecipeService;
import de.hdm.se3project.backend.services.impl.FridgeItemServiceImpl;
import de.hdm.se3project.backend.services.impl.RecipeServiceImpl;
import org.springframework.web.bind.annotation.*;

/* Controller class for "accounts" MongoDB collection
 * author: ag186
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController implements Serializable {

    private final AccountService accountService;
    private final RecipeService recipeService;
    private final FridgeItemService fridgeItemService;


    public AccountController(AccountService accountService, RecipeService recipeService, FridgeItemService fridgeItemService, RecipeServiceImpl recipeServiceImpl, FridgeItemServiceImpl fridgeItemServiceImpl) {

        this.accountService = accountService;
        this.recipeService = recipeService;
        this.fridgeItemService = fridgeItemService;
    }

    //TODO: delete
    @GetMapping()
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    Account getOneAccount(@PathVariable String id) throws ResourceNotFoundException {
        return accountService.getAccountById(id);
    }

    @PostMapping()
    Account createAccount(@RequestBody Account newAccount){ //whatever data you submit prom the client side will be accepted in the post object
        return accountService.createAccount(newAccount);
    }

    @PutMapping("/{id}")
    public Account replaceAccount(@PathVariable String id, @RequestBody Account newAccount) throws ResourceNotFoundException {
        return accountService.updateAccount(id, newAccount);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable String id) throws ResourceNotFoundException {
        accountService.deleteAccount(id);
    }
}
