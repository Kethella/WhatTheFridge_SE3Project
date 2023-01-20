package de.hdm.se3project.backend.services.impl;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.model.FridgeItem;
import de.hdm.se3project.backend.model.Recipe;
import de.hdm.se3project.backend.repository.AccountRepository;
import de.hdm.se3project.backend.repository.RecipeRepository;
import de.hdm.se3project.backend.services.AccountService;
import de.hdm.se3project.backend.services.FridgeItemService;
import de.hdm.se3project.backend.services.IdGenerationService;
import de.hdm.se3project.backend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private FridgeItemService fridgeItemService;

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
    public void deleteAccount(String id) throws ResourceNotFoundException {

        //delete all recipes connected to the account
        List<Recipe> recipes = recipeService.getRecipes(id, "no", null, null, null);
        if (recipes != null) {
            for (Recipe recipe : recipes) {
                recipeService.deleteRecipe(recipe.getId());
            }
        }

        //delete all fridgeItems connected to the account
        List<FridgeItem> fridgeItems = fridgeItemService.getFridgeItems(id);
        if (fridgeItems != null) {
            for (FridgeItem fridgeItem : fridgeItems) {
                fridgeItemService.deleteFridgeItem(fridgeItem.getId());
            }
        }

        accountRepository.deleteById(id);
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
        account.setPersonalRecipes(newAccount.getPersonalRecipes());
        account.setFridgeItems(newAccount.getFridgeItems());


        return accountRepository.save(account);
    }

    //TODO: delete
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
