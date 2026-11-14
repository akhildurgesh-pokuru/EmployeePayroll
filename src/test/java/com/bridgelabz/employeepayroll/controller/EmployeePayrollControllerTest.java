
package com.bridgelabz.employeepayroll.controller;

import com.bridgelabz.employeepayroll.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayroll.model.EmployeePayrollData;
import com.bridgelabz.employeepayroll.service.EmployeePayrollService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@ExtendWith(MockitoExtension.class)
class EmployeePayrollControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeePayrollService employeeService;

    @InjectMocks
    private EmployeePayrollController controller;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator =
                new LocalValidatorFactoryBean();

        validator.afterPropertiesSet();

        mockMvc = standaloneSetup(controller)
                .setValidator(validator)
                .build();
    }

    // Test 1: Create employee with valid details
    @Test
    void testCreateEmployeeWithValidData() throws Exception {

        when(employeeService.createEmployeePayrollData(any()))
                .thenReturn(new EmployeePayrollData(1, "Lisa", 2000));

        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lisa\",\"salary\":2000}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Lisa"))
                .andExpect(jsonPath("$.data.salary").value(2000));

        verify(employeeService).createEmployeePayrollData(any());
    }

    // Test 2: Empty name
    @Test
    void testCreateEmployeeWithEmptyName() throws Exception {

        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"salary\":2000}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(employeeService);
    }

    // Test 3: Lowercase first letter
    @Test
    void testCreateEmployeeWithLowercaseName() throws Exception {

        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"lisa\",\"salary\":2000}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(employeeService);
    }

    // Test 4: Name shorter than 3 characters
    @Test
    void testCreateEmployeeWithShortName() throws Exception {

        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ma\",\"salary\":2000}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(employeeService);
    }

    // Test 5: Salary less than 500
    @Test
    void testCreateEmployeeWithInvalidSalary() throws Exception {

        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lisa\",\"salary\":300}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(employeeService);
    }

    // Test 6: Update employee with valid details
    @Test
    void testUpdateEmployeeWithValidData() throws Exception {

        when(employeeService.updateEmployeePayrollData(
                eq(1), any(EmployeePayrollDTO.class)))
                .thenReturn(new EmployeePayrollData(1, "Mark", 5000));

        mockMvc.perform(put("/employeepayrollservice/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mark\",\"salary\":5000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Mark"))
                .andExpect(jsonPath("$.data.salary").value(5000));

        verify(employeeService).updateEmployeePayrollData(
                eq(1), any(EmployeePayrollDTO.class));
    }

    // Test 7: Update with invalid name
    @Test
    void testUpdateEmployeeWithInvalidName() throws Exception {

        mockMvc.perform(put("/employeepayrollservice/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"ma\",\"salary\":2000}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(employeeService);
    }

    // Test 8: Update with invalid salary
    @Test
    void testUpdateEmployeeWithInvalidSalary() throws Exception {

        mockMvc.perform(put("/employeepayrollservice/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mark\",\"salary\":100}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(employeeService);
    }

    // Test 9: Minimum valid salary boundary
    @Test
    void testCreateEmployeeWithMinimumSalary() throws Exception {

        when(employeeService.createEmployeePayrollData(any()))
                .thenReturn(new EmployeePayrollData(2, "Lisa", 500));

        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lisa\",\"salary\":500}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.salary").value(500));

        verify(employeeService).createEmployeePayrollData(any());
    }
}
