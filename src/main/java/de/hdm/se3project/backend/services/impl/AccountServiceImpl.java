package de.hdm.se3project.backend.services.impl;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.Account;
import de.hdm.se3project.backend.models.FridgeItem;
import de.hdm.se3project.backend.models.Recipe;
import de.hdm.se3project.backend.models.enums.SecurityQuestion;
import de.hdm.se3project.backend.repositories.AccountRepository;
import de.hdm.se3project.backend.services.AccountService;
import de.hdm.se3project.backend.services.FridgeItemService;
import de.hdm.se3project.backend.services.IdGenerationService;
import de.hdm.se3project.backend.services.RecipeService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private FridgeItemService fridgeItemService;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

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

    @Override
    public String getAllSecurityQuestions() {
        List<JSONObject> list = new ArrayList<>();
        JSONArray array = new JSONArray();

        for(SecurityQuestion q: SecurityQuestion.values()) {

            HashMap<String, String> seqQuestion = new HashMap<String, String>();
            seqQuestion.put("enumValue", q.toString());
            seqQuestion.put("text", q.getText());
            JSONObject seqQuestionObject = new JSONObject(seqQuestion);
            System.out.println(seqQuestionObject);
            array.put(seqQuestionObject);
        }
        System.out.println(array.toString());
        return array.toString();
    }

    @Override
    public Account getAccountByEmailPassword(String email, String password){

        List<Account> allAccounts = accountRepository.findAll();
        for (Account account: allAccounts) {
            if(account.getEmail().equals(email) && account.getPassword().equals(password)){
                return account;
            }
        }

        return null;
    }
}
