
package com.bridgelabz.employeepayroll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class EmployeePayrollApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context =
                SpringApplication.run(
                        EmployeePayrollApplication.class, args
                );

        logStartup();

        String environment = context.getEnvironment()
                .getProperty("environment", "UNKNOWN");

        log.info(
                "Employee Payroll App Running in {} Environment",
                environment
        );

        String dbUsername = context.getEnvironment()
                .getProperty("spring.datasource.username");

        log.info(
                "Employee Payroll Database Username: {}",
                dbUsername
        );
    }

    static void logStartup() {
        log.info("Employee Payroll App Started");
    }
}
