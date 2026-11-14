package com.bridgelabz.employeepayroll.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeePayrollDTOTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        EmployeePayrollDTO employee = new EmployeePayrollDTO();

        employee.setName("Lisa");
        employee.setSalary(2000);

        assertEquals("Lisa", employee.getName());
        assertEquals(2000, employee.getSalary());
    }

    @Test
    void testAllArgsConstructor() {
        EmployeePayrollDTO employee =
                new EmployeePayrollDTO("Mark", 5000);

        assertEquals("Mark", employee.getName());
        assertEquals(5000, employee.getSalary());
    }

    @Test
    void testEqualsAndHashCode() {
        EmployeePayrollDTO employee1 =
                new EmployeePayrollDTO("Lisa", 2000);

        EmployeePayrollDTO employee2 =
                new EmployeePayrollDTO("Lisa", 2000);

        assertEquals(employee1, employee2);
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testToString() {
        EmployeePayrollDTO employee =
                new EmployeePayrollDTO("Lisa", 2000);

        assertTrue(employee.toString().contains("Lisa"));
        assertTrue(employee.toString().contains("2000"));
    }
}
