package de.hdm.se3project.backend.controller;

import java.io.Serializable;
import java.util.List;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.model.Recipe;
import de.hdm.se3project.backend.services.AccountService;
import de.hdm.se3project.backend.services.FridgeItemService;
import de.hdm.se3project.backend.services.IdGenerationService;
import de.hdm.se3project.backend.services.RecipeService;
import de.hdm.se3project.backend.services.impl.FridgeItemServiceImpl;
import de.hdm.se3project.backend.services.impl.RecipeServiceImpl;
import org.springframework.web.bind.annotation.*;

/* Controller class for "accounts" MongoDB collection
 * author: ag186
 */
@RestController
@RequestMapping("/api/v1")
public class AccountController implements Serializable {

    private final AccountService accountService;
    private final RecipeService recipeService;
    private final FridgeItemService fridgeItemService;
    private final RecipeServiceImpl recipeServiceImpl;
    private final FridgeItemServiceImpl fridgeItemServiceImpl;

    public AccountController(AccountService accountService, RecipeService recipeService, FridgeItemService fridgeItemService, RecipeServiceImpl recipeServiceImpl, FridgeItemServiceImpl fridgeItemServiceImpl) {

        this.accountService = accountService;
        this.recipeService = recipeService;
        this.fridgeItemService = fridgeItemService;
        this.recipeServiceImpl = recipeServiceImpl;
        this.fridgeItemServiceImpl = fridgeItemServiceImpl;
    }



    @GetMapping("/accounts")
    List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}")
    Account getOneAccount(@PathVariable String id) throws ResourceNotFoundException {

        return accountService.getAccountById(id);
    }

    //temporary for testing
    /*@GetMapping("/accounts/seqQuestion/{id}")
    String getAccountSecurityQuestionText(@PathVariable String id) throws ResourceNotFoundException {
        Account account = accountService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));

        return account.getSecurityQuestion().getText();
    }
*/

    @PostMapping("/accounts")
    Account createAccount(@RequestBody Account newAccount){ //whatever data you submit prom the client side will be accepted in the post object
        newAccount.setId(IdGenerationService.generateId(newAccount));
        return accountService.createAccount(newAccount);
    }

    @PutMapping("/accounts/{id}")
    Account replaceAccount(@PathVariable String id, @RequestBody Account newAccount) throws ResourceNotFoundException {



        return accountService.updateAccount(id, newAccount);
    }

    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable String id) {
        List <Recipe> recipes = recipeServiceImpl.getAllRecipes();
        for (Recipe r: recipes) {
            recipeService.deleteRecipe(r.getId());
        }
        List <FridgeItem> fridgeItems = fridgeItemServiceImpl.getFridgeItems();
        for (FridgeItem f: fridgeItems) {
            fridgeItemService.deleteFridgeItem(f.getId());
        }
        accountService.deleteAccount(id);
    }
}
