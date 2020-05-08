package nl.rabobank.aggregationservice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountUtilTest {

    @Test
    void extractAccountNumber() {
        assertEquals("123456789", AccountUtil.extractAccountNumber("NL23RABO123456789"));
    }
}