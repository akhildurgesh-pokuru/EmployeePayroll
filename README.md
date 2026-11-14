# AddressBook

# Employee Payroll Management Application

## Project Overview

The **Employee Payroll Management Application** is a RESTful web application developed using Java and Spring Boot. It is designed to manage employee payroll information through REST APIs, including adding, retrieving, updating, and deleting employee records.

The project focuses on building a structured backend application by implementing Spring Boot features such as REST controllers, service layers, DTOs, Lombok, logging, application profiles, database configuration, input validation, and global exception handling.

The application follows a layered architecture to maintain clean, reusable, and maintainable code.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Backend programming language |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database persistence support |
| MySQL | Database configuration |
| Maven | Dependency management and build automation |
| Lombok | Automatic generation of boilerplate code |
| Jakarta Bean Validation | Employee data validation |
| SLF4J / Logback | Application logging |
| JUnit 5 | Unit testing |
| Mockito | Mocking dependencies during tests |
| MockMvc | REST controller testing |
| Git & GitHub | Version control and branch management |
| IntelliJ IDEA | Development environment |

## Project Features

- Create employee payroll records.
- Retrieve employee details by ID.
- Retrieve all employee records.
- Update existing employee information.
- Delete employee payroll records.
- Validate employee names and salaries.
- Generate user-friendly error messages.
- Handle employee-not-found exceptions.
- Configure development and production environments.
- Manage database settings through environment variables.
- Implement application logging using Lombok.
- Perform unit testing using JUnit and Mockito.

## Project Architecture

The application follows a layered architecture:

```text
Client / Postman
       |
       v
EmployeePayrollController
       |
       v
EmployeePayrollService
       |
       v
Employee Payroll Data
```

**Controller Layer:** Handles incoming HTTP requests and sends responses to clients.

**Service Layer:** Contains the business logic for managing employee records.

**DTO Layer:** Transfers employee information between the client and application.

**Model Layer:** Represents employee payroll data.

**Exception Layer:** Handles validation errors and employee-not-found exceptions.

**Database Configuration:** Provides MySQL connectivity through Spring Boot configuration. The current CRUD service uses in-memory storage, so records are not yet persisted in MySQL.

## Use Cases Implemented

### UC-1: Lombok Integration

- Integrated Lombok into the Spring Boot application.
- Used `@Data`, `@NoArgsConstructor`, and `@AllArgsConstructor`.
- Automatically generated getters, setters, and constructors.
- Created JUnit tests to verify DTO functionality.

### UC-2: Application Logging

- Implemented logging using Lombok's `@Slf4j` annotation.
- Logged application startup information.
- Configured logging levels and patterns.
- Created JUnit tests to verify logging functionality.

### UC-3: Application Environment Configuration

- Configured development and production environments using Spring Profiles.
- Created `application-dev.properties` and `application-prod.properties`.
- Configured environment-specific logging levels and patterns.
- Implemented tests for environment configurations.

### UC-4: Database Configuration

- Configured MySQL database connectivity.
- Used environment variables for database credentials.
- Avoided hardcoding database passwords.
- Created JUnit tests to verify database settings and environment variable resolution.

### UC-5: Employee Data Validation

- Implemented employee name validation using `@NotEmpty` and `@Pattern`.
- Implemented minimum salary validation using `@Min`.
- Added `@Valid` to Create and Update REST APIs.
- Created JUnit tests for valid and invalid employee data.

### UC-6: Global Exception Handling

- Implemented global validation exception handling using `@RestControllerAdvice`.
- Handled `MethodArgumentNotValidException`.
- Returned user-friendly validation errors.
- Created tests for validation error responses.

### UC-7: Employee Not Found Exception Handling

- Created a custom `EmployeePayrollException`.
- Implemented exception handling for invalid employee IDs.
- Returned HTTP `404 Not Found` responses.
- Added tests for retrieving, updating, and deleting nonexistent employees.

## REST API Endpoints

**Base URL:** `http://localhost:8080/employeepayrollservice`

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/create` | Create a new employee |
| GET | `/get/{employeeId}` | Retrieve employee by ID |
| GET | `/` | Retrieve all employees |
| PUT | `/update/{employeeId}` | Update employee details |
| DELETE | `/delete/{employeeId}` | Delete an employee |

### Sample Create Employee Request

**POST** `/employeepayrollservice/create`

```json
{
  "name": "Lisa",
  "salary": 2000
}
```

### Sample Success Response

```json
{
  "message": "Created Employee Payroll Data Successfully",
  "data": {
    "employeeId": 1,
    "name": "Lisa",
    "salary": 2000
  }
}
```

### Sample Validation Error

```json
{
  "message": "Validation failed",
  "data": {
    "name": "Employee name Invalid",
    "salary": "Min Wage should be more than or equal to 500"
  }
}
```

### Sample Employee Not Found Error

```json
{
  "message": "Employee not found",
  "data": "Employee not found with ID: 999"
}
```

## How to Run the Project

### Prerequisites

Ensure the following are installed:

- Java
