package app.web.utb.validator;

import java.util.Optional;
import java.util.regex.Pattern;

public class CommonValidators {
    public static void validateMinLength(String text, int minLength, String fieldName) {
        validateNotNull(text, fieldName);
        Optional.ofNullable(fieldName)
                .map(name -> name.substring(0, 1).toUpperCase() + name.substring(1))
                .orElseThrow(() -> new IllegalArgumentException("Nazwa pola nie może być pusta"));

        if (text.length() < minLength) {
            throwException(String.format("%s musi mieć przynajmniej %d znaków", fieldName, minLength));
        }
    }

    public static void validateEmail(String email) {
        validateNotNull(email, "email");
        String emailRegexPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        boolean correctEmailAddress = Pattern.compile(emailRegexPattern)
                .matcher(email)
                .matches();

        if (!correctEmailAddress)
            throwException(String.format("%s nie jest poprawnym adresem email", email));
    }


    public static <T> void validateNotNull(T value, String fieldName) {
        if (value == null)
            throwException(String.format("Pole %s nie może być nullem", fieldName));
    }

    public static void validateMaxLength(String text, int maxLength, String fieldName) {
        validateNotNull(text, fieldName);
        Optional.ofNullable(fieldName)
                .map(name -> name.substring(0, 1).toUpperCase() + name.substring(1))
                .orElseThrow(() -> new IllegalArgumentException("Nazwa pola nie może być pusta"));

        if (text.length() > maxLength) {
            throwException(String.format("%s nie może mieć wiecej jak %d znaków", fieldName, maxLength));
        }
    }

    private static void throwException(String msg) {
        throw new IllegalArgumentException(msg);
    }


    public static <T extends Number> void validatePositive(T number, String fieldName) {
        if (number.longValue() < 0)
            throwException(String.format("%s nie może być mniejsze od zera", fieldName));

    }
}
