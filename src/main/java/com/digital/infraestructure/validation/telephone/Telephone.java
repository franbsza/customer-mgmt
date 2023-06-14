package com.digital.infraestructure.validation.telephone;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy= TelephoneValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Telephone {
    String message() default "Invalid telephone format. Please digit using this format: (99) 99999-9999";
    Class[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
