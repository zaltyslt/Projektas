package lt.techin.schedule.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface EmailValid {

    String message() default "{Email is invalid}";

    int nameMaximumLength() default 35;

    int addressMaximumLength() default 35;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
