package controller;

import com.bank.db.FlywayTest;
import com.bank.dto.AddressInfo;
import com.bank.dto.CustomerInfo;
import com.bank.infrastructure.Application;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    Gson gson;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        om = new ObjectMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        gson = new Gson();
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

    @Test
    public void createCustomer() throws Exception {
        // preconditions
        CustomerInfo customer = createCustomerInfo();

        // execution
        String json = gson.toJson(customer);
        MvcResult mvcResult = mockMvc.perform(post("/api/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        // assertions
        CustomerInfo created = readCustomer(mvcResult);
        assertNotNull(created.getId());
        assertEquals(created.getId(), 6);
        assertEquals(created.getFirstName(), "Bart");
        assertEquals(created.getSurName(), "Simpson");
        assertEquals(created.getAddress().getCity(), "Springfield");
    }

    private List<CustomerInfo> readCustomers(MvcResult mvcResult) throws java.io.IOException {
        return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<CustomerInfo>>() {
        });
    }

    private CustomerInfo readCustomer(MvcResult mvcResult) throws java.io.IOException {
        return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<CustomerInfo>() {
        });
    }

    private CustomerInfo createCustomerInfo() {
        CustomerInfo customer = new CustomerInfo();
        customer.setFirstName("Bart");
        customer.setSurName("Simpson");
        customer.setAddress(createAddress());
        customer.setAccountIds(getAccountIds());
        customer.setBranchId(1L);
        return customer;
    }

    private Set<Integer> getAccountIds() {
        Set accountIds = new HashSet<>();
        accountIds.add(1);
        accountIds.add(2);
        return accountIds;
    }

    private AddressInfo createAddress() {
        AddressInfo address = new AddressInfo();
        address.setHouseNumber("42");
        address.setStreetName("Evergreen Terrace");
        address.setCity("Springfield");
        address.setCountry("USA");
        address.setEirCode("12345");
        return address;
    }
}
