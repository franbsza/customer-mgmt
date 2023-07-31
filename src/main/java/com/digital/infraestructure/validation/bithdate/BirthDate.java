package com.digital.infraestructure.validation.bithdate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy= BirthDateValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDate {
    String message() default "Invalid birthdate format. Please digit using this format: dd/MM/YYYY ";
    Class[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
