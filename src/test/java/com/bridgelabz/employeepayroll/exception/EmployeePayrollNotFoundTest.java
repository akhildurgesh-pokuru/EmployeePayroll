
package com.bridgelabz.employeepayroll.exception;

import com.bridgelabz.employeepayroll.controller.EmployeePayrollController;
import com.bridgelabz.employeepayroll.service.EmployeePayrollService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class EmployeePayrollNotFoundTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        EmployeePayrollService service =
                new EmployeePayrollService();

        EmployeePayrollController controller =
                new EmployeePayrollController(service);

        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(
                        new EmployeePayrollExceptionHandler()
                )
                .build();
    }

    @Test
    void testGetEmployeeNotFound() throws Exception {
        mockMvc.perform(get("/employeepayrollservice/get/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Employee not found"))
                .andExpect(jsonPath("$.data")
                        .value("Employee not found with ID: 999"));
    }

    @Test
    void testUpdateEmployeeNotFound() throws Exception {
        mockMvc.perform(put("/employeepayrollservice/update/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lisa\",\"salary\":2000}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Employee not found"));
    }

    @Test
    void testDeleteEmployeeNotFound() throws Exception {
        mockMvc.perform(delete("/employeepayrollservice/delete/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data")
                        .value("Employee not found with ID: 999"));
    }

    @Test
    void testGetExistingEmployee() throws Exception {
        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lisa\",\"salary\":2000}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/employeepayrollservice/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Lisa"));
    }

    @Test
    void testDeleteExistingEmployee() throws Exception {
        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mark\",\"salary\":5000}"))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/employeepayrollservice/delete/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employeepayrollservice/get/1"))
                .andExpect(status().isNotFound());
    }
}
