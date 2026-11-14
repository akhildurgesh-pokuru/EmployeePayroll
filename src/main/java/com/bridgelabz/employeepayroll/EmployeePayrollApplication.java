
package com.bridgelabz.employeepayroll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EmployeePayrollApplication {


    public static void main(String[] args) {
        SpringApplication.run(
                EmployeePayrollApplication.class, args
        );
        logStartup();
    }

    static void logStartup() {
        log.info("Employee Payroll App Started");
    }

}
