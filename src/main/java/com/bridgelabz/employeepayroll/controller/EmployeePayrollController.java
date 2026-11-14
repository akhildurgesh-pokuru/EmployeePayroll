
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
            @Valid @RequestBody EmployeePayrollDTO dto) {

        EmployeePayrollData employee =
                employeeService.createEmployeePayrollData(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(
                        "Created Employee Payroll Data Successfully",
                        employee
                ));
    }

    @GetMapping("/get/{employeeId}")
    public ResponseEntity<ResponseDTO> getEmployeePayrollData(
            @PathVariable int employeeId) {

        EmployeePayrollData employee =
                employeeService.getEmployeePayrollDataById(employeeId);

        return ResponseEntity.ok(new ResponseDTO(
                "Employee Payroll Data Retrieved Successfully",
                employee
        ));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO> getAllEmployeePayrollData() {
        return ResponseEntity.ok(new ResponseDTO(
                "All Employee Payroll Data Retrieved Successfully",
                employeeService.getAllEmployeePayrollData()
        ));
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<ResponseDTO> updateEmployeePayrollData(
            @PathVariable int employeeId,
            @Valid @RequestBody EmployeePayrollDTO dto) {

        EmployeePayrollData employee =
                employeeService.updateEmployeePayrollData(
                        employeeId, dto
                );

        return ResponseEntity.ok(new ResponseDTO(
                "Updated Employee Payroll Data Successfully",
                employee
        ));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<ResponseDTO> deleteEmployeePayrollData(
            @PathVariable int employeeId) {

        EmployeePayrollData employee =
                employeeService.deleteEmployeePayrollData(employeeId);

        return ResponseEntity.ok(new ResponseDTO(
                "Deleted Employee Payroll Data Successfully",
                employee
        ));
    }
}
