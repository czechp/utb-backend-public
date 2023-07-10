package app.web.utb.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CommonValidatorsTest {
    @Test
    @DisplayName("Text length validation - ok")
    void validateLengthTest() {
        //given
        String text = "Ala ma kota";
        int minLength = 3;
        String fieldName = "Some field name";
        //when
        CommonValidators.validateMinLength(text, minLength, fieldName);
        //then
    }


    @Test
    @DisplayName("Text length validation - text to short")
    void validateLengthTestTextToShort() {
        //given
        String text = "Al";
        int minLength = 3;
        String fieldName = "Some field name";
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            CommonValidators.validateMinLength(text, minLength, fieldName);
        });
    }

    @Test
    @DisplayName("Email validation - ok")
    void validateEmailTest() {
        //given
        String email = "webcoderc@gmail.com";
        //when
        CommonValidators.validateEmail(email);
        //then
    }

    @Test
    @DisplayName("Email validation - not correct format")
    void validateEmailTestNotCorrectFormat() {
        //given
        String email = "webcodercgmail.com";
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> CommonValidators.validateEmail(email));
    }

    @Test
    @DisplayName("CommondValidators.validateMaxLength() - ok")
    void validateMaxLengthTest() {
        //given
        String text = "abcdefghij";
        int maxLength = 10;
        String fieldName = "Some field";
        //when
        CommonValidators.validateMaxLength(text, maxLength, fieldName);
        //then
    }

    @Test
    @DisplayName("CommondValidators.validateMaxLength() - nok")
    void validateMaxLengthToLengthTest() {
        //given
        String text = "abcdefghijk";
        int maxLength = 10;
        String fieldName = "Some field";
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> CommonValidators.validateMaxLength(text, maxLength, fieldName));
    }
}