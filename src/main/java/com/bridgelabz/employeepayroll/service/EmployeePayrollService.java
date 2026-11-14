
package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayroll.exception.EmployeePayrollException;
import com.bridgelabz.employeepayroll.model.EmployeePayrollData;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmployeePayrollService {

    private final Map<Integer, EmployeePayrollData> employees =
            new ConcurrentHashMap<>();

    private final AtomicInteger nextId = new AtomicInteger(1);

    public EmployeePayrollData createEmployeePayrollData(
            EmployeePayrollDTO dto) {

        int id = nextId.getAndIncrement();

        EmployeePayrollData employee =
                new EmployeePayrollData(
                        id,
                        dto.getName(),
                        dto.getSalary()
                );

        employees.put(id, employee);
        return employee;
    }

    public EmployeePayrollData getEmployeePayrollDataById(
            int employeeId) {

        EmployeePayrollData employee = employees.get(employeeId);

        if (employee == null) {
            throw new EmployeePayrollException(
                    "Employee not found with ID: " + employeeId
            );
        }

        return employee;
    }

    public List<EmployeePayrollData> getAllEmployeePayrollData() {
        return new ArrayList<>(employees.values());
    }

    public EmployeePayrollData updateEmployeePayrollData(
            int employeeId,
            EmployeePayrollDTO dto) {

        EmployeePayrollData employee =
                getEmployeePayrollDataById(employeeId);

        employee.setName(dto.getName());
        employee.setSalary(dto.getSalary());

        return employee;
    }

    public EmployeePayrollData deleteEmployeePayrollData(
            int employeeId) {

        EmployeePayrollData removed = employees.remove(employeeId);

        if (removed == null) {
            throw new EmployeePayrollException(
                    "Employee not found with ID: " + employeeId
            );
        }

        return removed;
    }
}
