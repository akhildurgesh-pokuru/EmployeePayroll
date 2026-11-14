
package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayroll.model.EmployeePayrollData;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmployeePayrollService {

    private final Map<Integer, EmployeePayrollData> employees =
            new ConcurrentHashMap<>();

    private final AtomicInteger nextId = new AtomicInteger(1);

    public EmployeePayrollData createEmployeePayrollData(
            EmployeePayrollDTO employeeDTO) {

        int id = nextId.getAndIncrement();

        EmployeePayrollData employee = new EmployeePayrollData(
                id,
                employeeDTO.getName(),
                employeeDTO.getSalary()
        );

        employees.put(id, employee);

        return employee;
    }

    public EmployeePayrollData updateEmployeePayrollData(
            int employeeId,
            EmployeePayrollDTO employeeDTO) {

        EmployeePayrollData updatedEmployee = new EmployeePayrollData(
                employeeId,
                employeeDTO.getName(),
                employeeDTO.getSalary()
        );

        EmployeePayrollData result = employees.computeIfPresent(
                employeeId,
                (id, existingEmployee) -> updatedEmployee
        );

        if (result == null) {
            throw new IllegalArgumentException(
                    "Employee not found with ID: " + employeeId
            );
        }

        return result;
    }
}
