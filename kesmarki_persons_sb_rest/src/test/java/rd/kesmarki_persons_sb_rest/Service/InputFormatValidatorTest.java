package rd.kesmarki_persons_sb_rest.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputFormatValidatorTest {

    private InputFormatValidator validator;

    @BeforeEach
    void beforeEach() {
        validator = new InputFormatValidator();
    }

    @Test
    void testValidateAddressIds() {
        
        assertTrue(validator.validateAddressIds(1, null));
        assertTrue(validator.validateAddressIds(1, 5));
        assertTrue(validator.validateAddressIds(53, null));
        assertTrue(validator.validateAddressIds(1, 0));
        assertTrue(validator.validateAddressIds(-93, -70));

        assertFalse(validator.validateAddressIds(-16, -16));
        assertFalse(validator.validateAddressIds(2, 2));
        assertFalse(validator.validateAddressIds(122, 122));
        assertFalse(validator.validateAddressIds(0, 0));
    }

    @Test
    void testValidateCountryCode() {

        assertTrue(validator.validateCountryCode("HU"));
        assertTrue(validator.validateCountryCode("IN"));
        assertTrue(validator.validateCountryCode("US"));
        assertTrue(validator.validateCountryCode("JP"));

        assertFalse(validator.validateCountryCode("HUN"));
        assertFalse(validator.validateCountryCode("CJ"));
        assertFalse(validator.validateCountryCode("X"));
        assertFalse(validator.validateCountryCode(""));
        assertFalse(validator.validateCountryCode("hu"));
        assertFalse(validator.validateCountryCode("de"));
        assertFalse(validator.validateCountryCode("Germany"));
    }

    @Test
    void testValidateEmailAddressFormat() {

        assertTrue(validator.validateEmailAddressFormat("david@email.com"));
        assertTrue(validator.validateEmailAddressFormat("user75_job@email.com"));
        assertTrue(validator.validateEmailAddressFormat("david@email.co.my"));
        assertTrue(validator.validateEmailAddressFormat("127.0.0.1@localhost"));

        assertFalse(validator.validateEmailAddressFormat("myaddressemail.com"));
        assertFalse(validator.validateEmailAddressFormat("my@address@email.com"));
        assertFalse(validator.validateEmailAddressFormat("éva@freemail.hu"));
    }

    @Test
    void testValidateIdCardNumberFormat() {

        assertTrue(validator.validateIdCardNumberFormat("205630ZA"));
        assertTrue(validator.validateIdCardNumberFormat("119993GF"));
        assertTrue(validator.validateIdCardNumberFormat("122222CC"));

        assertFalse(validator.validateIdCardNumberFormat("1122222CC"));
        assertFalse(validator.validateIdCardNumberFormat("11223344CC"));
        assertFalse(validator.validateIdCardNumberFormat("122222CCT"));
        assertFalse(validator.validateIdCardNumberFormat("AB2222CC"));
        assertFalse(validator.validateIdCardNumberFormat("A1ZH22CC"));
        assertFalse(validator.validateIdCardNumberFormat("XYZ"));
        assertFalse(validator.validateIdCardNumberFormat(""));
        assertFalse(validator.validateIdCardNumberFormat("10"));
        assertFalse(validator.validateIdCardNumberFormat("1122334455"));
        assertFalse(validator.validateIdCardNumberFormat("233340G9"));
    }

    @Test
    void testValidatePersonNameFormat() {

        assertTrue(validator.validatePersonNameFormat("Kis-Kovács Géza"));
        assertTrue(validator.validatePersonNameFormat("Nagy Vivien"));
        assertTrue(validator.validatePersonNameFormat("Dr. Szabó Ilona"));
        assertTrue(validator.validatePersonNameFormat("Tóth D. András"));

        assertFalse(validator.validatePersonNameFormat("nemecsek ernő"));
        assertFalse(validator.validatePersonNameFormat("Minta_Péter"));
        assertFalse(validator.validatePersonNameFormat("Teszt elek"));
        assertFalse(validator.validatePersonNameFormat("Szalaijános"));
        assertFalse(validator.validatePersonNameFormat("Bíró D@ni"));
    }

    @Test
    void testValidatePhoneNumberFormat() {

        assertTrue(validator.validatePhoneNumberFormat("06-30/555-2020"));
        assertTrue(validator.validatePhoneNumberFormat("+36-30/555-2020"));
        assertTrue(validator.validatePhoneNumberFormat("06 30/555 2020"));
        assertTrue(validator.validatePhoneNumberFormat("+3630/5552020"));
        assertTrue(validator.validatePhoneNumberFormat("+36305552020"));
        assertTrue(validator.validatePhoneNumberFormat("06305552020"));
        assertTrue(validator.validatePhoneNumberFormat("+36/1/7008230"));
        assertTrue(validator.validatePhoneNumberFormat("+36/1/7008230/10"));
        assertTrue(validator.validatePhoneNumberFormat("+36-20/14-3-55-02"));

        assertFalse(validator.validatePhoneNumberFormat("GHJKL"));
        assertFalse(validator.validatePhoneNumberFormat(""));
        assertFalse(validator.validatePhoneNumberFormat("HU1080/558899"));
        assertFalse(validator.validatePhoneNumberFormat("0630  5552020"));
        assertFalse(validator.validatePhoneNumberFormat("06 30 555 2020 délután"));
        assertFalse(validator.validatePhoneNumberFormat("+36--1/7008230"));
        assertFalse(validator.validatePhoneNumberFormat("+36-/1/7008230"));
    }
}
