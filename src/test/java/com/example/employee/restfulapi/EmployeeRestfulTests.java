package com.example.employee.restfulapi;

import com.example.employee.restfulapi.controller.CompanyController;
import com.example.employee.restfulapi.controller.EmployeeController;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @Author Joker
 * @Description
 * @Date Create in 下午9:31 2018/5/9
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestfulApiApplication.class)
@WebAppConfiguration
public class EmployeeRestfulTests {
    private MockMvc mockMvc;
    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setUp() throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:mysql://localhost:3306/employee_db2","root","123456");
        flyway.clean();
        flyway.migrate();
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testGetEmployees() throws Exception {
        String contentAsString = mockMvc.perform(get("/employees")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        String result = "[{\"id\":1,\"name\":\"baidu1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":1}," +
                "{\"id\":2,\"name\":\"baidu2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":1}," +
                "{\"id\":3,\"name\":\"baidu3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":1}," +
                "{\"id\":4,\"name\":\"alibaba1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":2}," +
                "{\"id\":5,\"name\":\"alibaba2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":2}," +
                "{\"id\":6,\"name\":\"alibaba3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":2}," +
                "{\"id\":7,\"name\":\"ThoughtWorks1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":3}," +
                "{\"id\":8,\"name\":\"ThoughtWorks2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":3}," +
                "{\"id\":9,\"name\":\"ThoughtWorks3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":3}," +
                "{\"id\":10,\"name\":\"tengxun1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":4}," +
                "{\"id\":11,\"name\":\"tengxun2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":4}," +
                "{\"id\":12,\"name\":\"tengxun3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":4}," +
                "{\"id\":13,\"name\":\"huiwei1\",\"age\":20,\"gender\":\"male\",\"salary\":6000,\"companyId\":5}," +
                "{\"id\":14,\"name\":\"huiwei2\",\"age\":19,\"gender\":\"female\",\"salary\":7000,\"companyId\":5}," +
                "{\"id\":15,\"name\":\"huiwei3\",\"age\":19,\"gender\":\"male\",\"salary\":8000,\"companyId\":5}]";
        assertEquals(contentAsString, result);
    }


}
