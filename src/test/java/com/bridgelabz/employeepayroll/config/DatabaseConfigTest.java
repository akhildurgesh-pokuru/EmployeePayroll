
package com.bridgelabz.employeepayroll.config;

import org.junit.jupiter.api.Test;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;

import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConfigTest {

    private Properties loadProperties() throws IOException {
        Properties props = new Properties();

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            assertNotNull(input, "application.properties not found");
            props.load(input);
        }

        return props;
    }

    private PropertySourcesPropertyResolver createResolver(
            Map<String, Object> environment
    ) throws IOException {

        MutablePropertySources sources =
                new MutablePropertySources();

        sources.addLast(
                new MapPropertySource("testEnvironment", environment)
        );

        sources.addLast(
                new PropertiesPropertySource(
                        "applicationProperties",
                        loadProperties()
                )
        );

        return new PropertySourcesPropertyResolver(sources);
    }

    @Test
    void testDatabaseDriver() throws IOException {
        Properties props = loadProperties();

        assertEquals(
                "com.mysql.cj.jdbc.Driver",
                props.getProperty("spring.datasource.driver-class-name")
        );
    }

    @Test
    void testDatabaseUrlUsesEnvironmentVariables()
            throws IOException {

        Properties props = loadProperties();

        String url = props.getProperty(
                "spring.datasource.url"
        );

        assertTrue(url.contains("${MYSQL_HOST:localhost}"));
        assertTrue(url.contains("${MYSQL_PORT:3306}"));
        assertTrue(url.contains("${MYSQL_DATABASE:payroll_db}"));
    }

    @Test
    void testDefaultDatabaseConnection() throws IOException {

        PropertySourcesPropertyResolver resolver =
                createResolver(new HashMap<>());

        assertEquals(
                "jdbc:mysql://localhost:3306/payroll_db",
                resolver.getProperty("spring.datasource.url")
        );

        assertEquals(
                "payrolluser",
                resolver.getProperty("spring.datasource.username")
        );
    }

    @Test
    void testCustomDatabaseEnvironmentVariables()
            throws IOException {

        Map<String, Object> env = new HashMap<>();

        env.put("MYSQL_HOST", "db.example.internal");
        env.put("MYSQL_PORT", "3307");
        env.put("MYSQL_DATABASE", "custom_payroll");
        env.put("PAYROLL_DB_USERNAME", "custom_user");

        PropertySourcesPropertyResolver resolver =
                createResolver(env);

        assertEquals(
                "jdbc:mysql://db.example.internal:3307/custom_payroll",
                resolver.getProperty("spring.datasource.url")
        );

        assertEquals(
                "custom_user",
                resolver.getProperty("spring.datasource.username")
        );
    }

    @Test
    void testDatabasePasswordUsesEnvironmentVariable()
            throws IOException {

        Properties props = loadProperties();

        assertEquals(
                "${PAYROLL_DB_PASSWORD}",
                props.getProperty("spring.datasource.password")
        );
    }

    @Test
    void testDatabasePasswordResolution() throws IOException {

        Map<String, Object> env = Map.of(
                "PAYROLL_DB_PASSWORD", "test-secret-only"
        );

        PropertySourcesPropertyResolver resolver =
                createResolver(env);

        assertEquals(
                "test-secret-only",
                resolver.getProperty("spring.datasource.password")
        );
    }

    @Test
    void testJpaConfiguration() throws IOException {

        Properties props = loadProperties();

        assertEquals(
                "update",
                props.getProperty("spring.jpa.hibernate.ddl-auto")
        );
    }
}
