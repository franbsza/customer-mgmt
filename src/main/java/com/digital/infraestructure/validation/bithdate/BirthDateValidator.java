package com.digital.infraestructure.validation.bithdate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BirthDateValidator implements ConstraintValidator<BirthDate, String> {
    private final Pattern birthdatePattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$");

    @Override
    public void initialize(BirthDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher matcherPhone = birthdatePattern.matcher(value);
        return matcherPhone.matches();
    }
}
