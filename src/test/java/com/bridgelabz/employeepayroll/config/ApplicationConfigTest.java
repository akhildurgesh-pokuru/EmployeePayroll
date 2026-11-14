
package com.bridgelabz.employeepayroll.config;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationConfigTest {

    private Properties loadProperties(String fileName)
            throws IOException {

        Properties properties = new Properties();

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            assertNotNull(input,
                    "Configuration file not found: " + fileName);

            properties.load(input);
        }

        return properties;
    }

    @Test
    void testDefaultProfile() throws IOException {
        Properties properties =
                loadProperties("application.properties");

        assertEquals(
                "${SPRING_PROFILES_ACTIVE:dev}",
                properties.getProperty("spring.profiles.active")
        );
    }

    @Test
    void testDevelopmentEnvironment() throws IOException {
        Properties properties =
                loadProperties("application-dev.properties");

        assertEquals(
                "DEV",
                properties.getProperty("environment")
        );
    }

    @Test
    void testProductionEnvironment() throws IOException {
        Properties properties =
                loadProperties("application-prod.properties");

        assertEquals(
                "PROD",
                properties.getProperty("environment")
        );
    }

    @Test
    void testDevelopmentLoggingLevel() throws IOException {
        Properties properties =
                loadProperties("application-dev.properties");

        assertEquals(
                "DEBUG",
                properties.getProperty(
                        "logging.level.com.bridgelabz.employeepayroll"
                )
        );
    }

    @Test
    void testProductionLoggingLevel() throws IOException {
        Properties properties =
                loadProperties("application-prod.properties");

        assertEquals(
                "INFO",
                properties.getProperty(
                        "logging.level.com.bridgelabz.employeepayroll"
                )
        );
    }

    @Test
    void testProductionFileLogging() throws IOException {
        Properties properties =
                loadProperties("application-prod.properties");

        assertEquals(
                "logs/employee-payroll.log",
                properties.getProperty("logging.file.name")
        );
    }

    @Test
    void testDevelopmentConsolePattern() throws IOException {
        Properties properties =
                loadProperties("application-dev.properties");

        assertTrue(
                properties.getProperty("logging.pattern.console")
                        .contains("%logger{36}")
        );
    }

    @Test
    void testProductionFilePattern() throws IOException {
        Properties properties =
                loadProperties("application-prod.properties");

        assertTrue(
                properties.getProperty("logging.pattern.file")
                        .contains("%logger{36}")
        );
    }

    @Test
    void testDevelopmentSqlLogging() throws IOException {
        Properties properties =
                loadProperties("application-dev.properties");

        assertEquals(
                "true",
                properties.getProperty("spring.jpa.show-sql")
        );
    }

    @Test
    void testProductionSqlLogging() throws IOException {
        Properties properties =
                loadProperties("application-prod.properties");

        assertEquals(
                "false",
                properties.getProperty("spring.jpa.show-sql")
        );
    }
}
