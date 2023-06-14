package com.digital.infraestructure.validation.telephone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> {
    private final Pattern telephonePattern = Pattern.compile("(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})");

    @Override
    public void initialize(Telephone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcherPhone = telephonePattern.matcher(value);
        return matcherPhone.matches();
    }
}
