package de.hdm.se3project.backend.jUnitTest.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.hdm.se3project.backend.controllers.AccountController;
import de.hdm.se3project.backend.exceptions.ResourceNotFoundException;
import de.hdm.se3project.backend.models.Account;
import de.hdm.se3project.backend.models.enums.SecurityQuestion;
import de.hdm.se3project.backend.repositories.AccountRepository;
import de.hdm.se3project.backend.services.AccountService;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@Description("Testing class: AccountController")
class AccountControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    Account ACCOUNT_1 = new Account("1", "user", "user@mailmail.com", "123456",
            SecurityQuestion.Q2, "Buddy", null, null);
    Account ACCOUNT_2 = new Account("2", "user2", "user2@mailmail.com", "987654",
            SecurityQuestion.Q1, "Math", null, null);
    Account ACCOUNT_3 = new Account("3", "use3", "user3@mailmail.com", "741852",
            SecurityQuestion.Q4, "UserNick", null, null);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    //TODO: delete
    @Test
    @Description("Testing method: getAllAccounts - Should get all accounts in DB - GET request")
    public void getAllAccountsTest() throws Exception{

        List<Account> accountList = new ArrayList<>(Arrays.asList(ACCOUNT_1, ACCOUNT_2, ACCOUNT_3));

        Mockito.when(accountService.getAllAccounts()).thenReturn(accountList);

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
    public void getAccountById() throws Exception {
        Mockito.when(accountService.getAccountById(ACCOUNT_1.getId())).thenReturn((ACCOUNT_1));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/{id}", ACCOUNT_1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("user")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.securityAnswer", is("Buddy")));
    }

    @Test
    @Description("Testing Method: createAccount - Should create a new account - POST request")
    public void createAccountTest() throws Exception {

        Mockito.when(accountService.createAccount(ACCOUNT_1)).thenReturn(ACCOUNT_1);

        String accountStr = objectWriter.writeValueAsString(ACCOUNT_1);

        MockHttpServletRequestBuilder mockRequest
                = MockMvcRequestBuilders.post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(accountStr);

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("user")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("user@mailmail.com")));
    }

    @Test
    @Description("Testing method: replaceAccount - Should change infos of the account - PUT request ")
    public void replaceAccountTest() throws Exception {
        Account account = ACCOUNT_2;

        account.setName("Replaced account");
        account.setPassword("abcdef");
        account.setSecurityQuestion(SecurityQuestion.Q5);
        account.setSecurityAnswer("teacherName");

        Mockito.when(accountService.updateAccount(account.getId(),account)).thenReturn(account);

        String replacedAccount = objectWriter.writeValueAsString(account);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/accounts/{id}",
                        account.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(replacedAccount);

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Replaced account")))  //New value
                .andExpect(MockMvcResultMatchers.jsonPath("$.securityAnswer", is("teacherName")))  //New value
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", is("abcdef")))  //new value
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("user2@mailmail.com"))); //old value
    }

    @Test
    @Description("Testing method: deleteAccount - Check if the delete method of the controller " +
            "class is invoking the appropriate repository method and passing the correct parameters")
    public void deleteAccountTest() throws ResourceNotFoundException {

        accountController.deleteAccount(ACCOUNT_3.getId());
        Mockito.verify(accountService).deleteAccount(ACCOUNT_3.getId());  //check that the deleteById method on AccountRepository mock object was called with the same id.
    }

    @Test
    @Description("Testing method: getAllSecurityQuestions() - Check if the method returns all available security questions")
    public void getAllSecurityQuestions() throws Exception {
        String str = "[{\"enumValue\":\"Q1\",\"text\":\"What was the first exam you failed?\"},{\"enumValue\":\"Q2\",\"text\":\"What was the name of your first stuffed animal?\"},{\"enumValue\":\"Q3\",\"text\":\"In what city did you meet your spouse/significant other?\"},{\"enumValue\":\"Q4\",\"text\":\"What was your childhood nickname?\"},{\"enumValue\":\"Q5\",\"text\":\"What was the last name of your third grade teacher?\"}]";

        Mockito.when(accountService.getAllSecurityQuestions()).thenReturn(str);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/securityQuestions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(str));

    }

    @Test
    @Description("Testing method: getAccountByEmailPassword() - Check if the method returns the existing account based on email and password ()")
    public void getAccountByEmailPassword() throws Exception {
        String email = ACCOUNT_2.getEmail();
        String password = ACCOUNT_2.getPassword();


        Mockito.when(accountService.getAccountByEmailPassword(email,password)).thenReturn(ACCOUNT_2);


        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/one/")
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("user2")));

    }

}

