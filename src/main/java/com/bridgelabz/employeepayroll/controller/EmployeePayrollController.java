
package com.bridgelabz.employeepayroll.controller;

import com.bridgelabz.employeepayroll.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayroll.dto.ResponseDTO;
import com.bridgelabz.employeepayroll.model.EmployeePayrollData;
import com.bridgelabz.employeepayroll.service.EmployeePayrollService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employeepayrollservice")
public class EmployeePayrollController {

    private final EmployeePayrollService employeeService;

    public EmployeePayrollController(
            EmployeePayrollService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createEmployeePayrollData(
            @Valid @RequestBody EmployeePayrollDTO employeeDTO) {

        EmployeePayrollData employee =
                employeeService.createEmployeePayrollData(employeeDTO);

        ResponseDTO response = new ResponseDTO(
                "Created Employee Payroll Data Successfully",
                employee
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<ResponseDTO> updateEmployeePayrollData(
            @PathVariable int employeeId,
            @Valid @RequestBody EmployeePayrollDTO employeeDTO) {

        EmployeePayrollData employee =
                employeeService.updateEmployeePayrollData(
                        employeeId, employeeDTO
                );

        ResponseDTO response = new ResponseDTO(
                "Updated Employee Payroll Data Successfully",
                employee
        );

        return ResponseEntity.ok(response);
    }
}
