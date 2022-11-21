package de.hdm.se3project.backend.controller;

import java.io.Serializable;
import java.util.List;

import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.repository.AccountRepository;
import de.hdm.se3project.backend.services.AccountService;
import de.hdm.se3project.backend.services.IdGenerationService;
import org.springframework.web.bind.annotation.*;

/* Controller class for "accounts" MongoDB collection
 * author: ag186
 */
@RestController
@RequestMapping("/api/v1")
public class AccountController implements Serializable {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
        accountService.deleteAccount(id);


        ;
    }
}
