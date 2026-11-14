package com.bridgelabz.employeepayroll.config.exception;

import com.bridgelabz.employeepayroll.controller.EmployeePayrollController;
import com.bridgelabz.employeepayroll.exception.EmployeePayrollExceptionHandler;
import com.bridgelabz.employeepayroll.model.EmployeePayrollData;
import com.bridgelabz.employeepayroll.service.EmployeePayrollService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class EmployeePayrollExceptionHandlerTest {

    private MockMvc mockMvc;
    private LocalValidatorFactoryBean validator;

    @Mock
    private EmployeePayrollService employeeService;

    @BeforeEach
    void setUp() {
        EmployeePayrollController controller =
                new EmployeePayrollController(employeeService);

        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = standaloneSetup(controller)
                .setValidator(validator)
                .setControllerAdvice(
                        new EmployeePayrollExceptionHandler()
                )
                .build();
    }

    @Test
    void testInvalidNameReturnsFriendlyError() throws Exception {
        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"ma\",\"salary\":2000}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Validation failed"))
                .andExpect(jsonPath("$.data.name")
                        .value("Employee name Invalid"));

        verifyNoInteractions(employeeService);
    }

    @Test
    void testInvalidSalaryReturnsFriendlyError() throws Exception {
        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lisa\",\"salary\":300}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.salary")
                        .value("Min Wage should be more than or equal to 500"));

        verifyNoInteractions(employeeService);
    }

    @Test
    void testMultipleValidationErrors() throws Exception {
        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"ma\",\"salary\":100}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Validation failed"))
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.salary").exists());

        verifyNoInteractions(employeeService);
    }

    @Test
    void testUpdateValidationError() throws Exception {
        mockMvc.perform(put("/employeepayrollservice/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"mark\",\"salary\":300}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.name").exists())
                .andExpect(jsonPath("$.data.salary").exists());

        verifyNoInteractions(employeeService);
    }

    @Test
    void testValidEmployeeDoesNotTriggerHandler() throws Exception {
        when(employeeService.createEmployeePayrollData(any()))
                .thenReturn(new EmployeePayrollData(1, "Lisa", 2000));

        mockMvc.perform(post("/employeepayrollservice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lisa\",\"salary\":2000}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Lisa"));

        verify(employeeService).createEmployeePayrollData(any());
    }
}

