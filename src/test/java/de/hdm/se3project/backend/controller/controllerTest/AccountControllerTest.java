package de.hdm.se3project.backend.controller.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.hdm.se3project.backend.controller.AccountController;
import de.hdm.se3project.backend.model.Account;
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

    Account ACCOUNT_1 = new Account("1", "user", "user@mailmail.com", "123456", null, null, null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    //TODO: not working
    @Test
    @Description("Testing method: getAllAccounts")
    void getAllAccountsTest() throws Exception{
        List<Account> accountList = new ArrayList<>(Arrays.asList(ACCOUNT_1));

        Mockito.when(accountRepository.findAll()).thenReturn(accountList);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("user")));
    }

    @Test
    void getOneAccountTest() throws Exception{

        Mockito.when(accountRepository.findById("1")).thenReturn(java.util.Optional.ofNullable(ACCOUNT_1));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()));
    }
}
