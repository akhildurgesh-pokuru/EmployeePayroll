
package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayroll.exception.EmployeePayrollException;
import com.bridgelabz.employeepayroll.model.EmployeePayrollData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeePayrollServiceTest {

    private EmployeePayrollService service;

    @BeforeEach
    void setUp() {
        service = new EmployeePayrollService();
    }

    @Test
    void testCreateEmployee() {
        EmployeePayrollData employee =
                service.createEmployeePayrollData(
                        new EmployeePayrollDTO("Lisa", 2000));

        assertEquals(1, employee.getEmployeeId());
        assertEquals("Lisa", employee.getName());
    }

    @Test
    void testGetExistingEmployee() {
        EmployeePayrollData created =
                service.createEmployeePayrollData(
                        new EmployeePayrollDTO("Mark", 5000));

        EmployeePayrollData found =
                service.getEmployeePayrollDataById(
                        created.getEmployeeId());

        assertEquals("Mark", found.getName());
    }

    @Test
    void testGetNonExistingEmployee() {
        EmployeePayrollException exception = assertThrows(
                EmployeePayrollException.class,
                () -> service.getEmployeePayrollDataById(999)
        );

        assertEquals(
                "Employee not found with ID: 999",
                exception.getMessage()
        );
    }

    @Test
    void testGetAllEmployees() {
        service.createEmployeePayrollData(
                new EmployeePayrollDTO("Lisa", 2000));
        service.createEmployeePayrollData(
                new EmployeePayrollDTO("Mark", 5000));

        assertEquals(
                2,
                service.getAllEmployeePayrollData().size()
        );
    }

    @Test
    void testUpdateExistingEmployee() {
        EmployeePayrollData created =
                service.createEmployeePayrollData(
                        new EmployeePayrollDTO("Lisa", 2000));

        EmployeePayrollData updated =
                service.updateEmployeePayrollData(
                        created.getEmployeeId(),
                        new EmployeePayrollDTO("Mark", 5000));

        assertEquals("Mark", updated.getName());
        assertEquals(5000, updated.getSalary());
    }

    @Test
    void testUpdateNonExistingEmployee() {
        assertThrows(
                EmployeePayrollException.class,
                () -> service.updateEmployeePayrollData(
                        999,
                        new EmployeePayrollDTO("Mark", 5000))
        );
    }

    @Test
    void testDeleteExistingEmployee() {
        EmployeePayrollData created =
                service.createEmployeePayrollData(
                        new EmployeePayrollDTO("Lisa", 2000));

        service.deleteEmployeePayrollData(
                created.getEmployeeId());

        assertTrue(service.getAllEmployeePayrollData().isEmpty());
    }

    @Test
    void testDeleteNonExistingEmployee() {
        assertThrows(
                EmployeePayrollException.class,
                () -> service.deleteEmployeePayrollData(999)
        );
    }
}
