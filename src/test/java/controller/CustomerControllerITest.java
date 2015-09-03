package controller;

import com.bank.db.FlywayTest;
import com.bank.dto.CustomerInfo;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by eniakel on 02/09/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
@FlywayTest(locations = {"db/migration"}, invokeClean = true)
public class CustomerControllerITest {
    private MockMvc mockMvc;

    ObjectMapper om;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        om = new ObjectMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getBranches() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/customer"))
                .andExpect(status().isOk())
                .andReturn();

        List<CustomerInfo> customerList = readCustomers(mvcResult);

        // assertions
        assertEquals(customerList.size(), 5);
        assertEquals(customerList.get(0).getId(), 1);
        assertEquals(customerList.get(0).getFirstName(), "Niall");
        assertEquals(customerList.get(0).getSurName(), "Kelly");
    }
/*
    @Test
    public void getBranchById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/{customerId}", "5"))
                .andExpect(status().isOk())
                .andReturn();

        List<CustomerInfo> customerList = readCustomers(mvcResult);

        // assertions
        assertEquals(customerList.size(), 5);
        assertEquals(customerList.get(4).getId(), 5);
        assertEquals(customerList.get(4).getFirstName(), "Paul");
        assertEquals(customerList.get(4).getSurName(), "Byrne");
    }*/

    private List<CustomerInfo> readCustomers(MvcResult mvcResult) throws java.io.IOException {
        return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<CustomerInfo>>() {
        });
    }
}
