
package com.bridgelabz.employeepayroll.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePayrollDTO {

    @NotEmpty(message = "Employee name cannot be empty")
    @Pattern(
            regexp = "^[A-Z][a-zA-Z\\s]{2,}$",
            message = "Employee name Invalid"
    )
    private String name;

    @Min(
            value = 500,
            message = "Min Wage should be more than or equal to 500"
    )
    private long salary;
}
