package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void testValidFlightNumber() {
        assertTrue(ValidationUtils.isValidFlightNumber("AB123"));
        assertFalse(ValidationUtils.isValidFlightNumber("123"));
        assertFalse(ValidationUtils.isValidFlightNumber("A123456"));
    }

    @Test
    void testValidTimeFormat() {
        assertTrue(ValidationUtils.isValidTimeFormat("10:00 AM"));
        assertTrue(ValidationUtils.isValidTimeFormat("22:30"));
        assertFalse(ValidationUtils.isValidTimeFormat("25:00"));
    }

    @Test
    void testValidLocation() {
        assertTrue(ValidationUtils.isValidLocation("New York"));
        assertFalse(ValidationUtils.isValidLocation("12345"));
        assertFalse(ValidationUtils.isValidLocation("This location name is way too long to be valid because it exceeds fifty characters"));
    }
}
