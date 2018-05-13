package com.example.employee.restfulapi;

import com.example.employee.restfulapi.controller.CompanyController;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestfulApiApplication.class)
@WebAppConfiguration
public class RestfulApiApplicationTests {
    private MockMvc mockMvc;
    @Autowired
    private CompanyController companyController;

    @Before
    public void setUp() throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:mysql://localhost:3306/employee_db2","root","123456");
        flyway.clean();
        flyway.migrate();
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
    }

    @Test
    public void testGetAllCompany() throws Exception {
        String contentAsString = mockMvc.perform(get("/companies")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        String result = "[{\"id\":1,\"companyName\":\"baidu\",\"employeesNumber\":1000,\"" +
                "employees\":[{\"id\":1,\"name\":\"baidu1\",\"age\":20,\"gender\":\"male\"," +
                "\"salary\":6000,\"companyId\":1},{\"id\":2,\"name\":\"baidu2\",\"" +
                "age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":1},{\"id\"" +
                ":3,\"name\":\"baidu3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"" +
                "companyId\":1}]},{\"id\":2,\"companyName\":\"alibaba\",\"employeesNumber\":200,\"" +
                "employees\":[{\"id\":4,\"name\":\"alibaba1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":2}," +
                "{\"id\":5,\"name\":\"alibaba2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":2},{\"id\":6,\"name\"" +
                ":\"alibaba3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":2}]},{\"id\":3,\"companyName\":\"ThoughtWorks\"" +
                ",\"employeesNumber\":3000,\"employees\":[{\"id\":7,\"name\":\"ThoughtWorks1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"" +
                "companyId\":3},{\"id\":8,\"name\":\"ThoughtWorks2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":3},{\"id\":9,\"" +
                "name\":\"ThoughtWorks3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":3}]},{\"id\":4,\"companyName\":\"tengxun\",\"" +
                "employeesNumber\":3000,\"employees\":[{\"id\":10,\"name\":\"tengxun1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"" +
                "companyId\":4},{\"id\":11,\"name\":\"tengxun2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":4}," +
                "{\"id\":12,\"name\":\"tengxun3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":4}]}," +
                "{\"id\":5,\"companyName\":\"huiwei\",\"employeesNumber\":3000,\"employees\":" +
                "[{\"id\":13,\"name\":\"huiwei1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":5}," +
                "{\"id\":14,\"name\":\"huiwei2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":5}," +
                "{\"id\":15,\"name\":\"huiwei3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":5}]}]";
        assertEquals(contentAsString, result);
    }

    @Test
    public void testGetCompaniesById() throws Exception {
        String result = "{\"id\":1,\"companyName\":\"baidu\",\"employeesNumber\":1000," +
                "\"employees\":[{\"id\":1,\"name\":\"baidu1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":1}," +
                "{\"id\":2,\"name\":\"baidu2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":1}," +
                "{\"id\":3,\"name\":\"baidu3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":1}]}";
        String contentAsString = mockMvc.perform(get("/companies/1")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.companyName").value("baidu"))
                .andExpect(jsonPath("$.employeesNumber").value(1000))
                .andReturn().getResponse().getContentAsString();
        assertEquals(contentAsString,result);
    }

    @Test
    public void testGetEmployeesByCompanyId() throws Exception {
        String result = "[{\"id\":1,\"name\":\"baidu1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":1}," +
                "{\"id\":2,\"name\":\"baidu2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":1}," +
                "{\"id\":3,\"name\":\"baidu3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":1}]";
        String contentAsString = mockMvc.perform(get("/companies/1/employees")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        assertEquals(contentAsString,result);
    }

    @Test
    public void testGetCompaniesByPageAndPageSize() throws Exception {
        String result = "[{\"id\":1,\"companyName\":\"baidu\",\"employeesNumber\":1000,\"" +
                "employees\":[{\"id\":1,\"name\":\"baidu1\",\"age\":20,\"gender\":\"male\"," +
                "\"salary\":6000,\"companyId\":1},{\"id\":2,\"name\":\"baidu2\",\"" +
                "age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":1},{\"id\"" +
                ":3,\"name\":\"baidu3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"" +
                "companyId\":1}]},{\"id\":2,\"companyName\":\"alibaba\",\"employeesNumber\":200,\"" +
                "employees\":[{\"id\":4,\"name\":\"alibaba1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":2}," +
                "{\"id\":5,\"name\":\"alibaba2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":2},{\"id\":6,\"name\"" +
                ":\"alibaba3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":2}]},{\"id\":3,\"companyName\":\"ThoughtWorks\"" +
                ",\"employeesNumber\":3000,\"employees\":[{\"id\":7,\"name\":\"ThoughtWorks1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"" +
                "companyId\":3},{\"id\":8,\"name\":\"ThoughtWorks2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":3},{\"id\":9,\"" +
                "name\":\"ThoughtWorks3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":3}]},{\"id\":4,\"companyName\":\"tengxun\",\"" +
                "employeesNumber\":3000,\"employees\":[{\"id\":10,\"name\":\"tengxun1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"" +
                "companyId\":4},{\"id\":11,\"name\":\"tengxun2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":4}," +
                "{\"id\":12,\"name\":\"tengxun3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":4}]}," +
                "{\"id\":5,\"companyName\":\"huiwei\",\"employeesNumber\":3000,\"employees\":" +
                "[{\"id\":13,\"name\":\"huiwei1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":5}," +
                "{\"id\":14,\"name\":\"huiwei2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":5}," +
                "{\"id\":15,\"name\":\"huiwei3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":5}]}]";
        String contentAsString = mockMvc.perform(get("/companies/page/1/pageSize/5")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        assertEquals(contentAsString,result);
    }

    @Test
    public void testAddCompany() throws Exception {
        String result = "{\"id\":6,\"companyName\":\"company1\"," +
                "\"employeesNumber\":200,\"employees\":null}";
        String contentAsString = mockMvc.perform(post("/companies")
                .param("companyName", "company1")
                .param("employeesNumber", "200").
                contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        assertEquals(contentAsString,result);
    }

    @Test
    public void testUpdateCompany() throws Exception {
        String result = "{\"id\":1,\"companyName\":\"baidu1\"," +
                "\"employeesNumber\":1000,\"employees\":null}";
        String contentAsString = mockMvc.perform(put("/companies/1")
                .param("companyName", "baidu1")
                .param("employeesNumber", "1000").
                        contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        assertEquals(contentAsString, result);
    }

    @Test
    public void testDeleteCompany() throws Exception {
        String contentAsString = mockMvc.perform(delete("/companies/1"))
                .andReturn().getResponse().getContentAsString();
        assertEquals(contentAsString,"true");
    }


}
