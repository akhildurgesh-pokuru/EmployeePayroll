
package com.bridgelabz.employeepayroll.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePayrollData {

    private int employeeId;
    private String name;
    private long salary;
}
