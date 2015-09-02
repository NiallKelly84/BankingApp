package controller;

import com.bank.dto.AccountInfo;
import com.bank.infrastructure.Application;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by eniakel on 01/09/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class AccountControllerITest {

    private MockMvc mockMvc;

    ObjectMapper om;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        om = new ObjectMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //mockMvc = MockMvcBuilders.standaloneSetup(new AccountController()).build();
    }

    @Test
    public void getAccounts() throws Exception {
        final String SURNAME = "KELLY";
        final String NAME = "ALAN";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts"))
                .andExpect(status().isOk())
                .andReturn();

        List<AccountInfo> accountList = readAccounts(mvcResult);
        Collections.sort(accountList, (a1, a2) ->
                Integer.toString(a1.getId()).compareTo(Integer.toString(a2.getId())));

        // assertions
        assertEquals(accountList.size(), 11);
        assertEquals(accountList.get(0).getId(), 1);
        assertEquals(accountList.get(0).getBalance(), 1256.42, 0.01);

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts")
                .param("surName", SURNAME))
                .andExpect(status().isOk())
                .andReturn();

        accountList = readAccounts(mvcResult);
        // assertions
        assertEquals(accountList.size(), 4);

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts")
                .param("firstName", NAME)
                .param("surName", SURNAME))
                .andExpect(status().isOk())
                .andReturn();

        accountList = readAccounts(mvcResult);
        // assertions
        assertEquals(accountList.size(), 1);
        assertEquals(accountList.get(0).getId(), 7);
        assertEquals(accountList.get(0).getBalance(), 766.54, 0.01);
    }

    private List<AccountInfo> readAccounts(MvcResult mvcResult) throws java.io.IOException {
        return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<AccountInfo>>() {
        });
    }
}

