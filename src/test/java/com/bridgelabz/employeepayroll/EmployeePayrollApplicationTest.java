
package com.bridgelabz.employeepayroll;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class EmployeePayrollApplicationTest {

    @Test
    void testSlf4jLoggerExists() {
        Logger logger = (Logger) LoggerFactory.getLogger(
                EmployeePayrollApplication.class
        );

        assertNotNull(logger);
        assertEquals(
                EmployeePayrollApplication.class.getName(),
                logger.getName()
        );
    }

    @Test
    void testStartupLogMessage() {
        Logger logger = (Logger) LoggerFactory.getLogger(
                EmployeePayrollApplication.class
        );

        ListAppender<ILoggingEvent> appender =
                new ListAppender<>();

        appender.start();
        logger.addAppender(appender);

        try {
            EmployeePayrollApplication.logStartup();

            assertTrue(
                    appender.list.stream().anyMatch(event ->
                            event.getLevel() == Level.INFO &&
                                    event.getFormattedMessage().equals(
                                            "Employee Payroll App Started"
                                    )
                    )
            );
        } finally {
            logger.detachAppender(appender);
            appender.stop();
        }
    }
}
