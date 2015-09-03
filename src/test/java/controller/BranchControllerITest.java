package controller;

import com.bank.db.FlywayTest;
import com.bank.dto.BranchInfo;
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

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by eniakel on 02/09/2015.
 */
 @RunWith(SpringJUnit4ClassRunner.class)
 @SpringApplicationConfiguration(classes = {Application.class})
 @WebAppConfiguration
 @FlywayTest(locations = {"db/migration"}, invokeClean = true)
 public class BranchControllerITest {
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
        final String BRANCH_NAME = "Carlow";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/branch"))
                .andExpect(status().isOk())
                .andReturn();

        List<BranchInfo> branchList = readBranches(mvcResult).stream()
                .sorted(Comparator.comparing(BranchInfo::getId))
                .collect(toList());

        // assertions
        assertEquals(branchList.size(), 3);
        assertEquals(branchList.get(0).getId(), 1);
        assertEquals(branchList.get(0).getBranchName(), BRANCH_NAME);
    }

    @Test
    public void createBranch() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/branch"))
                .andExpect(status().isOk())
                .andReturn();

        List<BranchInfo> branchList = readBranches(mvcResult);
        assertThat(branchList.size(), is(3));

        // preconditions
        BranchInfo branch = createBranchInfo();

        // execution
        String json = gson.toJson(branch);
        mvcResult = mockMvc.perform(post("/api/branch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        // assertions
        BranchInfo created = readBranch(mvcResult);
        assertNotNull(created.getId());
        assertEquals(created.getId(), 4);
        assertEquals(created.getBranchName(), "Naas");

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/branch"))
                .andExpect(status().isOk())
                .andReturn();

        branchList = readBranches(mvcResult);
        assertThat(branchList.size(), is(4));
    }

    @Test
    public void deleteBranch() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/branch"))
                .andExpect(status().isOk())
                .andReturn();

        List<BranchInfo> branchList = readBranches(mvcResult);
        assertThat(branchList.size(), is(3));

        mvcResult = mockMvc
                .perform(delete("/api/branch/{branchId}", "3"))
                .andExpect(status().isOk())
                .andReturn();

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/branch"))
                .andExpect(status().isOk())
                .andReturn();

        branchList = readBranches(mvcResult);

        List<BranchInfo> branches = readBranches(mvcResult);
        assertThat(branchList.size(), is(2));
    }


    private List<BranchInfo> readBranches(MvcResult mvcResult) throws java.io.IOException {
        return om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<BranchInfo>>() {
        });
    }

    private BranchInfo readBranch(MvcResult mvcResult) throws java.io.IOException {
        return om.readValue(mvcResult.getResponse().getContentAsString(), BranchInfo.class);
    }

    private BranchInfo createBranchInfo() {
        BranchInfo branch = new BranchInfo();
        branch.setBranchName("Naas");
        return branch;
    }
}
