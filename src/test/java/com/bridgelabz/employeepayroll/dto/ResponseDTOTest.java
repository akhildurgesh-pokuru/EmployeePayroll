package com.bridgelabz.employeepayroll.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseDTOTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        ResponseDTO response = new ResponseDTO();

        response.setMessage("Success");
        response.setData("Employee Data");

        assertEquals("Success", response.getMessage());
        assertEquals("Employee Data", response.getData());
    }

    @Test
    void testAllArgsConstructor() {
        ResponseDTO response =
                new ResponseDTO("Employee Created", 101);

        assertEquals("Employee Created", response.getMessage());
        assertEquals(101, response.getData());
    }

    @Test
    void testEqualsAndHashCode() {
        ResponseDTO response1 = new ResponseDTO("Success", 101);
        ResponseDTO response2 = new ResponseDTO("Success", 101);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testToString() {
        ResponseDTO response =
                new ResponseDTO("Success", "Employee Data");

        assertTrue(response.toString().contains("Success"));
        assertTrue(response.toString().contains("Employee Data"));
    }
}
