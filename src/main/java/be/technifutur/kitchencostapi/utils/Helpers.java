package be.technifutur.kitchencostapi.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Helpers {

    public static BigDecimal round(BigDecimal value) {

        if (value == null) {
            return null;
        }

        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static <T> Map<String, List<String>> validate(Validator validator, T toValidate) {

        Set<ConstraintViolation<T>> violations = validator.validate(toValidate);
        if (!violations.isEmpty()) {
            List<String> errors = violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .toList();

            return Map.of("errors", errors);
        }

        return Map.of();
    }
}
