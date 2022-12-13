package de.hdm.se3project.backend.controller.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.hdm.se3project.backend.controller.AccountController;
import de.hdm.se3project.backend.model.Account;
import de.hdm.se3project.backend.model.enums.SecurityQuestion;
import de.hdm.se3project.backend.repository.AccountRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@Description("Testing class: AccountController")
class AccountControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountController accountController;

    Account ACCOUNT_1 = new Account("1", "user", "user@mailmail.com", "123456", SecurityQuestion.Q2, "Buddy", null, null);
    Account ACCOUNT_2 = new Account("2", "user2", "user2@mailmail.com", "987654", SecurityQuestion.Q1, "Math", null, null);
    Account ACCOUNT_3 = new Account("3", "use3", "user3@mailmail.com", "741852", SecurityQuestion.Q4, "UserNick", null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    @Description("Testing method: getAllAccounts - Should get all accounts in DB - GET request")
    void getAllAccountsTest() throws Exception{
        List<Account> accountList = new ArrayList<>(Arrays.asList(ACCOUNT_1, ACCOUNT_2, ACCOUNT_3));

        Mockito.when(accountRepository.findAll()).thenReturn(accountList);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("user")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].securityAnswer", is("Math")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].email", is("user3@mailmail.com")));
    }

    @Test
    @Description("testing method: getOneAccount - Should get one account based on its owners ID - GET request")
    void getOneAccountTest() throws Exception{

        Mockito.when(accountRepository.findById("1")).thenReturn(java.util.Optional.ofNullable(ACCOUNT_1));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("user")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.securityAnswer", is("Buddy")));
    }

    /**
    @Test
    @Description("")
    void getAccountSecurityQuestionTextTest() throws Exception {

        Account account = ACCOUNT_3;

        Mockito.when(accountRepository.findById(account.getId())).thenReturn(java.util.Optional.ofNullable(account));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/accounts/{id}", account.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
     */


    @Test
    @Description("Testing Method: createAccount - Should create a new account - POST request")
    void createAccountTest() throws Exception {

        Account newAccount = new Account();

        newAccount.setName("NewUser");
        newAccount.setEmail("NewUser@mailmail.com");
        newAccount.setPassword("123654");
        newAccount.setSecurityQuestion(SecurityQuestion.Q5);
        newAccount.setSecurityAnswer("NewAnswer");
        newAccount.setPersonalRecipes(newAccount.getPersonalRecipes());
        newAccount.setFridgeItems(newAccount.getFridgeItems());

        Mockito.when(accountRepository.save(newAccount)).thenReturn(newAccount);

        String account = objectWriter.writeValueAsString(newAccount);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(account);

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("NewUser")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("NewUser@mailmail.com")));
    }

    @Test
    @Description("Testing method: replaceAccount - should change the name, the security question and the password of the account - PUT request ")
    void replaceAccountTest() throws Exception {
        Account account = ACCOUNT_2;

        account.setName("Replaced account");
        account.setEmail(account.getEmail());
        account.setPassword("abcdef");
        account.setSecurityQuestion(SecurityQuestion.Q5);
        account.setSecurityAnswer("teacherName");
        account.setPersonalRecipes(account.getPersonalRecipes());
        account.setFridgeItems(account.getFridgeItems());

        Mockito.when(accountRepository.findById(account.getId())).thenReturn(java.util.Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        String replacedAccount = objectWriter.writeValueAsString(account);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/accounts/{id}", account.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(replacedAccount);

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Replaced account")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.securityAnswer", is("teacherName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", is("abcdef")));
    }

    @Test
    @Description("Testing method: deleteAccount - deleting ACCOUNT_2 - DELETE request")
    void deleteAccount() throws Exception {

        String accountId = "2";

        Mockito.when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(ACCOUNT_2));

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/accounts/{id}", accountId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
